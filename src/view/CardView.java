package view;

import controller.DeckController;
import domain.Card;
import domain.Deck;
import domain.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utils.Constants.LONG_DELAY;
import static utils.Constants.MEDIUM_DELAY;
import static utils.Delay.runWithDelay;

public class CardView {

    Scanner scanner = new Scanner(System.in);

    DeckController deckController = new DeckController();

    public void howDeckWorks() {

        runWithDelay(() -> System.out.println("Ora ora, jovem duelista! Vejo que deseja entender melhor como funciona o deck, hehehe!"), LONG_DELAY);
        runWithDelay(() -> System.out.println("Escute bem, pois vou te explicar: primeiro, você precisa contar com um pouco de sorte, hihi!"), LONG_DELAY);
        runWithDelay(() -> System.out.println("Serão puxadas aleatoriamente 50 cartas para formar seu baralho, sendo elas 40 cartas de monstros "), LONG_DELAY);
        runWithDelay(() -> System.out.println("e 10 poderosas cartas de efeito. Entendeu?"), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("Agora, preste atenção, vou mostrar para você um exemplo de deck verdadeiramente poderoso... "), LONG_DELAY);
        runWithDelay(() -> System.out.println("mas cuidado, pois esse tipo de poder não é para os fracos de coração, hehehe!"), MEDIUM_DELAY);

        Deck deck = deckController.createARandomMatchDeck();

        printDecksOnConsole(deck);

        System.out.println("Então, jovem duelista, conseguiu entender tudo direitinho? Vamos lá, responda com sabedoria!");
        System.out.println("1- Sim");
        System.out.println("2- Não");
        System.out.print("> ");

        String option = scanner.nextLine();

        switch (option) {
            case "1":
                System.out.println("Hehehe, que bom, jovem! Vejo que sua mente é tão afiada quanto a lâmina de um elfo... ou quase isso!");
                break;
            case "2":
                System.out.println("Oh, sério? Não entendeu? Hahaha! Quem diria! Talvez precise de um manual de como segurar cartas primeiro, hein?");
                break;
            default:
                System.out.println("Hmm... parece que as palavras não estão entre seus pontos fortes, hein? Acho que vou fingir que você disse 'não', só para eu rir mais um pouco. Hahaha!");
                break;
        }

    }

    public void printBoard(Monster[] monsters) {
        final int cardWidth = 37;
        final int cardHeight = 15;

        List<List<String>> cardLines = new ArrayList<>();


        for (Monster monster : monsters) {
            if (monster == null) {
                // Carta vazia
                List<String> emptyCard = new ArrayList<>();
                for (int i = 0; i < cardHeight; i++) {
                    if (i == 0 || i == cardHeight - 1) {
                        emptyCard.add("+" + "-".repeat(cardWidth - 2) + "+");
                    } else {
                        emptyCard.add("|" + " ".repeat(cardWidth - 2) + "|");
                    }
                }
                cardLines.add(emptyCard);
            } else {

                String printedCard = monster.printCard();
                List<String> lines = List.of(printedCard.split("\n"));
                cardLines.add(lines);
            }
        }


        for (int i = 0; i < cardHeight; i++) {
            StringBuilder row = new StringBuilder();
            for (List<String> card : cardLines) {
                if (i < card.size()) {
                    row.append(card.get(i)).append(" ");
                }
            }
            System.out.println(row);
        }
    }


    public void printCards(List<Card> cards) {

        List<String> cardString = new ArrayList<>();

        for (Card handCard : cards) {
            cardString.add(handCard.printCard());
        }

        // Dividir cada carta em linhas e armazenar as linhas de todas as cartas
        List<List<String>> cardLines = new ArrayList<>();
        for (String card : cardString) {
            String[] lines = card.split("\n"); // Divide a carta em linhas
            cardLines.add(List.of(lines));    // Adiciona as linhas ao cardLines
        }

        // Determina o número de linhas em cada carta (assumindo tamanhos iguais)
        int maxLines = cardLines.get(0).size();


        // Imprime as cartas lado a lado
        for (int i = 0; i < maxLines; i++) {
            StringBuilder row = new StringBuilder();
            for (List<String> card : cardLines) {
                row.append(card.get(i)).append(" "); // Junta as linhas correspondentes
            }

            System.out.println(row.toString());
        }
    }



    public void printOptionsOnBoard(Monster[] monsters) {
        final int cardWidth = 38;
        StringBuilder numbersRow = new StringBuilder();

        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] != null) {
                int spaces = (cardWidth / 2) - 2;
                numbersRow.append(" ".repeat(spaces))
                        .append("(").append(i + 1).append(")")
                        .append(" ".repeat(spaces));
            } else {
                numbersRow.append(" ".repeat(cardWidth));
            }
        }

        System.out.println(numbersRow.toString());
    }


    public void printOptions(int numberOfCards) {
        // Imprime os números abaixo das cartas
        StringBuilder numbersRow = new StringBuilder();
        for (int i = 0; i < numberOfCards; i++) {
            String spaces;
            if (i == 0) {
                spaces = " ".repeat(17);
            } else {
                spaces = " ".repeat(35);
            }

            numbersRow.append(spaces).append("(").append(i + 1).append(")");
        }
        System.out.println(numbersRow.toString());
    }

    //Function to print long decks on the console, 5 at a time
    public void printDecksOnConsole(Deck deck) {
        for (int i = 0; i < deck.getCards().size(); i += 5) {
            int finalI = i;
            runWithDelay(() -> printCards(deck.getCards().subList(finalI, finalI + 5)), MEDIUM_DELAY);
            System.out.println();
        }
    }

}
