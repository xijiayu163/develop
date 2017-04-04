package org.apache.tomcat.util.net;

import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;


public class SocketWrapper {
	private Socket socket = null;
	private final Lock blockingStatusReadLock;
    private final WriteLock blockingStatusWriteLock;

	public SocketWrapper(Socket socket) {
		this.socket  = socket;
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.blockingStatusReadLock = lock.readLock();
        this.blockingStatusWriteLock =lock.writeLock();
	}

	public Socket getSocket() {
		return this.socket;
	}
}
