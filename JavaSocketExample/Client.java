import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        final String serverHost = "localhost"; // thay đổi localhost thành ip theo ip của server
        int port = 9999;
        Socket socketOfClient = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            // gửi request kết nối đến server
            socketOfClient = new Socket(serverHost, port);

            bw = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Dont know about this host " + serverHost);
        } catch (IOException e) {
            System.err.println("Could not get I/O  ");
        }

        try {

            String responseLine;
            // đọc message
            while ((responseLine = br.readLine()) != null) {
                System.out.println(responseLine);
                if (responseLine.indexOf("OK") != -1) {
                    break;
                }
            }
            // kết thúc
            bw.close();
            br.close();

        } catch (UnknownHostException e) {
            System.err.println("Dont know about this host " + serverHost);
        } catch (IOException e) {
            System.err.println("Could not get I/O  ");
        }
    }
}
