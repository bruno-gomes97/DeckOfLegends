package view;

import java.util.Scanner;

import static utils.Constants.*;
import static utils.Delay.runWithDelay;

public class StartView {

    public Scanner scanner = new Scanner(System.in);
    MenuView menuView = new MenuView();

    public StartView() {
    }

    public static void explainHowItWorks() {

        runWithDelay(() -> System.out.println("Muito bem, preste atenção porque só vou falar uma vez, hein!"), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("Primeiro, você começa com um deck inicial. Não espere algo incrível, hehe."), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("Com ele, você vai enfrentar adversários e tentar esmagá-los! Hehehe..."), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println(""), SHORT_DELAY);
        runWithDelay(() -> System.out.println("Mas, deixa eu te explicar direitinho, porque você parece promissor..."), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("Em cada duelo, os dois jogadores começam com 10.000 pontos de vida."), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("O objetivo? Simples: destruir os pontos de vida do oponente antes que ele destrua os seus!"), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println(""), SHORT_DELAY);
        runWithDelay(() -> System.out.println("Existem dois tipos de cartas:"), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("1. " + BOLD + "Cartas Monstro:" + RESET + " Têm Ataque e Defesa para batalhas diretas."), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("2. " + BOLD + "Cartas Efeito:" + RESET + " Cada uma tem habilidades únicas que podem virar o jogo!"), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("   Elas podem fortalecer suas cartas, te curar, ou até complicar a vida do adversário."), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println(""), SHORT_DELAY);
        runWithDelay(() -> System.out.println("Você joga uma carta por rodada e pode atacar com ela, mas cuidado!"), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("Seu adversário pode ter truques e cartas tão poderosas quanto as suas."), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("Hehehe... Só duelando você vai entender mesmo!"), MEDIUM_DELAY);
        runWithDelay(() -> System.out.println("Agora vá, jovem duelista. Boa sorte, vai precisar dela! \n"), MEDIUM_DELAY);

    }

    public void run() {

        System.out.println("============================================");
        System.out.println("===  " + BOLD + " Bem-vindo ao Deck of Legends! " + RESET + "     ===");
        System.out.println("============================================");
        System.out.println("Hehehe... Olá, forasteiro! Entre, entre!");
        System.out.println("Você está na minha taberna, o único lugar onde heróis e vilões");
        System.out.println("se reúnem para batalhas épicas e duelos lendários!");
        System.out.println();
        System.out.println("Aqui você vai precisar de cérebro afiado, coragem de aço");
        System.out.println("e um baralho poderoso para derrotar seus inimigos!");
        System.out.println();
        System.out.println("Hehe... cuidado, muitos entram, mas poucos saem como campeões!");
        System.out.println("Agora sente-se, escolha suas cartas, e que o jogo comece!");


        boolean running = true;

        while (running) {
            System.out.println("Quer entrar?");
            System.out.println("Escolha com sabedoria jovem:");
            System.out.println("1- Entrar na taberna.");
            System.out.println("2- Ir embora, tenho medo!");
            System.out.print("> ");
            String option = scanner.next();


            switch (option) {
                case "1":
                    System.out.println("Fez a escolha correta!");
                    adventureStart();
                    break;
                case "2":
                    System.out.println("Que pena, poderia ter desfrutado um universo repleto de magia e estrategia, até mais.");
                    running = false;
                    break;
            }
        }


    }

    public void adventureStart() {
        System.out.println("============================================");
        System.out.println("Hehehe... diga-me, jovem aventureiro, como devo chamá-lo?");
        System.out.print("> ");
        String name = scanner.next();

        System.out.println("Ahhh, " + name + ", hein? Nome decente, eu acho...");
        System.out.println("Então, " + name + ", já sabe como as coisas funcionam por aqui?");
        System.out.println("1 - Claro que sim! Sou um veterano!");
        System.out.println("2 - Hmm... não faço ideia. Melhor você explicar.");

        System.out.print("> ");
        String option = scanner.next();

        switch (option) {
            case "1":
                System.out.println("Ótimo, ótimo! Detesto perder tempo explicando para novatos.");
                System.out.println("Agora vamos direto para o que interessa, hehe!");
                break;
            case "2":
                System.out.println("Rrr... Tudo bem, " + name + ", ouça com atenção.");
                explainHowItWorks();
                break;
            default:
                System.out.println("Hmpf... Responda direito, pestinha! Não tenho paciência para brincadeiras.");
                System.out.println("Quer saber? Se vire sozinho! Boa sorte, hahaha!");
        }

        menuView.run(name);
    }

}

