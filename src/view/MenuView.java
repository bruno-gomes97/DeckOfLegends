package view;

import controller.MatchController;

import java.util.List;
import java.util.Scanner;

public class MenuView {

    public static Scanner scanner = new Scanner(System.in);
    public CardView cardView = new CardView();
    public DeckEditView deckEditView = new DeckEditView();
    public MatchHistoryView matchHistoryView = new MatchHistoryView();

    public MenuView() {

    }

    public void run(String userName) {

        boolean runing = true;

        while (runing) {

            System.out.println("Bem vindo Jovem");
            System.out.println("O que deseja fazer agora? Escolha sabiamente...");
            System.out.println("1 - Ir para a batalha.");
            System.out.println("2 - Entender como funciona o deck.");
            System.out.println("3 - Listar regras do jogo.");
            System.out.println("4 - Listar histórico de partida.");
            System.out.println("5 - Editar Baralho de Jogo.");
            System.out.println("0 - Voltar");

            System.out.print("> ");
            String option = scanner.next();

            switch (option) {
                case "1":
                    MatchController.startAMatch(userName);
                    break;
                case "2":
                    cardView.howDeckWorks();
                    break;
                case "3":
                    StartView.explainHowItWorks();
                    break;
                case "4":
                    matchHistoryView.displayHistory();
                    break;
                case "5":
                    deckEditView.run();
                    break;
                case "0":
                    runing = false;
                    break;
            }
        }
    }

}
