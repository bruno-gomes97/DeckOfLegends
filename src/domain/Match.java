package domain;

import java.util.ArrayList;

public class Match {

    private Player player;
    private Player enemyBot;
    private int roundCount;
    private boolean isEndOfMatch;
    private boolean playerStarts;
    private boolean isPlayerTurn;
    private boolean placement;


    public Match(String playerName, String enemyName) {
        this.isEndOfMatch = false;
        this.roundCount = 1;
        this.player = new Player(playerName);
        this.enemyBot = new Player(enemyName);
    }

    public boolean isPlayerStarts() {
        return playerStarts;
    }

    public void setPlayerStarts(boolean playerStarts) {
        this.playerStarts = playerStarts;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getEnemyBot() {
        return enemyBot;
    }

    public void setEnemyBot(Player enemyBot) {
        this.enemyBot = enemyBot;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public boolean isEndOfMatch() {
        return isEndOfMatch;
    }

    public void setEndOfMatch(boolean endOfMatch) {
        isEndOfMatch = endOfMatch;
    }

    public void setMatchEnded(boolean matchEnded) {
        isEndOfMatch = matchEnded;
    }

    public void startNewRound() {
        this.roundCount++;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;

    }

    public void setPlayerTurn(boolean playerTurn) {
        isPlayerTurn = playerTurn;
    }

    public boolean isPlacement() {
        return placement;
    }

    public void setPlacement(boolean placement) {
        this.placement = placement;
    }

    public void endMatch() {
        this.isEndOfMatch = true;
    }

    //Function  to getANewPlayerOnTheMatch
    public Player createNewPlayerOrBotPlayer(String nameInput) {
        //Instantiate a player with a custom name
        return new Player(5000, nameInput, new Deck(new ArrayList<Card>()), new Deck(new ArrayList<Card>()));
    }

    public void switchTurn() {
        isPlayerTurn = !isPlayerTurn;

    }
}
