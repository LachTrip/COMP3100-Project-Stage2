import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyClient {

	static DataOutputStream dout;
	static Socket s;
	static Reader reader;

	public static void debug(String msg){
		System.out.println(msg);
	}

	public static void main(String[] args) {
		try {
			s = new Socket("127.0.0.1", 50000);
			dout = new DataOutputStream(s.getOutputStream());
			reader = new Reader(s);
			handshake();
			System.out.println("# Handshake successful");
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
		System.out.println("SENT " + message);
		reader.nextLine();
		System.out.println("RCVD " + reader.getLine());
	}

	// Handshake Protocol
	public static void handshake() throws IOException {
		send("HELO");
		send("AUTH " + System.getProperty("user.name"));
	}

	public static void nextEvent() throws IOException {
		send("REDY");
		if (reader.says("JOBN")){
			doJobn();
		} else if (reader.says("JOBP")){
			// TODO
		} else if (reader.says("JCPL")){
			// TODO
		} else if (reader.says("RESF")){
			// TODO
		} else if (reader.says("RESR")){
			// TODO
		} else if (reader.says("NONE")){
			send("QUIT");
		}
	}

	public static void doJobn() throws IOException{
		allToLargest();
	}

	public static void allToLargest() throws IOException{
		Job job = new Job(reader);
		send("GETS All");
		int serverNum = Integer.parseInt(reader.nextEntry());
		send("OK");

		List<Server> servers = new ArrayList<Server>();
		for (int i = 0; i < serverNum; i++){
			Server server = new Server(reader);
			servers.add(server);
			if(i != serverNum - 1){
				reader.nextLine();
			}
		}

		Server forUse = new Server();
		Server next = new Server();
		boolean forUseEmpty = true;
		for (Server s : servers){
			if (forUseEmpty){
				forUse = s;
				forUseEmpty = false;
			} else {
				next = s;
				if (forUse.getCore() < next.getCore()){
					forUse = next;
				}
			}

		}
		send("OK");

		send("SCHD " + job.getID() + " " + forUse.getType() + " " + forUse.getID());
	}

	public static void allToSmallest() throws IOException{
		Job job = new Job(reader);
		send("GETS Capable " + job.getCore() + " " + job.getMemory() + " " + job.getDisk());
		int serverNum = Integer.parseInt(reader.nextEntry());
		send("OK");

		List<Server> servers = new ArrayList<Server>();
		for (int i = 0; i < serverNum; i++){
			Server server = new Server(reader);
			servers.add(server);
			if(i != serverNum - 1){
				reader.nextLine();
			}
		}

		debug("SO CLOSE");

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

		send("SCHD " + job.getID() + " " + forUse.getType() + " " + forUse.getID());
	}
}