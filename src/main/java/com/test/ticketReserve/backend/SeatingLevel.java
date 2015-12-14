package com.test.ticketReserve.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatingLevel {
	public static final Integer ORCH = 0;
	public static final Integer MAIN = 1;
	public static final Integer BAL1 = 2;
	public static final Integer BAL2 = 3;	
	public static final Integer SEAT_DEPTH = 2000;
	
	private static List<Integer> seatingLevels = new ArrayList<Integer>();
	private static Map<Integer, SeatLayout> seatingRows = new HashMap<Integer,SeatLayout>();
	
	static {
		seatingLevels.add(ORCH);
		seatingLevels.add(MAIN);
		seatingLevels.add(BAL1);
		seatingLevels.add(BAL2);
		seatingRows.put(ORCH,new SeatLayout(25,50,100));
		seatingRows.put(MAIN,new SeatLayout(20,100,75));
		seatingRows.put(BAL1,new SeatLayout(15,100,50));
		seatingRows.put(BAL2,new SeatLayout(15,100,40));
	}
	
	public static List<Integer> getSeatingLevels(){
		return seatingLevels;
	}

	public static Map<Integer, SeatLayout> getSeatingRows() {
		return seatingRows;
	}	
}
