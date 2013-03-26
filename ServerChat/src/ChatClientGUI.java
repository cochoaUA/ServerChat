import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatClientGUI extends JFrame{

	
	private JTextArea chatArea = new JTextArea();
	private JTextField messageField = new JTextField();
	
	public static void main(String [] args){
		JFrame gui = new ChatClientGUI();
		gui.setVisible(true);
	}
	
	public ChatClientGUI(){
		layoutView();
		setUpListeners();
	}

	/**
	 * 
	 */
	private void setUpListeners() {
		// TODO 1:46:48 PM complete this method
		SendMessageListener listener = new SendMessageListener();
	}

	/**
	 * 
	 */
	private void layoutView() {
		// TODO 1:46:45 PM complete this method
		this.setTitle("Chat Client");
		this.setSize(500, 500);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(messageField, BorderLayout.NORTH);
		this.add(chatArea, BorderLayout.CENTER);
		
	}
	
	private class SendMessageListener implements ActionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 2:34:01 PM complete this method
			
		}
		
	}
}
