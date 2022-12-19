import Musics.Test;
import view.ChessGameFrame;
import view.Menu;

import javax.swing.*;

public class Main {
    public static Test.AudioPlay2 music;
    public static void main(String[] args) {
        Thread.currentThread().setName("游戏");
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu(720, 720);
        });
        MyThread t01 = new MyThread("背景音乐");
        t01.start();
    }

    static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String path = "Music/bgm.wav";
            music = new Test.AudioPlay2(path);
            music.run = true;
            music.start();
        }
    }
}



