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
            //todo:error check
            JOptionPane.showMessageDialog(chessboard,"error 101");
            chessboard.loadGame2(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void restartGame() {
        Menu.chessGameFrame = new ChessGameFrame(720,720);

    }
}
