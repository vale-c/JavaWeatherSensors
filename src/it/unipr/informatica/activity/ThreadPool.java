/*
 * it.unipr.informatica.concurrent.ThreadPool
 *
 * (c) 2019 Federico Bergenti. All rights reserved.
 */
package it.unipr.informatica.activity;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
	private Thread[] pool;
	
	private List<Task> queue;
	
	public ThreadPool(int size) {
		if(size <= 0)
			throw new IllegalArgumentException();
		
		queue = new LinkedList<>();
		
		pool = new Thread[10];
		
		for(int i = 0; i < size; i++) {
			pool[i] = new Worker();
			
			pool[i].start();
		}
	}
	
	public synchronized void stop() {
		add(new StopTask());
	}
	
	public synchronized void add(Task task) {
		queue.add(task);
		
		notifyAll();
	}
	
	private class Worker extends Thread {
		@Override
		public void run() {
			for(;;) {
				try {
					Task task;
					
					Object semaphore = ThreadPool.this;
				
					synchronized(semaphore) {
						while(queue.isEmpty())
							semaphore.wait();
						
						task = queue.get(0);
						
						if(task instanceof StopTask)
							return;
						
						queue.remove(0);
					}
				
					task.run();
				} catch(InterruptedException e) {
					// Blank
				} catch(Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	
	private class StopTask implements Task {
		@Override
		public void run() {
			// Blank
		}
	}
}
