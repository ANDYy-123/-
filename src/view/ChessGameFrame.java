package view;

import Music.Music;
import controller.ClickController;
import controller.GameController;
import javazoom.jl.decoder.JavaLayerException;
import model.ChessColor;
import model.ChessComponent;
import model.KingChessComponent;
import model.KnightChessComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private boolean p = false;
    private int background = 1;
    private Music musicobject;
    int clicks = 0;

    public void setBackground(int background) {
        this.background = background;
    }

    public ChessGameFrame(int width, int height, Music musicobject) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;
        this.musicobject = musicobject;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addGif();
        addChessboard();
//        addLabel();
        addHelloButton();
        addLoadButton();
        addHuiHeButton();
        addLoadOutButton();
        addZanTingButton();
        addLoadInButton();
        Change();

        addPicture();
    }

    private void addPicture() {
        ImageIcon picture ;
        if (background == 1) {
            picture = new ImageIcon("./picture/蛮羊.png");
        } else if (background == 2) {
            System.out.println(111);
            picture = new ImageIcon("./picture/比赛场.png");
        } else if (background == 3) {
            picture = new ImageIcon();
        }else {
            picture = new ImageIcon("./picture/蛮羊.png");
        }
        j = new JLabel(picture);
        j.setBounds(0, 0, this.WIDTH, this.HEIGTH);
        add(j);
    }

    static JLabel j = new JLabel();

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 30, HEIGTH / 10);
        add(chessboard);
    }



    /**
     * 在游戏面板中添加标签
     */
//    private void addLabel() {
//        JLabel statusLabel = new JLabel();
//        statusLabel.setText("round:" + gameController.getChessboard().getCnt());
//        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
//        statusLabel.setSize(200, 30);
//        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
//        if (gameController.getChessboard().getCnt() != 0) {
//            statusLabel.repaint();
//        }
//        add(statusLabel);
//
//    }
    private void Change() {
        JButton button = new JButton("Change");
        button.setLocation(HEIGTH, HEIGTH / 10);
        button.setSize(200, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            Object[] options = {"蛮羊", "国王", "香蕉"};
            String s = (String) JOptionPane.showInputDialog(null, "请选择你喜欢的主题:\n", "主题", JOptionPane.PLAIN_MESSAGE, new ImageIcon("xx.png"), options, "xx");
            if (Objects.equals(s, "蛮羊")) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessComponent.setZhuTi(1);
                        gameController.getChessboard().getChessComponents()[i][j].repaint();
                    }
                }
                j.setIcon(null);
                j.setIcon(new ImageIcon("./picture/蛮羊.png"));
            }
            if (Objects.equals(s, "国王")) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessComponent.setZhuTi(2);
                        gameController.getChessboard().getChessComponents()[i][j].repaint();
                    }
                }
                j.setIcon(null);
                j.setIcon(new ImageIcon("./picture/比赛场.png"));
            }
        });
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        List<String> srt = new ArrayList<>();
        srt.add("RNBQKBNR");
        srt.add("PPPPPPPP");
        srt.add("________");
        srt.add("________");
        srt.add("________");
        srt.add("________");
        srt.add("pppppppp");
        srt.add("rnbqkbnr");
        srt.add("b");

        JButton button = new JButton("Show Hello Here");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.getChessboard().loadChessGame(srt);
                System.out.println(1);
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addHuiHeButton() {
        JButton button = new JButton("round");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s;
                if (gameController.getChessboard().getCurrentColor().equals(ChessColor.BLACK)){
                    s="黑方";
                }
                else {
                    s="白方";
                }
                JOptionPane.showMessageDialog(null, "当前回合数:"
                        + gameController.getChessboard().getClickController().getCnt()+"  当前行棋方:"+s);
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addZanTingButton() {
        JButton button = new JButton("STOP");
        button.setLocation(HEIGTH, HEIGTH / 10 + 180);
        button.setSize(200, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filepath = "./music/Supercell Games - Battle 01.mp3";
//                Music musicObject = null;
                clicks++;
                if (clicks % 2 == 1) {
//                    musicObject = new Music(filepath);
                    musicobject.stop();
                } else if (clicks % 2 == 0) {
                    try {
                        musicobject.play();
                    } catch (FileNotFoundException | JavaLayerException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.loadGameFromFile(path);
        });
    }


    private void addLoadOutButton() {
        JButton button = new JButton("LoadOut");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            String filePath = "./resource/" + path + ".txt";
            String content = "";
            FileOutputStream fos = null;

            try {
                // true代表叠加写入，没有则代表清空一次写一次
                fos = new FileOutputStream(filePath, true);
                content = gameController.getChessboard().getChessboardGraph();
                byte[] bytes = content.getBytes();
                fos.write(bytes);

            } catch (IOException s) {
                s.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }


        });
    }

    private void addLoadInButton() {
        JButton button = new JButton("LoadIn");
        button.setLocation(HEIGTH, HEIGTH / 10 + 520);
        button.setSize(200, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            String filePath = "./resource/" + path + ".txt";
            FileInputStream fis = null;
            String result = "";
            List<String> a = new ArrayList<>();
//            try {
//
//                int size = fis.available();
//                byte[] bytes = new byte[size];
//                fis.read(bytes);
//
//                for (int i = 0; i < bytes.length; i++) {
//                    a.add(String.valueOf((bytes[i])));
//                }
////                应该传入9*8的，但是都变成一行了
//                gameController.getChessboard().loadChessGame(a);
//            } catch (IOException s) {
//                s.printStackTrace();
//            }

            try {
                fis = new FileInputStream(filePath);
//                if (!f.exists()){
//                    return null;
//                }
                BufferedReader br = new BufferedReader(new FileReader(filePath));

//构造一个BufferedReader类来读取文件
                String s = null;
                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    a.add(s);
                }
                br.close();
            } catch (Exception b) {
                b.printStackTrace();
            }
            gameController.getChessboard().loadChessGame(a);
//       } finally {
//                try {
//                    fis.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }

        });
    }

    private void addGif(){
        MyJPanel m = new MyJPanel();
        m.setBounds(HEIGTH-90, HEIGTH / 10 + 390,100,100);
        Timer timer=new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.repaint();
            }
        });
        timer.start();
        add(m);
    }
}
class MyJPanel extends JPanel{
    int i=0;
    ImageIcon[] gif=new ImageIcon[22];
    public void paint(Graphics g){
        for (int i=0;i<22;i++){
           int j=i+1;
            gif[i]=new ImageIcon("./picture/全部GIF图片帧/"+j+".png");
//            gif[i]=new ImageIcon("picture/全部GIF图片帧/1.png");
        }
        super.paint(g);
        g.drawImage(gif[i%22].getImage(),0,0,this);
        i++;
    }

}