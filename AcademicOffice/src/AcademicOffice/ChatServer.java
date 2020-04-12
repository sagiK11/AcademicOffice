package academicoffice;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
	private ServerSocket serverSocket;
	private ArrayList< ChatClientThread > clientsArrayList;
	private boolean listening;

	public ChatServer() throws IOException {
		establishServerSocket( );
		createServerSocket( );
		listenToClients( );
		closeServerSocket( );
	}

	private void listenToClients() {
		while ( listening ) {
			try {
				System.out.println( "waiting for clients" );
				Socket socket = serverSocket.accept( );

				InputStream inputStream = socket.getInputStream( );
				InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
				BufferedReader reader = new BufferedReader( inputStreamReader );
				PrintWriter writer = new PrintWriter( socket.getOutputStream( ) , true );

				String clientName = reader.readLine( );
				System.out.println( clientName + " has connected to server" );

				ChatClientThread chatClientThread = new ChatClientThread( clientName , socket );
				clientsArrayList.add( chatClientThread );

				sendMessageToClients( clientName + ":  has connected" );
				sendOnlineClientsToClients( );
				chatClientThread.start( );

			} catch ( IOException e ) {
				System.exit( 1 );
			}
		}
	}


	private void createServerSocket() {
		final int port = 7777;
		try {
			serverSocket = new ServerSocket( port );
		} catch ( IOException e ) {
			System.exit( 1 );
		}
	}

	private void establishServerSocket() {
		clientsArrayList = new ArrayList<>( );
		serverSocket = null;
		listening = true;
	}

	private void sendMessageToClients(String message) {

		for ( ChatClientThread currentClient : clientsArrayList ) {
			try {
				PrintWriter writer = currentClient.getPrintWriter( );
				writer.println( message );
				writer.flush( );
			} catch ( Exception ex ) {
				ex.printStackTrace( );
			}
		}
	}

	private void sendOnlineClientsToClients() {
		for ( ChatClientThread currentClient : clientsArrayList ) {
			try {
				PrintWriter writer = currentClient.getPrintWriter( );
				writer.println( clientsArrayList );
				writer.flush( );
			} catch ( Exception ex ) {
				ex.printStackTrace( );
			}
		}
	}

	private void closeServerSocket() throws IOException {
		serverSocket.close( );
	}

	private void removeClientFromClientsArrayList(String message) {
		int lastIndex = message.indexOf( ":" );
		String clientName = message.substring( 0 , lastIndex );

		for ( int i = 0 ; i < clientsArrayList.size( ) ; i++ ) {
			String currentClientName = clientsArrayList.get( i ).toString( );
			if( currentClientName.contains( clientName ) ) {
				clientsArrayList.remove( i );
				return;
			}
		}

	}

	private class ChatClientThread extends Thread {
		private String chatClientName;
		private PrintWriter client;
		private BufferedReader reader;

		private ChatClientThread(String clientName , Socket socket) {
			this.chatClientName = clientName.trim( );
			try {
				client = new PrintWriter( socket.getOutputStream( ) , true );
				reader = new BufferedReader( new InputStreamReader( socket.getInputStream( ) ) );
			} catch ( IOException e ) {

			}
		}

		public void run() {
			String message;
			final String CLIENT_DISCONNECTED_KEY = ": disconnected";
			try {
				while ( ( message = reader.readLine( ) ) != null ) {

					if( message.contains( CLIENT_DISCONNECTED_KEY ) )
						removeClientFromClientsArrayList( message );
					else if( message.length( ) == 0 )
						continue;

					sendMessageToClients( message );
					sendOnlineClientsToClients( );

				}
				client.close( );
				reader.close( );
			} catch ( IOException e ) {
				e.printStackTrace( );
			}
		}

		public String toString() {
			return chatClientName;
		}

		private PrintWriter getPrintWriter() {
			return client;
		}
	}

}
