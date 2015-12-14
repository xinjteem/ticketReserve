package com.test.ticketReserve.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import com.test.ticketReserve.SeatHold;
import com.test.ticketReserve.TicketService;
import com.test.ticketReserve.backend.TicketHoldMaster;
import com.test.ticketReserve.backend.TicketMaster;
import com.test.ticketReserve.backend.TicketReserveMaster;

public class TicketServiceImpl implements TicketService{
	
	private TicketMaster ticketMaster = TicketMaster.getInstance();
	private TicketHoldMaster holdMaster = TicketHoldMaster.getInstance();
	private TicketReserveMaster reserveMaster = TicketReserveMaster.getInstance();
	private Random random = new Random();

	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		// TODO Auto-generated method stub
		Integer ticketLevel = venueLevel.get();
		if(ticketLevel != null){
			return (ticketMaster.getSeatsByLevel(ticketLevel).size()
					- holdMaster.getSeatsHeldByLevel(ticketLevel));
		}
		return 0;
	}

	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
			Optional<Integer> maxLevel, String customerEmail) {
		
		Integer minLevelToSearch = null;
		Integer maxLevelToSearch = null;
		
		try{
			minLevelToSearch = minLevel.get();
			maxLevelToSearch = maxLevel.get();
		}catch(NoSuchElementException e){
			//can be ignored
		}
		
		//if there is a minLeveltoSearch we always search that.
		if(minLevelToSearch != null){			
			List<String> ticketList = ticketMaster.getSeatsByLevel(minLevelToSearch);
			return lookupAndHoldTickets(ticketList,minLevelToSearch,numSeats, customerEmail);
		}
		else if(maxLevelToSearch != null){
			List<String> ticketList = ticketMaster.getSeatsByLevel(maxLevelToSearch);
			return lookupAndHoldTickets(ticketList,maxLevelToSearch,numSeats, customerEmail);
		}
		return null;
	}

	private SeatHold lookupAndHoldTickets(List<String> ticketList, int level,
			int numSeats, String customerEmail) {
		//utility method to hold seats
		String[] ticketArray = (String[]) ticketList.toArray();
		Map<String, Integer> ticketsToHold = new HashMap<String, Integer>();
		for (String ticket : ticketArray) {
			if (holdMaster.checkIfHeld(ticket) == true) {
				ticketList.remove(ticket);
			} else {
				ticketsToHold.put(ticket, level);
				if (ticketsToHold.size() == numSeats) {
					break;
				}
			}
		}
		int holdId = random.nextInt();
		while(holdMaster.checkHold(holdId)!=null){
			holdId = random.nextInt();
		}		
		
		SeatHold hold = new SeatHold(ticketsToHold, holdId,
				holdMaster.getHoldDuration(), customerEmail);
		hold.setSeatingLevel(level);
		holdMaster.addHold(holdId, hold);
		return hold;
	}

	public String reserveSeats(int seatHoldId, String customerEmail) {
		// TODO Auto-generated method stub
		SeatHold hold = holdMaster.checkHold(seatHoldId);
		if(hold != null){
			Map<String,Integer> seatsHeld = hold.getSeatsHeld();
			if(seatsHeld != null && !seatsHeld.isEmpty()){
				Set<String> tickets = seatsHeld.keySet();
				int seatingLevel = hold.getSeatingLevel();				
				ticketMaster.bookTickets(seatingLevel,tickets);							
				reserveMaster.reserveTickets(seatsHeld);
				holdMaster.removeHold(seatHoldId);
				return TicketReserveMaster.RESULT_SUCCESS;
			}
		}
		
		return TicketReserveMaster.RESULT_FAILURE;		
	}

}
