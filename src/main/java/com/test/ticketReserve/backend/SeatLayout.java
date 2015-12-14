package com.test.ticketReserve.backend;

public class SeatLayout {
	
	private int rows;
	private int seatsPerRow;
	private int price;
	public SeatLayout(int rows, int seatsPerRow, int price) {
		super();
		this.rows = rows;
		this.seatsPerRow = seatsPerRow;
		this.price = price;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getSeatsPerRow() {
		return seatsPerRow;
	}
	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

}
