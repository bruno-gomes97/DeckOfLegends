package view;

import controller.CardController;
import controller.MatchController;
import domain.*;

import java.util.*;

import static utils.Constants.BOLD;
import static utils.Constants.RESET;


public class MatchView {
    static boolean selectPotion = false;
    static boolean placement = false;
    static boolean effectCardUsedOnRound = false;
    static boolean hasAttackedThisRound = false;


    MatchController matchController = new MatchController();
    CardController cardController = new CardController();
    Scanner scanner = new Scanner(System.in);
    CardView cardView = new CardView();

    public void showStartOfMatch(Match match) {
        System.out.println("==============================");
        System.out.println("Sua batalha irá ser contra " + match.getEnemyBot().getName());
        System.out.println("==============================");
        System.out.println();
        System.out.println("Hehehe! Bem-vindos, nobres duelistas, ao campo de batalha!");
        System.out.println("Eu, Grogz, o goblin mestre das cartas, serei o juiz desta disputa!");
        System.out.println("Antes de começarmos, devemos decidir quem terá a honra de fazer a primeira jogada.");
        System.out.println("Hehehe! Vamos resolver isso com uma moeda encantada!");
        System.out.println("Escolham sabiamente, aventureiros!");
        System.out.println("1 - Cara (o brilho do destino): ");
        System.out.println("2 - Coroa (o selo da sorte): ");

        System.out.print("> ");
        int choice = scanner.nextInt();
        String headsOrTails = choice == 1 ? "CARA" : "COROA";

        boolean winTheCoinFlip = matchController.flipACoin(choice);
        match.setPlayerStarts(winTheCoinFlip);


        if (winTheCoinFlip) {
            System.out.println("Hehehe! A moeda girou, brilhou e... " + headsOrTails + "!");
            System.out.println("A sorte sorriu para você, duelista astuto! O destino concedeu a primeira jogada a você!");
            System.out.println("Que as cartas estejam a seu favor! Prepare-se para atacar com tudo!");
        } else {
            System.out.println("Hohoho! A moeda dançou no ar e caiu... " + (headsOrTails.equals("CARA") ? "COROA" : "CARA") + "!");
            System.out.println("O destino é caprichoso, e desta vez a sorte pertence ao seu adversário!");
            System.out.println("Mas não se preocupe, duelista corajoso! A batalha mal começou, mostre sua força!");
        }
        System.out.println("Que comece a batalha! O campo está pronto para a guerra!\n");

        while (!match.isEndOfMatch()) {
            startANewRound(match);
            match.getEnemyBot().buyCard();
            match.getPlayer().buyCard();
        }

        System.out.println("\n============================================");
        System.out.println("===  " + BOLD + " A Batalha Chegou ao Fim! " + RESET + "  ===");
        System.out.println("============================================");
        System.out.println("Espero que você tenha aprendido algo valioso nesta batalha...");
        System.out.println("Até a próxima, herói!");


    }

    public void startANewRound(Match match) {

        hasAttackedThisRound = false;
        placement = false;
        effectCardUsedOnRound = false;
        System.out.println("A " + match.getRoundCount() + "ª rodada começou");
        MatchController.turn();


    }

    public void endARound(Match match) {
        String result = match.getPlayer().hasWon() ? "Jogador " + match.getPlayer().getName() + " venceu!" : "O bot venceu";
        MatchController.addToGameHistory("A partida terminou. " + result);
    }


