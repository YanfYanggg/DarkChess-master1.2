package view;

/**
 * Shelly：要去建立一个窗口（游戏的初始界面）
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JFrame implements ActionListener {
    private final int WIDTH;
    private final int HEIGHT;
    private JButton Game_Start;

    private JButton Game_Over;

    private static JLabel TitleLabel;
    public Menu(int width,int height) {
        setTitle("Dark Chess Menu"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        /**
         * add label
         */
        addLabel();
        addGame_Start();
        addGame_Over();
        /**
         * add background
         */

        //1.把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
        ImageIcon bg=new ImageIcon("/Users/shellyli/Desktop/南方科技大学/22秋季/计算机-陶伊达，朱悦铭/project/DarkChess-master/imgs/chessMenu4.jpeg");
        JLabel label=new JLabel(bg);
        label.setSize(bg.getIconWidth(),bg.getIconHeight());
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        label.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight());
        add(label);

        this.setVisible(true);
    }

    /**
    addLabel
     */

    private void addLabel(){
        TitleLabel = new JLabel("天天翻象棋");
        TitleLabel.setLocation(WIDTH*2/7, -150);//??完全看不出区别
        TitleLabel.setSize(800, 800);
        TitleLabel.setFont(new Font("Rockwell", Font.BOLD, 60));
        add(TitleLabel);
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e
     */

    @Override

    public void actionPerformed(ActionEvent e) {//跳转界面

        if (e.getSource() == Game_Start) {

            this.dispose();

            ChessGameFrame mainFrame = new ChessGameFrame(720, 720);

            mainFrame.setVisible(true);//想改在后2句加。

        }

        if (e.getSource() == Game_Over) {

            this.dispose();

            System.exit(0);

        }

    }

    public void addGame_Start(){
        Game_Start = new JButton("开始游戏");
        Game_Start.setLocation(150,360);
        Game_Start.setSize(180,100);
        add(Game_Start);
        Game_Start.addActionListener(this);
        setVisible(true);
    }
    public void addGame_Over(){
        Game_Over = new JButton("结束游戏");
        Game_Over.setLocation(400,360);
        Game_Over.setSize(180,100);
        add(Game_Over);
        Game_Over.addActionListener(this);
        setVisible(true);
    }

}
