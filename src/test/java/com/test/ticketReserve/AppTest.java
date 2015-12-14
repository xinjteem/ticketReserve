package com.test.ticketReserve;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.test.ticketReserve.backend.SeatLayout;
import com.test.ticketReserve.backend.SeatingLevel;
import com.test.ticketReserve.backend.TicketHoldMaster;
import com.test.ticketReserve.backend.TicketMaster;
import com.test.ticketReserve.utils.ExpiringMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
      
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
    

    /**
     * Rigourous Test :-)
     * @throws InterruptedException 
     */
    public void testExpiringMap() throws InterruptedException{    	
    	ExpiringMap<String,String> map = new ExpiringMap<String,String>(5000);
    	map.put("test", "test");
    	Thread.sleep(6000);
    	assertNull(map.get("test"));    	
    } 
    
}
