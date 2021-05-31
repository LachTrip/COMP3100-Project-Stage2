import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    
	private Reader reader = MyClient.reader;

	private void send(String msg) throws IOException{
		MyClient.send(msg);
	}

	private List<Server> allServers(){
		return MyClient.allServers;
	}

	public Server myAlg(Job job) throws IOException{
		
		List<Server> servers = new ArrayList<Server>();
		Server forUse = new Server();
		Server next = new Server();
		int serverNum;

		//get information on capable servers
		send("GETS Capable " + job.getCore() + " " + job.getMemory() + " " + job.getDisk());
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

		//find smallest capable server type
		for (Server s : servers){
			if (forUse.getType().equals("empty")){
				forUse = s;
			} else {
				next = s;
				for(Server t : allServers()){
					for(Server u : allServers()){
						if(forUse.getType()==t.getType() && next.getType() == u.getType()){
							if (t.getCore() > u.getCore()){
								forUse = next;
							}
						}
					}
					
				}
			}
		}

		//find server (of smallest capable type) with least number of incomplete jobs
		for (Server s : servers){
			next = s;
			if (forUse.getType() == next.getType() && forUse.getRJobs() + forUse.getWJobs() > next.getRJobs() + next.getWJobs()){
				forUse = next;
			}
		}
		

		if (forUse.getWJobs() > 3){
			for (Server s : servers){
				next = s;
				if (forUse.getWJobs() > next.getWJobs()){
					forUse = next;
				}
			}			
		}
	
		return forUse;
	}
}
