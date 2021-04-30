import java.io.IOException;

public class Server {
    
    private String type;
    private int ID;
    private String state;
    private int curStartTime;
    private int core;
    private int memory;
    private int disk;

    public Server(){

    }

    public Server(Reader reader) throws IOException{
        type = reader.getCurrent();
        ID = Integer.parseInt(reader.nextEntry());
        state = reader.nextEntry();
        curStartTime = Integer.parseInt(reader.nextEntry());
        core = Integer.parseInt(reader.nextEntry());
        memory = Integer.parseInt(reader.nextEntry());
        disk = Integer.parseInt(reader.nextEntry());
    }

    public String getType(){
        return type;
    }

    public int getID(){
        return ID;
    }

    public String getState(){
        return state;
    }

    public int getCurStartTime(){
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
