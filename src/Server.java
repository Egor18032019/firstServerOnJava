import java.net.*;
import java.io.*;


public class Server {
    private static final int SERVERNUMBER = (int)(Math.random() * 10);

    public static void main(String[] args) throws IOException {
        System.out.println("SERVERNUMBER = " + SERVERNUMBER);
        ServerSocket serverSocket = new ServerSocket(8000);
        int count = 0;
        boolean serverWork = true;
        while (serverWork) {
//TODO Придумать как сделать serverWork = false;
            Socket clientSocket = serverSocket.accept(); // получение от клиента
            count++;
            System.out.println("Client accepted: " + count);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String request = reader.readLine();
            String text = "<p>You made a wish :" + request + "</p>";
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            clientSocket.getOutputStream())
            );
            String winOrLose = "You lose .....";
            try {
                if (SERVERNUMBER == Integer.parseInt(request)) {
                    winOrLose = "You WIN" + "Server wish = " + SERVERNUMBER;
                }
            }
            catch (Exception foo){
                text = "You use browser";
                winOrLose = "Need client app";
            }

            String answer = "<h1>Hello bro</h1>" +
                    "<p>You " + count + "</p>" +
                     text +
                    "<p>" + winOrLose + "</p>";
            writer.write("HTTP/1.0 200 OK\n" +
                    "Content-type:text/html\n" +
                    "Content-Length:" + answer.length() + "\n" +
                    "\r\n" +
                    answer);
            writer.newLine();
            writer.flush();
            writer.close();
            reader.close();

            clientSocket.close();// закрываем именно открытый клинетом соккет
        }

//        serverSocket.close(); // завершаем работу сервера
    }
}
