package domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Card {

    private String name;
    private String description;

    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", description='" + description + '\'';
    }

    protected String[] formatDescription(String text, int maxLength) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        StringBuilder formattedDescription = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + 1 > maxLength) {
                formattedDescription.append(line).append("\n");
                line = new StringBuilder();
            }
            if (!line.isEmpty()) {
                line.append(" ");
            }
            line.append(word);
        }

        if (!line.isEmpty()) {
            formattedDescription.append(line);
        }

        String[] lines = formattedDescription.toString().split("\n");

        List<String> paddedLines = new ArrayList<>(List.of(lines));
        while (paddedLines.size() < 4) {
            paddedLines.add("");
        }

        return paddedLines.toArray(new String[0]);
    }

    public abstract String printCard();
}
