package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的车
 */
public class KingChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image KING_WHITE;
    private static Image KING_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image kingImage;
    private boolean WangCun;
    private ChessColor c;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));

        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));

        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            c = color;
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKingImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */
//    public boolean JiangJun(ChessComponent[][] chessComponents, ChessboardPoint destination) {
//        ChessColor a;
//        if (kingImage==KING_WHITE){
//            a=ChessColor.WHITE;
//        }
//        else {
//            a=ChessColor.BLACK;
//        }
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if ((!chessComponents[i][j].chessColor.equals(a))  && chessComponents[i][j].canMoveTo(chessComponents, destination)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        boolean p = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() != c
                        && (!(chessComponents[i][j] instanceof KingChessComponent))
                                 &&  chessComponents[i][j].canMoveTo(chessComponents, destination)) {
                    p = true;
                    break;
                }

            }
        }
        if ((Math.abs(destination.getX() - source.getY()) != 0) || (Math.abs(destination.getY() - source.getX()) != 0)) {
            if (chessComponents[destination.getX()][destination.getY()].getChessColor() != c) {
                if (!p) {
                    if (Math.abs(source.getX() - destination.getX()) == 1 && source.getY() == destination.getY()) {

                    } else if (Math.abs(source.getY() - destination.getY()) == 1 && source.getX() == destination.getX()) {

                    } else if (Math.abs(source.getY() - destination.getY()) == 1 && Math.abs(source.getX() - destination.getX()) == 1) {

                    } else { // Not on the same row or the same column.
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

//    public boolean PanDuanWangCun(ChessComponent[][] chessComponents) {
//        WangCun = false;
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (this.chessColor == ChessColor.BLACK) {
//                    if (chessComponents[i][j].chessColor == ChessColor.BLACK
//                            && chessComponents[i][j] instanceof KnightChessComponent) {
//                        WangCun = true;
//                    }
//                }
//            }
//        }
//        return WangCun;
//    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(kingImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
//            g.drawOval(50, 50, getWidth(), getHeight());
        }
    }
}
