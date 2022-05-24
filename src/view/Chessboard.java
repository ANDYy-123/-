package view;


import Music.Music;
import javazoom.jl.decoder.JavaLayerException;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private static final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private static int cnt = 0;
    StringBuilder b = new StringBuilder();
    private int co;
    private int ro;
    private ChessColor C;
    private int coo;
    private int roo;
    private int cnt1;
    ChessboardPoint a = null;
    ChessboardPoint attack = null;
    boolean attacknum = false;
    //    private ChessComponent[][]

    public int getCnt() {
        return cnt;
    }

    public static void setCnt(int cnt) {
        Chessboard.cnt = cnt;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
//        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
//        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
//        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
//        initBishopOnBoard(0, 2, ChessColor.BLACK);
//        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
//        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
//        initQueenOnBoard(0, 3, ChessColor.BLACK);
//        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
//        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//            initPawnOnBoard(1, i, ChessColor.BLACK);
//        }
//        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//            initPawnOnBoard(6, i, ChessColor.WHITE);
//        }
//        initKnightOnBoard(0, 1, ChessColor.BLACK);
//        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
//        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
//        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
    }

    public ClickController getClickController() {
        return clickController;
    }

    public static ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) throws IOException {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        boolean kingAndRook = chess1 instanceof KingChessComponent && chess2 instanceof EmptySlotComponent
                && Math.abs(chess1.getChessboardPoint().getY() - chess2.getChessboardPoint().getY()) == 2;
        ChessComponent chess3;
        ChessComponent chess4;
        if (chess1 instanceof PawnChessComponent && chess2 instanceof EmptySlotComponent
                && Math.abs(chess2.getChessboardPoint().getX() - chess1.getChessboardPoint().getX()) == 2) {
            coo = chess2.getChessboardPoint().getY();
            roo = chess2.getChessboardPoint().getX();
            cnt1 = cnt;
        }
        if (cnt - cnt1 != 0) {
            chessComponents[roo][coo].setPawnRiver(false);
        }
        if (chess1 instanceof PawnChessComponent && chess2 instanceof EmptySlotComponent
                && Math.abs(chess1.getChessboardPoint().getY() - chess2.getChessboardPoint().getY()) == 1) {
            remove(chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()]);

            putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(chess1.getChessboardPoint().getX(), chess2.getChessboardPoint().getY()),
                    calculatePoint(chess1.getChessboardPoint().getX(), chess2.getChessboardPoint().getY()),
                    clickController, CHESS_SIZE));
        }
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        if (kingAndRook) {
            if (chess1.getChessboardPoint().getY() < 4) {
                chess3 = chessComponents[chess2.getChessboardPoint().getX()][0];
                chess4 = chessComponents[chess2.getChessboardPoint().getX()][3];
                chess3.swapLocation(chess4);
                int row3 = chess3.getChessboardPoint().getX(), col3 = chess3.getChessboardPoint().getY();
                chessComponents[row3][col3] = chess3;
                int row4 = chess4.getChessboardPoint().getX(), col4 = chess4.getChessboardPoint().getY();
                chessComponents[row4][col4] = chess4;
                chess3.repaint();
                chess4.repaint();
            } else if (chess1.getChessboardPoint().getY() > 4) {
                chess3 = chessComponents[chess2.getChessboardPoint().getX()][7];
                chess4 = chessComponents[chess2.getChessboardPoint().getX()][5];
                chess3.swapLocation(chess4);
                int row3 = chess3.getChessboardPoint().getX(), col3 = chess3.getChessboardPoint().getY();
                chessComponents[row3][col3] = chess3;
                int row4 = chess4.getChessboardPoint().getX(), col4 = chess4.getChessboardPoint().getY();
                chessComponents[row4][col4] = chess4;
                chess3.repaint();
                chess4.repaint();
            }


        }

        chess1.repaint();
        chess2.repaint();
    }

    //判断将军
    public boolean KingJiang(ChessComponent[][] chessComponents) {
        int num = 0;
        attacknum = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((chessComponents[i][j] instanceof KingChessComponent) && chessComponents[i][j].getChessColor() != currentColor) {
                    a = chessComponents[i][j].getChessboardPoint();
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == currentColor
                        && (!((chessComponents[i][j]) instanceof KingChessComponent))
                        && chessComponents[i][j].canMoveTo(chessComponents, a)) {
                    num++;
                    attack = chessComponents[i][j].getChessboardPoint();

                }
            }
        }
        if (num > 0) {
            if (num > 1) {
                attacknum = true;
            }
            return true;
        } else {
            return false;
        }

    }


    public boolean PawnUp() {
        for (int i = 0; i < 8; i++) {
            if (chessComponents[0][i] instanceof PawnChessComponent
                    && chessComponents[0][i].getChessColor().equals(ChessColor.WHITE)) {
                co = i;
                ro = 0;
                C = ChessColor.WHITE;
                return true;
            }
            if (chessComponents[7][i] instanceof PawnChessComponent
                    && chessComponents[7][i].getChessColor().equals(ChessColor.BLACK)) {
                co = i;
                ro = 7;
                C = ChessColor.BLACK;
                return true;
            }
        }
        return false;
    }

    public boolean KingDie() {
//先判断王的自救
        if (KingJiang(chessComponents)) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessboardPoint b = new ChessboardPoint(i, j);
                    assert a != null;
                    if (chessComponents[a.getX()][a.getY()].canMoveTo(chessComponents, b)) {
                        return false;
                    }
                }
            }
            if (attacknum) {
                return true;
            } else {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (chessComponents[i][j].canMoveTo(chessComponents, attack)) {
                            return false;
                        }
                    }
                }
                if (attack.getY() == a.getY()) {
                    int col = a.getY();
                    for (int row = Math.min(a.getX(), attack.getX()); row < Math.max(a.getX(), attack.getX()); row++) {
                        ChessboardPoint dian = new ChessboardPoint(row, col);
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if (chessComponents[i][j].canMoveTo(chessComponents, dian)
                                        && chessComponents[i][j].getChessColor() != currentColor) {
                                    return false;
                                }
                            }
                        }
                    }
                } else if (a.getX() == attack.getX()) {
                    int row = a.getX();
                    for (int col = Math.min(a.getY(), attack.getY()); col < Math.max(a.getY(), attack.getY()); col++) {
                        ChessboardPoint dian = new ChessboardPoint(row, col);
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if (chessComponents[i][j].canMoveTo(chessComponents, dian)
                                        && chessComponents[i][j].getChessColor() != currentColor) {
                                    return false;
                                }
                            }
                        }
                    }
                } else if ((a.getX() > attack.getX() && a.getY() > attack.getY()) || (a.getX() < attack.getX() && a.getY() < attack.getY())) {
                    for (int row = Math.min(a.getX(), attack.getX()), col = Math.min(a.getY(), attack.getY());
                         row < Math.max(a.getX(), attack.getX()) && col < Math.max(a.getY(), attack.getY()); row++, col++) {
                        ChessboardPoint dian = new ChessboardPoint(row, col);
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if (chessComponents[i][j].canMoveTo(chessComponents, dian)
                                        && chessComponents[i][j].getChessColor() != currentColor) {
                                    return false;
                                }
                            }
                        }
                    }
                } else if ((a.getX() < attack.getX() && a.getY() > attack.getY()) || (a.getX() > attack.getX() && a.getY() < attack.getY())) {
                    for (int row = Math.max(a.getX(), attack.getX()), col = Math.min(a.getY(), attack.getY());
                         row > Math.max(a.getX(), attack.getX()) && col < Math.max(a.getY(), attack.getY()); row--, col++) {
                        ChessboardPoint dian = new ChessboardPoint(row, col);
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if (chessComponents[i][j].canMoveTo(chessComponents, dian)
                                        && chessComponents[i][j].getChessColor() != currentColor) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean KingLive() {
        int cun = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent) {
                    cun++;
                }
            }
        }
        if (cun != 2) {
            return false;
        }
        return true;

    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void drawPoint(ChessComponent chessComponent) {
        if (chessComponent.isSelected()) {
            for (int m = 0; m < chessComponent.canMoveTo(chessComponents).size(); m++) {
                int row = chessComponent.canMoveTo(chessComponents).get(m).getX();
                int col = chessComponent.canMoveTo(chessComponents).get(m).getY();
                if (chessComponents[row][col] instanceof EmptySlotComponent) {
                    chessComponents[row][col].setPoint(true);
                    chessComponents[row][col].repaint();
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessComponents[i][j] instanceof EmptySlotComponent) {
                        chessComponents[i][j].setPoint(false);
                        chessComponents[i][j].repaint();
                    }
                }
            }
        }


    }

    public void swapColor() throws IOException, JavaLayerException {
        if (PawnUp()) {
            Object[] options = {"皇后", "主教", "马", "车"};
            String s = (String) JOptionPane.showInputDialog(null, "请选择你的棋子:\n", "主题", JOptionPane.PLAIN_MESSAGE, new ImageIcon("xx.png"), options, "xx");
            if (Objects.equals(s, "皇后")) {
                remove(chessComponents[ro][co]);
                add(chessComponents[ro][co] = new EmptySlotComponent(chessComponents[ro][co].getChessboardPoint(), chessComponents[ro][co].getLocation(), clickController, CHESS_SIZE));
                initQueenOnBoard(ro, co, C);
                chessComponents[ro][co].repaint();
            } else if (Objects.equals(s, "主教")) {
                remove(chessComponents[ro][co]);
                add(chessComponents[ro][co] = new EmptySlotComponent(chessComponents[ro][co].getChessboardPoint(), chessComponents[ro][co].getLocation(), clickController, CHESS_SIZE));
                initBishopOnBoard(ro, co, C);
                chessComponents[ro][co].repaint();
            } else if (Objects.equals(s, "马")) {
                remove(chessComponents[ro][co]);
                add(chessComponents[ro][co] = new EmptySlotComponent(chessComponents[ro][co].getChessboardPoint(), chessComponents[ro][co].getLocation(), clickController, CHESS_SIZE));
                initKnightOnBoard(ro, co, C);
                chessComponents[ro][co].repaint();
            } else if (Objects.equals(s, "车")) {
                remove(chessComponents[ro][co]);
                add(chessComponents[ro][co] = new EmptySlotComponent(chessComponents[ro][co].getChessboardPoint(), chessComponents[ro][co].getLocation(), clickController, CHESS_SIZE));
                initRookOnBoard(ro, co, C);
                chessComponents[ro][co].repaint();
            }
        }
        if (KingDie()) {
            JOptionPane.showMessageDialog(null, "掘沙！吴姐！", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
            chessComponents[attack.getX()][attack.getY()].repaint();
            String filepath = "./music/人类音效3-欢呼的声音(胜利呼喊)_爱给网_aigei_com.mp3";
            Music musicObject = new Music(filepath);
            SwingUtilities.invokeLater(() -> {
                laught mainFrame = new laught(240, 240, musicObject);
                mainFrame.setVisible(true);
            });
            musicObject.play();
        } else if (KingJiang(chessComponents)) {
            JOptionPane.showMessageDialog(null, "您正在被将军！", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
            chessComponents[attack.getX()][attack.getY()].repaint();
        }
        if (!KingLive()) {
            JOptionPane.showMessageDialog(null, "掘沙！吴姐！", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
        }
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        cnt++;
        String s = getChessboardGraph();
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().equals("")) {
                ChessGameFrame.StepList.add(line);
                //每行可以做加工
            }
        }
        if (KingJiang(chessComponents)) {
            JOptionPane.showMessageDialog(null, "您不能送将！", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
            List<String> a = new ArrayList<>();
            for (int i = 8; i >= 0; i--) {
                a.add(ChessGameFrame.StepList.get(ChessGameFrame.StepList.size() - i - 10));
            }
            loadChessGame(a);
            Chessboard.setCnt(getCnt() - 1);
            for (int i = 8; i >= 0; i--) {
                ChessGameFrame.StepList.remove(ChessGameFrame.StepList.size() - i - 1);
            }
        }

    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }

    public void loadChessGame(List<String> chessboard) {
        System.out.println(chessboard.size());
        if (chessboard.size() == 9) {
            if (chessboard.get(8).charAt(0) == 'w') {
                this.currentColor = ChessColor.WHITE;
            } else if (chessboard.get(8).charAt(0) == 'b') {
                this.currentColor = ChessColor.BLACK;
            } else {
                JOptionPane.showMessageDialog(null, "行棋方错误！103", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
            }
            for (int i = 0; i < 8; i++) {
                if (chessboard.get(i).length() != 8) {
                    JOptionPane.showMessageDialog(null, "棋盘并非8*8！101", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (int j = 0; j < 8; j++) {
                        ChessboardPoint source = new ChessboardPoint(i, j);
                        PawnChessComponent pw = new PawnChessComponent(source, calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                        BishopChessComponent bw = new BishopChessComponent(source, calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                        KingChessComponent kw = new KingChessComponent(source, calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                        QueenChessComponent qw = new QueenChessComponent(source, calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                        KnightChessComponent nw = new KnightChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                        RookChessComponent rw = new RookChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                        PawnChessComponent pb = new PawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        BishopChessComponent bb = new BishopChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        KingChessComponent kb = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        QueenChessComponent qb = new QueenChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        KnightChessComponent nb = new KnightChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        RookChessComponent rb = new RookChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        EmptySlotComponent e = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE);
                        if (chessboard.get(i).charAt(j) == 'p') {
                            putChessOnBoard(pw);
                        } else if (chessboard.get(i).charAt(j) == 'b') {
                            putChessOnBoard(bw);
                        } else if (chessboard.get(i).charAt(j) == 'k') {
                            putChessOnBoard(kw);
                        } else if (chessboard.get(i).charAt(j) == 'q') {
                            putChessOnBoard(qw);
                        } else if (chessboard.get(i).charAt(j) == 'n') {
                            putChessOnBoard(nw);
                        } else if (chessboard.get(i).charAt(j) == 'r') {
                            putChessOnBoard(rw);
                        } else if (chessboard.get(i).charAt(j) == 'P') {
                            putChessOnBoard(pb);
                        } else if (chessboard.get(i).charAt(j) == 'B') {
                            putChessOnBoard(bb);
                        } else if (chessboard.get(i).charAt(j) == 'K') {
                            putChessOnBoard(kb);
                        } else if (chessboard.get(i).charAt(j) == 'Q') {
                            putChessOnBoard(qb);
                        } else if (chessboard.get(i).charAt(j) == 'N') {
                            putChessOnBoard(nb);
                        } else if (chessboard.get(i).charAt(j) == 'R') {
                            putChessOnBoard(rb);
                        } else if (chessboard.get(i).charAt(j) == '_') {
                            putChessOnBoard(e);
                        } else if (chessboard.get(i).charAt(j) == ' ') {
                            JOptionPane.showMessageDialog(null, "棋盘并非8*8！101", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, "棋子不是六种之一！102", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                        chessComponents[i][j].repaint();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "行棋方错误！103", "FBI WARNING", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public String getChessboardGraph() {
        String a;
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof PawnChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.WHITE)) {
                    b.append("p");
                }
                if (chessComponents[i][j] instanceof BishopChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.WHITE)) {
                    b.append("b");
                }
                if (chessComponents[i][j] instanceof KingChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.WHITE)) {
                    b.append("k");
                }
                if (chessComponents[i][j] instanceof QueenChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.WHITE)) {
                    b.append("q");
                }
                if (chessComponents[i][j] instanceof KnightChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.WHITE)) {
                    b.append("n");
                }
                if (chessComponents[i][j] instanceof RookChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.WHITE)) {
                    b.append("r");
                }
                if (chessComponents[i][j] instanceof PawnChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)) {
                    b.append("P");
                }
                if (chessComponents[i][j] instanceof BishopChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)) {
                    b.append("B");
                }
                if (chessComponents[i][j] instanceof KingChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)) {
                    b.append("K");
                }
                if (chessComponents[i][j] instanceof QueenChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)) {
                    b.append("Q");
                }
                if (chessComponents[i][j] instanceof KnightChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)) {
                    b.append("N");
                }
                if (chessComponents[i][j] instanceof RookChessComponent
                        && chessComponents[i][j].getChessColor().equals(ChessColor.BLACK)) {
                    b.append("R");
                }
                if (chessComponents[i][j] instanceof EmptySlotComponent) {
                    b.append("_");
                }

            }
            b.append("\n");
        }
        if (currentColor == ChessColor.BLACK) {
            b.append("b");
        }
        if (currentColor == ChessColor.WHITE) {
            b.append("w");
        }

        a = String.valueOf(b);
        return a;
    }


}
