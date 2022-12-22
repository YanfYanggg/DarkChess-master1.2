package view;

import Musics.MyThread;
import Musics.Test;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame implements ActionListener {
    MyThread t01 = new MyThread("111");
    public JButton regret;
    public JButton music;
    public JButton noMusic;
    public int RedCoins = 0;
    public int BlackCoins = 0;
    public JLabel BackLabel;
    public Chessboard chessboard1;
    public JFrame CheatingFrame;
    public ArrayList<String> target;
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private JButton close;
    private JButton Cheat;
    public JTextField redCredit = new JTextField("0", 2);
    public JTextField blackCredit = new JTextField("0", 2);
    public JLabel ProgressS = new JLabel("1");
    private GameController gameController;
    private static JLabel beginLabel;
    int countRA = 0;
    int countRM = 0;
    int countRChe = 0;
    int countRH = 0;
    int countRS = 0;
    int countRPao = 0;
    int countBA = 0;
    int countBM = 0;

    int countBChe = 0;

    int countBH = 0;

    int countBS = 0;

    int countBPao = 0;
    JTextField RGeneral = new JTextField("0");
    JTextField RAdvisor = new JTextField("0");
    JTextField RMinister = new JTextField("0");
    JTextField RChariot = new JTextField("0");
    JTextField RHorse = new JTextField("0");
    JTextField RSoldier = new JTextField("0");
    JTextField RCannon = new JTextField("0");
    JTextField BGeneral = new JTextField("0");
    JTextField BAdvisor = new JTextField("0");
    JTextField BMinister = new JTextField("0");
    JTextField BChariot = new JTextField("0");
    JTextField BHorse = new JTextField("0");
    JTextField BSoldier = new JTextField("0");
    JTextField BCannon = new JTextField("0");

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addMusicButton();
        t01.start();
        addChessboard();
        addLabel();
        addRestartButton();
        addRegret();
        addExitButton();
        addLoadButton();
        addSaveButton();
        addCheatingBottom();
        addRedName();
        addBlackName();
        addRedCredit();
        redCredit.setEditable(false);
        addBlackCredit();
        blackCredit.setEditable(false);
        addRedKilled();
        addBlackKilled();
        judgeWinner();
        addProgressS();
        //增加背景图片（最后）
        ImageIcon bg = new ImageIcon("imgs/对奕背景.png");
        BackLabel = new JLabel(bg);
        BackLabel.setSize(bg.getIconWidth(), bg.getIconHeight());
        this.getLayeredPane().add(BackLabel, new Integer(Integer.MIN_VALUE));
        BackLabel.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
        add(BackLabel);
        this.setVisible(true);
    }


    /**
     * 在游戏窗体中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE);
        chessboard1 = chessboard;
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }

    /**
     * 在游戏窗体中添加turn标签
     */
    private void addLabel() {
        beginLabel = new JLabel("Let's start!");
        beginLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10);
        beginLabel.setSize(200, 60);
        beginLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(beginLabel);
    }

    public static JLabel getBeginLabel() {
        return beginLabel;
    }

    /**
     * 在游戏窗体中增加一个按钮，如果按下的话就会显示是否重新开始游戏
     */
    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> {
            String path = "Music/大按钮的副本.wav";
            Test.AudioPlay2 clickMusic = new Test.AudioPlay2(path);
            clickMusic.run = true;
            clickMusic.start();
            int value = JOptionPane.showConfirmDialog(null, "你确定要重新开始吗？", "请确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (value == JOptionPane.YES_OPTION) {
                this.dispose();
                gameController.restartGame();
            }
        });
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 60);
        button.setSize(100, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        setVisible(true);
    }

    private void addExitButton() {
        JButton button = new JButton("Exit");
        button.addActionListener((e) -> {
            String path = "Music/大按钮的副本.wav";
            Test.AudioPlay2 clickMusic = new Test.AudioPlay2(path);
            clickMusic.run = true;
            clickMusic.start();
            int value = JOptionPane.showConfirmDialog(null, "     你确定要退出吗？\n本次游戏数据将不会保存", "请确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (value == JOptionPane.YES_OPTION) {
                this.dispose();
                Menu menu = new Menu(720, 720);
            }
        });
        button.setLocation(WIDTH * 3 / 5 + 100, HEIGHT / 10 + 60);
        button.setSize(80, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        setVisible(true);
    }

    /**
     * 按下load按钮输入读档路径
     */
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 130);
        button.setSize(100, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);
        button.addActionListener(e -> {
            String path1 = "Music/大按钮的副本.wav";
            Test.AudioPlay2 clickMusic = new Test.AudioPlay2(path1);
            clickMusic.run = true;
            clickMusic.start();
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.loadGameFromFile(path);
        });
    }

    /**
     * 按下save按钮存档
     */
    public void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(WIDTH * 3 / 5 + 100, HEIGHT / 10 + 130);
        button.setSize(80, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);
        button.addActionListener(e -> {
            String path1 = "Music/大按钮的副本.wav";
            Test.AudioPlay2 clickMusic = new Test.AudioPlay2(path1);
            clickMusic.run = true;
            clickMusic.start();
            System.out.println("Click Save");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            //存档功能自己加
            try {
                chessboard1.saveGameByButton(path);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
    }

    /**
     * 作弊模式
     */
    private void addCheatingBottom() {
        Cheat = new JButton("Cheat");
        Cheat.setLocation(WIDTH * 3 / 5 + 10, HEIGHT / 10 + 200);
        Cheat.setSize(80, 60);
        Cheat.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Cheat);
        Cheat.addActionListener(this::actionPerformed);
        close = new JButton("Close");
        close.setLocation(WIDTH * 3 / 5 + 90, HEIGHT / 10 + 200);
        close.setSize(80, 60);
        close.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(close);
        close.addActionListener(this::actionPerformed);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {//不要点2次cheat！!
        if (e.getSource() == Cheat) {
            String path = "Music/大按钮的副本.wav";
            Test.AudioPlay2 clickMusic = new Test.AudioPlay2(path);
            clickMusic.run = true;
            clickMusic.start();
            CheatingFrame = new JFrame("Cheating Frame");
            JLabel label = new JLabel(" 看完记得赶紧close～不要被对手发现啦！");
            label.setFont(new Font("Rockwell", Font.BOLD, 15));
            label.setSize(1000, 100);
            CheatingFrame.add(label);
            CheatingFrame.setSize(540, 720);
            CheatingFrame.setLayout(null);
            CheatingFrame.setVisible(true);
            target = (ArrayList<String>) chessboard1.ReverseRecord();
            chessboard1.CheatReverse();
            CheatingFrame.add(chessboard1);
            setVisible(true);
        }
        if (e.getSource() == close) {
            String path = "Music/大按钮的副本.wav";
            Test.AudioPlay2 clickMusic = new Test.AudioPlay2(path);
            clickMusic.run = true;
            clickMusic.start();
            CheatingFrame.remove(chessboard1);
            repaint();
            CheatingFrame.dispose();
            chessboard1.ReverseAgain(target);
            BackLabel.add(chessboard1);
            chessboard1.setVisible(true);
            repaint();
            setVisible(true);
        }
    }

    /**
     * 悔棋按钮
     */
    public void addRegret() {
        regret = new JButton("Regret");
        regret.setSize(100, 60);
        regret.setLocation(0, 10);
        regret.setFont(new Font("Rockwell", Font.BOLD, 16));
        add(regret);
        regret.setVisible(true);
        regret.addActionListener(e -> {
            gameController.regret();
        });

        }


        private void addProgressS () {
            JLabel label = new JLabel("回合数");
            label.setSize(200, 200);
            label.setLocation(WIDTH * 1 / 2 - 80, -55);
            label.setFont(new Font("Rockwell", Font.BOLD, 25));
            add(label);
            ProgressS.setFont(new Font("Rockwell", Font.BOLD, 25));
            ProgressS.setLocation(WIDTH * 1 / 2, 25);
            ProgressS.setSize(45, 40);
            add(ProgressS);
            setVisible(true);
        }

        private void addRedName () {
            JLabel chess = new JLabel("Red Killed");
            chess.setForeground(Color.RED);
            chess.setSize(1000, 100);
            chess.setFont(new Font("Rockwell", Font.BOLD, 20));
            chess.setLocation(WIDTH * 5 / 8 - 50, HEIGHT * 1 / 2);
            add(chess);
            setVisible(true);
        }
        private void addBlackName () {
            JLabel chess = new JLabel("Black Killed");
            chess.setSize(1000, 100);
            chess.setFont(new Font("Rockwell", Font.BOLD, 20));
            chess.setLocation(WIDTH * 8 / 9 - 55, HEIGHT * 1 / 2);
            add(chess);
            setVisible(true);
        }

        private void addRedCredit () {
            redCredit.setFont(new Font("Rockwell", Font.BOLD, 25));
            redCredit.setLocation(WIDTH * 5 / 8, 350);
            redCredit.setSize(45, 40);
            add(redCredit);
            setVisible(true);
        }
        private void addBlackCredit () {
            blackCredit.setFont(new Font("Rockwell", Font.BOLD, 25));
            blackCredit.setLocation(WIDTH * 8 / 9, 350);
            blackCredit.setSize(45, 40);
            add(blackCredit);
            setVisible(true);
        }

        private void addRedKilled () {
            //加Label
            JLabel General = new JLabel("帥");
            General.setForeground(Color.RED);
            General.setFont(new Font("Rockwell", Font.BOLD, 18));
            General.setLocation(WIDTH * 3 / 5 - 3, 565);
            General.setSize(100, 100);
            add(General);
            JLabel Advisor = new JLabel("仕");
            Advisor.setForeground(Color.RED);
            Advisor.setFont(new Font("Rockwell", Font.BOLD, 18));
            Advisor.setLocation(WIDTH * 3 / 5 - 3, 535);
            Advisor.setSize(100, 100);
            add(Advisor);
            JLabel Minister = new JLabel("相");
            Minister.setForeground(Color.RED);
            Minister.setFont(new Font("Rockwell", Font.BOLD, 18));
            Minister.setLocation(WIDTH * 3 / 5 - 3, 505);
            Minister.setSize(100, 100);
            add(Minister);
            JLabel Horse = new JLabel("傌");
            Horse.setForeground(Color.RED);
            Horse.setFont(new Font("Rockwell", Font.BOLD, 18));
            Horse.setLocation(WIDTH * 3 / 5 - 3, 475);
            Horse.setSize(100, 100);
            add(Horse);
            JLabel Chariot = new JLabel("俥");
            Chariot.setForeground(Color.RED);
            Chariot.setFont(new Font("Rockwell", Font.BOLD, 18));
            Chariot.setLocation(WIDTH * 3 / 5 - 3, 445);
            Chariot.setSize(100, 100);
            add(Chariot);
            JLabel Cannon = new JLabel("炮");
            Cannon.setForeground(Color.RED);
            Cannon.setFont(new Font("Rockwell", Font.BOLD, 18));
            Cannon.setLocation(WIDTH * 3 / 5 - 3, 415);
            Cannon.setSize(100, 100);
            add(Cannon);
            JLabel Soldier = new JLabel("兵");
            Soldier.setForeground(Color.RED);
            Soldier.setFont(new Font("Rockwell", Font.BOLD, 18));
            Soldier.setLocation(WIDTH * 3 / 5 - 3, 385);
            Soldier.setSize(100, 100);
            add(Soldier);
            RGeneral.setFont(new Font("Rockwell", Font.BOLD, 18));
            RGeneral.setEditable(false);
            RAdvisor.setFont(new Font("Rockwell", Font.BOLD, 18));
            RAdvisor.setEditable(false);
            RMinister.setFont(new Font("Rockwell", Font.BOLD, 18));
            RMinister.setEditable(false);
            RChariot.setFont(new Font("Rockwell", Font.BOLD, 18));
            RChariot.setEditable(false);
            RHorse.setFont(new Font("Rockwell", Font.BOLD, 18));
            RHorse.setEditable(false);
            RSoldier.setFont(new Font("Rockwell", Font.BOLD, 18));
            RSoldier.setEditable(false);
            RCannon.setFont(new Font("Rockwell", Font.BOLD, 18));
            RCannon.setEditable(false);
            RGeneral.setLocation(WIDTH * 5 / 8, 600);
            RAdvisor.setLocation(WIDTH * 5 / 8, 570);
            RMinister.setLocation(WIDTH * 5 / 8, 540);
            RChariot.setLocation(WIDTH * 5 / 8, 510);
            RHorse.setLocation(WIDTH * 5 / 8, 480);
            RSoldier.setLocation(WIDTH * 5 / 8, 420);
            RCannon.setLocation(WIDTH * 5 / 8, 450);
            RGeneral.setSize(30, 30);
            RAdvisor.setSize(30, 30);
            RMinister.setSize(30, 30);
            RChariot.setSize(30, 30);
            RHorse.setSize(30, 30);
            RSoldier.setSize(30, 30);
            RCannon.setSize(30, 30);
            RGeneral.setEditable(false);//不知道有什么用
            add(RGeneral);
            add(RAdvisor);
            add(RMinister);
            add(RChariot);
            add(RHorse);
            add(RSoldier);
            add(RCannon);
            setVisible(true);
        }
        private void addBlackKilled () {//可能要加actionListener
            //加Label
            JLabel General = new JLabel("將");
            General.setForeground(Color.BLACK);
            General.setFont(new Font("Rockwell", Font.BOLD, 18));
            General.setLocation(WIDTH * 8 / 9 - 20, 565);
            General.setSize(100, 100);
            add(General);
            JLabel Advisor = new JLabel("士");
            Advisor.setForeground(Color.BLACK);
            Advisor.setFont(new Font("Rockwell", Font.BOLD, 18));
            Advisor.setLocation(WIDTH * 8 / 9 - 20, 535);
            Advisor.setSize(100, 100);
            add(Advisor);
            JLabel Minister = new JLabel("象");
            Minister.setForeground(Color.BLACK);
            Minister.setFont(new Font("Rockwell", Font.BOLD, 18));
            Minister.setLocation(WIDTH * 8 / 9 - 20, 505);
            Minister.setSize(100, 100);
            add(Minister);
            JLabel Horse = new JLabel("馬");
            Horse.setForeground(Color.BLACK);
            Horse.setFont(new Font("Rockwell", Font.BOLD, 18));
            Horse.setLocation(WIDTH * 8 / 9 - 20, 475);
            Horse.setSize(100, 100);
            add(Horse);
            JLabel Chariot = new JLabel("車");
            Chariot.setForeground(Color.BLACK);
            Chariot.setFont(new Font("Rockwell", Font.BOLD, 18));
            Chariot.setLocation(WIDTH * 8 / 9 - 20, 445);
            Chariot.setSize(100, 100);
            add(Chariot);
            JLabel Cannon = new JLabel("砲");
            Cannon.setForeground(Color.BLACK);
            Cannon.setFont(new Font("Rockwell", Font.BOLD, 18));
            Cannon.setLocation(WIDTH * 8 / 9 - 20, 415);
            Cannon.setSize(100, 100);
            add(Cannon);
            JLabel Soldier = new JLabel("卒");
            Soldier.setForeground(Color.BLACK);
            Soldier.setFont(new Font("Rockwell", Font.BOLD, 18));
            Soldier.setLocation(WIDTH * 8 / 9 - 20, 385);
            Soldier.setSize(100, 100);
            add(Soldier);

            BGeneral.setFont(new Font("Rockwell", Font.BOLD, 18));
            BGeneral.setEditable(false);
            BAdvisor.setFont(new Font("Rockwell", Font.BOLD, 18));
            BAdvisor.setEditable(false);
            BMinister.setFont(new Font("Rockwell", Font.BOLD, 18));
            BMinister.setEditable(false);
            BChariot.setFont(new Font("Rockwell", Font.BOLD, 18));
            BChariot.setEditable(false);
            BHorse.setFont(new Font("Rockwell", Font.BOLD, 18));
            BHorse.setEditable(false);
            BSoldier.setFont(new Font("Rockwell", Font.BOLD, 18));
            BSoldier.setEditable(false);
            BCannon.setFont(new Font("Rockwell", Font.BOLD, 18));
            BCannon.setEditable(false);
            BGeneral.setLocation(WIDTH * 8 / 9, 600);
            BAdvisor.setLocation(WIDTH * 8 / 9, 570);
            BMinister.setLocation(WIDTH * 8 / 9, 540);
            BChariot.setLocation(WIDTH * 8 / 9, 510);
            BHorse.setLocation(WIDTH * 8 / 9, 480);
            BSoldier.setLocation(WIDTH * 8 / 9, 420);
            BCannon.setLocation(WIDTH * 8 / 9, 450);
            BGeneral.setSize(30, 30);
            BAdvisor.setSize(30, 30);
            BMinister.setSize(30, 30);
            BChariot.setSize(30, 30);
            BHorse.setSize(30, 30);
            BSoldier.setSize(30, 30);
            BCannon.setSize(30, 30);
            BGeneral.setEditable(false);//不知道有什么用
            add(BGeneral);
            add(BAdvisor);
            add(BMinister);
            add(BChariot);
            add(BHorse);
            add(BSoldier);
            add(BCannon);
            setVisible(true);
        }

        /**
         * 写胜利者判断
         */
        public void judgeWinner () {
            if (chessboard1.getRed_score() >= 60) {
                String path = "Music/掉钱.wav";
                Test.AudioPlay2 clickMusic = new Test.AudioPlay2(path);
                clickMusic.run = true;
                clickMusic.start();
                RedCoins += 10;
                int n = JOptionPane.showConfirmDialog(null, "红方胜利！是否开始新游戏？", "游戏结束", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (n == JOptionPane.YES_OPTION) {
                    this.dispose();
                    gameController.restartGame();
                    addLabel();
                }
                if (n == JOptionPane.NO_OPTION) {
                    this.dispose();
                    Menu menu = new Menu(720, 720);
                    String s1 = String.valueOf(RedCoins);
                    String s2 = String.valueOf(BlackCoins);
                    menu.redCoinsKuang.setText(s1);
                    menu.blackCoinsKuang.setText(s2);
                }
            }
            if (chessboard1.getBlack_score() >= 60) {
                String path = "Music/掉钱.wav";
                Test.AudioPlay2 clickMusic = new Test.AudioPlay2(path);
                clickMusic.run = true;
                clickMusic.start();
                BlackCoins += 10;
                int n = JOptionPane.showConfirmDialog(null, "黑方胜利！是否开始新游戏？", "游戏结束", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (n == JOptionPane.YES_OPTION) {
                    this.dispose();
                    gameController.restartGame();
                }
                if (n == JOptionPane.NO_OPTION) {
                    this.dispose();
                    Menu menu = new Menu(720, 720);
                    String s1 = String.valueOf(RedCoins);
                    String s2 = String.valueOf(BlackCoins);
                    menu.redCoinsKuang.setText(s1);
                    menu.blackCoinsKuang.setText(s2);
                }
            }
        }
        public void addMusicButton () {
            noMusic = new JButton("静音");
            noMusic.setSize(30, 30);
            noMusic.setLocation(WIDTH * 9 / 10 - 10, 10);
            add(noMusic);
            noMusic.addActionListener(this::actionMusic);
            music = new JButton("声音");
            music.setSize(30, 30);
            music.setLocation(WIDTH * 9 / 10 + 30, 10);
            add(music);
            setVisible(true);
            music.addActionListener(this::actionMusic);
        }

        public void actionMusic (ActionEvent e){//跳转界面
            if (e.getSource() == noMusic) {
                t01.over();
            }

            if (e.getSource() == music) {
                MyThread newMusic = new MyThread("重新开始");
                newMusic.start();
            }
        }
    }
