import Musics.MyThread;
import Musics.Test;
import view.ChessGameFrame;
import view.Menu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Thread.currentThread().setName("游戏");
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu(720, 720);
        });
    }
}



