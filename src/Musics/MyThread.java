package Musics;

public class MyThread extends Thread {
    public Test.AudioPlay2 music;
    public MyThread(String name) {
        super(name);
        String path = "Music/bgm.wav";
        music = new Test.AudioPlay2(path);
    }

    @Override
    public void run() {
        music.run = true;
        music.start();
    }

    public void over() {
        music.run = false;
    }
}

