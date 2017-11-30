package com.suning.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

import org.junit.Test;

public class PipeDemo {
	@Test
	public void testWrite(){
		try {
			Pipe pipe = Pipe.open();
			Pipe.SinkChannel sinkChannel = pipe.sink();
			String newData = "New String to write to file..." + System.currentTimeMillis();
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			buf.put(newData.getBytes());
			buf.flip();
			while(buf.hasRemaining()){
				sinkChannel.write(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRead(){
		try {
			Pipe pipe = Pipe.open();
			Pipe.SourceChannel sourceChannel = pipe.source();
			ByteBuffer buf = ByteBuffer.allocate(48);
			int byteRead = sourceChannel.read(buf);
			while(byteRead != -1){
				buf.flip();
				if(buf.hasRemaining()){
					System.out.println((char)buf.get());
				}
				buf.clear();
				byteRead = sourceChannel.read(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
