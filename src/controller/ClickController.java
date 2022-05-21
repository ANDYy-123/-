package controller;


import javazoom.jl.decoder.JavaLayerException;
import model.ChessComponent;
import view.ChessGameFrame;
import view.Chessboard;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;
    private static int cnt=0;

    public Chessboard getChessboard() {
        return chessboard;
    }

    public int getCnt() {
        return cnt;
    }

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) throws IOException {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                chessboard.drawPoint(first);
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                chessboard.drawPoint(first);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();

            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                cnt++;
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                chessboard.drawPoint(first);
                first = null;

            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(Chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }


}
