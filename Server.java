import java.io.IOException;

public class Server {
    
    private String serverType;
    private int serverID;
    private String state;
    private int curStartTime;
    private int core;
    private int memory;
    private int disk;

    public Server(){

    }

    public Server(Reader reader) throws IOException{
        serverType = reader.nextLine();
        serverID = Integer.parseInt(reader.nextEntry());
        state = reader.nextEntry();
        curStartTime = Integer.parseInt(reader.nextEntry());
        core = Integer.parseInt(reader.nextEntry());
        memory = Integer.parseInt(reader.nextEntry());
        disk = Integer.parseInt(reader.nextEntry());
    }

    public String getServerType(){
        return serverType;
    }

    public int getServerID(){
        return serverID;
    }

    public String getState(){
        return state;
    }

    public int getcurStartTime(){
        return curStartTime;
    }

    public int getCore(){
        return core;
    }

    public int getMemory(){
        return memory;
    }

    public int getDisk(){
        return disk;
    }
}
