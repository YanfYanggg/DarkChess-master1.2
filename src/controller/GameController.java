package controller;

import view.ChessGameFrame;
import view.Chessboard;
import view.Menu;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * 这个类主要完成由窗体上组件触发的动作。
 * 例如点击button等
 * ChessGameFrame中组件调用本类的对象，在本类中的方法里完成逻辑运算，将运算的结果传递至chessboard中绘制
 */
public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }
    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            if (!path.endsWith(".txt")) {
                JOptionPane.showMessageDialog(chessboard, "error 101");
                return null;
            }
            if (chessData.size() != 12) {
                JOptionPane.showMessageDialog(chessboard, "error 102");
                return null;
            } else for (int i = 0; i < 8; i++) {
                if (chessData.get(i).length() != 11&&chessData.get(i).length() != 12) {
                    JOptionPane.showMessageDialog(chessboard, "error 102");
                    return null;
                }
            }
            int A = 0, B = 0, C = 0, D = 0, E = 0, F = 0, G = 0, a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    switch (chessData.get(i).split(" ")[j]) {
                        case "A0":
                        case "A1":
                            A++;
                            break;
                        case "B0":
                        case "B1":
                            B++;
                            break;
                        case "C0":
                        case "C1":
                            C++;
                            break;
                        case "D0":
                        case "D1":
                            D++;
                            break;
                        case "E0":
                        case "E1":
                            E++;
                            break;
                        case "F0":
                        case "F1":
                            F++;
                            break;
                        case "G0":
                        case "G1":
                            G++;
                            break;
                        case "a0":
                        case "a1":
                            a++;
                            break;
                        case "b0":
                        case "b1":
                            b++;
                            break;
                        case "c0":
                        case "c1":
                            c++;
                            break;
                        case "d0":
                        case "d1":
                            d++;
                            break;
                        case "e0":
                        case "e1":
                            e++;
                            break;
                        case "f0":
                        case "f1":
                            f++;
                            break;
                        case "g0":
                        case "g1":
                            g++;
                            break;
                        case "em":
                            break;
                        default:
                            JOptionPane.showMessageDialog(chessboard, "error 103");
                            break;
                    }
                    }
                }
            if (A>5||a>5||B>2||b>2||C>2||c>2||D>2||d>2||E>2||e>2||F>2||f>2||G>1||g>1){
                JOptionPane.showMessageDialog(chessboard, "error 103");
                return null;
            }
            if (!chessData.get(9).split(" ")[1].equals("is")){
                JOptionPane.showMessageDialog(chessboard, "error 104");
                return null;
            }
            chessboard.loadGame2(chessData);
            
            //例如：红兵被吃掉的个数：5-A;黑炮被吃掉的个数：2-b;
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //悔棋
    public void regret(){
        File file = new File(String.format("recordByStep/%d.txt",chessboard.step));
//如果文件不存在，创建文件
        if (!file.exists()){
            chessboard.step--;
        }
        if (chessboard.step>0){
            chessboard.step--;
            chessboard.loadGame2(loadGameFromFile(String.format("recordByStep/%d.txt",chessboard.step)));
        }
    }
    public int fupan=1;
//    public void REView(){
//        chessboard.loadGame2(loadGameFromFile(String.format("recordByStep/%d.txt",fupan)));
//        if (fupan== chessboard.step){
//            int value =JOptionPane.showConfirmDialog(null, "复盘已结束，请退出", "请确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//            if (value == JOptionPane.YES_OPTION) {
//                this.dispose();
//                t01.over();
//                Menu menu = new Menu(720, 720);
//            }
//        }
//        fupan++;
//    }

    public void restartGame() {
        for (int i = 0; i < 1000; i++) {
            File file = new File(String.format("recordByStep/%d.txt",i));
            file.delete();
        }
        chessboard.step=0;
        fupan=1;
        Menu.chessGameFrame = new ChessGameFrame(720, 720);
        Menu.chessGameFrame.t01.start();
    }
}
