package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;

public class SoldierChessComponent extends ChessComponent {

    public SoldierChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = new ImageIcon("imgs/红兵.png").getImage();
            name1=11;
        } else {
            name = new ImageIcon("imgs/黒卒.png").getImage();
            name1=21;
        }
        rank=1;
    }

}
