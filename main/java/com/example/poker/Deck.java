package com.example.poker;

import java.util.Random;

public class Deck {
    private final static int allCards = 52;
    private Card[] deck;
    private int top;

    public Deck(){
        deck = new Card[allCards];
        top = 52;

        int cardPosition= 0;
        for(int suit=1; suit<=4; suit++){
            for(int rank=1; rank<=13; rank++){
                deck[cardPosition]= new Card(suit,rank);
                cardPosition++;
            }
        }
    }
    private void shuffle(){
        Random random= new Random();
        for (int i=0; i < allCards;i++){
            int randomPosition= random.nextInt(allCards);
            Card tempCard= deck[i];
            deck[i]= deck[randomPosition];
            deck[randomPosition]= tempCard;
        }
    }
    public Card deal(){
        return deck[--top];
    }
    public void reStart(){
        top = 52;
        this.shuffle();
    }
    public String toString(){
        String showDeck= "";
        int cardNum=1;
        for(Card cards : deck){
            showDeck+=String.format("%d: %s\n", cardNum,cards);
            cardNum++;
        }
            return showDeck;
    }
}
