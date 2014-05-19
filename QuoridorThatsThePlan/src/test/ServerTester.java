/*
 * ServerTester.java
 * Authors: David Rice
 * This class will test Server.java functionality so
 * things don't break.
 */

package test;

import org.junit.Test;

import Servers.Server;

import Clients.Client;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import java.io.IOException;
import java.io.PrintStream;

public class ServerTester extends TestCase{
    /** Default port number; used if none is provided. */
    public final static int DEFAULT_PORT_NUMBER = 3939;

    /** Default machine name is the local machine; used if none provided. */
    public final static String DEFAULT_MACHINE_NAME = "localhost";

    /** Command-line switches */
    public final static String ARG_PORT = "--port";
    public final static String ARG_MACHINE = "--machine";

    /** Name of the machine where the server is running. */
    private String machineName;

    /** Port number of distant machine */
    private int portNumber;
    
    /** Quit message for the reverse string program */
    public final String QUITMESSAGE = "quit";
	
	
	public ServerTester (String name) { 
		super(name);
	}
	
	// This test will see if the message "hi" is being received
	@Test
	public void testGetServerMessage2() throws Exception {
		String str = new String("Charles");
        int port = DEFAULT_PORT_NUMBER;
        String machine = DEFAULT_MACHINE_NAME;
        
       // Client myClient = new Client(machine, port);
		
        
		//assertEquals("Server sent: Charles", myClient.Respond(str));
	}
	
	/**
	 * This is the main for the ServerTester class.  It will
	 * launch the tests
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestRunner.runAndWait(new TestSuite(ServerTester.class));
	}
}
