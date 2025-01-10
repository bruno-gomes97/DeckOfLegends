package controller;

import domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckController {

    final int MAX_MATCH_DECK_SIZE_LIMIT = 50;
    final int INITIAL_HAND_DECK_SIZE_LIMIT = 5;
    final int INITIAL_EFFECT_CARDS = 10;
    Random randomIntGenerator = new Random();
    static CsvFileReader csvFileReader = new CsvFileReader();

    static List<Card> allCards = csvFileReader.readCards();
    static List<Card> monsterCards = new ArrayList<>();
    static List<Card> effectCards = new ArrayList<>();

    public List<Card> getAllCards() {
        return allCards;
    }

    public DeckController() {
        filterCardsByType();
    }

    private void filterCardsByType() {
        for (Card card : allCards) {
            if (card instanceof Monster) {
                monsterCards.add(card);
            } else if (card instanceof Effect) {
                effectCards.add(card);
            }
        }
    }

    public List<Card> copyCards(List<Card> total) {
        List<Card> copy = new ArrayList<>();
        for (Card card : total) {
            copy.add(card);
        }
        return copy;
    }

    public Deck createARandomMatchDeck() {
        List<Card> actualMonsterPool = copyCards(monsterCards);
        List<Card> actualEffectPool = copyCards(effectCards);


        Deck newDeck = new Deck();

        for (int i = 0; i < MAX_MATCH_DECK_SIZE_LIMIT - INITIAL_EFFECT_CARDS; i++) {
            Card selectedCard = actualMonsterPool.get(randomIntGenerator.nextInt(actualMonsterPool.size()));
            newDeck.addCard(selectedCard);
            actualMonsterPool.remove(selectedCard);
        }

        for (int i = 0; i < INITIAL_EFFECT_CARDS; i++) {
            Card selectedCard = actualEffectPool.get(randomIntGenerator.nextInt(actualEffectPool.size()));
            newDeck.addCard(selectedCard);
            actualEffectPool.remove(selectedCard);
        }

        return newDeck;
    }

    public Deck createARandomHandDeck(Deck deck) {

        Deck handDeck = new Deck();

        for (int i = 0; i < INITIAL_HAND_DECK_SIZE_LIMIT; i++) {

            Card selectedCard = deck.getCards().get(randomIntGenerator.nextInt(deck.getCards().size()));
            handDeck.addCard(selectedCard);
            deck.getCards().remove(selectedCard);
        }

        return handDeck;
    }

    public boolean addNewCardIntoDeck(String newCardNameParam, String newCardTypeParam, String newCardDescriptionParam, int newCardAttackPointsParams, int newCardDefensePointsParams){
        for(Card cardItem : allCards){
            if(cardItem.getName().equals(newCardNameParam)){
                return false;
            }
        }

        Card newCard;
        if(newCardTypeParam.equalsIgnoreCase("efeito")){
            newCard = new Effect(newCardNameParam, newCardDescriptionParam, newCardAttackPointsParams,  newCardDefensePointsParams, Target.DECK);
            effectCards.add(newCard);
        }else if(newCardTypeParam.equalsIgnoreCase("monstro")){
            newCard = new Monster(newCardNameParam, newCardDescriptionParam, newCardAttackPointsParams,  newCardDefensePointsParams);
            monsterCards.add(newCard);
        }else{
            return false;
        }

        allCards.add(newCard);
        return true;
    }

    public boolean editCardOfDeck() {
        return false;
    }

    public Card findACardOnAllCardsByName(String nameParam){
        for(Card cardItem : allCards){
            if(cardItem.getName().equals(nameParam)){
                return cardItem;
            }
        }
        return null;
    }
}
