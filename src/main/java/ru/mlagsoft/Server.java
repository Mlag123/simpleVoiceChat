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
    private SourceDataLine speakers;
    private AudioFormat format = new AudioFormat(16000f, 16, 1, true, true);


    public Server() {

        //init server;


    }

    public static int getPort() {
        return port;
    }

    public void host() throws IOException {
        try {
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();


            serverSocket = new ServerSocket(port);

            socket = serverSocket.accept();
            while (true) {
                System.out.println("client connect");
                inputStream = socket.getInputStream();
                int numBytesReads;
                byte[] data = new byte[1024];
                numBytesReads = inputStream.read(data);

                speakers.write(data, 0, numBytesReads);
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream((InputStream) speakers);
                    Clip clip = AudioSystem.getClip();

                    clip.open(audioInputStream);
                    clip.start();
                } catch (ClassCastException e) {

                }


            }


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (LineUnavailableException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
/*            serverSocket.close();
            socket.close();
            inputStream.close();*/

        }

    }

}
