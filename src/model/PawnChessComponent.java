package model;

import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的象
 */
public class PawnChessComponent extends ChessComponent {
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
    private Image pawnImage;
    private int id = 1;
    private ChessColor c;

    public int getId() {
        return id;
    }

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            c = color;
            if (color == ChessColor.WHITE) {
                pawnImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
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
      if (source.getX()==destination.getX()&&source.getY()==destination.getY()){
          return false;
      }else {
          if (chessComponents[destination.getX()][destination.getY()].getChessColor() != c) {
              if (pawnImage == BISHOP_BLACK) {
                  if (destination.getX() - source.getX() == 2 && destination.getY() == source.getY() && id == 1) {
                      if (!(chessComponents[destination.getX() - 1][destination.getY()] instanceof EmptySlotComponent)) {
                          setPawnRiver(true);
                          return false;
                      }

                  } else if (destination.getY() == source.getY() && destination.getX() - source.getX() == 1) {
                      if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                          return false;
                      }
                  } else if (chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.WHITE && destination.getX() - source.getX() == 1 && Math.abs(destination.getY() - source.getY()) == 1) {

                  }
                  else if (source.getX()==4&&destination.getX() - source.getX() == 1&&
                          source.getY()-destination.getY()==1&&chessComponents[source.getX()][destination.getY()].chessColor.equals(ChessColor.WHITE)&&
                          chessComponents[source.getX()][destination.getY()] instanceof PawnChessComponent
                          && chessComponents[source.getX()][destination.getY()].isPawnRiver()){

                  }
                  else {
                      return false;
                  }
              }
              else if (pawnImage == BISHOP_WHITE) {
                  if (source.getX() - destination.getX() == 2 && destination.getY() == source.getY() && id == 1) {
                      if (!(chessComponents[destination.getX() + 1][destination.getY()] instanceof EmptySlotComponent)) {
                          setPawnRiver(true);
                          System.out.println("FUCK");
                          return false;
                      }
                  } else if (destination.getY() == source.getY() && source.getX() - destination.getX() == 1) {
                      if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                          return false;
                      }
                  } else if (chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.BLACK && destination.getX() - source.getX() == -1 && Math.abs(destination.getY() - source.getY()) == 1) {

                  }
                  else if (source.getX()==3&&destination.getX() - source.getX() == -1&&
                          source.getY()-destination.getY()==1&&chessComponents[source.getX()][destination.getY()].chessColor.equals(ChessColor.BLACK)&&
                          chessComponents[source.getX()][destination.getY()] instanceof PawnChessComponent
                          && chessComponents[source.getX()][destination.getY()].isPawnRiver()){

                  }
                  else {
                      return false;
                  }
              } else {
                  return false;
              }
              id--;
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
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());

        }
        if (now) {
            g.setColor(Color.BLUE);
            g.drawRect(0,0,getWidth()-1,getHeight()-1);
        }
    }
//    public void HuaDian(ChessComponent[][] chessComponents){
//
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                canMoveTo( chessComponents,new  ChessboardPoint(i,j));
//                g.fillOval((getX()-i)*getWidth(),(getY()-j)*getHeight(),getWidth()/2,getHeight()/2);
//            }
//        }
//    }
}
