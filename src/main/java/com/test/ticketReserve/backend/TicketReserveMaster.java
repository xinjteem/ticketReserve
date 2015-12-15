package com.test.ticketReserve.backend;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TicketReserveMaster {
	
	private Map<String,Integer> backingMap = new ConcurrentHashMap<String,Integer>();
	private static TicketReserveMaster instance;
	
	public static final String RESULT_SUCCESS = "SUCCESS";
	public static final String RESULT_FAILURE = "FAILURE";
	
	private TicketReserveMaster(){
		
	}
	
	public static TicketReserveMaster getInstance(){
		synchronized(TicketReserveMaster.class){
			if(instance == null){
				instance = new TicketReserveMaster();
			}
		}
		return instance;
	}
	
	public void reserveTickets(Map<String,Integer> ticketsToReserve){
		if(ticketsToReserve != null && !ticketsToReserve.isEmpty()){
			ticketsToReserve.putAll(ticketsToReserve);
		}		
	}
	
	public boolean checkReserved(Map<String,Integer> ticketsToCheck){
		boolean held = false;
		boolean released = false;
		Set<String> keys = ticketsToCheck.keySet();
		for(String key: keys){
			if(backingMap.get(key) != null){
				held = true;
			}
			else{
				released = true;
			}			
		}
		if(held && !released){
			return held;
		}
		else{
			return released;
		}		
	}
	
	public Integer getValue(String key){
		return backingMap.get(key);
	}

}
