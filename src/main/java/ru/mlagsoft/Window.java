package ru.mlagsoft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Window extends JFrame {

    private int width = 300;
    private int height = 100;
    private JButton connectToServer = new JButton("Connect");
    private JButton host = new JButton("Host server");
    private JTextField ip = new JTextField("enter ip here");
    private Container container = getContentPane();
    private Server server;
    private JLayeredPane layeredPane = new JLayeredPane();


    public Window() {
        server = new Server();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);

        ip.setSize(new Dimension(50, 60));
        container.add(connectToServer);
        container.add(host);
        ip.setSize(20, 20);
        add(ip);
        add(new JLabel("написал это говно я, alpha 0.0.1 night"));
        ip.setBounds(20, 20, 20, 20);


        pack();
        butEvents();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void butEvents() {
        host.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            server.host();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }).start();
            }
        });


        connectToServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Client(ip.getText());
                    }
                }).start();
            }
        });
    }

    public static void main(String[] args) {
        new Window();
    }

}
