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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static view.RankPage.readFile;


/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame<MagicMatch> extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private boolean p = false;
    private int background = 1;
    private Music musicobject;
    int clicks = 0;
    public static ArrayList<String> StepList = new ArrayList<>();
    public boolean hadName = false;
    int clickLook=1;



    public void setBackground(int background) {
        this.background = background;
    }

    public ChessGameFrame(int width, int height, Music musicobject) {
        setTitle("2022 CS102A Project "); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;
        this.musicobject = musicobject;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        List<String> srt0 = new ArrayList<>();
        srt0.add("RNBQKBNR");
        srt0.add("PPPPPPPP");
        srt0.add("________");
        srt0.add("________");
        srt0.add("________");
        srt0.add("________");
        srt0.add("pppppppp");
        srt0.add("rnbqkbnr");
        srt0.add("w");
for (int i=0;i<9;i++){
    StepList.add(srt0.get(i));
}


        addHuiFang();
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
        addRankingButton();
        addRepentanceButton();
        addRegisterButton();
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

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("华文行楷", Font.PLAIN, 25));
        button.setForeground(new Color(43, 43, 43));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(218, 78, 78));
                button.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(43, 43, 43));
                button.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });
        button.addActionListener(e -> {
            System.out.println("Click load");
            Object[] options = {"蛮羊", "国王", "香蕉"};
            String s = (String) JOptionPane.showInputDialog(null, "请选择你喜欢的主题:\n", "主题", JOptionPane.PLAIN_MESSAGE, new ImageIcon("xx.png"), options, "xx");
            if (Objects.equals(s, "蛮羊")) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessComponent.setZhuTi(1);
                        Chessboard.getChessComponents()[i][j].repaint();
                    }
                }
                j.setIcon(null);
                j.setIcon(new ImageIcon("./picture/蛮羊.png"));
            }
            if (Objects.equals(s, "国王")) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessComponent.setZhuTi(2);
                        Chessboard.getChessComponents()[i][j].repaint();
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
        srt.add("w");

        JButton button = new JButton("New Game");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.getChessboard().loadChessGame(srt);
                Chessboard.setCnt(0);
                for (int i=0;i<StepList.size();i++){
                    StepList.remove(i);
                }

            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("华文行楷", Font.PLAIN, 25));
        button.setForeground(new Color(43, 43, 43));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(218, 78, 78));
                button.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(43, 43, 43));
                button.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });
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
                        + gameController.getChessboard().getCnt()+"  当前行棋方:"+s);
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("华文行楷", Font.PLAIN, 25));
        button.setForeground(new Color(43, 43, 43));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(218, 78, 78));
                button.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(43, 43, 43));
                button.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });
    }

    private void addZanTingButton() {
        JButton button = new JButton("STOP");
        button.setLocation(HEIGTH, HEIGTH / 10 + 180);
        button.setSize(200, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("华文行楷", Font.PLAIN, 25));
        button.setForeground(new Color(43, 43, 43));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(218, 78, 78));
                button.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(43, 43, 43));
                button.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });
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
        button.setSize(200, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("华文行楷", Font.PLAIN, 25));
        button.setForeground(new Color(201, 184, 184));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(218, 78, 78));
                button.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(201, 184, 184));
                button.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });
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

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("华文行楷", Font.PLAIN, 25));
        button.setForeground(new Color(43, 43, 43));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(218, 78, 78));
                button.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(43, 43, 43));
                button.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });
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

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("华文行楷", Font.PLAIN, 25));
        button.setForeground(new Color(43, 43, 43));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(218, 78, 78));
                button.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(43, 43, 43));
                button.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });
        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            String filePath = "./resource/" + path;
            Path paths = new File(filePath).toPath();
            try {
                String mimeType = Files.probeContentType(paths);
                System.out.println(mimeType);
                if (Objects.equals(mimeType, "text/plain")){
                    FileInputStream fis = null;
                    String result = "";
                    List<String> a = new ArrayList<>();
                    try {
                        fis = new FileInputStream(filePath);
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
                }
                else {
                    JOptionPane.showMessageDialog(null, "注意文件格式！104", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        });
    }

    private void addGif(){
        MyJPanel m = new MyJPanel();
        m.setBounds(HEIGTH-90, HEIGTH / 10 + 390,35,35);
        Timer timer=new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.repaint();
            }
        });
        timer.start();
        add(m);
    }

    private void addRankingButton() {
        JButton rankGameBtn = new JButton("Ranking");
        rankGameBtn.setLocation(HEIGTH, HEIGTH / 10 + 560);
        rankGameBtn.setSize(200, 30);
        rankGameBtn.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(rankGameBtn);

        rankGameBtn.setContentAreaFilled(false);
        rankGameBtn.setBorderPainted(false);
        rankGameBtn.setFocusPainted(false);
        rankGameBtn.setFont(new Font("华文行楷", Font.PLAIN, 25));
        rankGameBtn.setForeground(new Color(43, 43, 43));

        rankGameBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                rankGameBtn.setForeground(new Color(218, 78, 78));
                rankGameBtn.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                rankGameBtn.setForeground(new Color(43, 43, 43));
                rankGameBtn.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });
        rankGameBtn.addActionListener(e -> {
            try {
                RankPage.InitialData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                RankPage.Order();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            RankPage rankPage = new RankPage(400,800);
            rankPage.setVisible(true);
            //
            while (0 < RankPage.NameList.size()){
                RankPage.NameList.remove(0);
                RankPage.ScoreList.remove(0);
                RankPage.NameOrderList.remove(0);
                RankPage.ScoreOrderList.remove(0);
            }
            //
        });
    }

    private void addHuiFang(){
        JButton button = new JButton("Review");
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("华文行楷", Font.PLAIN, 25));
        button.setForeground(new Color(43, 43, 43));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(218, 78, 78));
                button.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(43, 43, 43));
                button.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });

        button.addActionListener(e -> {
//            String path = JOptionPane.showInputDialog(this, "请输入悔棋步数");
//            int pa= Integer.parseInt(path);

            System.out.println("clicked RegretGameBtn");
            List<String> a = new ArrayList<>();
            for (int i=clickLook*9-9;i<clickLook*9;i++){
                a.add(StepList.get(i));
            }

            gameController.getChessboard().loadChessGame(a);
            Chessboard.setCnt(clickLook-1);
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    Chessboard.getChessComponents()[i][j].repaint();
                }
            }
