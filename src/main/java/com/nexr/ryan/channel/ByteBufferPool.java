package com.nexr.ryan.channel;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class ByteBufferPool {
	static Logger log = Logger.getLogger(ByteBufferPool.class);

	private static final int MEMORY_BLOCKSIZE = 1024;
	private static final int FILE_BLOCKSIZE = 2048;

	private final ArrayList memoryQueue = new ArrayList();
	private final ArrayList fileQueue = new ArrayList();

	private boolean isWait = false;

	private ByteBufferPool(int memorySize, int fileSize, File file) {
		if (memorySize > 0) {
			initMemoryBuffer(memorySize);
		}

		if (fileSize > 0) {
			initFileBuffer(fileSize, file);
		}
	}

	private void initFileBuffer(int size, File f) {
		// TODO Auto-generated method stub
		int bufferCount = size / FILE_BLOCKSIZE;
		size = bufferCount * FILE_BLOCKSIZE;
		try {
			RandomAccessFile file = new RandomAccessFile(f, "rw");
			file.setLength(0);
			ByteBuffer fileBuffer = file.getChannel().map(
					FileChannel.MapMode.READ_WRITE, 0L, size);
			devideBuffer(fileBuffer, FILE_BLOCKSIZE, fileQueue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initMemoryBuffer(int size) {
		// TODO Auto-generated method stub
		int bufferCount = size / MEMORY_BLOCKSIZE;
		size = bufferCount * MEMORY_BLOCKSIZE;
		ByteBuffer directBuf = ByteBuffer.allocateDirect(size);
		devideBuffer(directBuf, MEMORY_BLOCKSIZE, memoryQueue);

	}

	private void devideBuffer(ByteBuffer buf, int blocksize, ArrayList list) {
		// TODO Auto-generated method stub
		int bufferCount = buf.capacity() / blocksize;
		int position = 0;
		for (int i = 0; i < bufferCount; i++) {
			int max = position + blocksize;
			buf.limit(max);
			list.add(buf.slice());
			position = max;
			buf.position(position);
		}
	}

	public ByteBuffer getMemoryBuffer() {
		return getBuffer(memoryQueue, fileQueue);
	}

	public ByteBuffer getFilebuffer() {
		return getBuffer(fileQueue, memoryQueue);
	}

	private ByteBuffer getBuffer(ArrayList firstQueue, ArrayList secondQueue) {
		// TODO Auto-generated method stub
		ByteBuffer buffer = getBuffer(firstQueue, false);
		if (buffer == null) {
			buffer = getBuffer(secondQueue, false);
			if (buffer == null) {
				if (isWait) {
					buffer = getBuffer(firstQueue, true);
				} else {
					buffer = ByteBuffer.allocate(MEMORY_BLOCKSIZE);
				}

			}
		}
		return buffer;
	}

	private ByteBuffer getBuffer(ArrayList queue, boolean wait) {
		// TODO Auto-generated method stub
		synchronized (queue) {
			if (queue.isEmpty()) {
				if (wait) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						return null;
					}
				} else {
					return null;
				}
			}
			return (ByteBuffer) queue.remove(0);

		}
	}

	public void putBuffer(ByteBuffer buffer) {
		if (buffer.isDirect()) {
			switch (buffer.capacity()) {
			case MEMORY_BLOCKSIZE:
				putBuffer(buffer, memoryQueue);
				break;
			case FILE_BLOCKSIZE:
				putBuffer(buffer, fileQueue);
				break;
			}
		}
	}

	private void putBuffer(ByteBuffer buffer, ArrayList queue) {
		// TODO Auto-generated method stub
		buffer.clear();
		synchronized (queue) {
			queue.add(buffer);
			queue.notify();
		}
	}

	public synchronized void setWait(boolean wait) {
		this.isWait = wait;
	}
	
	public synchronized boolean getWait() {
		return this.isWait;
	}
}
