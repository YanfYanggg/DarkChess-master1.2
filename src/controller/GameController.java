package controller;

import view.ChessGameFrame;
import view.Chessboard;
import view.Menu;

import javax.swing.*;
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

    //这个方法是写  如何从文件中读取数据  ！！  sakai第介绍38分钟  我没有改这个方法，存档
    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            if (!path.endsWith(".txt")) {
                JOptionPane.showMessageDialog(chessboard, "error 101");
            }
            if (chessData.size() != 12) {
                JOptionPane.showMessageDialog(chessboard, "error 102");
            } else for (int i = 0; i < 8; i++) {
                if (chessData.get(i).length() != 11&&chessData.get(i).length() != 12) {
                    JOptionPane.showMessageDialog(chessboard, "error 102");
                    break;
                }
            }
            int A = 0, B = 0, C = 0, D = 0, E = 0, F = 0, G = 0, a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0;
            label:
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
//                    if (!chessData.get(i).split(" ")[j].equals("A0") && !chessData.get(i).split(" ")[j].equals("B0") && !chessData.get(i).split(" ")[j].equals("C0") && !chessData.get(i).split(" ")[j].equals("D0") && !chessData.get(i).split(" ")[j].equals("E0") && !chessData.get(i).split(" ")[j].equals("F0") && !chessData.get(i).split(" ")[j].equals("G0") && !chessData.get(i).split(" ")[j].equals("A1") && !chessData.get(i).split(" ")[j].equals("B1") && !chessData.get(i).split(" ")[j].equals("C1") && !chessData.get(i).split(" ")[j].equals("D1") && !chessData.get(i).split(" ")[j].equals("E1") && !chessData.get(i).split(" ")[j].equals("F1") && !chessData.get(i).split(" ")[j].equals("A0") && !chessData.get(i).split(" ")[j].equals("G1")
//                            && !chessData.get(i).split(" ")[j].equals("a0") && !chessData.get(i).split(" ")[j].equals("b0") && !chessData.get(i).split(" ")[j].equals("c0") && !chessData.get(i).split(" ")[j].equals("d0") && !chessData.get(i).split(" ")[j].equals("e0") && !chessData.get(i).split(" ")[j].equals("f0") && !chessData.get(i).split(" ")[j].equals("g0") && !chessData.get(i).split(" ")[j].equals("a1") && !chessData.get(i).split(" ")[j].equals("b1") && !chessData.get(i).split(" ")[j].equals("c1") && !chessData.get(i).split(" ")[j].equals("d1") && !chessData.get(i).split(" ")[j].equals("e1") && !chessData.get(i).split(" ")[j].equals("f1") && !chessData.get(i).split(" ")[j].equals("g1") && !chessData.get(i).split(" ")[j].equals("em")) {
//                        JOptionPane.showMessageDialog(chessboard, "error 103");
//                        break label;
//                    }
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
            }
            if (!chessData.get(9).split(" ")[1].equals("is")){
                JOptionPane.showMessageDialog(chessboard, "error 104");
            }
            chessboard.loadGame2(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void restartGame() {
        Menu.chessGameFrame = new ChessGameFrame(720, 720);

    }
}
