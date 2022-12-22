package view;


import Musics.Test;
import chessComponent.*;
import controller.GameController;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent implements Cloneable {
    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;

    private SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];
    //todo: you can change the initial player
    private ChessColor currentColor = ChessColor.BLACK;

    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        CHESS_SIZE = (height - 6) / 8;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);
        //初始化棋子
        initAllChessOnBoard(ChushihuaQiZi());
    }

    public SquareComponent[][] getChessComponents() {
        return squareComponents;
    }//二维数组

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * 将SquareComponent 放置在 ChessBoard上。里面包含移除原有的component及放置新的component
     *
     * @param squareComponent
     */
    public void putChessOnBoard(SquareComponent squareComponent) {
        int row = squareComponent.getChessboardPoint().getX(), col = squareComponent.getChessboardPoint().getY();
        if (squareComponents[row][col] != null) {
            remove(squareComponents[row][col]);
        }
        add(squareComponents[row][col] = squareComponent);
    }

    /**
     * 交换chess1 chess2的位置
     *
     * @param chess1
     * @param chess2
     */

    //在这里加积分
    private int Red_score = 0;
    private int Black_score = 0;

    public void setBlack_score(int black_score) {
        Black_score = black_score;
    }

    public void setRed_score(int red_score) {
        Red_score = red_score;
    }

    //你要用到的方法
    public int getRed_score() {
        return Red_score;
    }

    public int getBlack_score() {
        return Black_score;
    }

    private int AddHowMuchScore(SquareComponent chessBeEaten) {
        if (chessBeEaten.getRank() == 1) {
            return 1;
        } else if (chessBeEaten.getRank() == 2) {
            return 5;
        } else if (chessBeEaten.getRank() == 3) {
            return 5;
        } else if (chessBeEaten.getRank() == 4) {
            return 5;
        } else if (chessBeEaten.getRank() == 5) {
            return 5;
        } else if (chessBeEaten.getRank() == 6) {
            return 10;
        } else {
            return 30;
        }
    }

    //被吃的是什么棋子
    private SquareComponent ChessBeEaten;

    public SquareComponent getChessBeEaten() {
        return ChessBeEaten;
    }

    /**
     * 交换2棋子位置
     *
     * @param chess1
     * @param chess2
     */
    public void swapChessComponents(SquareComponent chess1, SquareComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        chess1.JudgeNotProbable(this.getChessComponents());
        if (!(chess2 instanceof EmptySlotComponent)) {
            if (chess2.getChessColor() == ChessColor.BLACK) {//吃黑色的chess2
                Red_score += AddHowMuchScore(chess2);
            }
            if (chess2.getChessColor() == ChessColor.RED) {//吃红色的chess2
                Black_score += AddHowMuchScore(chess2);
            }
            ChessBeEaten = chess2;
            remove(chess2);
            if (ChessBeEaten.getChessColor() == ChessColor.RED) {
                if (ChessBeEaten.getRank() == 1) {
                    Menu.chessGameFrame.countRS++;
                    Menu.chessGameFrame.RSoldier.setText(String.valueOf(Menu.chessGameFrame.countRS));
                } else if (ChessBeEaten.getRank() == 2) {
                    Menu.chessGameFrame.countRPao++;
                    Menu.chessGameFrame.RCannon.setText(String.valueOf(Menu.chessGameFrame.countRPao));
                } else if (ChessBeEaten.getRank() == 3) {
                    Menu.chessGameFrame.countRChe++;
                    Menu.chessGameFrame.RChariot.setText(String.valueOf(Menu.chessGameFrame.countRChe));
                } else if (ChessBeEaten.getRank() == 4) {
                    Menu.chessGameFrame.countRH++;
                    Menu.chessGameFrame.RHorse.setText(String.valueOf(Menu.chessGameFrame.countRH));
                } else if (ChessBeEaten.getRank() == 5) {
                    Menu.chessGameFrame.countRM++;
                    Menu.chessGameFrame.RMinister.setText(String.valueOf(Menu.chessGameFrame.countRM));
                } else if (ChessBeEaten.getRank() == 6) {
                    Menu.chessGameFrame.countRA++;
                    Menu.chessGameFrame.RAdvisor.setText(String.valueOf(Menu.chessGameFrame.countRA));
                } else {
                    Menu.chessGameFrame.RGeneral.setText("1");
                }
            }
            if (ChessBeEaten.getChessColor() == ChessColor.BLACK) {
                if (ChessBeEaten.getRank() == 1) {
                    Menu.chessGameFrame.countBS++;
                    Menu.chessGameFrame.BSoldier.setText(String.valueOf(Menu.chessGameFrame.countBS));
                } else if (ChessBeEaten.getRank() == 2) {
                    Menu.chessGameFrame.countBPao++;
                    Menu.chessGameFrame.BCannon.setText(String.valueOf(Menu.chessGameFrame.countBPao));
                } else if (ChessBeEaten.getRank() == 3) {
                    Menu.chessGameFrame.countBChe++;
                    Menu.chessGameFrame.BChariot.setText(String.valueOf(Menu.chessGameFrame.countBChe));
                } else if (ChessBeEaten.getRank() == 4) {
                    Menu.chessGameFrame.countBH++;
                    Menu.chessGameFrame.BHorse.setText(String.valueOf(Menu.chessGameFrame.countBH));
                } else if (ChessBeEaten.getRank() == 5) {
                    Menu.chessGameFrame.countBM++;
                    Menu.chessGameFrame.BMinister.setText(String.valueOf(Menu.chessGameFrame.countBM));
                } else if (ChessBeEaten.getRank() == 6) {
                    Menu.chessGameFrame.countBA++;
                    Menu.chessGameFrame.BAdvisor.setText(String.valueOf(Menu.chessGameFrame.countBA));
                } else {
                    Menu.chessGameFrame.BGeneral.setText("1");
                }
            }


            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;

        //只重新绘制chess1 chess2，其他不变
        chess1.repaint();
        chess2.repaint();
        String path = "Music/吃棋子.wav";
        Test.AudioPlay2 clickMusic = new Test.AudioPlay2(path);
        clickMusic.run = true;
        clickMusic.start();

        String s1 = String.valueOf(getRed_score());
        String s2 = String.valueOf(getBlack_score());
        Menu.chessGameFrame.redCredit.setText(s1);
        Menu.chessGameFrame.blackCredit.setText(s2);
        Menu.chessGameFrame.judgeWinner();
    }

    //增加一个方法，先弄出一个list，再按list中的数字给棋子初始化
    public List<String> ChushihuaQiZi() {
        List<Integer> ChushihuaQiZi2 = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            ChushihuaQiZi2.add(i);
        }
        Collections.shuffle(ChushihuaQiZi2);//默认随机重排
        List<String> ChushihuaQiZi = new ArrayList<>();//此处根据重排后的序号进行布棋
        for (int i = 0; i < 32; i++) {
            if (ChushihuaQiZi2.get(i) < 5) {
                ChushihuaQiZi.add("R1");
            } else if (ChushihuaQiZi2.get(i) < 10) {
                ChushihuaQiZi.add("B1");
            } else if (ChushihuaQiZi2.get(i) < 12) {
                ChushihuaQiZi.add("R2");
            } else if (ChushihuaQiZi2.get(i) < 14) {
                ChushihuaQiZi.add("B2");
            } else if (ChushihuaQiZi2.get(i) < 16) {
                ChushihuaQiZi.add("R3");
            } else if (ChushihuaQiZi2.get(i) < 18) {
                ChushihuaQiZi.add("B3");
            } else if (ChushihuaQiZi2.get(i) < 20) {
                ChushihuaQiZi.add("R4");
            } else if (ChushihuaQiZi2.get(i) < 22) {
                ChushihuaQiZi.add("B4");
            } else if (ChushihuaQiZi2.get(i) < 24) {
                ChushihuaQiZi.add("R5");
            } else if (ChushihuaQiZi2.get(i) < 26) {
                ChushihuaQiZi.add("B5");
            } else if (ChushihuaQiZi2.get(i) < 28) {
                ChushihuaQiZi.add("R6");
            } else if (ChushihuaQiZi2.get(i) < 30) {
                ChushihuaQiZi.add("B6");
            } else if (ChushihuaQiZi2.get(i) < 31) {
                ChushihuaQiZi.add("R7");
            } else ChushihuaQiZi.add("B7");
        }
        return ChushihuaQiZi;
    }


    //这个是初始化棋子
    //!!!   我把这个方法从private改成了public！！！Restart要用到这个方法
    public void initAllChessOnBoard(List<String> ChushihuaQiZi) {
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                ChessColor color;
                SquareComponent squareComponent = null;
                if (ChushihuaQiZi.get(4 * i + j).charAt(0) == 'R') {
                    color = ChessColor.RED;
                } else color = ChessColor.BLACK;
                if (ChushihuaQiZi.get(4 * i + j).charAt(1) == '1') {//第i行，第j个
                    squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (ChushihuaQiZi.get(4 * i + j).charAt(1) == '2') {
                    squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (ChushihuaQiZi.get(4 * i + j).charAt(1) == '3') {
                    squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (ChushihuaQiZi.get(4 * i + j).charAt(1) == '4') {
                    squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (ChushihuaQiZi.get(4 * i + j).charAt(1) == '5') {
                    squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (ChushihuaQiZi.get(4 * i + j).charAt(1) == '6') {
                    squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                } else if (ChushihuaQiZi.get(4 * i + j).charAt(1) == '7') {
                    squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                }
                squareComponent.setVisible(true);
                putChessOnBoard(squareComponent);
            }
        }
        repaint();
        try {
            recordGame(step);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 写cheating mode
     */
    public List<String> ReverseRecord() {
        List<String> NotReverse = new ArrayList<>();
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                if (squareComponents[i][j].isReversal() == false) {
                    String s1 = String.format("%d %d", i, j);
                    NotReverse.add(s1);
                }
            }
        }
        return NotReverse;
    }

    public void CheatReverse() {
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                if (squareComponents[i][j].isReversal() == false) {
                    squareComponents[i][j].setReversal(true);
                }
            }
        }
    }

    public void ReverseAgain(List<String> arr1) {
        for (int a = 0; a < arr1.size(); a++) {
            String[] arr2 = arr1.get(a).split("\\s");
            int x = Integer.parseInt(arr2[0]);
            int y = Integer.parseInt(arr2[1]);
            for (int i = 0; i < squareComponents.length; i++) {
                for (int j = 0; j < squareComponents[i].length; j++) {
                    if (i == x && j == y)
                        squareComponents[i][j].setReversal(false);
                }
            }
        }
    }


    /**
     * 绘制棋盘格子
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     *
     * @param row 棋盘上的行
     * @param col 棋盘上的列
     * @return
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE + 3, row * CHESS_SIZE + 3);
    }

    /**
     * 通过GameController调用该方法
     *
     * @param
     */
    public int step=0;
    public void recordGame(int step) throws IOException {
        File file = new File(String.format("recordByStep/%d.txt",step));
//如果文件不存在，创建文件
        if (!file.exists())
            file.createNewFile();
//创建BufferedWriter对象并向文件写入内容
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//向文件中写入内容
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                if (squareComponents[i][j].getName1() == 11 && squareComponents[i][j].isReversal()) {
                    bw.write("A1 ");
                } else if (squareComponents[i][j].getName1() == 11 && !squareComponents[i][j].isReversal()) {
                    bw.write("A0 ");
                } else if (squareComponents[i][j].getName1() == 12 && squareComponents[i][j].isReversal()) {
                    bw.write("B1 ");
                } else if (squareComponents[i][j].getName1() == 12 && !squareComponents[i][j].isReversal()) {
                    bw.write("B0 ");
                } else if (squareComponents[i][j].getName1() == 13 && squareComponents[i][j].isReversal()) {
                    bw.write("C1 ");
                } else if (squareComponents[i][j].getName1() == 13 && !squareComponents[i][j].isReversal()) {
                    bw.write("C0 ");
                } else if (squareComponents[i][j].getName1() == 14 && squareComponents[i][j].isReversal()) {
                    bw.write("D1 ");
                } else if (squareComponents[i][j].getName1() == 14 && !squareComponents[i][j].isReversal()) {
                    bw.write("D0 ");
                } else if (squareComponents[i][j].getName1() == 15 && squareComponents[i][j].isReversal()) {
                    bw.write("E1 ");
                } else if (squareComponents[i][j].getName1() == 15 && !squareComponents[i][j].isReversal()) {
                    bw.write("E0 ");
                } else if (squareComponents[i][j].getName1() == 16 && squareComponents[i][j].isReversal()) {
                    bw.write("F1 ");
                } else if (squareComponents[i][j].getName1() == 16 && !squareComponents[i][j].isReversal()) {
                    bw.write("F0 ");
                } else if (squareComponents[i][j].getName1() == 17 && squareComponents[i][j].isReversal()) {
                    bw.write("G1 ");
                } else if (squareComponents[i][j].getName1() == 17 && !squareComponents[i][j].isReversal()) {
                    bw.write("G0 ");
                } else if (squareComponents[i][j].getName1() == 21 && squareComponents[i][j].isReversal()) {
                    bw.write("a1 ");
                } else if (squareComponents[i][j].getName1() == 21 && !squareComponents[i][j].isReversal()) {
                    bw.write("a0 ");
                } else if (squareComponents[i][j].getName1() == 22 && squareComponents[i][j].isReversal()) {
                    bw.write("b1 ");
                } else if (squareComponents[i][j].getName1() == 22 && !squareComponents[i][j].isReversal()) {
                    bw.write("b0 ");
                } else if (squareComponents[i][j].getName1() == 23 && squareComponents[i][j].isReversal()) {
                    bw.write("c1 ");
                } else if (squareComponents[i][j].getName1() == 23 && !squareComponents[i][j].isReversal()) {
                    bw.write("c0 ");
                } else if (squareComponents[i][j].getName1() == 24 && squareComponents[i][j].isReversal()) {
                    bw.write("d1 ");
                } else if (squareComponents[i][j].getName1() == 24 && !squareComponents[i][j].isReversal()) {
                    bw.write("d0 ");
                } else if (squareComponents[i][j].getName1() == 25 && squareComponents[i][j].isReversal()) {
                    bw.write("e1 ");
                } else if (squareComponents[i][j].getName1() == 25 && !squareComponents[i][j].isReversal()) {
                    bw.write("e0 ");
                } else if (squareComponents[i][j].getName1() == 26 && squareComponents[i][j].isReversal()) {
                    bw.write("f1 ");
                } else if (squareComponents[i][j].getName1() == 26 && !squareComponents[i][j].isReversal()) {
                    bw.write("f0 ");
                } else if (squareComponents[i][j].getName1() == 27 && squareComponents[i][j].isReversal()) {
                    bw.write("g1 ");
                } else if (squareComponents[i][j].getName1() == 27 && !squareComponents[i][j].isReversal()) {
                    bw.write("g0 ");
                } else if (squareComponents[i][j].getName1() == 0) {
                    bw.write("em ");
                }
            }
            bw.newLine();
        }
        bw.write(String.format("%s", clickController.getProgress()));
        bw.newLine();
        if (currentColor == ChessColor.RED) {
            bw.write("Red is the next");
        } else if (currentColor == ChessColor.BLACK) {
            bw.write("Black is the next");
        }
        bw.newLine();
        bw.write(String.format("%s", getRed_score()));
        bw.newLine();
        bw.write(String.format("%s", getBlack_score()));
        bw.flush();
        this.step++;
    }

