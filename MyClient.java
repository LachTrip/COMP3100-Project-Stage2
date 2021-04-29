import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyClient {

	static DataOutputStream dout;
	static Socket s;
	static Reader reader;

	public static void main(String[] args) {
		try {
			s = new Socket("127.0.0.1", 50000);
			dout = new DataOutputStream(s.getOutputStream());
			handshake();
			System.out.println("Handshake successful");
			while(!reader.says(".")){
				doJob();
			}
			send("QUIT");
			dout.flush();
			dout.close();
			s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// format and send message to ds-server
	public static void send(String message) throws IOException {
		System.out.println("C: " + message);
		dout.write((message + "\n").getBytes());
	}

	// Handshake Protocol
	public static void handshake() throws IOException {
		send("HELO");
		reader = new Reader(s);
		if (reader.says("OK")) {
			send("AUTH " + System.getProperty("user.name"));
		}
		reader.nextLine();
	}


	public static void doJob() throws IOException{
		send("REDY");
		reader.nextLine();
		Job job = new Job();
		if(reader.says("JOBN")){
			job = new Job(reader);
		}
		send("GETS Capable " + job.getCore() + " " + job.getMemory() + " " + job.getDisk());
		reader.nextLine();
		int serverNum = Integer.parseInt(reader.nextEntry());
		send("OK");
		List<Server> servers = new ArrayList<Server>();
		for (int i = 0; i < serverNum; i++){
			Server server = new Server(reader);
			servers.add(server);
		}

		Server forUse = new Server();
		boolean done = false;
		for (Server s : servers){
			if(!done){
				if(s.getState().equals("inactive") || s.getState().equals("idle")){
					forUse = s;
					done = true;
				}
			}
		}

		send("OK");

		send("SCHD " + job.getJobID() + " " + forUse.getServerType() + " " + forUse.getServerID());
		reader.nextLine();
		}
}