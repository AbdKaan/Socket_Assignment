import java.net.*;
import java.io.*;
import java.util.Random;

public class Client {

    public static void main(String [] args) {
        String serverName = "localhost";
        Random rng = new Random();
        int guessedNumber = rng.nextInt(1000);
        int port = 6666;
        try {
            Socket client = new Socket(serverName, port);

            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("Server: " + in.readUTF());

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("Client: " + guessedNumber);
            out.writeInt(guessedNumber);

            //Condition: loop until condition gets false.(Guessing right number)
            boolean condition = true;

            //hi: highest possible number, lo: lowest possible number.
            int hi = 1000;
            int lo = 0;
            while (condition) {

                //Get the response from the server.
                String serverResponse = in.readUTF();
                System.out.println("Server: " + serverResponse);

                //Sleep for readability on terminal for us.
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Guess a number based on the response.
                switch (serverResponse) {
                    case "Guess a greater number.":

                        lo = guessedNumber + 1;
                        guessedNumber = rng.nextInt(hi - lo) + lo;
                        System.out.println("Client: " + guessedNumber);
                        out.writeInt(guessedNumber);

                        break;
                    case "Guess a smaller number.":

                        hi = guessedNumber;
                        guessedNumber = rng.nextInt(hi - lo) + lo;
                        System.out.println("Client: " + guessedNumber);
                        out.writeInt(guessedNumber);

                        break;
                    default:
                        condition = false;
                        break;
                }

            }

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}