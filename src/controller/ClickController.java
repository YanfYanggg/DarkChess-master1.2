package controller;


import Musics.Test;
import chessComponent.SquareComponent;
import chessComponent.EmptySlotComponent;
import model.ChessColor;
import view.ChessGameFrame;
import view.Chessboard;
import view.Menu;

import java.io.IOException;

public class ClickController {
    private final Chessboard chessboard;
    private SquareComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(SquareComponent squareComponent) {
        //判断第一次点击
        if (first == null) {//初次点击
            String path = "Music/棋子按键音.wav";
            Test.AudioPlay2 clickMusic = new Test.AudioPlay2(path);
            clickMusic.run = true;
            clickMusic.start();
            if (handleFirst(squareComponent)) {
                squareComponent.setSelected(true);//标红状态
                first = squareComponent;
                first.repaint();
                //first.judgeProbable();
            }
        }
        else {//点到有棋子的
            if (first == squareComponent) { // 再次点击自己，取消选取
                squareComponent.setSelected(false);
                SquareComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(squareComponent)) {//点到别的
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, squareComponent);
                chessboard.clickController.swapPlayer();
                first.setSelected(false);
                first = null;
            }
        }
    }


    /**
     * @param squareComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private int a=0;//加一个a,用于判断第一个是什么方
    private boolean handleFirst(SquareComponent squareComponent) {//第一次点选，翻转棋子
        //未翻转且不是不空位进入if
        if (!squareComponent.isReversal() && !(squareComponent instanceof EmptySlotComponent)) {
            if (a==0){
                chessboard.setCurrentColor(squareComponent.getChessColor());
                a=1;
            }
            squareComponent.setReversal(true);//翻转
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());
            squareComponent.repaint();
            chessboard.clickController.swapPlayer();
            return false;
        }
        return squareComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param squareComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(SquareComponent squareComponent) {//选定一个棋后，第二次点选

        //没翻开或空棋子，进入if
        if (!squareComponent.isReversal()) {
            if (first.getRank()==2){//点击是炮：可以打
                return first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint())&&!(squareComponent instanceof EmptySlotComponent);
            }
            //对于不是炮的，第二次点击的地方是没翻开且非空棋子不能走
            else if (!(squareComponent instanceof EmptySlotComponent)) {
                return false;
            }
        }
        return squareComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint());
    }

    //轮次
    private int progress=1;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    private boolean ShiFouYiHuiHe=false;

    public void setShiFouYiHuiHe(boolean shiFouYiHuiHe) {
        ShiFouYiHuiHe = shiFouYiHuiHe;
    }


    public void swapPlayer() {//交换行棋方
        chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        ChessGameFrame.getBeginLabel().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
        if (ShiFouYiHuiHe){
            progress++;
            ShiFouYiHuiHe=false;
        }else {
            ShiFouYiHuiHe=true;
        }
        Menu.chessGameFrame.ProgressS.setText(String.format("%s",progress));
        try {
            chessboard.saveGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}