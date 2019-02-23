package backend;

import java.net.*;

import java.io.*;

public class Client implements AutoCloseable, Runnable {
	Socket s;
	private BufferedReader in;
	private ObjectOutputStream out;

	public Client(String ip, int port) {
		try {
			s = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendData(String s) {
		try {

			out.writeObject(s + '\n');
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			String line = null;

			// while reader line is not empty
			while ((line = in.readLine()) != null) {

				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() throws IOException {
		s.close();
	}
}
