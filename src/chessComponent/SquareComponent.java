package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 这个类是一个抽象类，主要表示8*4棋盘上每个格子的棋子情况。
 * 有两个子类：
 * JComponent: 继承它是要用到JComponent的特性
 * <p>
 * 1. EmptySlotComponent: 空棋子
 * 2. ChessComponent: 表示非空棋子
 */
public abstract class SquareComponent extends JComponent{
    private static final Color squareColor = new Color(250, 220, 190);
    protected static int spacingLength;
    protected static final Font CHESS_FONT = new Font("宋体", Font.BOLD, 36);

    /**
     * chessboardPoint: 表示8*4棋盘中，当前棋子在棋格对应的位置，如(0, 0), (1, 0)等等
     * chessColor: 表示这个棋子的颜色，有红色，黑色，无色三种
     * isReversal: 表示是否翻转
     * selected: 表示这个棋子是否被选中
     * isProbable: 表示选中（isSelected）的棋子可能去走这一步
     */
    private ChessboardPoint chessboardPoint;
    protected final ChessColor chessColor;//枚举类的使用
    protected boolean isReversal;
    private boolean selected;
    public boolean isProbable;
    protected int rank;

    public int getRank() {
        return rank;
    }


    /**
     * handle click event
     */
    private final ClickController clickController;

    protected SquareComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);//点的窗口坐标（用calculatePoint计算）
        setSize(size, size);
        this.chessboardPoint = chessboardPoint;//棋盘位置坐标
        this.chessColor = chessColor;
        this.selected = false;
        this.clickController = clickController;
        this.isReversal = false;
        this.isProbable = false;
    }

    public boolean isReversal() {
        return isReversal;
    }


    public void setReversal(boolean reversal) {
        isReversal = reversal;
    }
    public boolean isProbable() {
        return isProbable;
    }

    public void setProbable(boolean probable) {
        isProbable = probable;
    }

    public static void setSpacingLength(int spacingLength) {
        SquareComponent.spacingLength = spacingLength;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @param another 主要用于和2另外一个棋子交换位置
     *                <br>
     *                调用时机是在移动棋子的时候，将操控的棋子和对应的空位置棋子(EmptySlotComponent)做交换
     */
    public void swapLocation(SquareComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    /**
     * @param e 响应鼠标监听事件
     *          <br>
     *          当接收到鼠标动作的时候，这个方法就会自动被调用，调用监听者的onClick方法，处理棋子的选中，移动等等行为。
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.printf("Click [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            clickController.onClick(this);
        }
    }

    /**
     * @param chessboard  棋盘
     * @param destination 目标位置，如(0, 0), (0, 1)等等
     * @return this棋子对象的移动规则和当前位置(chessboardPoint)能否到达目标位置
     * <br>
     * 这个方法主要是检查移动的合法性，如果合法就返回true，反之是false。
     */
    //todo: Override this method for Cannon
    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination) {
        SquareComponent destinationChess = chessboard[destination.getX()][destination.getY()];

        boolean HongHei;//是否为对方棋子
        if (!destinationChess.getChessColor().equals(this.getChessColor())) {
            HongHei = true;
        } else HongHei = false;

        //如果不是炮
        if (this.rank != 2) {
            if ((Math.abs(this.chessboardPoint.getX() - destination.getX()) == 1 && this.chessboardPoint.getY() - destination.getY() == 0) || (Math.abs(this.chessboardPoint.getY() - destination.getY()) == 1 && this.chessboardPoint.getX() - destination.getX() == 0)) {
                boolean BingChiJiang;//兵吃将
                if (this.rank == 1 && destinationChess.rank == 7) {
                    BingChiJiang = true;
                } else BingChiJiang = false;
                //               空的可以走                                 翻出来的                          对方的     兵吃将           大吃小（除了将不能吃兵）
                return destinationChess instanceof EmptySlotComponent || (destinationChess.isReversal && HongHei && (BingChiJiang || (this.rank >= destinationChess.rank && !(this.rank == 7 && destinationChess.rank == 1))));
            } else return false;
        }


        //如果是炮
        else {
            int count = 0;
            if (this.chessboardPoint.getX() == destination.getX()) {
                if (this.chessboardPoint.getY() > destination.getY() + 1) {
                    for (int i = destination.getY() + 1; i < this.chessboardPoint.getY(); i++) {
                        if (chessboard[destination.getX()][i] instanceof EmptySlotComponent) {
                            //中间是空棋子
                        } else count++;
                    }
                    if (count == 1) {
                        //                               不是空棋子                               没翻出来的可以吃              翻出来对面的可以吃
                        return !(destinationChess instanceof EmptySlotComponent)&&((!destinationChess.isReversal) || (destinationChess.isReversal && HongHei));
                    } else return false;
                } else if (destination.getY() > this.chessboardPoint.getY() + 1) {
                    for (int i = this.chessboardPoint.getY()+ 1; i <destination.getY() ; i++) {
                        if (chessboard[destination.getX()][i] instanceof EmptySlotComponent) {
                            //中间是空棋子
                        } else count++;
                    }
                    if (count == 1) {
                        //                没翻出来的可以吃              翻出来对面的可以吃
                        return !(destinationChess instanceof EmptySlotComponent)&&((!destinationChess.isReversal) || (destinationChess.isReversal && HongHei));
                    } else return false;
                }else return false;

            } else if (this.chessboardPoint.getY() == destination.getY()) {
                if (this.chessboardPoint.getX() > destination.getX() + 1) {
                    for (int i = destination.getX() + 1; i < this.chessboardPoint.getX(); i++) {
                        if (chessboard[i][destination.getY()] instanceof EmptySlotComponent) {
                            //中间是空棋子
                        } else count++;
                    }
                    if (count == 1) {
                        //                没翻出来的可以吃              翻出来对面的可以吃
                        return !(destinationChess instanceof EmptySlotComponent)&&((!destinationChess.isReversal) || (destinationChess.isReversal && HongHei));
                    } else return false;
                } else if (destination.getX() > this.chessboardPoint.getX() + 1) {
                    for (int i = this.chessboardPoint.getX()+ 1; i <destination.getX() ; i++) {
                        if (chessboard[i][destination.getY()] instanceof EmptySlotComponent) {
                            //中间是空棋子
                        } else count++;
                    }
                    if (count == 1) {
                        //                没翻出来的可以吃              翻出来对面的可以吃
                        return !(destinationChess instanceof EmptySlotComponent)&&((!destinationChess.isReversal) || (destinationChess.isReversal && HongHei));
                    } else return false;
                }else return false;
            } else return false;
        }

//        SquareComponent destinationChess = chessboard[destination.getX()][destination.getY()];
//        return destinationChess.isReversal|| destinationChess instanceof EmptySlotComponent;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        System.out.printf("repaint chess [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
        g.setColor(squareColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
    }

    /**
     * 为了在判断isprobable时用point找棋子加的方法
     * @param chessboard
     * @param chessboardPoint
     * @return
     */
    public SquareComponent searchChess(SquareComponent[][] chessboard,ChessboardPoint chessboardPoint){
        SquareComponent target = null;
        for(int i = 0; i < chessboard.length; i++){
            for(int j = 0; j < chessboard[i].length; j++){
                if(i == chessboardPoint.getX() && j == chessboardPoint.getY())
                    target = chessboard[i][j];
            }
        }
        return target;
    }



}
