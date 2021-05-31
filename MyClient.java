import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyClient {

	static DataOutputStream dout;
	static Socket s;
	static Reader reader;

	static List<Server> allServers = new ArrayList<Server>();

	public static void debug(String msg){
		System.out.println(msg);
	}

	public static void main(String[] args) {
		try {
			s = new Socket("127.0.0.1", 50000);
			dout = new DataOutputStream(s.getOutputStream());
			reader = new Reader(s);
			handshake();
			while(!reader.says("QUIT")){
				nextEvent();
			}
			dout.flush();
			dout.close();
			s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// format and send message to ds-server
	public static void send(String message) throws IOException {
		dout.write((message + "\n").getBytes());
		reader.nextLine();
	}

	// Handshake Protocol
	public static void handshake() throws IOException {
		send("HELO");
		send("AUTH " + System.getProperty("user.name"));
	}

	// choose what to do based on response from server
	public static void nextEvent() throws IOException {
		send("REDY");
		
		if(allServers.size() == 0){
			getServers();
		}
		
		if (reader.says("JOBN")){
			doJobn();
		} else if (reader.says("NONE")){
			send("QUIT");
		}
	}

	// recieved JOBN from server
	public static void doJobn() throws IOException{
		Job job = new Job(reader);
		Algorithm a = new Algorithm();
		Server forUse = a.myAlgOld(job);
		send("SCHD " + job.getID() + " " + forUse.getType() + " " + forUse.getID());
	}

	public static void getServers() throws IOException{
		send("GETS All");
		int serverNum = Integer.parseInt(reader.nextEntry());
		send("OK");
		for (int i = 0; i < serverNum; i++){
			Server server = new Server(reader);
			allServers.add(server);
			if(i != serverNum - 1){
				reader.nextLine();
			}
		}
		send("OK");
	}
}