    public void battleMenu(Match match) {
        boolean inBattle = !match.isEndOfMatch();


        if (match.getRoundCount() == 1) {

            System.out.println("\n============================================");
            System.out.println("===  " + BOLD + " O Grande Duelo Começa! " + RESET + "  ===");
            System.out.println("============================================");
            System.out.println("Prepare-se, herói! Cada decisão pode ser a diferença entre a glória e a derrota!");
        }

        while (inBattle) {

            System.out.println();
            System.out.println("É a sua vez " + match.getPlayer().getName() + " !");
            System.out.println();
            System.out.println(match.getPlayer().getName()+": "+ match.getPlayer().getLifePoints()+" de vida");
            System.out.println(match.getEnemyBot().getName()+": "+ match.getEnemyBot().getLifePoints()+" de vida");
            System.out.println();
            printEntireBoard(match);
            System.out.println("Sua mão atual:");
            cardView.printCards(match.getPlayer().getHandDeck().getCards());
          
            System.out.println("\nEscolha sua próxima ação com sabedoria:");
            System.out.println("1 - Posicionar um monstro no campo.");
            System.out.println("2 - Atacar o inimigo com suas forças!");
            System.out.println("3 - Utilizar uma carta de efeito poderosa.");
            System.out.println("4 - Modificar a posição de uma de suas cartas.");
            System.out.println("5 - Finalizar a vez !");
            System.out.println("0 - Retirar-se e planejar sua próxima batalha.");

            System.out.print("> ");
            String option = scanner.next();

            switch (option) {
                case "1":
                    positionMenu(match);
                    break;
                case "2":
                    attackMenu(match);
                    if(matchController.verifyMatchEnd(match)){
                        inBattle = false;
                    }
                    break;
                case "3":
                    useEffectCardMenu(match);
                    break;
                case "4":
                    System.out.println("Você reorganiza suas cartas, mudando sua estratégia.");
                    System.out.println("Às vezes, uma defesa sólida é o melhor ataque...");
                    matchController.modifyCardPosition(match);
                    break;
                case "5":
                    match.switchTurn();
                    System.out.println();
                    inBattle = false;
                    selectPotion = false;
                    break;
                case "0":
                    System.out.println("Você decide recuar e poupar forças para a próxima batalha.");
                    inBattle = false;
                    match.endMatch();
                    break;
                default:
                    System.out.println("Opção inválida! Não desperdice sua chance com escolhas erradas!");
                    break;
            }
        }
    }

    private void useEffectCardMenu(Match match) {
        String target = "";
        Player playerTarget = match.getPlayer();
        int targetCardPosition;
        Effect effectCard = null;
        boolean selectedValidTargetCardPosition = false;
        int selectedCardPositionOnHandDeck;
        boolean hasPlayerSelectedAnEffectCard = false;

        //If already used a card from hand deck
        if (effectCardUsedOnRound) {
            System.out.println("Ei, você já utilizou uma carta de efeito! Não dá pra fazer isso de novo!");
            return;
        }


        //Validate if There is an effect card on hand deck
        if (!MatchController.validateEffectCardOnDeck(match.getPlayer().getHandDeck().getCards())) {
            cardView.printCards(match.getPlayer().getHandDeck().getCards());
            System.out.println("Hehehe, você é muito azarado! Não possui nenhuma carta de Efeito, que pena, que pena!");
            return;
        }


        //Validate if There is monsterCards on board
        if (!MatchController.validateMonster(Arrays.asList(match.getPlayer().getZone())) && !MatchController.validateMonster(Arrays.asList(match.getEnemyBot().getZone()))) {
            printEntireBoard(match);
            System.out.println("Você é cego? Nenhum monstro no campo de batalha, não é possível usar carta de efeito");
            return;
        }


        System.out.println("\n============================================");
        System.out.println("===  " + BOLD + " Escolha uma carta! " + RESET + "  ===");

        cardView.printCards(match.getPlayer().getHandDeck().getCards());
        cardView.printOptions(match.getPlayer().getHandDeck().getCards().size());

        System.out.println("O goblin diz: 'Hrrm... Escolha logo uma carta de efeito, aventureiro! Mostre o que sabe fazer!'");
        selectedCardPositionOnHandDeck = scanner.nextInt();
        scanner.nextLine();

        //Requires user to input a valid card position
        do {
            if (selectedCardPositionOnHandDeck < 0 || selectedCardPositionOnHandDeck > match.getPlayer().getHandDeck().getCards().size()) {
                System.out.println("O goblin bufa: 'BAAAHHH, essa posição não é válida! Olhe as cartas direito!");
                selectedCardPositionOnHandDeck = scanner.nextInt();
                scanner.nextLine();
            } else if (match.getPlayer().getHandDeck().getCards().get(selectedCardPositionOnHandDeck - 1) instanceof Monster) {
                System.out.println("O goblin de taverna estreita os olhos e diz: 'Hmm... essa carta aí não parece uma carta de efeito, não pode ser utilizada!'");
                System.out.println("Escolha outra carta");
                selectedCardPositionOnHandDeck = scanner.nextInt();
                scanner.nextLine();
            } else {
                hasPlayerSelectedAnEffectCard = true;
                effectCard = (Effect) match.getPlayer().getHandDeck().getCards().get(selectedCardPositionOnHandDeck - 1);
            }
        }
        while (!hasPlayerSelectedAnEffectCard);

        //If ther target is the player itself it uses this function
        if (effectCard.getTarget() == Target.PLAYER) {
            matchController.useEffectCardOnPlayer(selectedCardPositionOnHandDeck, playerTarget);
            System.out.println("O goblin grita: 'Haha, feitiço lançado com sucesso! Espero que funcione... ou não! Hehehe!'");
            effectCardUsedOnRound = true;
            return;
        }

        //Checks if exists a valid target on board
        if (MatchController.validateMonster(Arrays.asList(match.getPlayer().getZone()))) {
            System.out.println("O goblin rosna: 'Hehehe! Olhe bem suas cartas no tabuleiro, aventureiro! Talvez tenha salvação!'");
        } else {
            System.out.println("O goblin suspira: 'Sem aliados? Hah! Boa sorte com isso, herói sem equipe!' Não é possível utilizar cartas de efeito sem possuir cartas monstro em campo!");
            return;
        }

        printBoardWithNumberOfCardPositions(match, playerTarget.getZone());

        do {
            System.out.println("Escolha alvo para o feitiço: ");
            targetCardPosition = scanner.nextInt();

            if (targetCardPosition > playerTarget.getZone().length || targetCardPosition < 1 || playerTarget.getZone()[targetCardPosition - 1] == null) {
                System.out.println("Escolha uma posição com um monstro!");
            } else {
                selectedValidTargetCardPosition = true;
                placement = true;
                matchController.useEffectCardOnMonster(selectedCardPositionOnHandDeck, playerTarget, target, targetCardPosition);
                System.out.println("O goblin vibra: 'Feitiço lançado! Agora é só esperar o estrago... ou a vergonha!'");
                effectCardUsedOnRound = true;

                printBoardWithNumberOfCardPositions(match, playerTarget.getZone());
                System.out.println("Atributos da Carta Modificados com sucesso!");
            }
        } while (!selectedValidTargetCardPosition);

    }

