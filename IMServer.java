//Levi Pinkard, 2016
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.net.InetAddress;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;


public class IMServer extends Thread {
	static class ServerThread implements Runnable {
    //Declares variable for whole class
    Socket client = null;


		public ServerThread(Socket c) {
      client = c;
    }
    //Thread running in background recieves messages
    public void run() {
      while(true) {
				try {
          //Gets input stream from client
          BufferedReader clientData = new BufferedReader(new InputStreamReader(client.getInputStream()));
					//Sets clientMessage to text sent by client
          String clientMessage = clientData.readLine();
          //Stops from spamming null at disconnect
          if (clientMessage != null) {
            System.out.println("\n" + clientMessage + "\n");
          }
				}catch (Exception e) {
					System.err.println(e.getMessage());
				}

			}
		}
	}
	public static void main(String[] args) throws Exception {
    //Gets username
    System.out.print("Enter username: ");
  	Scanner userName = new Scanner(System.in);
    String userString = userName.nextLine();
    String clientName;
    //Prints hostname of system for easy connection
    System.out.println("Hostname: " + InetAddress.getLocalHost());
    //Opens socket at port 1337
    ServerSocket serverSock = new ServerSocket(1337);
    //Accepts connection from client
  	Socket connection = serverSock.accept();
    //Loops infinitely
    while(true) {
      //Starts thread
      new Thread(new ServerThread(connection)).start();
      //Gets user text, sends it
      DataOutputStream backToClient = new DataOutputStream(connection.getOutputStream());
      Scanner textIn = new Scanner(System.in);
      String thisClient = textIn.nextLine();
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			Date currentTimestamp = new Date();
			//Attaches username when sending
      backToClient.writeBytes(df.format(currentTimestamp) + " " + userString + ": " + thisClient + "\n");
		}
	}
}
