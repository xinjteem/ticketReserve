package com.test.ticketReserve.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.ticketReserve.SeatHold;
import com.test.ticketReserve.TicketService;
import com.test.ticketReserve.service.TicketServiceImpl;

public class HoldSeatServlet extends HttpServlet{
	
	TicketService service = new TicketServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//
		service(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//
		service(req,resp);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String customerEmail = req.getParameter("email");
		Integer min = null;
		Integer max = null;
		int numSeats = Integer.parseInt(req.getParameter("num"));
		if(req.getParameter("min") != null){
			min = Integer.parseInt(req.getParameter("min"));
		}
		if(req.getParameter("max") != null){
			max = Integer.parseInt(req.getParameter("max"));
		}
		Optional<Integer> minLevel = Optional.ofNullable(min);
		Optional<Integer> maxLevel = Optional.ofNullable(max);
		
		SeatHold hold = service.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
		resp.setIntHeader("HOLD-ID", hold.getHoldId());
		resp.getWriter().print(hold.toString());
	}

}
