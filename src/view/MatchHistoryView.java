package view;

import controller.MatchController;

import java.util.List;

public class MatchHistoryView {

    public void displayHistory() {
        List<String> history = MatchController.getGameHistory();

        if (history.isEmpty()) {
            System.out.println("Nenhuma partida foi registrada.");
        } else {
            System.out.println("Histórico de partidas:");
            for (int i = 0; i < history.size(); i++) {
                System.out.println((i + 1) + ". " + history.get(i));
            }
        }
    }
}
