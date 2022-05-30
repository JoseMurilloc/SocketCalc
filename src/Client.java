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

            System.out.println("*****************************************************");
            System.out.println("Digite 1 para operações básicas (+,-,*,/)");
            System.out.println("Digite 2 para demais operações (%,^,r)");
            System.out.println("*****************************************************");

            Scanner scanner = new Scanner(System.in);
            int options = scanner.nextInt();

            switch (options) {
                case 1: {
                    System.out.println("~~ Call Server 1 ~~");
                    client = new Socket("127.0.1.1", 9000);
                    tryConnectionClient(client);
                    break;
                }
                case 2: {
                    System.out.println("~~ Call Server 2 ~~");
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

        RequestClient requestClient = scannerInputUser();

        outputData.writeDouble(requestClient.first);
        outputData.writeDouble(requestClient.second);
        outputData.writeChar(requestClient.operator);

        outputData.flush();

        Double totalCalc = inputData.readDouble();
        System.out.println(totalCalc);

        outputData.close();
        inputData.close();
        client.close();
    }


    public static RequestClient scannerInputUser() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Informe o primeiro numero");
        double first = scanner.nextInt();

        System.out.println("Informe o segundo numero");
        double second = scanner.nextInt();

        System.out.println("Qual operação desejada?");
        char operator = scanner.next().charAt(0);

        RequestClient requestClient = new RequestClient(
                first,
                second,
                operator
        );

        return requestClient;
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
