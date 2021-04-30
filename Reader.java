
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

    public Reader(Socket s) throws IOException{
        din = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    public String nextLine() throws IOException{
        line = din.readLine();
        scan = new Scanner(line);
        scan.useDelimiter(" ");
        return nextEntry();
    }

    public String nextEntry(){
        current = scan.next();
        return current;
    }

    public String getCurrent(){
        return current;
    }

    public String getLine(){
        return line;
    }

    public boolean says(String string){
        return current.equals(string);
    }
}
