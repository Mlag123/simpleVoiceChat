package ru.mlagsoft;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private String ip;
    private Socket socket;
    private AudioFormat format;
    TargetDataLine microphone;


    public Client(String ip) {
        this.ip = ip;
        format = new AudioFormat(16000f, 16, 1, true, true);
        try {
            microphone = AudioSystem.getTargetDataLine(format);
            microphone.open(format);
            microphone.start();
        } catch (LineUnavailableException e) {
            microphone.close();
            JOptionPane.showMessageDialog(null,"Проблему знаю, потому что я еблан и при подключении, создает новый сокет");
            JOptionPane.showMessageDialog(null,"Вообще я хуйлан ленивый, что бы нормально закодить, но я так пить перестану");
            JOptionPane.showMessageDialog(null,"если ЭТО появится на андройде, то просто плюньте мне в ебало");
            JOptionPane.showMessageDialog(null,"для меня");
            JOptionPane.showMessageDialog(null,"Ашипка \n"+e.getMessage());
            JOptionPane.showMessageDialog(null,e.getStackTrace());
        }
        try {
            socket = new Socket(ip, Server.getPort());
            OutputStream outputStream = socket.getOutputStream();

            byte[] data = new byte[1024];
            while (true) {
                int numbytesread = microphone.read(data, 0, 1024);
                outputStream.write(data, 0, numbytesread);
                outputStream.flush();

            }


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"asdas"+e.getMessage());
            try {
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            microphone.close();

        }


    }
}
