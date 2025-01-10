package controller;

import domain.Card;
import domain.Effect;
import domain.Monster;

public class CardController {

    public void chageCardAtributeName(Card card, String newName){
            card.setName(newName);
    }
    public void chageCardAtributeDescription(Card card, String newDescription){
            card.setDescription(newDescription);
    }
    public void chageCardAtributeAttack(Card card, int newAttackPoints){
            if(card instanceof Monster){
                Monster monsterCard = (Monster) card;
                monsterCard.setAttack(newAttackPoints);
            }else if( card instanceof Effect){
                Effect effectCard = (Effect) card;
                effectCard.setBonusAttack(newAttackPoints);
            }
    }
    public void chageCardAtributeDefense(Card card, int newDefensePoints){
        if(card instanceof Monster){
            Monster monsterCard = (Monster) card;
            monsterCard.setDefense(newDefensePoints);
        }else if( card instanceof Effect){
            Effect effectCard = (Effect) card;
            effectCard.setBonusDefense(newDefensePoints);
        }
    }

}
