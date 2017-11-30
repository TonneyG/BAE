package com.suning.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

public class ChannelDemo {
	@Test
	public void readBufferByFileChannel(){
		RandomAccessFile file = null;
		FileChannel inChannel = null;
		try {
			file = new RandomAccessFile("/nio-data.txt","rw");
			inChannel = file.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.put(new String("abcdefghijklmnopqrstuvwxyz").getBytes());
			int bytesRead = inChannel.read(buf);
			while(bytesRead != -1){
				System.out.println("Read " + bytesRead);
				buf.flip();
				while(buf.hasRemaining()){
					System.out.print((char)buf.get());
				}
				buf.clear();
				bytesRead = inChannel.read(buf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				inChannel.close();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void channelToChannel(){
		try {
			RandomAccessFile fromFile = new RandomAccessFile("/fromFile.txt","rw");
			FileChannel fromChannel = fromFile.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(20);
			buffer.put(new String("hello world").getBytes());
			//buffer.flip();
			fromChannel.write(buffer);
			RandomAccessFile toFile = new RandomAccessFile("/toFile.txt","rw");
			FileChannel toChannel = toFile.getChannel();
			long position = 0;
			long count = fromChannel.size();
			toChannel.transferFrom(fromChannel, position, count);
			//fromChannel.transferTo(position, count,toChannel);
			System.out.println(toChannel.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectKeys(){
		try {
			//创建selector
			Selector selector = Selector.open();
			SocketChannel ssc = SocketChannel.open();
			ssc.configureBlocking(false);
			SelectionKey key = ssc.register(selector, SelectionKey.OP_READ);
			Channel channel = key.channel();
			System.out.println(SelectionKey.OP_READ);//1
			System.out.println(SelectionKey.OP_WRITE);//4
			System.out.println(SelectionKey.OP_CONNECT);//8
			System.out.println(SelectionKey.OP_ACCEPT);//16
			System.out.println(key.interestOps());
			System.out.println(key.isReadable());
			System.out.println(key.isWritable());
			System.out.println(key.isConnectable());
			System.out.println(key.isAcceptable());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSocketChannel(){
		SocketChannel socketChannel;
		try {
			socketChannel = SocketChannel.open();
			socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));
			while(!socketChannel.finishConnect()){
				//wait ,or do something
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//监听多个端口情况
	@Test
	public void testServerSocketChannel(){
		int port = 80;
		try {
			//创建selector
			Selector selector = Selector.open();
			ServerSocketChannel ssc = ServerSocketChannel.open();
			//将ServerSocketChannel设置为非阻塞
			ssc.configureBlocking(false);
			//绑定到指定端口
			ServerSocket ss = ssc.socket();
			InetSocketAddress address = new InetSocketAddress(port);
			ss.bind(address);
			//将ServerSocketChannel注册到Selector上
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			
			//内部循环
			int num = selector.select();
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = selectedKeys.iterator();
			while(it.hasNext()){
				SelectionKey key = it.next();
				if(key.isAcceptable()){
					System.out.println("Acceptable");
				}
				if(key.isConnectable()){
					System.out.println("Connectable");
				}
				if(key.isReadable()){
					System.out.println("Readable");
				}
				if(key.isWritable()){
					System.out.println("Writable");
				}
				it.remove();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
