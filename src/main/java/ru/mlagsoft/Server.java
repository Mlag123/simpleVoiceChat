package ru.mlagsoft;

import ru.mlagsoft.VoiceEngine.VoiceIO;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static int port = 25565;
    private boolean isHost;
    private Socket socket;
    private ServerSocket serverSocket;
    private InputStream inputStream;
    private AudioFormat format = new AudioFormat(16000f, 16, 1, true, true);
    private OutputStream outputStream;
    private ArrayList<Socket> list_clients = new ArrayList<>();
    private int connected = 0;
    private ArrayList<OutputStream> listOs = new ArrayList<>();

    public Server() throws IOException {

        //init server;
     //   host();

    }


    public static int getPort() {
        return port;
    }

    public void host() throws IOException {
        list_clients = new ArrayList<>();
        try {


            serverSocket = new ServerSocket(port);

            //  socket = serverSocket.accept();
           
           /*     //принятие
                System.out.println("client connect");
                inputStream = socket.getInputStream();
                int numBytesReads;
                byte[] data = new byte[1024];
                numBytesReads = inputStream.read(data);
                //отправка
                //    outputStream = socket.getOutputStream();
                //  outputStream.write(data, 0, numBytesReads);
                //outputStream.flush();
                //отправка*/
                addClient(serverSocket.accept());
                sendVoice(inputVoice(list_clients));





        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void sendVoice(ArrayList<VoiceIO> listVoiceIO) {
     OutputStream _os = listOs.get(0);

        while (true){
            try {
                _os.write(new byte[1024], 0, listVoiceIO.get(0).numBytesRead);
                System.out.println(listVoiceIO.get(0).numBytesRead);
                _os.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



    }

    public ArrayList<VoiceIO> inputVoice(ArrayList<Socket> list_clients) {
        ArrayList<VoiceIO> arrayListVoiceIo = new ArrayList<>();
        VoiceIO voiceIO = new VoiceIO();


        for (Socket _socket : list_clients) {
            try {
                InputStream in = _socket.getInputStream();
                int numBytesReads = in.read(new byte[1024]);
                voiceIO.setNumBytesRead(numBytesReads);
                arrayListVoiceIo.add(voiceIO);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return arrayListVoiceIo;

    }


    public void addClient(Socket socket) {
        try {
            list_clients.add(socket);
            listOs.add(socket.getOutputStream());
            this.connected += 1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeClient(Socket socket) {
        for (Socket _socket : list_clients) {
            if (socket.equals(_socket)) {
                list_clients.remove(_socket);
                if (!(connected <= 0)) {
                    this.connected -= 1;
                }

            }

        }
    }


}
