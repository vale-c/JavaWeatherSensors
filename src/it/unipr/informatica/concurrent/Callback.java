/*
 * it.unipr.informatica.concurrent.Callback
 *
 * (c) 2019 Federico Bergenti. All rights reserved.
 */
package it.unipr.informatica.concurrent;

public interface Callback<T> {
	public void onResult(T result);
	
	public void onFailure(Throwable throwable);
}
