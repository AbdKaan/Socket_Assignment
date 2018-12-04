import java.net.*;
import java.io.*;
import java.util.Random;

public class Server {
    private ServerSocket serverSocket;
    private int number;

    public Server(int port) throws IOException {
        //Open the server with the given port.
        serverSocket = new ServerSocket(port);
        Random rng = new Random();
        //Server thinking of a number between 0 and 1000.
        number = rng.nextInt(1000);
        System.out.println("Number is: " + number);

    }

    public void acceptAndRun() {
        try {
            //Accept the server socket.
            Socket server = serverSocket.accept();

            //Create DataOutputStream to send messages to client.
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Guess the number:");

            //Create DataInputStream to get client guesses.
            DataInputStream in = new DataInputStream(server.getInputStream());

            //condition: Run until guess is correct.
            boolean condition = true;

            while (condition) {

                //Get the guessedNumber from client.
                int guessedNumber = in.readInt();

                //Sleep for readability on terminal for us.
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Server will response based on the guess.
                if (guessedNumber < number) {
                    out.writeUTF("Guess a greater number.");
                } else if (guessedNumber > number) {
                    out.writeUTF("Guess a smaller number.");
                } else {
                    out.writeUTF("Correct!");
                    condition = false;
                }

            }

            server.close();

        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        int port = 6666;
        try {
            Server server = new Server(port);
            server.acceptAndRun();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}