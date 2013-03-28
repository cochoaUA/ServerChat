/**=============================================================================
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
	Vector<Liason> connectedClients;

	public final static int PORT_NUMBER = 4009;

	public static void main(String args[]) {

		Server server = new Server(PORT_NUMBER);
		Thread serverThread = new Thread(server);
		serverThread.start();
	}

	public Server(int port) {
		connectedClients = new Vector<Liason>();

		try {
			myServerSocket = new ServerSocket(PORT_NUMBER);

		} catch (IOException e) {
			System.out.println("In Server.Server() (the constructor)");
			e.printStackTrace();
		}

	}

	/**
	 * public void print() loop through vector and print
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
				connectedClients.add(oneUser);
				new Thread(oneUser).start();

			}
		} catch (IOException e) {
			System.out.println("In Server.run");
			e.printStackTrace();
		}
	}

	private class Liason extends Thread {
		private Socket intoServer;
		private Vector<Liason> connectedClients;

		private ObjectOutputStream oos;
		private ObjectInputStream ois;

		public Liason(Socket socketFromServer, Vector<Liason> connectedClients) {
			intoServer = socketFromServer;
			this.connectedClients = connectedClients;

			try {
				// that socket has input/output stream to send things between
				// each other.
				oos = new ObjectOutputStream(intoServer.getOutputStream());
				ois = new ObjectInputStream(intoServer.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * gives us the ability to get the output stream for this liason (which
		 * will help us send messages to all the other liasons)
		 * 
		 * @return ObjectOutputStream
		 */
		public ObjectOutputStream getOutputStream() {
			return oos;
		}

		public void run() {

			try {
				while (true) {
					Object text;
					text = ois.readObject();
					// if they closed the stream -- send they disconnected
					// message
					if (text.toString().indexOf("disconnected") > 0) {
						connectedClients.remove(this);
						for (Liason liason : connectedClients) {
							liason.getOutputStream().writeObject(text + "\n");
						}
						// close the now unnecessary streams
						try {
							intoServer.close();
							oos.close();
							ois.close();
						} catch (IOException e) {
							System.out.println("server");
							e.printStackTrace();
						}
						break;
					}
					// send messages to all the other liasons
					for (Liason liason : connectedClients) {
						liason.getOutputStream().writeObject(text + "\n");
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
