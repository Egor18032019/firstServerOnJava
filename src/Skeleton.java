import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Skeleton {
    public static void main(String[] args) throws IOException, InterruptedException {
        Skeleton skeleton = new Skeleton();
        if (args[0].equals("server")) {
            skeleton.runServer(args[1], args[2]);
        }
        if (args[0].equals("client")) {
            System.out.println("client work on " + args[1] + " c " + args[3] + " и " + args[4]);
            skeleton.runClient(args[1], args[2], args[3], args[4]);
        }
    }

    private void runClient(String ip, String port, String firstNumber, String secondNumber) throws IOException {
        Phone phone = new Phone(ip, port);
        phone.writeLine(firstNumber);
        phone.writeLine(secondNumber);
        String answer = phone.readLine();
        System.out.println(answer);
        phone.close();
    }

    private void runServer(String port, String operation) throws IOException, InterruptedException {
        Phone phone = new Phone(port);
        System.out.println("Started server on: " + port);
        boolean truOrFalse = true;
        while (truOrFalse) {
            phone.accept();
            String a = phone.readLine();
            System.out.println(a);
            String b = phone.readLine();
            System.out.println(b);
            int result = calculate(a, b, operation);
            String message = a + " " + operation + " " + b + " = " + result;
            phone.writeLine(message);
            //TODO сделать типа логов.Запись в файл .txt
            phone.close();
        }
    }

    private int calculate(String a, String b, String operation) {
        int foo = Integer.parseInt(a);
        int bar = Integer.parseInt(b);
        switch (operation) {
            case "+":
                return foo + bar;
            case "-":
                return foo - bar;
            case "/":
                return foo / bar;
            case "*":
                return foo * bar;
            default:
                return 0;
        }
    }
}

class Phone {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Phone(String port) throws IOException {
        this.serverSocket = new ServerSocket(Integer.parseInt(port));
    }

    public Phone(String ip, String port) throws IOException {
        this.clientSocket = new Socket(ip, Integer.parseInt(port));
        createStreams();
    }


    public void writeLine(String message) throws IOException {

        writer.write(message);
        writer.newLine();
        writer.flush(); //метод flush() выбрасывает всё из буфера в соответствующий поток.

    }

    public String readLine() throws IOException {

        String request = reader.readLine();
        return request;
    }

    public void accept() throws IOException {
        this.clientSocket = this.serverSocket.accept();
        createStreams();
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        this.clientSocket.close();

    }

    private void createStreams() {
        try {
            reader = new BufferedReader(
                    new InputStreamReader(this.clientSocket.getInputStream()));

            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            this.clientSocket.getOutputStream()
                    ));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}