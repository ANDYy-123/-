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
    public List<ChessboardPoint> canMoveTo(ChessComponent[][] chessComponents) {
        ChessboardPoint source = getChessboardPoint();
        List<ChessboardPoint> can=new ArrayList<>();
        for (int m=0;m<8;m++){
            for (int n=0;n<8;n++){
                boolean CanMove=true;
                ChessboardPoint destination=new ChessboardPoint(m,n);
        boolean p = false;
        boolean s=true;
        ChessboardPoint KING=new ChessboardPoint((source.getX()+destination.getX())/2,(source.getY()+destination.getY())/2);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() != c
                        && (!(chessComponents[i][j] instanceof KingChessComponent))){
                    for (int o=0;o<chessComponents[i][j].canMoveTo(chessComponents).size();o++){
                        if (destination.getX()==chessComponents[i][j].canMoveTo(chessComponents).get(o).getX()
                        &&destination.getY()==chessComponents[i][j].canMoveTo(chessComponents).get(o).getY()){
                            p = true;
                        }
                    }
                    for (int o=0;o<chessComponents[i][j].canMoveTo(chessComponents).size();o++){
                        if( (source.getX()==chessComponents[i][j].canMoveTo(chessComponents).get(o).getX()
                                &&source.getY()==chessComponents[i][j].canMoveTo(chessComponents).get(o).getY())||
                                (KING.getX()==chessComponents[i][j].canMoveTo(chessComponents).get(o).getX()
                                        &&KING.getY()==chessComponents[i][j].canMoveTo(chessComponents).get(o).getY())){
                            s=false;
                    }
                }
                }
            }
        }
        if (source.getY() == destination.getY() && source.getX() == destination.getX()) {
            CanMove= false;
        }
        else {
            if (chessComponents[destination.getX()][destination.getY()].getChessColor() != c) {
                if (!p) {
                    if (Math.abs(source.getX() - destination.getX()) == 1 && source.getY() == destination.getY()) {

                    } else if (Math.abs(source.getY() - destination.getY()) == 1 && source.getX() == destination.getX()) {

                    } else if (Math.abs(source.getY() - destination.getY()) == 1 && Math.abs(source.getX() - destination.getX()) == 1) {

                    }
                    else if (destination.getX()==source.getX()&&source.getY()-destination.getY()==2
                            &&source.getX()==0&&source.getY()==4&&chessColor.equals(ChessColor.BLACK)&&this.move==0
                            &&((chessComponents[0][0] instanceof RookChessComponent
                            &&chessComponents[0][0].chessColor.equals(ChessColor.BLACK)&&chessComponents[0][0].getMove()==0))
                            &&s){
                        if (source.getY()>destination.getY()){
                            for (int col = 1;
                                 col < 4; col++) {
                                if (!(chessComponents[0][col] instanceof EmptySlotComponent)) {
                                    CanMove= false;
                                }
                            }
                            KingAndRook=true;
                        }else {
                            CanMove= false;
                        }
                    }
                    else if (destination.getX()==source.getX()&&source.getY()-destination.getY()==2&&source.getX()==7
                            &&source.getY()==4&&chessColor.equals(ChessColor.WHITE)&&this.move==0
                            &&((chessComponents[7][0] instanceof RookChessComponent
                            &&chessComponents[7][0].chessColor.equals(ChessColor.WHITE)&&chessComponents[7][0].getMove()==0))
                            &&s){
                        if (source.getY()>destination.getY()){
                            for (int col = 1;
                                 col < 4; col++) {
                                if (!(chessComponents[7][col] instanceof EmptySlotComponent)) {
                                    CanMove= false;
                                }
                            }
                            KingAndRook=true;
                        }else {
                            CanMove= false;
                        }
                    }
                    else if (destination.getX()==source.getX()&&source.getY()-destination.getY()==-2
                            &&source.getX()==0&&source.getY()==4&&chessColor.equals(ChessColor.BLACK)&&this.move==0
                            &&((chessComponents[0][7] instanceof RookChessComponent
                            &&chessComponents[0][7].chessColor.equals(ChessColor.BLACK)&&chessComponents[0][7].getMove()==0))
                            &&s){
                        if (source.getY()<destination.getY()){
                            for (int col = 5;
                                 col < 7; col++) {
                                if (!(chessComponents[0][col] instanceof EmptySlotComponent)) {
                                    CanMove= false;
                                }
                            }
                            KingAndRook=true;
                        }else {
                            CanMove= false;
                        }
                    }
                    else if (destination.getX()==source.getX()&&source.getY()-destination.getY()==-2&&source.getX()==7
                            &&source.getY()==4&&chessColor.equals(ChessColor.WHITE)&&this.move==0
                            &&((chessComponents[7][7] instanceof RookChessComponent
                            &&chessComponents[7][7].chessColor.equals(ChessColor.WHITE)
                            &&chessComponents[7][7].getMove()==0))&&s){
                        if (source.getY()<destination.getY()){
                            for (int col = 5;
                                 col < 7; col++) {
                                if (!(chessComponents[7][col] instanceof EmptySlotComponent)) {
                                    CanMove= false;
                                }
                            }
                            KingAndRook=true;
                        }else {
                            CanMove= false;
                        }
                    }
                    else { // Not on the same row or the same column.
                        CanMove= false;
                    }
                }
                else {
                    CanMove= false;
                }
            } else {
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
        boolean p = false;
        boolean s=true;
        ChessboardPoint KING=new ChessboardPoint((source.getX()+destination.getX())/2,(source.getY()+destination.getY())/2);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() != c
                        && (!(chessComponents[i][j] instanceof KingChessComponent))
                        ) {
                    for (int o=0;o<chessComponents[i][j].canMoveTo(chessComponents).size();o++){
                        if (chessComponents[i][j].canMoveTo(chessComponents).get(o).getX()==destination.getX()&&
                                chessComponents[i][j].canMoveTo(chessComponents).get(o).getY()==destination.getY() ){
                            p = true;
                        }
                    }
                }
                if (chessComponents[i][j].getChessColor() != c
                        && (!(chessComponents[i][j] instanceof KingChessComponent))){
                    for (int o=0;o<chessComponents[i][j].canMoveTo(chessComponents).size();o++){
                        if ((chessComponents[i][j].canMoveTo(chessComponents).get(o).getX()==source.getX()&&
                                chessComponents[i][j].canMoveTo(chessComponents).get(o).getY()==source.getY()) ||
                                ((chessComponents[i][j].canMoveTo(chessComponents).get(o).getX()==KING.getX()&&
                                        chessComponents[i][j].canMoveTo(chessComponents).get(o).getY()==KING.getY()))){
                            s=false;
                        }
                    }

                }
            }
        }
        if (source.getY() == destination.getY() && source.getX() == destination.getX()) {
            return false;
        }
        else {
            if (chessComponents[destination.getX()][destination.getY()].getChessColor() != c) {
                if (!p) {
                    if (Math.abs(source.getX() - destination.getX()) == 1 && source.getY() == destination.getY()) {

                    } else if (Math.abs(source.getY() - destination.getY()) == 1 && source.getX() == destination.getX()) {

                    } else if (Math.abs(source.getY() - destination.getY()) == 1 && Math.abs(source.getX() - destination.getX()) == 1) {

                    }
                    else if (destination.getX()==source.getX()&&source.getY()-destination.getY()==2
                            &&source.getX()==0&&source.getY()==4&&chessColor.equals(ChessColor.BLACK)
                            &&this.move==0
                            &&((chessComponents[0][0] instanceof RookChessComponent
                            &&chessComponents[0][0].chessColor.equals(ChessColor.BLACK)
                            &&chessComponents[0][0].getMove()==0
                    ))
                            &&s
                    ){
                            if (source.getY()>destination.getY()){
                                for (int col = 1;
                                     col < 4; col++) {
                                    if (!(chessComponents[0][col] instanceof EmptySlotComponent)) {
                                        return false;
                                    }
                                }
                                KingAndRook=true;
                            }else {
                                return false;
                            }
                    }
                    else if (destination.getX()==source.getX()&&source.getY()-destination.getY()==2&&source.getX()==7
                            &&source.getY()==4&&chessColor.equals(ChessColor.WHITE)
                            &&this.move==0
                            &&((chessComponents[7][0] instanceof RookChessComponent
                            &&chessComponents[7][0].chessColor.equals(ChessColor.WHITE)&&chessComponents[7][0].getMove()==0))
                            &&s
                    ){
                        if (source.getY()>destination.getY()){
                            for (int col = 1;
                                 col < 4; col++) {
                                if (!(chessComponents[7][col] instanceof EmptySlotComponent)) {
                                    return false;
                                }
                            }
                            KingAndRook=true;
                        }else {
                            return false;
                        }
                    }
                    else if (destination.getX()==source.getX()&&source.getY()-destination.getY()==-2
                            &&source.getX()==0&&source.getY()==4&&chessColor.equals(ChessColor.BLACK)&&this.move==0
                            &&((chessComponents[0][7] instanceof RookChessComponent
                            &&chessComponents[0][7].chessColor.equals(ChessColor.BLACK)&&chessComponents[0][7].getMove()==0))
                            &&s){
                        if (source.getY()<destination.getY()){
                            for (int col = 5;
                                 col < 7; col++) {
                                if (!(chessComponents[0][col] instanceof EmptySlotComponent)) {
                                    return false;
                                }
                            }
                            KingAndRook=true;
                        }else {
                            return false;
                        }
                    }
                    else if (destination.getX()==source.getX()&&source.getY()-destination.getY()==-2&&source.getX()==7
                            &&source.getY()==4&&chessColor.equals(ChessColor.WHITE)&&this.move==0
                            &&((chessComponents[7][7] instanceof RookChessComponent
                            &&chessComponents[7][7].chessColor.equals(ChessColor.WHITE)
                            &&chessComponents[7][7].getMove()==0))&&s){
                        if (source.getY()<destination.getY()){
                            for (int col = 5;
                                 col < 7; col++) {
                                if (!(chessComponents[7][col] instanceof EmptySlotComponent)) {
                                    return false;
                                }
                            }
                            KingAndRook=true;
                        }else {
                            return false;
                        }
                    }
                    else { // Not on the same row or the same column.
                        return false;
                    }
                }
                else {
                    return false;
                }
            } else {
                return false;
            }
        }
        move++;
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
        if (now) {
            g.setColor(Color.BLUE);
            g.drawRect(0,0,getWidth()-1,getHeight()-1);
        }
    }
}
