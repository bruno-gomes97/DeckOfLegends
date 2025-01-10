package domain;

public class Effect extends Card {

    private int bonusAttack;
    private int bonusDefense;
    private Target target;


    public Effect(String name, String description, int bonusAttack, int bonusDefense, Target target) {
        super(name, description);
        this.bonusAttack = bonusAttack;
        this.bonusDefense = bonusDefense;
        this.target = target;
    }

    public int getBonusAttack() {
        return bonusAttack;
    }

    public void setBonusAttack(int bonusAttack) {
        this.bonusAttack = bonusAttack;
    }

    public int getBonusDefense() {
        return bonusDefense;
    }

    public void setBonusDefense(int bonusDefense) {
        this.bonusDefense = bonusDefense;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Effect: " +
                super.toString() +
                "bonusAttack=" + bonusAttack +
                ", bonusDefense=" + bonusDefense +
                ", target=" + target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Effect effect = (Effect) o;
        return bonusAttack == effect.bonusAttack && bonusDefense == effect.bonusDefense && target == effect.target;
    }

    public String printCard() {
        int maxCaracterWidth = 35;
        String cardTopBottom = "+-----------------------------------+";

        int sideWidth = (maxCaracterWidth - getName().length()) / 2;

        String nameLine = String.format("|%" + sideWidth + "s%-" + (maxCaracterWidth - sideWidth) + "s|", "", getName().toUpperCase());

        String typeLine = "| Efeito                            |";
        String emptyLine = "|                                   |";
        String targetLine = String.format("| Alvo: %-28s|", getTarget().getDisplayName());
        String[] descriptionLines = formatDescription(getDescription(), 34);

        String atkDefLine = String.format("| Bonus ATK: %-6d Bonus DEF: %-5d|", getBonusAttack(), getBonusDefense());

        StringBuilder cardBuilder = new StringBuilder();
        cardBuilder.append(cardTopBottom).append("\n");
        cardBuilder.append(nameLine).append("\n");
        cardBuilder.append(cardTopBottom).append("\n");
        cardBuilder.append(typeLine).append("\n");
        cardBuilder.append(targetLine).append("\n");
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
