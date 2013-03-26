import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class ChatClientGUI extends JFrame {

	private JTextArea chatArea = new JTextArea();
	private JTextField messageField = new JTextField(
			"Replace me with your name");

	private String userName;

	public static void main(String[] args) {
		JFrame gui = new ChatClientGUI();
		gui.setVisible(true);
	}

	public ChatClientGUI() {
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
}
