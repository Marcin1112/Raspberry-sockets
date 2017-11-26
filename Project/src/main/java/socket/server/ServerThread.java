package socket.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.SocketException;

/**
 * Allow to create server that allow to multiple client connection
 * 
 * @author Marcin
 *
 */
public class ServerThread extends Thread {
	Socket server;
	private DataOutputStream out;

	/**
	 * Constructor
	 * 
	 * @param server
	 *            client connection
	 * 
	 * @throws IOException
	 * @see java.io.IOException
	 */
	public ServerThread(Socket server) throws IOException {
		this.server = server;
	}

	/**
	 * Override method in new thread
	 */
	@Override
	public void run() {

		while (true) {
			try (RandomAccessFile br = new RandomAccessFile("measurementData.dat", "r")) {
				out = new DataOutputStream(server.getOutputStream());
				String line;
				while (true) {
					if ((line = br.readLine()) != null) {
						out.writeUTF(line);
						out.flush();
						Thread.sleep(10);
					}
				}
			} catch (SocketException e) {
				e.printStackTrace();
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