    public void attackMenu(Match match) {

        if (hasAttackedThisRound) {
            System.out.println("Você já atacou nesse round");
            return;
        }

        if (match.getRoundCount() == 1) {
            System.out.println("Não pode atacar no primeiro round");
            return;
        }


        Monster[] myBoard = match.getPlayer().getZone();
        Monster[] enemyBoard = match.getEnemyBot().getZone();

        boolean hasMonstersInAttackPosition = Arrays.stream(myBoard)
                .filter(Objects::nonNull)
                .anyMatch(monster -> monster.getPosition() == CardPosition.ATTACK);

        if (!hasMonstersInAttackPosition) {
            System.out.println("voce não tem nenhum monstro em posição de Ataque");
            return;
        }

        boolean hasMonstersToAttack = false;
        for (Monster monster : myBoard) {
            if (monster != null) {
                hasMonstersToAttack = true;
                break;
            }
        }

        if (!hasMonstersToAttack) {
            System.out.println("Você não tem monstros disponíveis para atacar.");
            return;
        }

        boolean isDirectAttack = false;

        boolean hasEnemyMonsters = false;
        for (Monster monster : enemyBoard) {
            if (monster != null) {
                hasEnemyMonsters = true;
                break;
            }
        }

        if (!hasEnemyMonsters) {
            System.out.println("O inimigo não possui monstros para serem alvos. Um ataque direto será realizado.");
            isDirectAttack = true;
        }

        Monster cardSelectedToAttack = null;
        boolean choosing = true;

        while (choosing) {
            System.out.println("Escolha com qual carta você quer atacar:");
            cardView.printBoard(myBoard);
            cardView.printOptionsOnBoard(myBoard);

            int indexCardSelectedToAttack = scanner.nextInt();
            scanner.nextLine();

            if (indexCardSelectedToAttack < 1 || indexCardSelectedToAttack > myBoard.length) {
                System.out.println("Escolha inválida. Tente novamente.");
            } else {
                cardSelectedToAttack = myBoard[indexCardSelectedToAttack - 1];


                if (cardSelectedToAttack.getPosition() == CardPosition.ATTACK) {
                    choosing = false;
                } else {

                    System.out.println("Essa carta não está na posição de ataque. Escolha outra.");
                }
            }
        }

        choosing = true;

        while (choosing) {
            if (isDirectAttack) {
                attack(cardSelectedToAttack, match.getEnemyBot(), null);
                System.out.println("Ataque direto realizado com sucesso!");
                return;
            }
            System.out.println("Agora escolha o alvo do ataque:");

            cardView.printBoard(enemyBoard);
            cardView.printOptionsOnBoard(enemyBoard);

            int indexCardSelectedToBeTarget = scanner.nextInt();
            scanner.nextLine();

            if (indexCardSelectedToBeTarget < 1 || indexCardSelectedToBeTarget > enemyBoard.length) {
                System.out.println("Escolha inválida. Tente novamente.");
            } else {
                Monster cardSelectedToBeTarget = enemyBoard[indexCardSelectedToBeTarget - 1];
                attack(cardSelectedToAttack, match.getEnemyBot(), cardSelectedToBeTarget);
                choosing = false;
            }
        }

        hasAttackedThisRound = true;
        verifyMonsterIsAlive(match);
    }

