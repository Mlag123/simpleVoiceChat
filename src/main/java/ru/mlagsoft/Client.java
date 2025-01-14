package ru.mlagsoft;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Client {
    private String ip;
    private Socket socket;
    private AudioFormat format;
    private TargetDataLine microphone;
    private SourceDataLine speakers;


    public Client(String ip) {
        this.ip = ip;
        format = new AudioFormat(16000f, 16, 1, true, true);
        try {
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();
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
            InputStream inputStream = socket.getInputStream();

            byte[] data = new byte[1024];
            int innumByteRead;


         /*   byte[] inData = new byte[1024];
            numBytesReads = inputStream.read(data);*/
            while (true) {

                //отправка
                int numbytesread = microphone.read(data, 0, 1024);
                outputStream.write(data, 0, numbytesread);
                outputStream.flush();
                //отправка end




                try {
                    innumByteRead= inputStream.read(data);



                    //принятия
                    speakers.write(data, 0, innumByteRead);
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream((InputStream) speakers);
                    Clip clip = AudioSystem.getClip();

                    clip.open(audioInputStream);
                    clip.start();
                } catch (ClassCastException e) {

                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }

            }


        } catch (IOException e) {
       //     JOptionPane.showMessageDialog(null,"asdas"+e.getMessage());
            try {
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            microphone.close();

        }


    }
}
