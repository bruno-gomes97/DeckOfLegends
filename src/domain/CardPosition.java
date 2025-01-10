package domain;

public enum CardPosition {
    ATTACK("Ataque"), DEFENSE("Defesa"), OUTSIDE("Fora do campo");

    private final String displayName;

    CardPosition(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
