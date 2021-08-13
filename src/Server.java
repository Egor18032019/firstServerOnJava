import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        int count = 0;
        boolean serverWork = true;
        while (serverWork) {
//TODO Придумать как сделать serverWork = false;
            Socket clientSocket = serverSocket.accept(); // получение от клиента
            count++;
            System.out.println("Client accepted: " + count);
            // работает пока клиент не подключиться -> долгая
            // clientSocket.getOutputStream().write(3);// по одному байту
            // clientSocket.getOutputStream().write(("HTTP/1.0 200 OK\n" +
            //  "Content-type:text/html\n"+
            //  "\r\n"+
            // "<h1>Hello bro</h1>\n").getBytes());


            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
           String request = reader.readLine();

            OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());
            String answer = "<h1>Hello bro</h1>" +
                    "<p>You " + count + "</p>" +
                    "<p>You message:" + request.length()+ "</p>";
            System.out.println(answer);
            writer.write("HTTP/1.0 200 OK\n" +
                    "Content-type:text/html\n" +
                    "Content-Length:" + answer.length() + "\n" +
                    "\r\n" +
                    answer);

            writer.flush();
            writer.close();
            reader.close();

            clientSocket.close();// закрываем именно открытый клинетом соккет
        }

//        serverSocket.close(); // завершаем работу сервера
    }
}
