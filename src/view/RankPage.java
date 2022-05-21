package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RankPage extends JFrame{
    public static List<String>ScoreList=new ArrayList<>();
    public static List<String>NameList = new ArrayList<>();
    public static List<String>ChangingNameList = new ArrayList<>();
    public static List<String>ChangingScoreList = new ArrayList<>();
    public static List<String>NameOrderList = new ArrayList<>();
    public static List<Integer>ScoreOrderList = new ArrayList<>();
    public static int iOrder = 0;
    public static int iScore = 0;

    public RankPage(int a , int b) {

        int width = a/2 ;
        int height = b/13;
        String[] strings = {"  用户名", "   胜局数"};
        JLabel[][] jLabels = new JLabel[2][11];

        this.setTitle(" CHESS ");
        this.setLayout(null);
        Insets inset = this.getInsets();
        this.setSize(a + inset.left + inset.right, b + inset.top + inset.bottom);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        JButton quitRankBtn = new JButton("退出");
        quitRankBtn.setSize(120, 50);
        quitRankBtn.setLocation((a/2)-60,b*12/13);
        add(quitRankBtn);

        quitRankBtn.setContentAreaFilled(false);
        quitRankBtn.setBorderPainted(false);
        quitRankBtn.setFocusPainted(false);
        quitRankBtn.setFont(new Font("华文行楷",Font.PLAIN,30));
        quitRankBtn.setForeground(new Color(201, 184, 184));

        quitRankBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                quitRankBtn.setForeground(new Color(227, 150, 150));
                quitRankBtn.setFont(new Font("华文琥珀",Font.PLAIN,35));
            }
            public void mouseExited(MouseEvent e) {
                quitRankBtn.setForeground(new Color(201, 184, 184));
                quitRankBtn.setFont(new Font("华文行楷",Font.PLAIN,30));
            }
        });

        quitRankBtn.addActionListener(e -> {
                while (0 < NameList.size()){
                    NameList.remove(0);
                    ScoreList.remove(0);
                    NameOrderList.remove(0);
                    ScoreOrderList.remove(0);
                }

            this.dispose();
        });
        for (int i = 0; i < 2; i++) {
            jLabels[i][0] = new JLabel(strings[i]);
            jLabels[i][0].setFont(new Font("华文琥珀", Font.PLAIN, 25));
            jLabels[i][0].setBounds(width * i, 0, width, height);
            jLabels[i][0].setVisible(true);
            jLabels[i][0].setOpaque(false);
            jLabels[i][0].setForeground(new Color(201, 184, 184));
            this.add(jLabels[i][0]);
        }

        for (int i = 0; i < Math.min(NameOrderList.size(), 10); i++) {
            jLabels[0][i + 1] = new JLabel("  " + NameOrderList.get(i));
            jLabels[0][i + 1].setFont(new Font("华文琥珀", Font.PLAIN, 25));
            jLabels[0][i + 1].setBounds(0, (i + 1) * height, width, height);
            jLabels[0][i + 1].setVisible(true);
            jLabels[0][i + 1].setOpaque(false);
            jLabels[0][i + 1].setForeground(new Color(227, 150, 150));
            this.add(jLabels[0][i + 1]);
        }

        for (int i = 0; i < Math.min(ScoreOrderList.size(), 10); i++) {
            jLabels[1][i + 1] = new JLabel("  " + String.valueOf(ScoreOrderList.get(i)));
            jLabels[1][i + 1].setFont(new Font("华文琥珀", Font.PLAIN, 25));
            jLabels[1][i + 1].setBounds(width, (i + 1) * height, width, height);
            jLabels[1][i + 1].setVisible(true);
            jLabels[1][i + 1].setOpaque(false);
            jLabels[1][i + 1].setForeground(new Color(27, 150, 150));
            this.add(jLabels[1][i + 1]);
        }
}
    public static List<String> readFile(String path) throws IOException {
        List<String> list = new ArrayList<String>();
        FileInputStream fileInputStream = new FileInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            if (line.lastIndexOf("---") < 0) {
                list.add(line);
            }
        }
        bufferedReader.close();
        inputStreamReader.close();
        fileInputStream.close();
        return list;
    }
    public static void InitialData() throws IOException {

        for (int i = 0; i < readFile("DLC/name.txt").size(); i++){

            //录入name, score到arraylist//
            NameList.add(readFile("DLC/name.txt").get(i));
            ScoreList.add(readFile("DLC/score.txt").get(i));

        }

    }
    public static void Order() throws IOException {

        while(NameList.size() > 0) {
            for (int i = 0; i < NameList.size(); i++) {
                if (Integer.parseInt(ScoreList.get(i)) >= iScore) {
                    iScore = Integer.parseInt(ScoreList.get(i));
//                    iScore = ScoreList.get(i);
                    iOrder = i;
                }
            }

            ScoreOrderList.add(iScore);
            NameOrderList.add(NameList.get(iOrder));

            NameList.remove(iOrder);
            ScoreList.remove(iOrder);

            iScore = 0;
            iOrder = 0;

        }
    }


}
