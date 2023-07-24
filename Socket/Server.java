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

        try {
            listener = new ServerSocket(9999);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }

        try {
            while (true) {
                // Chấp nhận một yêu cầu kết nối từ phía Client
                Socket socketServer = listener.accept();

                // Tạo luồng riêng cho client này
                new ServerThread(socketServer, clientNumber).start();
            }
        } finally {
            listener.close();
        }
    }

}

class ServerThread extends Thread {

    private int clientNumber;

    private Socket socketServer;

    public ServerThread(Socket socketServer, int clientNumber) {
        this.socketServer = socketServer;
        this.clientNumber = clientNumber;
    }

    public void run() {
        try {
            System.out.println("Client " + clientNumber + " connected");
            BufferedReader br = new BufferedReader(new InputStreamReader(socketServer.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socketServer.getOutputStream()));

            while (true) {
                // Đọc dữ liệu từ client gửi tới
                System.out.println("Send message to client " + clientNumber + ": ");
                String line = System.console().readLine();

                // Nếu client gửi quit thì đóng kết nối
                if (line.equals("quit")) {
                    System.out.println("Client " + clientNumber + " disconnected");

                    break;
                }

                // tạo message gửi tới client
                bw.write("Server: " + line);
                // Kết thúc dòng
                bw.newLine();
                // Đẩy dữ liệu đi
                bw.flush();

            }
        } catch (Exception e) {
            System.out.println("Client " + clientNumber + " disconnected");
            e.printStackTrace();
        }
    }
}
