package com.yu.bytechunk;

import java.io.IOException;

public final class ByteChunk {

    public static interface ByteInputChannel{
             public int realReadBytes(byte cbuf[], int off, int len) throws IOException;
    }

    public static interface ByteOutputChannel{
             public void realWriteBytes(byte cbuf[], int off, int len) throws IOException;
    }

    private byte[] buff;
    private int start = 0;
    private int end;
    private int limit = -1;
    private ByteInputChannel in = null;
    private ByteOutputChannel out = null;

    public ByteChunk() {

    }



    public void allocate(int initial, int limit) {
             if (buff == null || buff.length< initial) {
                      buff = new byte[initial];
             }
             this.limit = limit;
             start = 0;
             end = 0;
    }

    public void setByteInputChannel(ByteInputChannel in) {
             this.in = in;
    }

    public void setByteOutputChannel(ByteOutputChannel out) {
             this.out = out;
    }

    public void append(byte b) throws IOException {
             makeSpace(1);
             if (limit > 0 && end>= limit) {
                      flushBuffer();
             }
             buff[end++]= b;
    }

    public void flushBuffer() throws IOException {
             out.realWriteBytes(buff, start,end - start);
             end = start;
    }

    private void makeSpace(int count) {
             byte[] tmp = null;
             int newSize= buff.length * 2;
             if (limit > 0 &&newSize > limit) {
                      newSize = limit;
             }
             tmp = new byte[newSize];
             System.arraycopy(buff, start,tmp, 0, end - start);
             buff = tmp;
             tmp = null;
             end = end - start;
             start = 0;
    }
}
