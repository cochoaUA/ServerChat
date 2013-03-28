package ClientView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
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

	private ObjectInputStream inputStream; // to server
	private ObjectOutputStream outputStream; // from server
	private Socket server = null;
	private Vector<String> sharedCollectionReference;

	public static void main(String[] args) {

		JFrame gui = new ChatClientGUI();
		gui.setVisible(true);

	}

	public ChatClientGUI() {

		String IPAddress = "localhost";
		int port = 4009;

		try {
			server = new Socket(IPAddress, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// GUI stuff
		layoutView();
		setUpListeners();

		new Thread(this).start();
	}

	/**
	 * prepares all of the starting listeners to be used in the GUI
	 */
	private void setUpListeners() {
		// TODO 1:46:48 PM complete this method
		GetUserNameListener listener1 = new GetUserNameListener();
		messageField.addActionListener(listener1);

		MyWindowListener window = new MyWindowListener();
		this.addWindowListener(window);
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
				outputStream.reset();
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
				outputStream.reset();
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

	private class MyWindowListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent
		 * )
		 */
		@Override
		public void windowClosing(WindowEvent arg0) {

			// close connection
			try {
				outputStream.writeObject(userName + " has disconnected!");
				server.close();
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				System.out
						.println("Exception thrown trying to close connection");
				e.printStackTrace();
			}

		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
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
			outputStream = new ObjectOutputStream(server.getOutputStream());
			inputStream = new ObjectInputStream(server.getInputStream());
		} catch (IOException e) {
			System.out
					.println("Exception thrown while obtaining input & output streams");
			e.printStackTrace();
		}

		// stay connected
		while (true) {

			try {
				// outputStream = new
				// ObjectOutputStream(server.getOutputStream());
				// inputStream = new ObjectInputStream(server.getInputStream());

				String message = inputStream.readObject().toString();
				chatArea.append(message);

				// inputStream.close();
				// outputStream.close();
			} catch (SocketException closed) {
				// nothing to worry about... trust me... im serious!
			} catch (IOException e) {
				System.out.println("Client Exception");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}
}
