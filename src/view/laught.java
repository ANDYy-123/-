package view;

import Music.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class laught extends JFrame{
    private final int WIDTH;
    private final int HEIGTH;
    private Music musicobject;

    public laught(int width, int height, Music musicobject) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.musicobject = musicobject;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addGif();
    }

    private void addGif(){
        MyJPanel1 m = new MyJPanel1();
        m.setBounds(0, 0,240,240);
        Timer timer=new Timer(80, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.repaint();
            }
        });
        timer.start();
        add(m);
    }

}
class MyJPanel1 extends JPanel{
    int i=0;
    ImageIcon[] gif=new ImageIcon[31];
    public void paint(Graphics g){
        for (int i=0;i<31;i++){
            int j=i+1;
            gif[i]=new ImageIcon("./picture/全部GIF图片帧1/"+j+".png");
//            gif[i]=new ImageIcon("picture/全部GIF图片帧/1.png");
        }
        super.paint(g);
        g.drawImage(gif[i%31].getImage(),0,0,this);
        i++;
    }

}
