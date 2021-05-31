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

		// Server bestOfSmall = forUse;
		
		// //find next smallest server type
		// for (Server s : servers){
		// 	next = s;
		// 	if(bestOfSmall.getType() != next.getType()){
		// 		for(Server t : allServers()){
		// 			for(Server u : allServers()){
		// 				if(forUse.getType()==t.getType() && next.getType() == u.getType()){
		// 					if (t.getCore() > u.getCore()){
		// 						forUse = next;
		// 					}
		// 				}
		// 			}
					
		// 		}
		// 	}
		// }

		// //find server (of next smallest type) with least number of incomplete jobs
		// for (Server s : servers){
		// 	next = s;
		// 	if (forUse.getType() == next.getType() && forUse.getRJobs() + forUse.getWJobs() > next.getRJobs() + next.getWJobs()){
		// 		forUse = next;
		// 	}
		// }	

		// if (bestOfSmall.getWJobs() < 1 || bestOfSmall.getWJobs() < forUse.getWJobs()){
		// 	forUse = bestOfSmall;
		// }

		// if (forUse.getWJobs() > 1){
		// 	for (Server s : servers){
		// 		next = s;
		// 		if (forUse.getWJobs() > next.getWJobs()){
		// 			forUse = next;
		// 		}
		// 	}			
		// }

		// send("GETS Avail " + job.getCore() + " " + job.getMemory() + " " + job.getDisk());
		// serverNum = Integer.parseInt(reader.nextEntry());
		// send("OK");
		// if(serverNum > 0){
		// 	servers = new ArrayList<Server>();
		// 	for (int i = 0; i < serverNum; i++){
		// 		Server server = new Server(reader);
		// 		servers.add(server);
		// 		if(i != serverNum - 1){
		// 			reader.nextLine();
		// 		}
		// 	}
		// 	send("OK");
		// 	for (Server s : servers){
		// 		next = s;
		// 		if (next.getWJobs() == 0 && !next.getState().equals("inactive") && (forUse.getState().equals("inactive") || forUse.getCore() > next.getCore())){
		// 			forUse = next;
		// 		}
		// 	}
		// }

		return forUse;
	}

	
	
	
	
	
	
	// This one gets full marks but not great TT
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
			if (forUse.getWJobs() + forUse.getRJobs() > 4 && forUse.getWJobs() + forUse.getRJobs() > next.getWJobs() + next.getRJobs()){
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
