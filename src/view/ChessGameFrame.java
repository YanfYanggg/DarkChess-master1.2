package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame implements ActionListener {
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;

    private JButton Cheat;

    public JTextField redCredit = new JTextField("0", 2);
    public JTextField blackCredit = new JTextField("0", 2);
    private GameController gameController;
    private static JLabel beginLabel;

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

        addChessboard();
        addLabel();
        addRestartButton();
        addLoadButton();
        addCheatingBottom();
        addRedName();
        addBlackName();
        addRedCredit();
        addBlackCredit();
        addRedKilled();
        addBlackKilled();
        addWhoWinLabel();

        //增加背景图片（最后）
        ImageIcon bg = new ImageIcon("imgs/GamePicture.png");
        JLabel label = new JLabel(bg);
        label.setSize(bg.getIconWidth(), bg.getIconHeight());
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        label.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
        add(label);
        this.setVisible(true);
    }

    public void addWhoWinLabel() {

    }


    /**
     * 在游戏窗体中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE);
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
            int value = JOptionPane.showConfirmDialog(null, "你确定要重新开始吗？", "请确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (value == JOptionPane.YES_OPTION) {
                gameController.restartGame();
            }
        });
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 60);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        setVisible(true);
    }

    /**
     * 按下按钮输入读档路径
     */
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 130);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        //点击buttom触发的事件（只是介绍 没有改方法）
        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.loadGameFromFile(path);
        });
    }

    private void addCheatingBottom() {
        Cheat = new JButton("Cheat");
        Cheat.setLocation(WIDTH * 3 / 5 + 50, HEIGHT / 10 + 200);
        Cheat.setSize(80, 60);
        Cheat.setFont(new Font("Rockwell", Font.BOLD, 20));
        Cheat.setBackground(Color.blue);//为什么没颜色？
        add(Cheat);
        Cheat.addActionListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {//跳转界面

        if (e.getSource() == Cheat) {
            JFrame cheatingFrame = new JFrame("Cheating Frame");
            cheatingFrame.setLayout(null);
            cheatingFrame.setSize(540, 540);
            cheatingFrame.setVisible(true);
        }

    }


    private void addRedName() {
        JLabel chess = new JLabel("Red");
        chess.setForeground(Color.RED);
        chess.setSize(100, 100);
        chess.setFont(new Font("Rockwell", Font.BOLD, 20));
        chess.setLocation(WIDTH * 5 / 8, HEIGHT * 1 / 2);
        add(chess);
        setVisible(true);
    }

    private void addBlackName() {
        JLabel chess = new JLabel("Black");
        chess.setSize(100, 100);
        chess.setFont(new Font("Rockwell", Font.BOLD, 20));
        chess.setLocation(WIDTH * 8 / 9, HEIGHT * 1 / 2);
        add(chess);
        setVisible(true);
    }

    private void addRedCredit() {
        redCredit.setFont(new Font("Rockwell", Font.BOLD, 25));
        redCredit.setLocation(WIDTH * 5 / 8, 350);
        redCredit.setSize(45, 40);
        add(redCredit);
        setVisible(true);
    }

    private void addBlackCredit() {
        blackCredit.setFont(new Font("Rockwell", Font.BOLD, 25));
        blackCredit.setLocation(WIDTH * 8 / 9, 350);
        blackCredit.setSize(45, 40);
        add(blackCredit);
        setVisible(true);
    }

    private void addRedKilled() {//可能加一个被吃掉的eventListener然后setfield
        //加文本框
        JTextField General = new JTextField("0", 1);
        JTextField Advisor = new JTextField("0", 1);
        JTextField Minister = new JTextField("0", 1);
        JTextField Chariot = new JTextField("0", 1);
        JTextField Horse = new JTextField("0", 1);
        JTextField Soldier = new JTextField("0", 1);
        JTextField Cannon = new JTextField("0", 1);
        General.setFont(new Font("Rockwell", Font.BOLD, 18));
        Advisor.setFont(new Font("Rockwell", Font.BOLD, 18));
        Minister.setFont(new Font("Rockwell", Font.BOLD, 18));
        Chariot.setFont(new Font("Rockwell", Font.BOLD, 18));
        Horse.setFont(new Font("Rockwell", Font.BOLD, 18));
        Soldier.setFont(new Font("Rockwell", Font.BOLD, 18));
        Cannon.setFont(new Font("Rockwell", Font.BOLD, 18));
        General.setLocation(WIDTH * 5 / 8, 600);
        Advisor.setLocation(WIDTH * 5 / 8, 570);
        Minister.setLocation(WIDTH * 5 / 8, 540);
        Chariot.setLocation(WIDTH * 5 / 8, 510);
        Horse.setLocation(WIDTH * 5 / 8, 480);
        Soldier.setLocation(WIDTH * 5 / 8, 450);
        Cannon.setLocation(WIDTH * 5 / 8, 420);
        General.setSize(30, 30);
        Advisor.setSize(30, 30);
        Minister.setSize(30, 30);
        Chariot.setSize(30, 30);
        Horse.setSize(30, 30);
        Soldier.setSize(30, 30);
        Cannon.setSize(30, 30);
        General.setEditable(false);//不知道有什么用
        add(General);
        add(Advisor);
        add(Minister);
        add(Chariot);
        add(Horse);
        add(Soldier);
        add(Cannon);
        setVisible(true);
    }
    private void addBlackKilled() {//可能要加actionListener
        //加文本框
        JTextField General = new JTextField("0", 1);
        JTextField Advisor = new JTextField("0", 1);
        JTextField Minister = new JTextField("0", 1);
        JTextField Chariot = new JTextField("0", 1);
        JTextField Horse = new JTextField("0", 1);
        JTextField Soldier = new JTextField("0", 1);
        JTextField Cannon = new JTextField("0", 1);
        General.setFont(new Font("Rockwell", Font.BOLD, 18));
        Advisor.setFont(new Font("Rockwell", Font.BOLD, 18));
        Minister.setFont(new Font("Rockwell", Font.BOLD, 18));
        Chariot.setFont(new Font("Rockwell", Font.BOLD, 18));
        Horse.setFont(new Font("Rockwell", Font.BOLD, 18));
        Soldier.setFont(new Font("Rockwell", Font.BOLD, 18));
        Cannon.setFont(new Font("Rockwell", Font.BOLD, 18));
        General.setLocation(WIDTH * 8 / 9, 600);
        Advisor.setLocation(WIDTH * 8 / 9, 570);
        Minister.setLocation(WIDTH * 8 / 9, 540);
        Chariot.setLocation(WIDTH * 8 / 9, 510);
        Horse.setLocation(WIDTH * 8 / 9, 480);
        Soldier.setLocation(WIDTH * 8 / 9, 450);
        Cannon.setLocation(WIDTH * 8 / 9, 420);
        General.setSize(30, 30);
        Advisor.setSize(30, 30);
        Minister.setSize(30, 30);
        Chariot.setSize(30, 30);
        Horse.setSize(30, 30);
        Soldier.setSize(30, 30);
        Cannon.setSize(30, 30);
        General.setEditable(false);//不知道有什么用
        add(General);
        add(Advisor);
        add(Minister);
        add(Chariot);
        add(Horse);
        add(Soldier);
        add(Cannon);
        setVisible(true);
    }

}
