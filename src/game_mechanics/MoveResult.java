package game_mechanics;

public class MoveResult {
    private MoveType type;

    public MoveType getType() {
        return type;
    }

    private Chip chip;

    public Chip getChip() {
        return chip;
    }

    public MoveResult(MoveType type) {
        this(type, null);
    }

    public MoveResult(MoveType type, Chip piece) {
        this.type = type;
        this.chip = piece;
    }
}
