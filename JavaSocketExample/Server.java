import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket listener = null;
        int clientNumber = 0;
        int port = 9999;

        try {
            // khởi tạo 1 listener đợi 1 client gửi request kết nối
            listener = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Error: \n " + e.getMessage());
            System.exit(1);
        }

        try {
            while (true) {
                Socket servSocket = listener.accept(); // accept kết nối socket

                // tạo 1 luồng riêng cho client này chạy
                new ServerThread(servSocket, clientNumber).start(); 
            }
        } finally {
            // dừng lắng nghe client
            listener.close();
        }
    }
}

class ServerThread extends Thread {
    private int clientNumber;
    private Socket socketServer;

    public ServerThread(Socket _socketServer, int _clientNumber) {
        this.clientNumber = _clientNumber;
        this.socketServer = _socketServer;
    }

    public void run() {
        try {
            System.out.println("Send message to localhost port " + clientNumber);
            BufferedReader br = new BufferedReader(new InputStreamReader(socketServer.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socketServer.getOutputStream()));

            while (true) {
                System.out.println("Send a message: ");
                String line = System.console().readLine();

                if (line.equals("quit")) { // dừng connect nếu nhập "quit"
                    System.out.println("Client " + clientNumber + " disconnected");
                    break;
                }
                // tạo message được gửi từ server
                bw.write("Server: " + line);
                // kết thúc dòng message
                bw.newLine();
                // đẩy message đi
                bw.flush();
            }

        } catch (Exception e) {
            System.out.println("Client " + clientNumber + " disconnected");
            e.printStackTrace();
        }
    }
}
