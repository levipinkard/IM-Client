import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class imclient extends Thread {
	
	public static void main(String[] args) throws Exception {
		System.out.print("Enter username: ");
		Scanner nameIn = new Scanner(System.in);
		String userName = nameIn.nextLine();
		System.out.print("Enter hostname: ");
		Scanner hostIn = new Scanner(System.in);
		String hostName = hostIn.nextLine();
		String message = null;
		Socket server = new Socket(hostName, 1337);
		while(message != "STOP") {
			BufferedReader userMessage = new BufferedReader(new InputStreamReader(System.in));
			message = userMessage.readLine();
			DataOutputStream broadcastMessage = new DataOutputStream(server.getOutputStream());
			broadcastMessage.writeBytes(userName + ": " + message + "\n");
			BufferedReader incomingMessage = new BufferedReader(new InputStreamReader(server.getInputStream()));
			String incoming = incomingMessage.readLine();
			System.out.println(incoming);
			
		}
		hostIn.close();
		server.close();
	}
}
