package com.example.poker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private ArrayList<Card> hand;
    private double bankroll;
    private double bet;

    public Player(){
        hand = new ArrayList<Card>();
        bankroll = 25;
        bet = 0;
    }
    public void addCard(Card card){
        hand.add(card);
    }
    public void removeCard(Card card){
        hand.remove(card);
    }
    public void removeAllCards(){
        hand.clear();
    }
    public void bets(double amount){
        bet = amount;
        bankroll -= amount;
    }
    public void winnings(double odds){
        bankroll += odds;
    }
    public double getBankroll(){
        return bankroll;
    }
    public ArrayList<Card> getHand(){
        return hand;
    }
    public void sortHand(){
        Collections.sort(hand);
    }
}
