package domain;

public class Monster extends Card {

    private int attack;
    private int defense;
    private CardPosition position;

    public Monster(String name, String description, int attack, int defense) {
        super(name, description);
        this.attack = attack;
        this.defense = defense;
        this.position = CardPosition.OUTSIDE;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public CardPosition getPosition() {
        return position;
    }

    public void setPosition(CardPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Monster:" +
                super.toString() +
                "attack= " + attack +
                ", defense= " + defense +
                ", position= " + position;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster monster = (Monster) o;
        return attack == monster.attack && defense == monster.defense && position == monster.position;
    }

    public String printCard() {
        int maxCaracterWidth = 35;
        String cardTopBottom = "+-----------------------------------+";

        int sideWidth = (maxCaracterWidth - getName().length()) / 2;

        String nameLine = String.format("|%" + sideWidth + "s%-" + (maxCaracterWidth - sideWidth) + "s|", "", getName().toUpperCase());

        String typeLine = "| Monstro                           |";
        String emptyLine = "|                                   |";
        String positionLineString = String.format("| Posição: %-25s|", getPosition().getDisplayName());
        String positionLine = getPosition() == CardPosition.OUTSIDE ? emptyLine : positionLineString;
        String[] descriptionLines = formatDescription(getDescription(), 34);
        String atkDefLine = String.format("| ATK: %-10dDEF: %-10d    |", getAttack(), getDefense());

        StringBuilder cardBuilder = new StringBuilder();
        cardBuilder.append(cardTopBottom).append("\n");
        cardBuilder.append(nameLine).append("\n");
        cardBuilder.append(cardTopBottom).append("\n");
        cardBuilder.append(typeLine).append("\n");
        cardBuilder.append(positionLine).append("\n");
        cardBuilder.append(emptyLine).append("\n");
        cardBuilder.append(emptyLine).append("\n");

        for (String line : descriptionLines) {
            cardBuilder.append(String.format("| %-34s|\n", line));
        }

        cardBuilder.append(emptyLine).append("\n");
        cardBuilder.append(cardTopBottom).append("\n");
        cardBuilder.append(atkDefLine).append("\n");
        cardBuilder.append(cardTopBottom);

        return cardBuilder.toString();
    }


}
