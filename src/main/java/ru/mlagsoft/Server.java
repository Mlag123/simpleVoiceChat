package ru.mlagsoft;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int port = 25565;

    private boolean isHost;
    private Socket socket;
    private ServerSocket serverSocket;
    private InputStream inputStream;
       private AudioFormat format = new AudioFormat(16000f, 16, 1, true, true);
    private OutputStream outputStream;

    public Server() throws IOException {

        //init server;
        host();

    }

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public static int getPort() {
        return port;
    }

    public void host() throws IOException {
        try {



            serverSocket = new ServerSocket(port);

            socket = serverSocket.accept();
            while (true) {
                //принятие
                System.out.println("client connect");
                inputStream = socket.getInputStream();
                int numBytesReads;
                byte[] data = new byte[1024];
                numBytesReads = inputStream.read(data);
                //отправка
                outputStream = socket.getOutputStream();
                outputStream.write(data,0,numBytesReads);
                outputStream.flush();
                //отправка




            }


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
/*            serverSocket.close();
            socket.close();
            inputStream.close();*/

        }

    }

}
