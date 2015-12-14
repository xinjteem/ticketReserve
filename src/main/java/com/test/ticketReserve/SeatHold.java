package com.test.ticketReserve;

import java.util.Map;

public class SeatHold {
	
	private Map<String,Integer> seatsHeld;
	private int holdId;
	private long durationHeldFor;
	private String email;
	private int seatingLevel;
	
	public SeatHold(Map<String, Integer> seatsHeld, int holdId,
			long durationHeldFor, String email) {
		this.seatsHeld = seatsHeld;
		this.holdId = holdId;
		this.durationHeldFor = durationHeldFor;
		this.email = email;
	}

	public Map<String, Integer> getSeatsHeld() {
		return seatsHeld;
	}

	public long getDurationHeldFor() {
		return durationHeldFor;
	}

	public String getEmail() {
		return email;
	}

	public int getHoldId() {
		return holdId;
	}

	public int getSeatingLevel() {
		return seatingLevel;
	}

	public void setSeatingLevel(int seatingLevel) {
		this.seatingLevel = seatingLevel;
	}

	@Override
	public String toString() {
		return "SeatHold [seatsHeld=" + seatsHeld + ", holdId=" + holdId
				+ ", durationHeldFor=" + durationHeldFor + ", email=" + email
				+ ", seatingLevel=" + seatingLevel + "]";
	}
	
}
