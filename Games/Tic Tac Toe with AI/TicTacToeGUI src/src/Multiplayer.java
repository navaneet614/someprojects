import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;

public class Multiplayer {

	ServerSocket serverSocket;
	Socket clientSocket;
	BufferedReader in;
	PrintWriter out;
	boolean hosting;
	
	public Multiplayer(Board b){
		b.blankScreen = true;
		b.repaint();
		System.out.println("Do Multiplayer Stuff");
		b.blankScreen = false;
		b.repaint();
	}
	
	public void makeServer(){
        try {
            serverSocket = new ServerSocket(5555);
            hosting = true;
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5555.");
            System.exit(1);
        }

	}
	
	public void ConnectToServer(String serverAdress, int port) throws UnknownHostException, IOException{
		clientSocket = new Socket(serverAdress, port);
		hosting = false;
        in = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        
	}

	public boolean getOtherMove() {
		if(hosting){
			try {
				String s = clientSocket.getOutputStream().toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