clickLook++;
        });

    }

    private void addRepentanceButton() {
        JButton rankGameBtn = new JButton("Repentance");
        rankGameBtn.setLocation(HEIGTH, HEIGTH / 10 + 600);
        rankGameBtn.setSize(200, 30);
        rankGameBtn.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(rankGameBtn);

        rankGameBtn.setContentAreaFilled(false);
        rankGameBtn.setBorderPainted(false);
        rankGameBtn.setFocusPainted(false);
        rankGameBtn.setFont(new Font("华文行楷", Font.PLAIN, 25));
        rankGameBtn.setForeground(new Color(43, 43, 43));

        rankGameBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                rankGameBtn.setForeground(new Color(218, 78, 78));
                rankGameBtn.setFont(new Font("华文琥珀", Font.PLAIN, 30));
            }

            public void mouseExited(MouseEvent e) {
                rankGameBtn.setForeground(new Color(43, 43, 43));
                rankGameBtn.setFont(new Font("华文行楷", Font.PLAIN, 25));
            }
        });
        rankGameBtn.addActionListener(e -> {
//            String path = JOptionPane.showInputDialog(this, "请输入悔棋步数");
//            int pa= Integer.parseInt(path);
            System.out.println("clicked RegretGameBtn");
            List<String> a = new ArrayList<>();
            for (int i=8;i>=0;i--){
                a.add(StepList.get(StepList.size() - i-10));
            }

            gameController.getChessboard().loadChessGame(a);
            Chessboard.setCnt(gameController.getChessboard().getCnt()-1);
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    Chessboard.getChessComponents()[i][j].repaint();
                }
            }
            for (int i=8;i>=0;i--){
                StepList.remove(StepList.size() - i-1 );
            }

            // TODO: 2022/5/20 :改变到。。下
        });
    }

    private void addRegisterButton() {
        JButton CreateGameBtn = new JButton("Register");
        CreateGameBtn.setLocation(HEIGTH, HEIGTH / 10 +80);
        CreateGameBtn.setSize(200, 30);
        CreateGameBtn.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(CreateGameBtn);

        CreateGameBtn.setContentAreaFilled(false);
        CreateGameBtn.setBorderPainted(false);
        CreateGameBtn.setFocusPainted(false);
        CreateGameBtn.setFont(new Font("华文行楷",Font.PLAIN,30));
        CreateGameBtn.setForeground(new Color(43, 43, 43));
        CreateGameBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                CreateGameBtn.setForeground(new Color(218, 78, 78));
                CreateGameBtn.setFont(new Font("华文琥珀",Font.PLAIN,35));
            }
            public void mouseExited(MouseEvent e) {
                CreateGameBtn.setForeground(new Color(43, 43, 43));
                CreateGameBtn.setFont(new Font("华文行楷",Font.PLAIN,30));
            }
        });
        CreateGameBtn.addActionListener(e -> {
            JFrame jFrame = new JFrame("用户注册");
            jFrame.setBounds(0, 0, 300, 250);
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);

            jFrame.setAlwaysOnTop(true);

            JPanel jPanel = new JPanel();
            jPanel.setLayout(null);
            jPanel.setBounds(0, 0, 300, 200);
            jPanel.setVisible(true);
            jFrame.add(jPanel);


            JTextField nameTextField = new JFormattedTextField();
            nameTextField.setVisible(true);
            nameTextField.setFont(new Font("宋体", Font.PLAIN, 30));
            nameTextField.setForeground(new Color(0, 0, 0));
            nameTextField.setBounds(0, 0, 300, 200 / 2);
            jPanel.add(nameTextField);

            JButton ok = new JButton("确定");
            ok.setContentAreaFilled(false);
            ok.setBorderPainted(false);
            ok.setFocusPainted(false);
            ok.setFont(new Font("华文琥珀",Font.PLAIN,30));
            ok.setForeground(new Color(225, 101, 101));
            jPanel.add(ok);
            ok.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    ok.setForeground(new Color(178, 3, 3));
                    ok.setFont(new Font("华文琥珀",Font.PLAIN,25));
                }
                public void mouseExited(MouseEvent e) {
                    ok.setForeground(new Color(225, 101, 101));
                    ok.setFont(new Font("华文琥珀",Font.PLAIN,30));
                }

            });
            ok.setVisible(true);
            ok.setBounds(0, 100, 150, 100);
            ok.addActionListener(e1 -> {


                try {

                    for (int i = 0; i < readFile("DLC/name.txt").size(); i++) {
                        if (readFile("DLC/name.txt").get(i).equals(nameTextField.getText())){
                            hadName = true;
                            break;
                        }
                    }

                } catch (Exception f) {
                    f.printStackTrace();
                }

                if (hadName){
                    JOptionPane.showMessageDialog(null, "用户名已存在", "CHESS", JOptionPane.INFORMATION_MESSAGE);
                    jFrame.dispose();
                }else{
                    WriteName(nameTextField.getText());
                    jFrame.dispose();
                }




            });

            JButton bye = new JButton("取消");
            bye.setContentAreaFilled(false);
            bye.setBorderPainted(false);
            bye.setFocusPainted(false);
            bye.setFont(new Font("华文琥珀",Font.PLAIN,30));
            bye.setForeground(new Color(225, 101, 101));
            jPanel.add(bye);
            bye.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    bye.setForeground(new Color(178, 3, 3));
                    bye.setFont(new Font("华文琥珀",Font.PLAIN,25));
                }
                public void mouseExited(MouseEvent e) {
                    bye.setForeground(new Color(225, 101, 101));
                    bye.setFont(new Font("华文琥珀",Font.PLAIN,30));
                }

            });
            bye.setVisible(true);
            bye.setBounds(150, 100, 150, 100);
            bye.addActionListener(e1 -> jFrame.dispose());
        });
    }
    public void WriteName(String Player) {

        File file = new File("DLC/name.txt");
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {
            fos = new FileOutputStream(file, true);
            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);

            for (int i = 0; i < readFile("DLC/name.txt").size(); i++) {
                if (readFile("DLC/name.txt").get(i).equals(Player)){
                    hadName = true;
                    break;
                }
            }
            if (!hadName){
                osw.write(Player);
                osw.write("\r\n");
                WriteScore();
            }else{
                System.out.printf("%s has existed!\n",Player);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void WriteScore(){

        File file = new File("DLC/score.txt");
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {
            fos = new FileOutputStream(file, true);
            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            osw.write("0");
            osw.write("\r\n");

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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