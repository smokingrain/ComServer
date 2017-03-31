package com.xk.server.test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test {

	private ReentrantReadWriteLock lock;
	
	public int price = 0;
	
	public static void main(String[] args) {
		final Test test = new Test();
		for(int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					test.add();
				}
			}).start();
		}
		for(int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					test.get();
				}
			}).start();
		}

	}
	
	public Test() {
		lock = new ReentrantReadWriteLock();
		
	}
	
	public void add() {
		lock.writeLock().lock();
		
		price++;
		System.out.println("add" + price);
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock.writeLock().unlock();
		
	}
	
	public int get(){
		lock.readLock().lock();
		System.out.println("getprice");
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock.readLock().unlock();
		return price;
	}

}
