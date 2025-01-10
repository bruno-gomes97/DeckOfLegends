package controller;


import domain.Card;
import domain.CardPosition;
import domain.Match;
import domain.Monster;
import view.CardView;

import domain.*;

import view.MatchView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class MatchController {

    static Match match;
    static Monster monster;
    static MatchView matchView = new MatchView();
    static DeckController deckController = new DeckController();
    static List<String> gameHistory = new ArrayList<>();
    static boolean isMonsterPlaced = false;
    static CardView cardView = new CardView();


    public static void startAMatch(String name) {

        String enemyName = getEnemyName();

        //Instantiate a match passing the player name
        match = new Match(name, enemyName);

        //Generating random deck for both players
        //Player Deck
        match.getPlayer().setDeck(deckController.createARandomMatchDeck());
        match.getPlayer().setHandDeck(deckController.createARandomHandDeck(match.getPlayer().getDeck()));
        //EnemyBot Deck
        match.getEnemyBot().setDeck(deckController.createARandomMatchDeck());
        match.getEnemyBot().setHandDeck(deckController.createARandomHandDeck(match.getEnemyBot().getDeck()));

        //Draw into users console
        initializeMatchViewBatlle();
    }

    public static String getEnemyName() {
        List<String> enemies = List.of(
                "Garran, o Cortador de Gargantas",
                "Bruxa Pálida Grelza",
                "Ferro, o Goblin Travesso",
                "Sangue-Frio o Mercenário",
                "Hagri, o Ladrão de Almas",
                "Durna, a Pirata Sem Lei",
                "Zorak, o Caçador de Cabeças",
                "Lobo, o Saqueador do Norte",
                "Morgath, o Impiedoso",
                "Gurt, o Sombra de Ferro",
                "Ravena, a Senhora dos Bandidos",
                "Brock, o Devorador de Ouro",
                "Kazzik, o Goblin Sorridente",
                "Grizz, o Monstro da Taberna",
                "Wartok, o Cavaleiro de Caverna",
                "Grim, o Mercador de Morte",
                "Vorna, a Fúria Selvagem",
                "Krag, o Fanfarrão",
                "Rogar, o Senhor dos Arruaceiros",
                "Shank, o Mão Leve"
        );

        Random random = new Random();

        int randomIndex = random.nextInt(enemies.size());
        return enemies.get(randomIndex);
    }

    public static List<String> getGameHistory() {
        return gameHistory;
    }

    public static void initializeMatchViewBatlle() {
        matchView.showStartOfMatch(match);
    }

    public static void addToGameHistory(String history) {
        gameHistory.add(history);
    }

    public static void summonMonster(int positionSelect, int batleMode, int postionHand) {
        int position = positionSelect - 1;

        // Validar posição no campo
        if (positionSelect < 1 || positionSelect > match.getPlayer().getZone().length) {
            System.out.println("Posição inválida no campo! Escolha uma posição entre 1 e " + match.getPlayer().getZone().length + ".");
            return;
        }

        // Validar posição na mão
        if (postionHand < 1 || postionHand > match.getPlayer().getHandDeck().getCards().size()) {
            System.out.println("Posição inválida na mão! Escolha uma posição entre 1 e " + match.getPlayer().getHandDeck().getCards().size() + ".");
            return;
        }

        // Validar modo de batalha
        if (batleMode != 1 && batleMode != 2) {
            System.out.println("Modo inválido! Escolha 1 para Ataque ou 2 para Defesa.");
            return;
        }

        // Verificar se a posição no campo está livre
        if (match.getPlayer().getZone()[position] == null) {
            Card cardToSummon = match.getPlayer().getHandDeck().getCards().get(postionHand - 1);

            // Verificar se a carta é um monstro
            if (cardToSummon instanceof Monster) {
                match.getPlayer().getZone()[position] = (Monster) cardToSummon;

                // Configurar posição do monstro
                if (batleMode == 1) {
                    ((Monster) cardToSummon).setPosition(CardPosition.ATTACK);
                } else {
                    ((Monster) cardToSummon).setPosition(CardPosition.DEFENSE);
                }

                // Remover carta da mão
                match.getPlayer().getHandDeck().getCards().remove(postionHand - 1);

                // Mensagem de sucesso
                System.out.println("O goblin de taverna grita: 'Ah, sim, meu jovem aventureiro! Você chama o poderoso "
                        + cardToSummon.getName()
                        + " para o campo! \nEste magnífico monstro agora está em modo de "
                        + ((Monster) cardToSummon).getPosition()
                        + ". Vamos ver se ele aguenta os tapas!'");
            } else {
                System.out.println("O goblin de taverna coça a cabeça e diz: 'Hmm... essa carta aí não parece um monstro, não pode lutar!'");
            }
        } else {
            System.out.println("O goblin de taverna bufa: 'Ei, seu espaço aí já tá ocupado! Escolha outro lugar pra invocar!'");
        }
    }


    public static void validPositions() {
        int index = 1;

        System.out.println("Aqui estão as posições disponíveis, aventureiro! Escolha sabiamente:'");


        System.out.println("O goblin examina o tabuleiro e grita: 'Ahá! \nEstá são as melhores posições para emboscada");
        for (Monster monster : match.getPlayer().getZone()) {
            if (monster == null) {

                System.out.println("posição: " + index + " está livre!");
            }
            index++;
        }
        System.out.println();
    }

    public static boolean isValidOption(int option) {
        return option >= 1 && option <= 5;
    }

    public static boolean isValidBatleMode(int option) {
        return option == 1 || option == 2;
    }

    public static boolean verifyMatchEnd(Match match) {

        if(match.getEnemyBot().getLifePoints() <= 0){
                match.endMatch();
            System.out.println();
            System.out.println(match.getPlayer().getName() + " ganhou a partida !");
            return true;
            }

        if(match.getPlayer().getLifePoints() <= 0){
            match.endMatch();
            System.out.println();
            System.out.println(match.getEnemyBot().getName() + " ganhou a partida !");
            return true;
        }
        return false;

    }

    public static void turn() {
        if (match.isPlayerStarts()) {
            match.setPlayerTurn(true);
            match.setPlacement(true);
            playerTurn();
            match.setPlayerTurn(false);
            match.setPlacement(true);
            enemyBotTurn();

        } else {
            match.setPlayerTurn(false);
            match.setPlacement(true);
            enemyBotTurn();
            match.setPlayerTurn(true);
            match.setPlacement(true);
            playerTurn();

        }
        match.startNewRound();
    }

    public static void playerTurn() {
        matchView.battleMenu(match);
    }

    private static void enemyBotTurn() {
        enemyBotPosition();
        botAttack(match);
        verifyMatchEnd(match);
    }

    public boolean flipACoin(int choice) {

        Random random = new Random();
        int result = random.nextInt(2) + 1;
        return result == choice;
    }


    public static boolean validateMonster(List<Card> cards) {
        return cards.stream().anyMatch(obj -> obj instanceof Monster);
    }

    public static boolean validateEffectCardOnDeck(List<Card> cards) {
        return cards.stream().anyMatch(obj -> obj instanceof Effect);
    }

    public static boolean validadeMonsterIsInAttackMode(List<Monster> cards) {
        return cards.stream().anyMatch(obj -> obj.getPosition() == CardPosition.ATTACK);
    }


    private static void enemyBotPosition() {
        List<Card> handCards = match.getEnemyBot().getHandDeck().getCards();
        if (!hasMonstersInHand(handCards)) {
            System.out.println("O bot não tem monstros para posicionar.");
            return;
        }

        if (isZoneFull(match.getEnemyBot().getZone())) {
            System.out.println("A zona do bot está cheia. Não é possível posicionar mais monstros.");
            return;
        }

        boolean hasDefenseMonster = false;
        for (Monster monster : match.getEnemyBot().getZone()) {
            if (monster != null && monster.getPosition() == CardPosition.DEFENSE) {
                hasDefenseMonster = true;
                break;
            }
        }

        Card selectedCard;
        if (!hasDefenseMonster) {
            int maxDefenseIndex = maxDefensePosition(handCards);
            selectedCard = handCards.get(maxDefenseIndex);
        } else {
            selectedCard = selectRandomMonster(handCards);
        }

        if (selectedCard == null) {
            System.out.println("O bot não encontrou um monstro na mão.");
            return;
        }

        int position = getRandomEmptyPosition();
        match.getEnemyBot().getZone()[position] = (Monster) selectedCard;
        match.getEnemyBot().getHandDeck().getCards().remove(selectedCard);
        setBattleMode((Monster) selectedCard);
        System.out.println("O bot posicionou o monstro " + selectedCard.getName() + " em modo de "
                + ((Monster) selectedCard).getPosition() + " na posição " + (position + 1) + ".");
    }

    private static boolean isZoneFull(Monster[] zone) {
        for (Monster monster : zone) {
            if (monster == null) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasMonstersInHand(List<Card> handCards) {
        return handCards.stream().anyMatch(card -> card instanceof Monster);
    }

    private static Card selectRandomMonster(List<Card> handCards) {
        List<Card> monsters = new ArrayList<>();
        for (Card card : handCards) {
            if (card instanceof Monster) {
                monsters.add(card);
            }
        }
        if (monsters.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return monsters.get(random.nextInt(monsters.size()));
    }

    private static int getRandomEmptyPosition() {
        Random random = new Random();
        int position;
        do {
            position = random.nextInt(match.getEnemyBot().getZone().length);
        } while (match.getEnemyBot().getZone()[position] != null);
        return position;
    }

    private static void setBattleMode(Monster monster) {
        Random random = new Random();
        int battleMode = random.nextInt(2) + 1;
        if (battleMode == 1) {
            monster.setPosition(CardPosition.ATTACK);
        } else {
            monster.setPosition(CardPosition.DEFENSE);
        }
    }

    public static int maxDefensePosition(List<Card> cards) {
        int maxDefense = 0;
        int position = -1;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) instanceof Monster) {
                Monster monster = (Monster) cards.get(i);
                if (monster.getDefense() > maxDefense) {
                    maxDefense = monster.getDefense();
                    position = i;
                }
            }
        }

        return position;
    }

    public static int maxAtaquePosition(List<Card> cards) {
        int maxAttack = 0;
        int position = -1;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) instanceof Monster) {
                Monster monster = (Monster) cards.get(i);
                if (monster.getAttack() > maxAttack) {
                    maxAttack = monster.getAttack();
                    position = i;
                }
            }

        }
        return position;
    }


    public static void botAttack(Match match) {
        if (match.getRoundCount() == 1) {
            System.out.println("O bot não pode atacar na primeira rodada.");
            return;
        }

        System.out.println("O bot está atacando...");

        Monster[] playerBoard = match.getPlayer().getZone();
        Monster[] botBoard = match.getEnemyBot().getZone();

        boolean isDirectAttack = true;
        for (Monster monster : playerBoard) {
            if (monster != null) {
                isDirectAttack = false;
                break;
            }
        }

        int botAttackIndex = getAttackMonsterBot(botBoard);
        if (botAttackIndex == -1) {
            System.out.println("O bot não tem monstros em modo de ataque para atacar.");
            return;
        }

        if (isDirectAttack) {
            matchView.attack(botBoard[botAttackIndex], match.getPlayer(), null);
            System.out.println("O bot realizou um ataque direto com " + botBoard[botAttackIndex].getName() + "!");
        } else {
            int playerDefenseIndex = getDefenseModePlayer(playerBoard);
            if (playerDefenseIndex != -1) {
                matchView.attack(botBoard[botAttackIndex], match.getPlayer(), playerBoard[playerDefenseIndex]);
                System.out.println("O bot atacou " + playerBoard[playerDefenseIndex].getName() + " com " + botBoard[botAttackIndex].getName() + "!");
            } else {
               int playerBoardMonster = getMonsterPlayer(playerBoard);
               if(playerBoardMonster != -1){
                   matchView.attack(botBoard[botAttackIndex], match.getPlayer(), playerBoard[playerBoardMonster]);

               }
            }
        }

        matchView.verifyMonsterIsAlive(match);
    }

    public static int getDefenseModePlayer(Monster[] board) {
        for (int i = 0; i < board.length; i++) {
            Monster monster = board[i];
            if (monster != null && monster.getPosition().equals(CardPosition.DEFENSE)) {
                return i;
            }
        }
        return -1;
    }

    public static int getAttackMonsterBot(Monster[] board) {
        for (int i = 0; i < board.length; i++) {
            Monster monster = board[i];
            if (monster != null && monster.getPosition().equals(CardPosition.ATTACK)) {
                return i;
            }
        }
        return -1;
    }

    public static int getMonsterPlayer(Monster[] board) {
        for (int i = 0; i < board.length; i++) {
            Monster monster = board[i];
            if (monster != null) {
                return i;
            }
        }
        return -1;
    }

    public static void modifyCardPosition(Match match) {
        Scanner scanner = new Scanner(System.in);

        Monster[] zone = match.getPlayer().getZone();

        cardView.printBoard(zone);
        cardView.printOptionsOnBoard(zone);
        boolean hasMonster = false;

        System.out.println("\nCartas disponíveis para alterar a posição: ");
        for(int i = 0; i < zone.length; i++) {
            if (zone[i] != null) {
                hasMonster = true;
                System.out.println((i+1) + " - " + zone[i].getName() + "(Posição atual: " + zone[i].getPosition() + ")");
            }
        }

        if (!hasMonster) {
            System.out.println("Nenhuma carta disponível no campo para modificar posição!");
            return;
        }

        int choice = -1;

        while (choice < 1 || choice > zone.length || zone[choice - 1] == null) {
            System.out.println("Escolha a posição da carta que deseja modificar (1 a 5)");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > zone.length || zone[choice - 1] == null) {
                System.out.println("Escolha inválida! Tente novamente.");
            }
        }

        Monster selectedMonster = zone[choice - 1];
        CardPosition currentPosition = selectedMonster.getPosition();

        if (currentPosition == CardPosition.ATTACK) {
            selectedMonster.setPosition(CardPosition.DEFENSE);
            System.out.println("A carta " + selectedMonster.getName() + " foi alterada para a posição de DEFESA.");
        } else {
            selectedMonster.setPosition(CardPosition.ATTACK);
            System.out.println("A carta " + selectedMonster.getName() + " foi alterada para a posição de ATAQUE.");
        }
    }


    public void useEffectCardOnMonster(int selectedCardPositionOnHandDeck, Player playerTarget, String targetType, int targetCardPosition) {

        //Remove card from hand deck
        Effect effectCard = (Effect) match.getPlayer().getHandDeck().getCards().remove(selectedCardPositionOnHandDeck - 1);
        Monster monsterCard = playerTarget.getZone()[targetCardPosition - 1];

        monsterCard.setAttack(monsterCard.getAttack() + effectCard.getBonusAttack());
        monsterCard.setDefense(monsterCard.getDefense() + effectCard.getBonusDefense());

    }

    public void useEffectCardOnPlayer(int selectedCardPositionOnHandDeck, Player playerTarget) {

        //Remove card from hand deck
        Effect effectCard = (Effect) match.getPlayer().getHandDeck().getCards().remove(selectedCardPositionOnHandDeck - 1);
        //Adiciona vida ao player
        match.getPlayer().setLifePoints(match.getPlayer().getLifePoints() + effectCard.getBonusDefense());

        System.out.println(match.getPlayer().getLifePoints());
    }
}
