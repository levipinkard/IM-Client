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

public class imclient extends Thread {
	static class ServerThread implements Runnable {
		Socket client = null;
		public ServerThread(Socket c) {
			client = c;
		}
		public void run() {
			while (true) {
				try {
					BufferedReader incomingMessage = new BufferedReader(new InputStreamReader(client.getInputStream()));
					String incoming = incomingMessage.readLine();
					System.out.println(incoming);
				}
				catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		System.out.print("Enter username: ");
		Scanner nameIn = new Scanner(System.in);
		String userName = nameIn.nextLine();
		System.out.print("Enter hostname: ");
		Scanner hostIn = new Scanner(System.in);
		String hostName = hostIn.nextLine();
		String message = null;
		Socket server = new Socket(hostName, 1337);
		new Thread(new ServerThread(server)).start();
		while (true) {
			BufferedReader userMessage = new BufferedReader(new InputStreamReader(System.in));
			message = userMessage.readLine();
			DataOutputStream broadcastMessage = new DataOutputStream(server.getOutputStream());
			broadcastMessage.writeBytes(userName + ": " + message + "\n");
		}
		//hostIn.close();
		//server.close();
	}
}
