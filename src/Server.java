import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        int count = 0;
        boolean serverWork = true;
        while (serverWork) {
            Socket clientSocket = serverSocket.accept(); // получение от клиента
            count++;
            System.out.println("Cleint accepter: " + count);
            // работает пока клиент не подключиться -> долгая
            // clientSocket.getOutputStream().write(3);// по одному байту
            clientSocket.getOutputStream().write("<h2>Hello bro</h2>".getBytes());
            OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());
            writer.write("<h1>How you ?" + count + " </h1>");
            writer.flush();
            writer.close();

            clientSocket.close();// закрываем именно открытый клинетом соккет
        }

//        serverSocket.close(); // завершаем работу сервера
    }
}
