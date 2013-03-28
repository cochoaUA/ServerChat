package ClientView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
 | Description: This class simulates our chat client
 |
 *===========================================================================*/

public class ChatClientGUI extends JFrame implements Runnable {

	private JTextArea chatArea = new JTextArea();
	private JTextField messageField = new JTextField(
			"Replace me with your name");

	private String userName;

	private static final String HOST_NAME = "localhost";
	private static final int PORT_NUMBER = 4009;

	private ObjectInputStream inputStream; // to server
	private ObjectOutputStream outputStream; // from server
	private Socket clientSocket;
	private Vector<String> sharedCollectionReference;

	private boolean firstTimeOpening = true;

	public static void main(String[] args) {
		String IPAdress = "localhost";
		int port = 4009;
		Socket server = null;
		
		try {
			server = new Socket(IPAdress, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(server.getInputStream());
			output.writeObject("Hi, Server!");
			try {
				Object x = input.readObject();
				System.out.println(x.toString());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		try {
			JFrame gui = new ChatClientGUI(new Socket(HOST_NAME, PORT_NUMBER), null);
			gui.setVisible(true);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ChatClientGUI(Socket socketFromServer, Vector<String> sharedCollectionReference1) {
		
//		  try {
//			 	s = new Socket("localhost", 4009);
//			 	
//			 	Object OutputStream oos = ...
//			Object InputStream ios = ...
//			  }
//			  catch (UnknownHostException e) {
//			  
//			  e.printStackTrace();
//			  } catch (IOException e) {
//			  
//			 
//			  
//			  
//			  
//			 
//			  e.printStackTrace();
//		
		// sockets
		clientSocket = socketFromServer;
		sharedCollectionReference = sharedCollectionReference1;

		// GUI
		if (firstTimeOpening) {
			layoutView();
			setUpListeners();
			firstTimeOpening = false;
		}
	}

	/**
	 * prepares all of the starting listeners to be used in the GUI
	 */
	private void setUpListeners() {
		// TODO 1:46:48 PM complete this method
		GetUserNameListener listener1 = new GetUserNameListener();
		messageField.addActionListener(listener1);

	}

	/**
	 * sets the GUI dimensions and general stuff such as color and specifying
	 * where things are laid out
	 */
	private void layoutView() {
		// GUI layout
		this.setTitle("Chat Client");
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// message Field
		getContentPane().add(messageField, BorderLayout.NORTH);

		// chat Area
		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(true);
		chatArea.setEditable(false);
		getContentPane().add(chatArea, BorderLayout.CENTER);

	}

	private class SendMessageListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO send messages to server
			// get message
			String text = messageField.getText();
			messageField.setText("");

			// write message to outputStream
			try {
				// outputStream.reset();
				outputStream.writeObject(userName + ": " + text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private class GetUserNameListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// get username
			userName = messageField.getText();
			messageField.setText("");

			// write message to outputStream
			try {
				// outputStream.reset();
				outputStream.writeObject(userName + " has connected!");
			} catch (IOException e) {
				System.out.println("Error writing to outputStream");
				e.printStackTrace();
			}

			// change listener for messageField
			SendMessageListener listener = new SendMessageListener();
			messageField
					.removeActionListener(messageField.getActionListeners()[0]);
			messageField.addActionListener(listener);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// set input / output streams
		try {
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
			outputStream = new ObjectOutputStream(
					clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out
					.println("Exception thrown while obtaining input & output streams");
			e.printStackTrace();
		}

		// stay connected
		while (true) {

			try {
				String message = (String) inputStream.readObject();
				chatArea.append(message);
								
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (true) // want to end connection
				break;
		}

		// close connection
		try {
			outputStream.writeObject(userName + " has disconnected!");
			clientSocket.close();
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			System.out.println("Exception thrown trying to close connection");
			e.printStackTrace();
		}
	}
}

/**
 * 
 */







