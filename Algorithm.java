import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    
	private Reader reader = MyClient.reader;

	private void send(String msg) throws IOException{
		MyClient.send(msg);
	}

	private void debug(String msg){
		MyClient.debug(msg);
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
		send("OK");

		Server forUse = new Server();
		Server next = new Server();
		for (Server s : servers){
			if (forUse.getType().equals("empty")){
				forUse = s;
			} else {
				next = s;
				if (forUse.getCore() > next.getCore()){
					forUse = next;
				}
			}
		}
		for (Server s : servers){
			next = s;
			if ((forUse.getWJobs() + forUse.getRJobs() > 4) && forUse.getWJobs() + forUse.getRJobs() > next.getWJobs() + next.getRJobs()){
				forUse = next;
			}
		}


		send("GETS Avail " + job.getCore() + " " + job.getMemory() + " " + job.getDisk());
		serverNum = Integer.parseInt(reader.nextEntry());
		send("OK");
		servers = new ArrayList<Server>();
		for (int i = 0; i < serverNum; i++){
			Server server = new Server(reader);
			servers.add(server);
			if(i != serverNum - 1){
				reader.nextLine();
			}
		}
		send("OK");

		for (Server s : servers){
			next = s;
			if (!next.getState().equals("inactive") && (forUse.getState().equals("inactive") || forUse.getCore() > next.getCore())){
				forUse = next;
			}
		}

		return forUse;
	}

	public Server myAlgOld(Job job) throws IOException{
		
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

		Server forUse = new Server();
		Server next = new Server();
		for (Server s : servers){
			if (forUse.getType().equals("empty")){
				forUse = s;
			} else {
				next = s;
				if (forUse.getCore() > next.getCore()){
					forUse = next;
				}
			}
		}
		for (Server s : servers){
			next = s;
			if ((forUse.getWJobs() + forUse.getRJobs() > 4) && forUse.getWJobs() + forUse.getRJobs() > next.getWJobs() + next.getRJobs()){
				forUse = next;
			}
		}
		for (Server s : servers){
			next = s;
			if (next.getState().equals("idle") && (!forUse.getState().equals("idle") || forUse.getCore() >= next.getCore())){
				forUse = next;
			}
		}
		send("OK");
		return forUse;
	}
}
