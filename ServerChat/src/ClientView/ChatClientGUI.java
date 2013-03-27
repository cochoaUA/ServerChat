package ClientView;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

	public static void main(String[] args) {
		JFrame gui = new ChatClientGUI(null); // null?? or clientSocket
		gui.setVisible(true);
	}

	public ChatClientGUI(Socket socketFromServer) {
		// sockets
		clientSocket = socketFromServer;

		// GUI
		layoutView();
		setUpListeners();
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

			// stuff....

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
			
			if (true) // want to end connection
				break;
		}

		// close connection
		try {
			clientSocket.close();
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			System.out.println("Exception thrown trying to close connection");
			e.printStackTrace();
		}
	}
}
