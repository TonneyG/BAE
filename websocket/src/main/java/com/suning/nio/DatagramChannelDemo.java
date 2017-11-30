package com.suning.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.junit.Test;

public class DatagramChannelDemo {
	@Test
	public void testReceive(){
		try {
			//打开的DatagramChannel可以在UDP端口8080上接收数据包
			DatagramChannel channel = DatagramChannel.open();
			channel.socket().bind(new InetSocketAddress(8080));
			//receive()方法会将接收到的数据包内容复制到指定的Buffer。如果Buffer容不下收到的数据,多出的数据将被丢弃。
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			channel.receive(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSend(){
		try {
			DatagramChannel channel = DatagramChannel.open();
			String data = "New String to write to file..."+System.currentTimeMillis();
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			buf.put(data.getBytes());
			buf.flip();
			//发送数据
			channel.send(buf, new InetSocketAddress("localhost", 8080));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void fakeReceiveAndSend(){
		//由于UDP是无连接的，连接到特定地址并不会像TCP通道那样创建一个真正的连接，而是锁住DatagramChannel，让其只能从特定地址收发数据。
		try {
			DatagramChannel channel = DatagramChannel.open();
			channel.connect(new InetSocketAddress("localhost", 8080));
			ByteBuffer buffer = ByteBuffer.allocate(100);
			channel.read(buffer);
			
			buffer.put("hello DatagramChannel".getBytes());
			channel.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
