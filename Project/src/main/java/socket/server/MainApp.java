package socket.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Main class
 * 
 * @author Marcin
 * 
 */
public class MainApp {
	/**
	 * main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		int port = 6066;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				try {
					Thread t = new ServerThread(serverSocket.accept());
					t.setDaemon(true);
					t.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
