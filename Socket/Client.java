import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args){
        final String serverHost = "localhost";

        Socket socketOfClient = null;
        BufferedWriter bw = null;
        BufferedReader br = null;

        try {
            socketOfClient = new Socket(serverHost, 9999);
            
            bw = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.print("Don't know about host " + serverHost);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverHost);
        }

        try {
            
            // bw.write("Hello");
            // bw.newLine();
            // bw.flush();
            // bw.write("How are you?");
            // bw.newLine();
            // bw.flush();
            // // bw.write("quit");
            // bw.newLine();
            // bw.flush();

            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                System.out.println("Server: " + responseLine);
                if (responseLine.indexOf("OK") != -1) {
                    break;
                }
            }
                
            bw.close();
            br.close();

        } catch (UnknownHostException e) {
            System.err.print("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}