package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Music.Music;
import controller.ClickController;
import controller.GameController;
import model.ChessColor;
import model.ChessComponent;
import model.KingChessComponent;
import model.KnightChessComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;
    private Music musicobject;

    public Menu(int width, int height, Music musicobject) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.musicobject = musicobject;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addZanTingButton();
        addPicture();
    }

    private void addZanTingButton() {
        JButton button = new JButton("PLAY");
        button.setLocation(240 , 417 );
        button.setSize(150, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("华文行楷", Font.PLAIN, 25));
        button.setForeground(new Color(255, 0, 0));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(218, 78, 78));
                button.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(255, 0, 0));
                button.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    ChessGameFrame mainFrame = new ChessGameFrame(1000, 760, musicobject);
                    mainFrame.setVisible(true);
                });
            }
        });

    }

    private void addPicture() {
        ImageIcon picture = new ImageIcon("./picture/皇室战争.png");
        JLabel label = new JLabel(picture);
        label.setBounds(0, 0, this.WIDTH, this.HEIGTH);
        add(label);
    }


}
