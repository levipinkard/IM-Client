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


public class IMServer extends Thread {
  PrintWriter writer;
	static class ServerThread implements Runnable {
    static String oldString;
    static String newString;
    static String[] chatStr = new String[2];
		Socket client = null;
    static int threadCount = 0;
		public ServerThread(Socket c) {
      client = c;
    }
		public void run() {
      int localThreadCount = threadCount;
      threadCount = threadCount + 1;
      while(true) {
				try {
          //BufferedReader clientData = new BufferedReader(new InputStreamReader(client.getInputStream()));
          DataOutputStream backToClient = new DataOutputStream(client.getOutputStream());
          //String clientMessage = clientData.readLine();
          //string = clientMessage
					backToClient.writeBytes("what up" + "\n");

				}catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		String clientName;
		try{
		    PrintWriter writer = new PrintWriter("chatlog.txt", "UTF-8");
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
		ServerSocket serverSock = new ServerSocket(1337);
		while(true) {
			Socket connection = serverSock.accept();
      new Thread(new ServerThread(connection)).start();
		}
	}
}
