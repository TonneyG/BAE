package com.suning.nio;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class MultiChannel {
	public static void main(String[] args) throws Exception {
		Thread1 t1 = new Thread1();
		t1.start();
		Thread.sleep(1000);
		//t1.selector.wakeup();
	}
}

class Thread1 extends Thread{
	Selector selector = null;
	@Override
	public void run() {
		try {
			selector = Selector.open();
			SocketChannel ssc = SocketChannel.open();
			ssc.configureBlocking(false);
			SelectionKey key = ssc.register(selector, SelectionKey.OP_READ);
			Selector selector = key.selector();
			selector.wakeup();
			System.out.println(selector.select());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
