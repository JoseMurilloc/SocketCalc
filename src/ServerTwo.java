import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTwo {

    private ServerSocket socket;
    private int port;

    public ServerTwo(int port) {
        this.port = port;
    }


    public static void main(String[] args) throws IOException {
        ServerTwo server = startServer(9001);
        System.out.println("Server end watch: " + server.port);
    }

    public static ServerTwo startServer(int port) throws IOException {
        ServerTwo server = new ServerTwo(port);
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
        double firstNumber, secondNumber;
        double total = 0;
        char operation;

        try {
            ObjectOutputStream outputData = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputData = new ObjectInputStream(socket.getInputStream());

            firstNumber = inputData.readDouble();
            secondNumber = inputData.readDouble();
            operation = inputData.readChar();

            switch (operation) {
                case '%':
                    total = ((firstNumber * secondNumber) / 100);
                    break;
                case '^':
                    total = (int) Math.pow(firstNumber, secondNumber);
                    break;
                case 'r':
                    total: Math.sqrt(firstNumber);
                    break;
                default:
                    throw new IOException("|x| Invalid option |x|");
            }

            outputData.writeDouble(total);
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