    public void verifyMonsterIsAlive(Match match) {
        Monster[] myBoard = match.getPlayer().getZone();
        Monster[] enemyBoard = match.getEnemyBot().getZone();

        for (int i = 0; i < myBoard.length; i++) {
            if (myBoard[i] != null && myBoard[i].getDefense() <= 0) {
                myBoard[i] = null;
            }
        }

        for (int i = 0; i < enemyBoard.length; i++) {
            if (enemyBoard[i] != null && enemyBoard[i].getDefense() <= 0) {
                enemyBoard[i] = null;
            }
        }
    }

    public void attack(Monster attacker, Player targetPlayer, Monster targetCard) {
        if (targetCard == null) {
            // Ataque direto ao jogador
            targetPlayer.sufferDamage(attacker.getAttack());
            System.out.println(attacker.getName() + " atacou diretamente " + targetPlayer.getName() +
                    " causando " + attacker.getAttack() + " de dano!");
            System.out.println("Vida atual = " + targetPlayer.getLifePoints());
            return;
        }

        if (attacker.getPosition() == CardPosition.ATTACK && targetCard.getPosition() == CardPosition.ATTACK) {
            // Duelos entre cartas em modo de ataque
            System.out.println(attacker.getName() + " (ATK: " + attacker.getAttack() + ") duelou com " +
                    targetCard.getName() + " (ATK: " + targetCard.getAttack() + ")!");

            if (attacker.getAttack() > targetCard.getAttack()) {
                // Atacante vence o duelo
                int damageToPlayer = attacker.getAttack() - targetCard.getAttack();
                targetPlayer.sufferDamage(damageToPlayer);
                System.out.println(attacker.getName() + " venceu o duelo e destruiu " + targetCard.getName() + "!");
                System.out.println(targetPlayer.getName() + " sofreu " + damageToPlayer + " de dano!");
                targetCard.setDefense(0); // Marca como destruída
                System.out.println("Vida atual de " + targetPlayer.getName() + " = " + targetPlayer.getLifePoints());
            } else if (attacker.getAttack() < targetCard.getAttack()) {
                // Atacante perde o duelo
                int damageToAttackerPlayer = targetCard.getAttack() - attacker.getAttack();
                attacker.setDefense(0); // Marca como destruída
                System.out.println(attacker.getName() + " foi destruído ao perder o duelo contra " + targetCard.getName() + "!");
                System.out.println("Você sofreu " + damageToAttackerPlayer + " de dano!");
            } else {
                // Empate no duelo
                attacker.setDefense(0); // Marca como destruída
                targetCard.setDefense(0); // Marca como destruída
                System.out.println("O duelo terminou empatado! Ambas as cartas foram destruídas.");
            }
        } else {
            // Situação original: uma das cartas não está em ataque
            int totalDamage = attacker.getAttack() - targetCard.getDefense();

            if (totalDamage > 0) {
                // Ataque bem-sucedido contra uma carta
                System.out.println(attacker.getName() + " atacou " + targetCard.getName() +
                        " e causou " + totalDamage + " de dano na defesa!");

                targetCard.setDefense(targetCard.getDefense() - totalDamage);

                if (targetCard.getDefense() <= 0) {
                    System.out.println(targetCard.getName() + " foi destruída!");

                    if (targetCard.getPosition() == CardPosition.ATTACK) {
                        int damageToPlayer = totalDamage;
                        targetPlayer.sufferDamage(damageToPlayer);
                        System.out.println("Como " + targetCard.getName() + " estava em modo de ataque, " + targetPlayer.getName() +
                                " sofreu " + damageToPlayer + " de dano!");
                        System.out.println("Vida atual = " + targetPlayer.getLifePoints());
                    }

                } else {
                    System.out.println(targetCard.getName() + " agora possui " + targetCard.getDefense() + " de defesa.");
                }
            } else {
                // Ataque insuficiente
                int selfDamage = Math.abs(totalDamage);
                attacker.setDefense(attacker.getDefense() - selfDamage);

                System.out.println(attacker.getName() + " atacou " + targetCard.getName() +
                        " mas a defesa de " + targetCard.getName() + " foi superior!");
                System.out.println(attacker.getName() + " sofreu " + selfDamage + " de dano na própria defesa.");

                if (attacker.getDefense() <= 0) {
                    System.out.println(attacker.getName() + " foi destruído ao tentar atacar!");

                } else {
                    System.out.println(attacker.getName() + " agora possui " + attacker.getDefense() + " de defesa.");
                }
            }
        }

    }



