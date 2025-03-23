package game_mechanics;

public class MoveResult {
    private Chip chip;
    private MoveType type;

    public MoveResult(MoveType type) {
        this(type, null);
    }

    public MoveResult(MoveType type, Chip chip) {
        this.type = type;
        this.chip = chip;
    }

    public MoveType getType() {
        return type;
    }

    public Chip getChip() {
        return chip;
    }
}
