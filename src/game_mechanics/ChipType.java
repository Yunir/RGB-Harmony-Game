package game_mechanics;


import javafx.scene.paint.Color;

public enum ChipType {
    YELLOW("#ffeead"), ORANGE("#ffcc5c"), RED("#ff6f69"), BLOCK("#36454f");

    final String color;

    ChipType(String c) {
        color = c;
    }

}
