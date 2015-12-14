package com.test.ticketReserve.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.ticketReserve.TicketService;
import com.test.ticketReserve.service.TicketServiceImpl;

public class ListSeatServlet extends HttpServlet{
	
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
		int seatingLevel = Integer.parseInt(req.getParameter("seatingLevel"));
		Optional level = Optional.ofNullable(seatingLevel);
		int seatsAvailable = service.numSeatsAvailable(level);
		resp.getWriter().print(seatsAvailable);
	}


}
