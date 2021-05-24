public class Job {

    private int submitTime;
    private int ID;
    private int estRunTime;
    private int core;
    private int memory;
    private int disk;

    public Job(Reader reader){
        submitTime = Integer.parseInt(reader.nextEntry());
        ID = Integer.parseInt(reader.nextEntry());
        estRunTime = Integer.parseInt(reader.nextEntry());
        core = Integer.parseInt(reader.nextEntry());
        memory = Integer.parseInt(reader.nextEntry());
        disk = Integer.parseInt(reader.nextEntry());
    }

    public int getSubmitTime(){
        return submitTime;
    }

    public int getID(){
        return ID;
    }

    public int getEstRunTime(){
        return estRunTime;
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
