package domain;

public class Player {
    private static final int ZONE_CAPACITY = 5;
    private String name;
    private int lifePoints;
    private Deck deck;
    private Deck handDeck;
    private Monster[] zone;

    public Player(int lifePoints, String name, Deck deck, Deck handDeck) {
        this.lifePoints = lifePoints;
        this.name = name;
        this.deck = deck;
        this.handDeck = deck;
        this.zone = new Monster[ZONE_CAPACITY];
    }

    public Player(String name) {
        this.lifePoints = 10000;
        this.name = name;
        this.zone = new Monster[ZONE_CAPACITY];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Deck getHandDeck() {
        return handDeck;
    }

    public void setHandDeck(Deck handDeck) {
        this.handDeck = handDeck;
    }

    public Monster[] getZone() {
        return zone;
    }

    public void buyCard() {
        //Remove a carta do deck de partida e adiciona ao deck na mão do player
        if (deck == null || deck.getCards().isEmpty()) {
            System.out.println("O baralho está vazio!");
        }

        Card selectedCard = deck.getCards().get(0);

        handDeck.getCards().add(selectedCard);
        deck.getCards().remove(selectedCard);
    }

    public void sufferDamage(int damage){
        this.lifePoints -=damage;
    }

    public boolean hasWon() {
        return lifePoints > 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", lifePoints=" + lifePoints +
                ", deck=" + deck +
                '}';
    }
}
