package academicoffice;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ChatSceneController implements Initializable, StudentUpdater {
	private Student student;
	private ExecutorService executorService = Executors.newCachedThreadPool( );
	private ChatThread chatThread = new ChatThread( );
	private Socket chatSocket;
	private PrintWriter writer;
	private BufferedReader reader;
	private String hostName = "localhost";
	private boolean connectedToServer;
	@FXML
	private TextArea typingTextArea, messagesTextArea;
	@FXML
	private ListView onlineUsersListView;

	@Override
	public void initialize(URL location , ResourceBundle resources) {
		student = Main.student;
		executorService.execute( chatThread );
		connectToServer( );
	}

	private void connectToServer() {
		try {
			chatSocket = new Socket( hostName , 7777 );
			writer = new PrintWriter( chatSocket.getOutputStream( ) , true );
			reader = new BufferedReader( new InputStreamReader( chatSocket.getInputStream( ) ) );
			notifyChatServerHostNameConnected( );
		} catch ( IOException e ) {
			switchToDisconnectedMode( );
			return;
		}
		switchToConnectedMode( );
		listenToServerThread( );
	}

	private void notifyChatServerHostNameConnected() {
		writer.println( hostName );
		writer.flush( );
	}

	private void listenToServerThread() {
		Thread listeningThread = new Thread( new ListeningThread( ) );
		listeningThread.start( );
	}

	private void listenToServer() {
		try {
			String msgFromServer, IllegalNameMark = "Illegal Name", onlineUsersMark = "[";

			while ( ( msgFromServer = reader.readLine( ) ) != null ) {
				if( msgFromServer.equals( IllegalNameMark ) )
					disconnectFromServer( );
				else if( ! msgFromServer.contains( onlineUsersMark ) )
					updateMessagesInPanel( msgFromServer );
				else
					updateOnlineUsersInPanel( msgFromServer );
			}
		} catch ( SocketException e ) {
			disconnectFromServer( );
		} catch ( IOException e ) {
			System.out.println( "failed reading from server" );
			e.printStackTrace( );
			System.exit( 1 );
		}
	}

	private void sendMessage() {
		if( connectedToServer ) {
			String hostMessage = typingTextArea.getText( );
			writer.println( hostName + ": " + hostMessage );
			writer.flush( );
		}
	}

	private void switchToConnectedMode() {
		connectedToServer = true;
		typingTextArea.requestFocus( );
	}

	private void switchToDisconnectedMode() {
		connectedToServer = false;
	}

	private void disconnectFromServer() {
		String disconnect = ( hostName + ": disconnected" );
		try {
			writer.println( disconnect );
			writer.flush( );
		} finally {
			switchToDisconnectedMode( );
			executorService.shutdownNow( );
		}
	}

	private void updateMessagesInPanel(String msgFromServer) {
		messagesTextArea.setText( messagesTextArea.getText( ) + msgFromServer + "\n" );
	}

	private void updateOnlineUsersInPanel(String msgFromServer) {
		String[] onlineUsersArray = parseOnlineUsersFromServer( msgFromServer );
		onlineUsersListView.setItems(
			FXCollections.observableList( new ArrayList<>( Arrays.asList( onlineUsersArray ) ) ) );

	}

	private String[] parseOnlineUsersFromServer(String msgFromServer) {
		onlineUsersListView.getItems( ).clear( );
		msgFromServer = msgFromServer.substring( 1 );
		msgFromServer = msgFromServer.substring( 0 , msgFromServer.length( ) - 1 );
		String[] onlineUsersArray = msgFromServer.split( "," );
		return onlineUsersArray;
	}

	@FXML
	private void activateMessageSenderThread() {
		new MessageSenderThread( ).start( );
	}

	@Override
	public void updateStudent() {

	}

	private class ListeningThread implements Runnable {
		@Override
		public void run() {
			listenToServer( );
		}
	}

	private class ChatThread implements Runnable {
		@Override
		public void run() {
			try {
				new ChatServer( );
			} catch ( IOException e ) {
				e.printStackTrace( );
			}
		}
	}

	private class MessageSenderThread extends Thread {
		public void run() {
			sendMessage( );
		}
	}

}
