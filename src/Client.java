import javax.naming.ldap.SortKey;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends JFrame implements ActionListener, KeyListener {
    public static void main(String[] args) throws IOException {
        try {

            Socket client;

            System.out.println("Send number server");
            Scanner scanner = new Scanner(System.in);
            int options = scanner.nextInt();

            switch (options) {
                case 1: {
                    System.out.println("~~ Server 1 start");
                    client = new Socket("127.0.1.1", 9000);
                    tryConnectionClient(client);
                    break;
                }
                case 2: {
                    System.out.println("~~  Server 2 start");
                    client = new Socket("127.0.1.1", 9001);
                    tryConnectionClient(client);
                    break;
                }
                default:
                    System.out.println("No existing server with is number");
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }


    public static void tryConnectionClient(Socket client) throws IOException {
        ObjectOutputStream outputData = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream inputData = new ObjectInputStream(client.getInputStream());

        outputData.writeUTF("Client");
        outputData.flush();
        String messageServer = inputData.readUTF();
        System.out.println(messageServer);

        outputData.close();
        inputData.close();
        client.close();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
