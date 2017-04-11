package com.yu.InternalInputBuffer;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InternalInputBuffer {
	private InputStream inputStream;
	private byte[] buf = new byte[8*1024];
	private int lastValid;
	private int pos;
	private int end;
	private boolean parsingHeader=false;
	
	
	
	public void init(InputStream inputStream){
		this.inputStream = inputStream;
	}
	
	private boolean fill() throws IOException{
		int nRead = 0;
		
        if (parsingHeader) {
            if (lastValid == buf.length) {
                throw new IllegalArgumentException("iib.requestheadertoolarge.error");
            }
            nRead = inputStream.read(buf, pos, buf.length - lastValid);
            if (nRead > 0) {
                lastValid = pos + nRead;
            }else{
            	System.out.println("满了");
            }
        } else {
            if (buf.length - end < 4500) {
                // In this case, the request header was really large, so we allocate a
                // brand new one; the old one will get GCed when subsequent requests
                // clear all references
                buf = new byte[buf.length];
                end = 0;
            }
            pos = end;
            lastValid = pos;
            nRead = inputStream.read(buf, pos, buf.length - lastValid);
            if (nRead > 0) {
                lastValid = pos + nRead;
            }else{
            	System.out.println("满了");
            }
        }

        return (nRead > 0);
	}
	
	public boolean parseRequestLine() throws IOException{
        int start = 0;
        byte chr = 0;
        boolean space = false;
        while (!space) {
            if (pos >= lastValid)
                fill();
            if (buf[pos] == (byte) ' ') {
                space = true;
                printBuf(start,pos,"method");
            }
            pos++;
        }
 
        while (space) {
            if (pos >= lastValid)
                fill();
            if (buf[pos] == (byte) ' ') {
                pos++;
            } else {
                space = false;
            }
        }
 
       start = pos;
        while (!space) {
            if (pos >= lastValid)
                fill();
            if (buf[pos] == (byte) ' ') {
                space = true;
                printBuf(start,pos,"uri");
            }
            pos++;
        }
       return true;
    }
	
    public boolean parseHeaders()
        throws IOException {
//        if (!parsingHeader) {
//            throw new IllegalStateException("iib.parseheaders.ise.error");
//        }

        while (parseHeader()) {
            // Loop until we run out of headers
        }

        parsingHeader = false;
        end = pos;
        return true;
    }
    
    private boolean parseHeader() throws IOException {
            //
            // Check for blank line
            //
            byte chr = 0;
            while (true) {
                // Read new bytes if needed
                if (pos >= lastValid) {
                    if (!fill())
                        throw new EOFException("iib.eof.error");
                }
                chr = buf[pos];

                if (chr == (byte) '\r') {
                    // Skip
                } else if (chr == (byte) '\n') {
                    pos++;
                    return false;
                } else {
                    break;
                }
                pos++;
            }

            // Mark the current buffer position
            int start = pos;

            //
            // Reading the header name
            // Header name is always US-ASCII
            //

            boolean colon = false;

            while (!colon) {

                // Read new bytes if needed
                if (pos >= lastValid) {
                    if (!fill())
                        throw new EOFException("iib.eof.error");
                }

                if (buf[pos] == (byte) ':') {
                    colon = true;
                    printBuf(start, pos, " ");
//                    headerValue = headers.addValue(buf, start, pos - start);
                }

                chr = buf[pos];
                if ((chr >= (byte) 'A') && (chr <= (byte) 'Z')) {
                    buf[pos] = (byte) (chr - ('A' - 'a'));
                }

                pos++;

            }

            // Mark the current buffer position
            start = pos;
            int realPos = pos;

            //
            // Reading the header value (which can be spanned over multiple lines)
            //

            boolean eol = false;
            boolean validLine = true;

            while (validLine) {

                boolean space = true;

                // Skipping spaces
                while (space) {

                    // Read new bytes if needed
                    if (pos >= lastValid) {
                        if (!fill())
                            throw new EOFException("iib.eof.error");
                    }

                    if ((buf[pos] == (byte) ' ') || (buf[pos] == (byte) '\t')) {
                        pos++;
                    } else {
                        space = false;
                    }

                }

                int lastSignificantChar = realPos;

                // Reading bytes until the end of the line
                while (!eol) {

                    // Read new bytes if needed
                    if (pos >= lastValid) {
                        if (!fill())
                            throw new EOFException("iib.eof.error");
                    }

                    if (buf[pos] == (byte) '\r') {
                        // Skip
                    } else if (buf[pos] == (byte) '\n') {
                        eol = true;
                    } else if (buf[pos] == (byte) ' ') {
                        buf[realPos] = buf[pos];
                        realPos++;
                    } else {
                        buf[realPos] = buf[pos];
                        realPos++;
                        lastSignificantChar = realPos;
                    }

                    pos++;

                }

                realPos = lastSignificantChar;

                // Checking the first character of the new line. If the character
                // is a LWS, then it's a multiline header

                // Read new bytes if needed
                if (pos >= lastValid) {
                    if (!fill())
                        throw new EOFException("iib.eof.error");
                }

                chr = buf[pos];
                if ((chr != (byte) ' ') && (chr != (byte) '\t')) {
                    validLine = false;
                } else {
                    eol = false;
                    // Copying one extra space in the buffer (since there must
                    // be at least one space inserted between the lines)
                    buf[realPos] = chr;
                    realPos++;
                }

            }

            // Set the header value
            printBuf(start,realPos," ");
//            headerValue.setBytes(buf, start, realPos - start);

            return true;

        }
	
    private void printBuf(int start,int pos,String prefix){
    	byte[] methodB = new byte[pos -start];
        System.arraycopy(buf, start,methodB,0, pos - start);
        String method = new String(methodB);
        System.out.println(prefix+":"+method);
    }
    
	public static void main(String[] args) throws IOException{
		InternalInputBuffer internalInputBuffer = new InternalInputBuffer();
		FileInputStream fileInputStream = new FileInputStream("src/main/java/com/yu/InternalInputBuffer/hello.txt");
		internalInputBuffer.init(fileInputStream);
		internalInputBuffer.parseRequestLine();
		internalInputBuffer.parseHeaders();
		System.out.println("finish");
	}
}
