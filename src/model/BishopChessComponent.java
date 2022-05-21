package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示国际象棋里面的象
 */
public class BishopChessComponent extends ChessComponent {
    /**
     * 黑象和白象的图片，static使得其可以被所有象对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image bishopImage;
    private ChessColor c;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            c = color;
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘

     * @return 车棋子移动的合法性
     */
@Override
public  List<ChessboardPoint> canMoveTo(ChessComponent[][] chessComponents){
    List<ChessboardPoint> can=new ArrayList<>();
    ChessboardPoint source = getChessboardPoint();
    for (int i=0;i<8;i++){
        for (int j=0;j<8;j++){
            boolean CanMove=true;
            ChessboardPoint destination=new ChessboardPoint(i,j);
            int col = source.getY();
            int row = source.getX();
            if (col == destination.getY() && row == destination.getX()) {
                CanMove= false;
            } else {
                if (chessComponents[destination.getX()][destination.getY()].getChessColor() != c) {
                    if (Math.abs(source.getX() - destination.getX()) == Math.abs(source.getY() - destination.getY())) {
                        if (destination.getY() > source.getY() && destination.getX() > source.getX()) {
                            for (; col + 1 < destination.getY() && row + 1 < destination.getX(); col++, row++) {
                                if (!(chessComponents[row + 1][col + 1] instanceof EmptySlotComponent)) {
                                    CanMove= false;
                                }
                            }
                        }
                        else if (destination.getY() < source.getY() && destination.getX() > source.getX()) {
                            for (; col - 1 > destination.getY() && row + 1 < destination.getX(); col--, row++) {
                                if (!(chessComponents[row + 1][col - 1] instanceof EmptySlotComponent)) {
                                    CanMove= false;
                                }
                            }
                        }
                        else if (destination.getY() > source.getY() && destination.getX() < source.getX()) {
                            for (; col + 1 < destination.getY() && row - 1 > destination.getX(); col++, row--) {
                                if (!(chessComponents[row - 1][col + 1] instanceof EmptySlotComponent)) {
                                    CanMove= false;
                                }
                            }
                        }
                        else if (destination.getY() < source.getY() && destination.getX() < source.getX()) {
                            for (; col - 1 > destination.getY() && row - 1 > destination.getX(); col--, row--) {
                                if (!(chessComponents[row - 1][col - 1] instanceof EmptySlotComponent)) {
                                    CanMove= false;
                                }
                            }
                        }
                        else {
                            CanMove= false;
                        }
                    } else {
                        CanMove= false;
                    }
                }
                else {
                    CanMove= false;
                }
            }
            if (CanMove){
                can.add(destination);
            }
        }
    }
return can;
}

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int col = source.getY();
        int row = source.getX();
        if (col == destination.getY() && row == destination.getX()) {
            return false;
        } else {
            if (chessComponents[destination.getX()][destination.getY()].getChessColor() != c) {
                if (Math.abs(source.getX() - destination.getX()) == Math.abs(source.getY() - destination.getY())) {
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
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        move++;
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
        g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
//        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
        if (now) {
            g.setColor(Color.BLUE);
            g.drawRect(0,0,getWidth()-1,getHeight()-1);
        }
    }
}