//    public void saveGame() throws IOException {
//        String dir = "";
//        File file = new File(dir);
////如果文件不存在，创建文件
//        if (!file.exists())
//            file.createNewFile();
////创建BufferedWriter对象并向文件写入内容
//        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
////向文件中写入内容
//        for (int i = 0; i < squareComponents.length; i++) {
//            for (int j = 0; j < squareComponents[i].length; j++) {
//                if (squareComponents[i][j].getName1() == 11 && squareComponents[i][j].isReversal()) {
//                    bw.write("A1 ");
//                } else if (squareComponents[i][j].getName1() == 11 && !squareComponents[i][j].isReversal()) {
//                    bw.write("A0 ");
//                } else if (squareComponents[i][j].getName1() == 12 && squareComponents[i][j].isReversal()) {
//                    bw.write("B1 ");
//                } else if (squareComponents[i][j].getName1() == 12 && !squareComponents[i][j].isReversal()) {
//                    bw.write("B0 ");
//                } else if (squareComponents[i][j].getName1() == 13 && squareComponents[i][j].isReversal()) {
//                    bw.write("C1 ");
//                } else if (squareComponents[i][j].getName1() == 13 && !squareComponents[i][j].isReversal()) {
//                    bw.write("C0 ");
//                } else if (squareComponents[i][j].getName1() == 14 && squareComponents[i][j].isReversal()) {
//                    bw.write("D1 ");
//                } else if (squareComponents[i][j].getName1() == 14 && !squareComponents[i][j].isReversal()) {
//                    bw.write("D0 ");
//                } else if (squareComponents[i][j].getName1() == 15 && squareComponents[i][j].isReversal()) {
//                    bw.write("E1 ");
//                } else if (squareComponents[i][j].getName1() == 15 && !squareComponents[i][j].isReversal()) {
//                    bw.write("E0 ");
//                } else if (squareComponents[i][j].getName1() == 16 && squareComponents[i][j].isReversal()) {
//                    bw.write("F1 ");
//                } else if (squareComponents[i][j].getName1() == 16 && !squareComponents[i][j].isReversal()) {
//                    bw.write("F0 ");
//                } else if (squareComponents[i][j].getName1() == 17 && squareComponents[i][j].isReversal()) {
//                    bw.write("G1 ");
//                } else if (squareComponents[i][j].getName1() == 17 && !squareComponents[i][j].isReversal()) {
//                    bw.write("G0 ");
//                } else if (squareComponents[i][j].getName1() == 21 && squareComponents[i][j].isReversal()) {
//                    bw.write("a1 ");
//                } else if (squareComponents[i][j].getName1() == 21 && !squareComponents[i][j].isReversal()) {
//                    bw.write("a0 ");
//                } else if (squareComponents[i][j].getName1() == 22 && squareComponents[i][j].isReversal()) {
//                    bw.write("b1 ");
//                } else if (squareComponents[i][j].getName1() == 22 && !squareComponents[i][j].isReversal()) {
//                    bw.write("b0 ");
//                } else if (squareComponents[i][j].getName1() == 23 && squareComponents[i][j].isReversal()) {
//                    bw.write("c1 ");
//                } else if (squareComponents[i][j].getName1() == 23 && !squareComponents[i][j].isReversal()) {
//                    bw.write("c0 ");
//                } else if (squareComponents[i][j].getName1() == 24 && squareComponents[i][j].isReversal()) {
//                    bw.write("d1 ");
//                } else if (squareComponents[i][j].getName1() == 24 && !squareComponents[i][j].isReversal()) {
//                    bw.write("d0 ");
//                } else if (squareComponents[i][j].getName1() == 25 && squareComponents[i][j].isReversal()) {
//                    bw.write("e1 ");
//                } else if (squareComponents[i][j].getName1() == 25 && !squareComponents[i][j].isReversal()) {
//                    bw.write("e0 ");
//                } else if (squareComponents[i][j].getName1() == 26 && squareComponents[i][j].isReversal()) {
//                    bw.write("f1 ");
//                } else if (squareComponents[i][j].getName1() == 26 && !squareComponents[i][j].isReversal()) {
//                    bw.write("f0 ");
//                } else if (squareComponents[i][j].getName1() == 27 && squareComponents[i][j].isReversal()) {
//                    bw.write("g1 ");
//                } else if (squareComponents[i][j].getName1() == 27 && !squareComponents[i][j].isReversal()) {
//                    bw.write("g0 ");
//                } else if (squareComponents[i][j].getName1() == 0) {
//                    bw.write("em ");
//                }
//
//
////                if (squareComponents[i][j].toString().equals(new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("A1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("A0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("B1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("B0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("C1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("C0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("D1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("D0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("E1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("E0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("F1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("F0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("G1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.RED) {
////                    bw.write("G0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("a1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("a0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("b1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("b0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("c1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("c0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("d1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("d0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("e1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("e0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("f1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("f0 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("g1 ");
////                }
////                else if (squareComponents[i][j].toString().equals(new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE).toString()) && !squareComponents[i][j].isReversal() && squareComponents[i][j].getChessColor() == ChessColor.BLACK) {
////                    bw.write("g0 ");
////                }
//            }
//            bw.newLine();
//        }
//
//        bw.write(String.format("%s", clickController.getProgress()));
//        bw.newLine();
//        if (currentColor == ChessColor.RED) {
//            bw.write("Red is the next");
//        } else if (currentColor == ChessColor.BLACK) {
//            bw.write("Black is the next");
//        }
//        bw.newLine();
//        bw.write(String.format("%s", getRed_score()));
//        bw.newLine();
//        bw.write(String.format("%s", getBlack_score()));
//        bw.flush();
//    }

    public void saveGameByButton(String youType) throws IOException {
        File file = new File(youType);
//如果文件不存在，创建文件
        if (!file.exists())
            file.createNewFile();
//创建BufferedWriter对象并向文件写入内容
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//向文件中写入内容
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                if (squareComponents[i][j].getName1() == 11 && squareComponents[i][j].isReversal()) {
                    bw.write("A1 ");
                } else if (squareComponents[i][j].getName1() == 11 && !squareComponents[i][j].isReversal()) {
                    bw.write("A0 ");
                } else if (squareComponents[i][j].getName1() == 12 && squareComponents[i][j].isReversal()) {
                    bw.write("B1 ");
                } else if (squareComponents[i][j].getName1() == 12 && !squareComponents[i][j].isReversal()) {
                    bw.write("B0 ");
                } else if (squareComponents[i][j].getName1() == 13 && squareComponents[i][j].isReversal()) {
                    bw.write("C1 ");
                } else if (squareComponents[i][j].getName1() == 13 && !squareComponents[i][j].isReversal()) {
                    bw.write("C0 ");
                } else if (squareComponents[i][j].getName1() == 14 && squareComponents[i][j].isReversal()) {
                    bw.write("D1 ");
                } else if (squareComponents[i][j].getName1() == 14 && !squareComponents[i][j].isReversal()) {
                    bw.write("D0 ");
                } else if (squareComponents[i][j].getName1() == 15 && squareComponents[i][j].isReversal()) {
                    bw.write("E1 ");
                } else if (squareComponents[i][j].getName1() == 15 && !squareComponents[i][j].isReversal()) {
                    bw.write("E0 ");
                } else if (squareComponents[i][j].getName1() == 16 && squareComponents[i][j].isReversal()) {
                    bw.write("F1 ");
                } else if (squareComponents[i][j].getName1() == 16 && !squareComponents[i][j].isReversal()) {
                    bw.write("F0 ");
                } else if (squareComponents[i][j].getName1() == 17 && squareComponents[i][j].isReversal()) {
                    bw.write("G1 ");
                } else if (squareComponents[i][j].getName1() == 17 && !squareComponents[i][j].isReversal()) {
                    bw.write("G0 ");
                } else if (squareComponents[i][j].getName1() == 21 && squareComponents[i][j].isReversal()) {
                    bw.write("a1 ");
                } else if (squareComponents[i][j].getName1() == 21 && !squareComponents[i][j].isReversal()) {
                    bw.write("a0 ");
                } else if (squareComponents[i][j].getName1() == 22 && squareComponents[i][j].isReversal()) {
                    bw.write("b1 ");
                } else if (squareComponents[i][j].getName1() == 22 && !squareComponents[i][j].isReversal()) {
                    bw.write("b0 ");
                } else if (squareComponents[i][j].getName1() == 23 && squareComponents[i][j].isReversal()) {
                    bw.write("c1 ");
                } else if (squareComponents[i][j].getName1() == 23 && !squareComponents[i][j].isReversal()) {
                    bw.write("c0 ");
                } else if (squareComponents[i][j].getName1() == 24 && squareComponents[i][j].isReversal()) {
                    bw.write("d1 ");
                } else if (squareComponents[i][j].getName1() == 24 && !squareComponents[i][j].isReversal()) {
                    bw.write("d0 ");
                } else if (squareComponents[i][j].getName1() == 25 && squareComponents[i][j].isReversal()) {
                    bw.write("e1 ");
                } else if (squareComponents[i][j].getName1() == 25 && !squareComponents[i][j].isReversal()) {
                    bw.write("e0 ");
                } else if (squareComponents[i][j].getName1() == 26 && squareComponents[i][j].isReversal()) {
                    bw.write("f1 ");
                } else if (squareComponents[i][j].getName1() == 26 && !squareComponents[i][j].isReversal()) {
                    bw.write("f0 ");
                } else if (squareComponents[i][j].getName1() == 27 && squareComponents[i][j].isReversal()) {
                    bw.write("g1 ");
                } else if (squareComponents[i][j].getName1() == 27 && !squareComponents[i][j].isReversal()) {
                    bw.write("g0 ");
                } else if (squareComponents[i][j].getName1() == 0) {
                    bw.write("em ");
                }
            }
            bw.newLine();
        }
        bw.write(String.format("%s", clickController.getProgress()));
        bw.newLine();
        if (currentColor == ChessColor.RED) {
            bw.write("Red is the next");
        } else if (currentColor == ChessColor.BLACK) {
            bw.write("Black is the next");
        }
        bw.newLine();
        bw.write(String.format("%s", getRed_score()));
        bw.newLine();
        bw.write(String.format("%s", getBlack_score()));
        bw.flush();
    }

    public void loadGame2(List<String> chessData) {
        String[][] chesses = new String[8][4];
        for (int i = 0; i < chesses.length; i++) {
            for (int j = 0; j < chesses[i].length; j++) {
                chesses[i][j] = chessData.get(i).split(" ")[j];
            }
        }
        initialGameByCharacters2(chesses);
        clickController.setProgress(Integer.parseInt(chessData.get(8)));
        Menu.chessGameFrame.ProgressS.setText(chessData.get(8));
        Menu.chessGameFrame.ProgressS.setText(chessData.get(8));
        if (chessData.get(9).equals("Red is the next")) {
            currentColor = ChessColor.RED;
        } else if (chessData.get(9).equals("Black is the next")) {
            currentColor = ChessColor.BLACK;
        }
        ChessGameFrame.getBeginLabel().setText(String.format("%s's TURN", currentColor.getName()));
        setRed_score(Integer.parseInt(chessData.get(10)));
        Menu.chessGameFrame.redCredit.setText(chessData.get(10));
        setBlack_score(Integer.parseInt(chessData.get(11)));
        Menu.chessGameFrame.blackCredit.setText(chessData.get(11));
    }

    public void initialGameByCharacters2(String[][] chesses) {
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                SquareComponent squareComponent = null;
                switch (chesses[i][j]) {
                    case "A0":
                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "A1":
                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "a0":
                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "a1":
                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "B0":
                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "B1":
                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "b0":
                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "b1":
                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "C0":
                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "C1":
                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "c0":
                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "c1":
                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "D0":
                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "D1":
                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "d0":
                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "d1":
                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "E0":
                        squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "E1":
                        squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "e0":
                        squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "e1":
                        squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "F0":
                        squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "F1":
                        squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "f0":
                        squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "f1":
                        squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "G0":
                        squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "G1":
                        squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    case "g0":
                        squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(false);
                        break;
                    case "g1":
                        squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        squareComponent.setReversal(true);
                        break;
                    default:
                        squareComponent = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE);
                        break;
                }
                squareComponent.setVisible(true);
                putChessOnBoard(squareComponent);
            }
        }
        repaint();
    }
}
