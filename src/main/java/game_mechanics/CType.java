package game_mechanics;


public enum CType {
    BLOCK("#36454f", 0, -1), RED("#ff6f69", 1, 0), GREEN("#88d8b0", 2, 2), BLUE("#63ace5", 3, 4), WHITE("#36454f", 4, -1);

    final String color;
    final int id;
    final int defPosX;

    CType(String c, int id, int defPosX) {
        color = c;
        this.id = id;
        this.defPosX = defPosX;
    }

    public static CType getCellTypeById(int id) {
        switch (id) {
            case 1: return RED;
            case 2: return GREEN;
            case 3: return BLUE;
            default: return WHITE;
        }
    }

    public static CType getCellTypeByPosX(int posX) {
        switch (posX) {
            case 0: return RED;
            case 2: return GREEN;
            case 4: return BLUE;
            default: return WHITE;
        }
    }

    public int getId() {
        return id;
    }
    //    getChipTypeById

}
