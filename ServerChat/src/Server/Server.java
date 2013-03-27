/*=============================================================================
 | Assignment: Program #5
 | Authors: Carlton Ochoa (cochoa@email.arizona.edu)
 | 			Haziel Zuniga (zuniga7@email.arizona.edu)
 |
 | Grader: Rohit
 | Course: 335
 | Instructor: R. Mercer
 | Due Date: Tuesday April 2, 2013 at 3:00
 |
 | Description: This class simulates our server.
 |
 *===========================================================================*/



package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable {

	private ServerSocket myServerSocket;
	private Vector<String> connectedClients;

	public final static int PORT_NUMBER = 4000;

	public static void main(String args[]) {

		Server server = new Server(PORT_NUMBER);
		Thread serverThread = new Thread(server);
		serverThread.start();
	}

	public Server(int port) {
		connectedClients = new Vector<String>();

		try {
			myServerSocket = new ServerSocket(PORT_NUMBER);
		} catch (IOException e) {
			System.out.println("In Server.Server() (the constructor)");
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		try {
			while (true) {
				Socket intoServer = myServerSocket.accept();
				ConnectionThreadForEachClient aThreadForOneClient = new ConnectionThreadForEachClient(
						intServer, connectedClients);

				Thread thread = new Thread(aThreadForOneClient);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println("In Server.run");
			e.printStackTrace();
		}
	}

}
