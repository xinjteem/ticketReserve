package com.test.ticketReserve;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.test.ticketReserve.backend.SeatLayout;
import com.test.ticketReserve.backend.SeatingLevel;
import com.test.ticketReserve.backend.TicketMaster;
import com.test.ticketReserve.servlet.HoldSeatServlet;
import com.test.ticketReserve.servlet.ListSeatServlet;
import com.test.ticketReserve.servlet.ReserveSeatServlet;

public class App 
{
	static TicketMaster master = TicketMaster.getInstance();
    public static void main( String[] args ) throws Exception
    {    	
    	prepopulateMaster();   
    	master.trimMaster();
    	master.printMaster();
    	Server server = new Server(8080);
    	 
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        
        context.addServlet(new ServletHolder(new ListSeatServlet()),"/list");
        context.addServlet(new ServletHolder(new HoldSeatServlet()),"/hold");
        context.addServlet(new ServletHolder(new ReserveSeatServlet()),"/reserve");
 
        server.start();
        server.join();        
    }

	private static void prepopulateMaster() {
		
		Map<Integer, SeatLayout> seatingRows = SeatingLevel.getSeatingRows();
		List<Integer> seatingLevels = SeatingLevel.getSeatingLevels();
		if(!seatingLevels.isEmpty()){
			int startFrom = 0;
			for(int i = 0; i<seatingLevels.size(); i++){
				Integer seatingLevel = seatingLevels.get(i);
				StringBuffer seatRepresentation = new StringBuffer();
				int numRows = startFrom + seatingRows.get(i).getRows();
				int seatsPerRow = seatingRows.get(i).getSeatsPerRow();
				List<String> seatList = new ArrayList<String>();
				for(int j=startFrom;j<numRows;j++){
					for(int k=0;k<seatsPerRow;k++){						
						seatRepresentation.append(j);
						seatRepresentation.append(":");
						seatRepresentation.append(k);
						seatList.add(seatRepresentation.toString());
						seatRepresentation.delete(0, seatRepresentation.length());					
					}
				}
				startFrom = numRows;	
				master.updateMaster(seatingLevel, seatList);
			}
		}		
	}
}
