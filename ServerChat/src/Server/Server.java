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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import ClientView.ChatClientGUI;

public class Server implements Runnable {

	ServerSocket myServerSocket;
	Vector<String> connectedClients;

	public final static int PORT_NUMBER = 4009;

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

	/**
	 * public void print() loop through vectorandprint
	 */
	@Override
	public void run() {
		try {

			while (true) {
				Socket intoServer = myServerSocket.accept(); // use this socket
																// object to
																// talk to
																// client socket
																// that
																// represents
																// that client

				Liason oneUser = new Liason(intoServer, connectedClients);
				new Thread(oneUser).start();

			}
		} catch (IOException e) {
			System.out.println("In Server.run");
			e.printStackTrace();
		}
	}

	private class Liason extends Thread {
		private Socket intoServer;
		private Vector<String> connectedClients;

		private ObjectOutputStream oos;
		private ObjectInputStream ois;

		public Liason(Socket socketFromServer, Vector<String> connectedClients) {
			intoServer = socketFromServer;
			this.connectedClients = connectedClients;
			// this.connectedClients.add(this);

			try {
				// that socket has input/output stream to send things between
				// each other.
				oos = new ObjectOutputStream(intoServer.getOutputStream());
				ois = new ObjectInputStream(intoServer.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {

			try {
				while (true) {
					Object text;
					text = ois.readObject();
					if (text.toString().indexOf("disconnected") > 0){
						break;
					}
					oos.writeObject("You said: " + text + "\n");
				}
			} catch (IOException e1) {
				// try {
				// intoServer.close();
				// oos.close();
				// ois.close();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * private class Liason extends Thread { public Liason(ObjectInputStream
	 * ois, ObjectOutputStrea oos)
	 * 
	 * public void run() { try { oos.writeObject("FDSD"); }catch(IOException e)
	 * { oos.close(); ois.close(); clients.remove(this); }
	 * 
	 * 
	 * 
	 * }
	 */

	// -------------------------------------------

	/**
	 * public class Clients {
	 * 
	 * public static void main(String [] args) { }
	 * 
	 * Socket s;
	 * 
	 * public Client() {
	 * 
	 * 
	 * try { s = new Socket("localhost", 4009); Object InputStream ios = ...
	 * Object OutputStream oos = ... } catch (UnknownHostException e) {
	 * 
	 * e.printStackTrace(); } catch (IOException e) {
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * e.printStackTrace(); }
	 * 
	 */

}
