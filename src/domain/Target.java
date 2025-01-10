package domain;

public enum Target {
    MONSTER("Monstro"),
    ENEMY("Inimigo"),
    DECK("Deck"),
    PLAYER("Jogador");

    private final String displayName;

    Target(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
