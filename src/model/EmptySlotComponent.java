package model;

import view.ChessboardPoint;
import controller.ClickController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {

    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, ClickController listener, int size) {
        super(chessboardPoint, location, ChessColor.NONE, listener, size);
    }
    @Override
    public List<ChessboardPoint> canMoveTo(ChessComponent[][] chessComponents){
        List<ChessboardPoint> a=new ArrayList<>();
        return a;
    }
    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        if (isNow()) { // Highlights the model if selected.
            g.setColor(Color.BLUE);
            g.fillRect(0,0,getWidth(),getHeight());
        }
        if (isPoint()){
            g.setColor(Color.BLACK);
            g.fillOval(getWidth()/4,getWidth()/4,getWidth()/2,getHeight()/2);
        }
    }

}
