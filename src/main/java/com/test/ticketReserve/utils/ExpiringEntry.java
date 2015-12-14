package com.test.ticketReserve.utils;

public class ExpiringEntry<T> {
	
	private T entry;
	private long createdDate;
	
	public ExpiringEntry(T entry, long createdDate) {
		super();
		this.entry = entry;
		this.createdDate = createdDate;
	}

}
