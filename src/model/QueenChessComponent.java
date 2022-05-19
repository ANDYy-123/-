package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的象
 */
public class QueenChessComponent extends ChessComponent {
    /**
     * 黑象和白象的图片，static使得其可以被所有象对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image queenImage;
    private ChessColor c;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            c = color;
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int col;
        int row;
        if ((Math.abs(destination.getX() - source.getY()) != 0) || (Math.abs(destination.getY() - source.getX()) != 0)) {
            if (chessComponents[destination.getX()][destination.getY()].getChessColor() != c) {
                if (Math.abs(source.getX() - destination.getX()) == Math.abs(source.getY() - destination.getY())) {
                    col = source.getY();
                    row = source.getX();
                    if (destination.getY() > source.getY() && destination.getX() > source.getX()) {
                        for (; col + 1 < destination.getY() && row + 1 < destination.getX(); col++, row++) {
                            if (!(chessComponents[row + 1][col + 1] instanceof EmptySlotComponent)) {
                                return false;
                            }
                        }
                    } else if (destination.getY() < source.getY() && destination.getX() > source.getX()) {
                        for (; col - 1 > destination.getY() && row + 1 < destination.getX(); col--, row++) {
                            if (!(chessComponents[row + 1][col - 1] instanceof EmptySlotComponent)) {
                                return false;
                            }
                        }
                    } else if (destination.getY() > source.getY() && destination.getX() < source.getX()) {
                        for (; col + 1 < destination.getY() && row - 1 > destination.getX(); col++, row--) {
                            if (!(chessComponents[row - 1][col + 1] instanceof EmptySlotComponent)) {
                                return false;
                            }
                        }
                    } else if (destination.getY() < source.getY() && destination.getX() < source.getX()) {
                        for (; col - 1 > destination.getY() && row - 1 > destination.getX(); col--, row--) {
                            if (!(chessComponents[row - 1][col - 1] instanceof EmptySlotComponent)) {
                                return false;
                            }
                        }
                    }else {
                        return false;
                    }
                } else if (source.getX() == destination.getX()) {
                    row = source.getX();
                    for (col = Math.min(source.getY(), destination.getY()) + 1;
                         col < Math.max(source.getY(), destination.getY()); col++) {
                        if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                            return false;
                        }
                    }
                } else if (source.getY() == destination.getY()) {
                    col = source.getY();
                    for (row = Math.min(source.getX(), destination.getX()) + 1;
                         row < Math.max(source.getX(), destination.getX()); row++) {
                        if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                            return false;
                        }
                    }
                } else { // Not on the same row or the same column.
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

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
