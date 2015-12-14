package com.test.ticketReserve.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.ticketReserve.TicketService;
import com.test.ticketReserve.backend.TicketReserveMaster;
import com.test.ticketReserve.service.TicketServiceImpl;

public class ReserveSeatServlet extends HttpServlet {
	
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
		int holdId = Integer.parseInt(req.getParameter("holdId"));
		String email = req.getParameter("email");
		String result = service.reserveSeats(holdId, email);
		if(result.equals(TicketReserveMaster.RESULT_SUCCESS)){
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().print("Your tickets have been successfully booked");
		}
		else{
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			resp.getWriter().print("Sorry your session has expired.");
		}
	}


}
