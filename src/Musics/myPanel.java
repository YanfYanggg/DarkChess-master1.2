package Musics;
import javax.swing.*;
import java.awt.*;


public class myPanel extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    int[] snowx = new int[720];
    int[] snowy = new int[720];
    int[] snowy1 = new int[720];

    public myPanel() {
        for (int i = 0; i < 720; i++) {
            snowx[i] = this.random(720);
            snowy[i] = this.random(720);
        }
    }

    public void snowspaint(Graphics g) {
        for (int i = 0; i < 200; i++) {
            ImageIcon bg = new ImageIcon("imgs/花.png");
            g.drawImage(bg.getImage(), snowx[i], snowy[i], bg.getImageObserver());
            for (int j = -1; j < snowy1[i]; j++) {
                g.drawImage(bg.getImage(), snowx[i], 720 - j * 3, bg.getImageObserver());
            }
        }
    }

    public void paint(Graphics g) {
        snowspaint(g);
    }

    public void run() {
        while (true) {
            for (int i = 0; i < snowy.length; i++) {
                if (snowy[i] <= 720) {
                    snowy[i]++;
                } else {
                    snowy1[i]++;
                    snowy[i] = 0;
                }
            }
            repaint();
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int random(int num) {
        return (int) (Math.random() * num);
    }
}


