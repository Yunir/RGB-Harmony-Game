package game_mechanics;


import javafx.scene.paint.Color;

public enum ChipType {
    YELLOW(Color.YELLOW), ORANGE(Color.ORANGE), RED(Color.RED), BLOCK(Color.BLACK);

    final Color color;

    ChipType(Color c) {
        color = c;
    }

}
