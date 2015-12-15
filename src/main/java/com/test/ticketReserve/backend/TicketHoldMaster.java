package com.test.ticketReserve.backend;

import java.util.Set;

import com.test.ticketReserve.SeatHold;
import com.test.ticketReserve.utils.ExpiringMap;

public class TicketHoldMaster {
	
	private static TicketHoldMaster instance;
	int holdDuration = Integer.parseInt(System.getProperty("ticket.hold.duration", "5000"));
	private ExpiringMap<Integer,SeatHold> backingMap = new ExpiringMap<Integer,SeatHold>(holdDuration);
	
	private TicketHoldMaster(){
		
	}
	public static TicketHoldMaster getInstance(){
		if(instance==null){
			instance = new TicketHoldMaster();
		}
		return instance;
	}
	
	public void addHold(Integer holdId,SeatHold hold){
		if(holdId != null){
			backingMap.put(holdId, hold);
		}			
	}
	
	public SeatHold checkHold(Integer holdId){
		return backingMap.get(holdId);	
	}	
	
	public boolean checkIfHeld(String ticket){
		Set<Integer> keys = backingMap.keySet();
		boolean held = false;	
		for(Integer key: keys){
			if(backingMap.get(key).getSeatsHeld().get(ticket) != null){
				held = true;
			}
		}
		return held;
	}
	
	public long getHoldDuration(){
		return backingMap.getExpirationTime();
	}
	public void removeHold(int seatHoldId) {
		backingMap.remove(seatHoldId);		
	}
	public int getSeatsHeldByLevel(int level){
		Set<Integer> keys = backingMap.keySet();
		int seatsHeld = 0;
		for(Integer key: keys){
			SeatHold mapEntry = backingMap.get(key);
			if(mapEntry.getSeatingLevel()==level){
				seatsHeld += mapEntry.getSeatsHeld().size();
			}			
		}
		return seatsHeld;
	}
}
