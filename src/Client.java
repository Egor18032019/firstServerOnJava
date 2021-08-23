import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    int clientNumber;

    public static void main(String[] args) throws IOException, InterruptedException {
        Client thisClient = new Client();
        boolean needNumber =true;
        while (needNumber){
        Scanner input = new Scanner(System.in, "Cp1251");
            if (input.hasNextInt()) {
                thisClient.clientNumber = input.nextInt();
                needNumber=false;
            } else {
                System.out.println("Напишите целое число больше нуля но меньше 10: ");
            }
        }
        if (thisClient.clientNumber > 0) {
            Socket clientSocket = new Socket("127.0.0.1", 8000);

            OutputStreamWriter writer = new OutputStreamWriter(
                    clientSocket.getOutputStream());

            //отправили
            writer.write(thisClient.clientNumber + "\n"); // обязательно ставить \n
            writer.flush(); //метод flush() выбрасывает всё из буфера в соответствующий поток.

            InputStreamReader reader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader strReader = new BufferedReader(reader);
            List answerServer = new ArrayList<>();
            do {
                answerServer.add(strReader.readLine());
            }
            while (strReader.readLine() != null);
            System.out.println(answerServer);

            writer.close();
            reader.close();
            strReader.close();

            clientSocket.close();
        }
    }

}

