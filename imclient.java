//Levi Pinkard, 2016
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.net.InetAddress;

public class imclient extends Thread {
	static class ServerThread implements Runnable {
		//Declares client for whole class
		Socket client = null;
		public ServerThread(Socket c) {
			client = c;
		}
		//Thread that runs in background, recieving messages
		public void run() {
			while (true) {
				try {
					//Gets incoming message, prints it
					BufferedReader incomingMessage = new BufferedReader(new InputStreamReader(client.getInputStream()));
					String incoming = incomingMessage.readLine();
					//Stops null spamming at disconnect
					if (incoming != null) {
						System.out.println(incoming);
					}
				}
				catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		//Gets username
		System.out.print("Enter username: ");
		Scanner nameIn = new Scanner(System.in);
		String userName = nameIn.nextLine();
		//Gets hostname for connection
		System.out.print("Enter hostname: ");
		Scanner hostIn = new Scanner(System.in);
		String hostName = hostIn.nextLine();
		String message = null;
		//Starts socket at user specified hostname
		Socket server = new Socket(hostName, 1337);
		new Thread(new ServerThread(server)).start();
		//Loops infinitely
		while (true) {
			//Gets user message, opens output stream
			BufferedReader userMessage = new BufferedReader(new InputStreamReader(System.in));
			message = userMessage.readLine();
			DataOutputStream broadcastMessage = new DataOutputStream(server.getOutputStream());
			//Attaches username to message when sending
			broadcastMessage.writeBytes(userName + ": " + message + "\n");
		}
	}
}