    public void positionMenu(Match match) {

        if (!MatchController.validateMonster(match.getPlayer().getHandDeck().getCards())) {
            System.out.println("Você é muito azarado não conseguiu nenhum monstro");
            cardView.printCards(match.getPlayer().getHandDeck().getCards());
            return;
        }

        int optionMode;
        boolean modeSelect = false;
        boolean playerSelection = false;
        boolean selectposition = false;
        int handPosition;
        int boardPosition;


        if (placement) {
            System.out.println("Ei, você já posicionou um monstro! Não dá pra fazer isso de novo!");
            return;
        }

        System.out.println("\n============================================");
        System.out.println("===  " + BOLD + " Escolha uma carta! " + RESET + "  ===");

        cardView.printCards(match.getPlayer().getHandDeck().getCards());
        cardView.printOptions(match.getPlayer().getHandDeck().getCards().size());


        System.out.println("O goblin rosna: 'Vamos lá, aventureiro! Selecione uma carta do seu deck!'");
        handPosition = scanner.nextInt();
        scanner.nextLine();

        do {
            if (handPosition < 0 || handPosition > match.getPlayer().getHandDeck().getCards().size()) {
                System.out.println("O goblin bufa: 'BAAAHHH, essa posição não é válida! Olhe as cartas direito!");
                handPosition = scanner.nextInt();
                scanner.nextLine();
            } else if (match.getPlayer().getHandDeck().getCards().get(handPosition - 1) instanceof Effect) {
                System.out.println("O goblin de taverna coça a cabeça e diz: 'Hmm... essa carta aí não parece um monstro, não pode lutar!'");
                System.out.println("Escolha outra carta");
                handPosition = scanner.nextInt();
                scanner.nextLine();
            } else {
                playerSelection = true;
            }
        }
        while (!playerSelection);

        System.out.println("O goblin de taverna ri: 'Hahaha! Agora decida o modo de batalha para essa carta!'");
        System.out.println("1 - modo de ataque");
        System.out.println("2 - modo de defesa");
        optionMode = scanner.nextInt();

        while (!modeSelect) {
            if (MatchController.isValidBatleMode(optionMode)) {
                modeSelect = true;
            } else {
                System.out.println("O goblin coça o queixo: 'Hmm, esse modo não existe! Escolha ataque ou defesa!'");
                optionMode = scanner.nextInt();
            }
        }

        System.out.println("Escolha uma posição para invocar");
        MatchController.validPositions();
        boardPosition = scanner.nextInt();

        while (!selectposition) {
            if (MatchController.isValidOption(handPosition)) {
                MatchController.summonMonster(boardPosition, optionMode, handPosition);
                placement = true;
                selectposition = true;
            } else {
                System.out.println("O goblin suspira: 'Essa posição não é válida. Escolha direito, aventureiro!'");
                MatchController.validPositions();
                MatchController.validPositions();
                boardPosition = scanner.nextInt();
            }

        }

    }

    public void printEntireBoard(Match match) {
        System.out.println("Campo do Adversario");
        cardView.printBoard(match.getEnemyBot().getZone());
        System.out.println("Seu campo:");
        cardView.printBoard(match.getPlayer().getZone());
    }

    public void printBoardWithNumberOfCardPositions(Match match, Monster[] zoneBoardParam) {
        cardView.printBoard(zoneBoardParam);
        cardView.printOptionsOnBoard(zoneBoardParam);
    }

}