package Musics;

public class MenuThread extends Thread{
    public Test.AudioPlay2 music;
    public MenuThread(String name) {
        super(name);
        String path = "Music/MenuBGM.wav";
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
