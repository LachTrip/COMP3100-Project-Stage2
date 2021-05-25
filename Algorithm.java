import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    
	private Reader reader = MyClient.reader;

	private void send(String msg) throws IOException{
		MyClient.send(msg);
	}
	
	public Server allToLargest() throws IOException{
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
		for (Server s : servers){
			if (forUse.getType().equals("empty")){
				forUse = s;
			} else {
				next = s;
				if (forUse.getCore() < next.getCore()){
					forUse = next;
				}
			}

		}
		send("OK");
		return forUse;
	}

	public Server myAlg(Job job) throws IOException{
		
		send("GETS Avail " + job.getCore() + " " + job.getMemory() + " " + job.getDisk());
		int serverNum = Integer.parseInt(reader.nextEntry());
		send("OK");
		List<Server> servers = new ArrayList<Server>();

		if(serverNum > 0){	
			for (int i = 0; i < serverNum; i++){
				Server server = new Server(reader);
				servers.add(server);
				if(i != serverNum - 1){
					reader.nextLine();
				}
			}
		} else {
			send("GETS Capable " + job.getCore() + " " + job.getMemory() + " " + job.getDisk());
			serverNum = Integer.parseInt(reader.nextEntry());
			send("OK");
			for (int i = 0; i < serverNum; i++){
				Server server = new Server(reader);
				servers.add(server);
				if(i != serverNum - 1){
					reader.nextLine();
				}
			}
		}

		Server forUse = new Server();
		Server next = new Server();
		for (Server s : servers){
			if (forUse.getType().equals("empty")){
				forUse = s;
			} else {
				next = s;
				if (forUse.getCore() <= next.getCore() && forUse.getWJobs() > next.getWJobs()){
					forUse = next;
				}
			}

		}
		send("OK");
		return forUse;
	}
}
