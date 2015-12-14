package com.test.ticketReserve.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TicketMaster {
	

	private static TicketMaster instance;
	private int seatingLevels = SeatingLevel.getSeatingLevels().size();
	private int seatDepth = SeatingLevel.SEAT_DEPTH;
	private String seatMap [][] = new String[seatingLevels][seatDepth+2];
	private final ScheduledExecutorService cleanupService = Executors
			.newScheduledThreadPool(1);
	
	private TicketMaster(){
		System.out.println("Initializing TicketMaster");
		/*for (int i = 0; i<seatingLevels; i++){
			seatMap[i][0] = SeatingLevel.getSeatingLevels().get(i).toString();
		}*/
	}
	
	public static TicketMaster getInstance(){
		if (instance == null){
			instance = new TicketMaster();
		}
		return instance;
	}
	
	public void updateMaster(Integer seatingLevel, List<String> seats){
		synchronized(seatMap){
			for(int i = 0; i< seats.size(); i++){			
				seatMap[seatingLevel][i] = seats.get(i);
			}
		}
		/*we schedule the cleanupService to remove null values from the ticket
		master array*/
		cleanupService.schedule(new Runnable(){

			public void run() {	
				instance.trimMaster();
			}
		
		}, 10000, TimeUnit.MILLISECONDS);
	}
	
	public void printMaster(){
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i< seatingLevels; i++){
			for(int j = 0; j<seatMap[i].length;j++){
				buffer.append(seatMap[i][j]);
				buffer.append(" | ");
			}
			buffer.append("\n\r");
		}
		System.out.println(buffer.toString());
	}
	
	public final void trimMaster(){
		synchronized(seatMap){
			for(int i=0; i < seatMap.length; i++){
				ArrayList<String> list = new ArrayList<String>(); // creates a list to store the elements != null
				for(int j = 0; j < seatMap[i].length; j++){
					if(seatMap[i][j] != null){
						list.add(seatMap[i][j]); // elements != null will be added to the list.
					}
				}
				seatMap[i] = list.toArray(new String[list.size()]); // all elements from list to an array.
			}
		}
	}
	
	public List<String> getSeatsByLevel(int seatingLevel){
		if(seatingLevel <= seatingLevels){
			return Arrays.asList(seatMap[seatingLevel]);
		}
		return null;		
	}

	public void bookTickets(int seatingLevel, Set<String> tickets) {
		synchronized(seatMap){
			List<Integer> ticketList = new ArrayList<Integer>();
			Arrays.sort(seatMap[seatingLevel]);
			for(String ticket:tickets){
				int ticketLocation = Arrays.binarySearch(seatMap[seatingLevel], ticket);
				ticketList.add(ticketLocation);
			}
			for(Integer location: ticketList){
				seatMap[seatingLevel][location]=null;
			}
			trimMaster();
			//printMaster();
		}
	}

}
