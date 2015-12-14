package com.test.ticketReserve.utils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExpiringMap<K,V> {
	
	private final ConcurrentHashMap<K,V> backingMap = new ConcurrentHashMap<K,V>();
	private long expirationTime;
	private static final long DEFAULT_EXPIRATION_TIME = 1000;
	
	private final ScheduledExecutorService cleanupService = Executors
			.newScheduledThreadPool(1);
	
	public ExpiringMap(){
		this.expirationTime = DEFAULT_EXPIRATION_TIME;
	}
	
	public ExpiringMap(long expirationTime) {
		super();
		this.expirationTime = expirationTime;		
	}	
	
	public void putAll(final Map<K,V> inputMap){
		backingMap.putAll(inputMap);
		cleanupService.schedule(new Runnable(){

			public void run() {	
				Set<K> keys = (Set<K>) inputMap.entrySet();
				for(K key: keys){
				backingMap.remove(key);
				}
			}
		
		}, expirationTime, TimeUnit.MILLISECONDS);
	}
	
	public V put(final K key, V value){
		V returnValue = backingMap.get(key);
		backingMap.put(key,value);
		cleanupService.schedule(new Runnable(){

			public void run() {				
				backingMap.remove(key);
			}
		
		}, expirationTime, TimeUnit.MILLISECONDS);
		
		return returnValue;
	}
	
	public V get(K key){
		return backingMap.get(key);		
	}
	
	public V remove(K key){
		return backingMap.remove(key);
	}

	public long getExpirationTime() {
		return expirationTime;
	}
	
	public Set<K> keySet(){
		return backingMap.keySet();
	}
	
}
