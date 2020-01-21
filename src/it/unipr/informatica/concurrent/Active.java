/*
 * it.unipr.informatica.concurrent.Active
 *
 * (c) 2019 Federico Bergenti. All rights reserved.
 */
package it.unipr.informatica.concurrent;

public abstract class Active {
	private ThreadPool pool;
	
	protected Active(int size) {
		pool = new ThreadPool(size);
	}
	
	protected void add(Task task) {
		pool.add(task);
	}
	
	protected void stop() {
		pool.stop();
	}
}
