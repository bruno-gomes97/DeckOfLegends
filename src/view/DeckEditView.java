package view;

import controller.CardController;
import controller.DeckController;
import domain.Card;
import domain.Deck;
import domain.Effect;

import java.sql.SQLOutput;
import java.util.Scanner;

import static utils.Constants.MEDIUM_DELAY;
import static utils.Constants.SHORT_DELAY;
import static utils.Delay.runWithDelay;

public class DeckEditView {

    static Scanner sc = new Scanner(System.in);
    static DeckController deckController = new DeckController();
    static CardView cardView = new CardView();
    static CardController cardController = new CardController();

    public void run() {

        System.out.println("==============================");
        System.out.println("Bem vindo ao sistema de edição de baralho");
        System.out.println("==============================");
        String op;
        do {
            showDeckEditionOptions();
            op = sc.next();
            sc.nextLine();
            switch (op) {
                case "1":
                    addNewCardIntoDeck();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Escolha umas opções!!");
                    break;
            }
        } while (!op.equals("0"));
    }

    public void showDeckEditionOptions() {
        System.out.println("Escolha sabiamente: ");
        System.out.println("1 - Adicionar uma nova carta ao baralho");
        System.out.println("0 - Sair do menu de edição de baralho");
    }

    public void addNewCardIntoDeck() {
        boolean running = true;
        while (running) {

            String newCardType = "";
            while (!newCardType.equalsIgnoreCase("efeito") && !newCardType.equalsIgnoreCase("monstro")) {
                System.out.println("Oi, oi, humano! Me diga, que tipo de carta você quer criar? ('Efeito' ou 'Monstro')");
                newCardType = sc.nextLine();
                if (!newCardType.equalsIgnoreCase("efeito") && !newCardType.equalsIgnoreCase("monstro")) {
                    System.out.println("Argh! Isso não serve, humano! Escolha direito!");
                }
            }

            System.out.println("Hehehe, agora me diga o nome dessa carta incrível que você está criando!");
            String newCardName = sc.nextLine();

            //Validatng attackPoints
            int newCardAttackPoints = -1;
            while (newCardAttackPoints > 3000 || newCardAttackPoints < 0) {
                System.out.println("Quantos pontos de ataque ela terá? (0 a 3000, mas escolha sabiamente!)");
                newCardAttackPoints = sc.nextInt();
                sc.nextLine();
                if (newCardAttackPoints > 3000 || newCardAttackPoints < 0) {
                    System.out.println("Raaah! Isso está errado! Ataque precisa ser entre 0 e 3000!");
                }
            }

            //Validatng Defense Points
            int newCardDefensePoints = -1;
            while (newCardDefensePoints > 3000 || newCardDefensePoints < 0) {
                System.out.println("E a defesa? Quantos pontos? (0 a 3000 também!)");
                newCardDefensePoints = sc.nextInt();
                sc.nextLine();
                if (newCardDefensePoints > 3000 || newCardDefensePoints < 0) {
                    System.out.println("Raaah! Você só pode estar maluco! Seja justo e escolha entre 0 e 3000 pontos de defesa!");
                }
            }

            System.out.println("Hmm, hmm, agora diga algo criativo para descrever sua carta! Seja épico!");
            String newCardDescription = sc.nextLine();

            //Adding new card
            //Check if adding worked or not
            if (deckController.addNewCardIntoDeck(newCardName, newCardType, newCardDescription, newCardAttackPoints, newCardDefensePoints)) {
                running = false;
                System.out.println("Hehehe, sua carta está pronta! Isso vai ser divertido no campo de batalha!");
            } else {
                System.out.println("Hehehe, o nome de carta já existe! Tente novamente!");
            }
        }
    }


    //Funcionlaidade Inutilizada
    public void editCardOfDeck() {
        System.out.println("==============================");
        System.out.println("Selecione uma das cartas do baralho para editar!");
        System.out.println("==============================");
        runWithDelay(() -> System.out.println("Modificar uma carta?? Que ousadia!"), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("Veja as cartas com cuidado e selecione qual você, mero plebeu quer modificar: "), SHORT_DELAY);

        //Function to list all cards
        Deck deck = new Deck();
        deck.setCards(deckController.getAllCards());
        cardView.printDecksOnConsole(deck);

        //User select a card
        boolean running = true;
        while (running) {

            System.out.println("Digite o nome da carta a ser modificada:  ");
            String nameEditCardInput = sc.nextLine();
            Card editCard = deckController.findACardOnAllCardsByName(nameEditCardInput);

            int op;
            if (editCard != null) {
                running = false;
                do {
                    System.out.println("Qual item você quer modificar?");
                    System.out.println("""
                            1 - O nome da carta
                            2 - Descrição da Carta
                            3 - Atributo de Ataque da carta
                            4 - Atributo de Defesa da carta
                            0 - Parar de editar essa carta
                            """);
                    op = sc.nextInt();
                    sc.nextLine();
                    switch (op) {
                        case 1:
                            System.out.print("Digite o novo nome da carta: ");
                            String newNameInput = sc.nextLine();
                            cardController.chageCardAtributeName(editCard, newNameInput);
                            break;
                        case 2:
                            System.out.print("Digite a nova descrição da carta: ");
                            String newDescriptionInput = sc.nextLine();
                            cardController.chageCardAtributeName(editCard, newDescriptionInput);
                            break;
                        case 3:
                            System.out.print("Digite os novos pontos de ataque da Carta: ");
                            int newAttackPoints = sc.nextInt();
                            cardController.chageCardAtributeAttack(editCard, newAttackPoints);
                            break;
                        case 4:
                            System.out.print("Digite os novos pontos de defesa da Carta: ");
                            int newDefensePoints = sc.nextInt();
                            cardController.chageCardAtributeAttack(editCard, newDefensePoints);
                            break;
                        case 0:
                            System.out.println("Saindo do sistema!");
                            break;
                        default:
                            System.out.println("Opção incorreta");
                            break;
                    }
                }
                while (op != 0);
                cardView.printDecksOnConsole(deck);

            } else {
                System.out.println("Carta não encontrada!");
            }
        }
    }
}
