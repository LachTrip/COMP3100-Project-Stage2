
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Reader {
    private BufferedReader din;
	private String line;
    private String current;
    private Scanner scan;

    // initialize reader
    public Reader(Socket s) throws IOException{
        din = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    // initialize scanner for the line and get next entry
    public String nextLine() throws IOException{
        line = din.readLine();
        scan = new Scanner(line);
        scan.useDelimiter(" ");
        return nextEntry();
    }

    // get next entry in line
    public String nextEntry(){
        current = scan.next();
        return current;
    }

    // get current entry
    public String getCurrent(){
        return current;
    }

    // return true if current entry is equal
    // used mostly for commands from server
    public boolean says(String string){
        return current.equals(string);
    }
}
