package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;

public class CannonChessComponent extends ChessComponent {

    public CannonChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = new ImageIcon("imgs/红炮.png").getImage();
        } else {
            name = new ImageIcon("imgs/黑炮.png").getImage();
        }
        rank=2;
    }
}