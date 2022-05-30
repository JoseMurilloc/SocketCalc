import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Server {

    private ServerSocket socket;
    private int port;

    public Server(int port) {
        this.port = port;
    }


    public static void main(String[] args) throws IOException {
        Server server = startServer(9000);
        System.out.println("Server end watch: " + server.port);
    }

    public static Server startServer(int port) throws IOException {
        Server server = new Server(port);
        Socket client  = server.createServerSocket();
        server.tryConnection(client);
        return server;
    }

    public Socket createServerSocket() throws IOException {
        this.socket = new ServerSocket(this.port);
        Socket client =  waitClient();

        System.out.println("New Server create with success in " + client.getInetAddress().getHostAddress());
        return client;
    }

    public void close(Socket s) throws IOException {
        s.close();
    }

    public void tryConnection(Socket socket) throws IOException {
        try {
            ObjectOutputStream outputData = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputData = new ObjectInputStream(socket.getInputStream());

            String inputMessage = inputData.readUTF();
            outputData.writeUTF("[Server One] Send message " + inputMessage);
            outputData.flush();

            inputData.close();
            outputData.close();
        } catch(Exception error) {
            System.out.println("Error in try connection");
        } finally {
            socket.close();
        }
    }

    public Socket waitClient() throws IOException {
        System.out.println("Wait client in port " + this.port + " ...");
        return this.socket.accept();
    }
}
