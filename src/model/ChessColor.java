package model;

import java.awt.*;

/**
 * 这个类主要用于包装Color对象，用于Chess游戏使用。
 */
public enum ChessColor {//enum类：构造三种颜色取值，初始化三个private实例

    BLACK("Black", Color.BLACK), RED("RED", Color.RED), NONE("No Player", Color.WHITE);
    private final String name;
    private final Color color;//这个Color是java提供的类，已经自带颜色
    ChessColor(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
