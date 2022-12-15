package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;

public class HorseChessComponent extends ChessComponent {

    public HorseChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = new ImageIcon("imgs/红马.png").getImage();
        } else {
            name = new ImageIcon("imgs/黑马.png").getImage();
        }
        rank=4;
    }

}
