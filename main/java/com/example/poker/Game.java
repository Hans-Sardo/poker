package com.example.poker;

import javax.xml.bind.SchemaOutputResolver;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Player player;
    private Deck deck;
    private final int fullHandSize = 5;
    private Scanner in;
    public Game(String[] testHand){
        in= new Scanner(System.in);
        player = new Player();
        System.out.println(deck);
        for (String card : testHand){
            char readSuit = card.charAt(0);
            int suit = 0;
            switch (readSuit){
                case 'c':
                    suit = 1;
                    break;
                case'd':
                    suit = 2;
                    break;
                case 'h':
                    suit = 3;
                    break;
                case 's':
                    suit = 4;
                    break;
            }
            int rank = Integer.parseInt(card.substring(1));
            Card newCard= new Card(suit,rank);
            player.addCard(newCard);
        }

        }
        public Game(){
        in = new Scanner(System.in);
        player= new Player();
        deck = new Deck();
        System.out.println(deck);
    }
    public void play(){
        System.out.println("5 Card Draw Poker");
        boolean start= true;
        while(start && player.getBankroll()>0){
            System.out.println("New Game");
            System.out.println("\n Chips: " + player.getBankroll());
            System.out.println("[y]es to start game, [n]o to end: ");
            String game= in.next().toLowerCase();
            if(game.equals("n")){
                start= false;
            }
            else if(game.equals("y")){
                player.bets(0);
                while(true){
                    System.out.println("How much would you like to bet");
                    double bet = in.nextDouble();
                    if(bet<1||bet>5){
                        System.out.println("\nError!");
                        continue;
                    }
                    if(bet > player.getBankroll()){
                        System.out.println("\n Sorry you're out of money");
                        break;
                    }
                    player.bets(bet);
                    deck.reStart();
                    System.out.println(deck);
                    if (player.getHand().size()==0){
                        this.deal5Cards();
                    }
                    player.sortHand();
                    System.out.println("Your Hand: " + player.getHand());
                    while(true){
                        System.out.print("Do you want to exchange?" + "[y]es or [n]o: ");
                        String exchange = in.next().toLowerCase();
                        if(exchange.equals("y")){
                            this.exchangeCard();
                            player.sortHand();
                            System.out.println("\n Your New Hand: " + player.getHand());
                            break;
                        }
                        else if (exchange.equals("n")){
                            break;
                        }
                        else{
                            System.out.println("Error");
                        }
                    }
                    System.out.println("\n Winnings");
                    String combo= checkHand(player.getHand());
                    System.out.printf("you got: %s! \n", combo);
                    double chipsWon=getPayout(combo) * bet;
                    System.out.printf("you gained: %s chips. \n", chipsWon);
                    player.winnings(chipsWon);
                    System.out.printf("you now have: %s chips"+ " total \n", player.getBankroll());
                    player.removeAllCards();
                    break;
                }
                continue;
            }
            else{
                System.out.println("Error");
                continue;
            }
        }
        System.out.println("\n Thanks for Playing!\n");
    }
    private Card newDealtCard(){
        Card newCard= null;
        boolean dealt= true;
        while(dealt){
            newCard = deck.deal();
            dealt= false;
            for(Card dealtCard: player.getHand()){
               if(newCard.compareTo(dealtCard)== 0){
                   dealt = true;
               }
            }
        }
        return newCard;
    }
    private void deal5Cards(){
        for(int i=0; i<fullHandSize; i++){
            player.addCard(this.newDealtCard());
        }
    }
    private void exchangeCard(){
        while(true){
            System.out.print("\n How many cards do you want to exchange?");
            int numOfExchange= in.nextInt();
            if (numOfExchange < 1 || numOfExchange > 5) {
                System.out.println("\nError");
                continue;
            }
            else{
                int[] selectedPosition= new int[numOfExchange];
                int i =0;
                while(i<numOfExchange){
                    System.out.print("Which card?");
                    int cardPostion=in.nextInt();
                    boolean validPosition=true;
                    while(validPosition){
                        validPosition = false;
                        if(cardPostion<1 || cardPostion >5){
                            System.out.print("\nError, please try again");
                            cardPostion=in.nextInt();
                            validPosition= true;
                        }
                        else{
                            for(int existedPosition : selectedPosition){
                                if(cardPostion == existedPosition){
                                    System.out.print("\n Error");
                                    cardPostion = in.nextInt();
                                    validPosition= true;
                                }
                            }
                        }
                    }
                    selectedPosition[i] = cardPostion;
                    player.getHand().set(cardPostion-1, this.newDealtCard());
                    i++;
                }
            }
            break;
        }
    }
    public String checkHand(ArrayList<Card> hand){
        if(isRoyalFLush(hand)){
            return "Royal Flush";
        }
        else if (isStraightFlush(hand)){
            return "Straight Flush";
        }
        else if (isFourOfAKind(hand)){
            return "Four of a Kind";
        }
        else if (isFullHouse(hand)){
            return "Full House";
        }
        else if (isFlush(hand)){
            return "Flush";
        }
        else if (isStraight(hand)){
            return "Straight";
        }
        else if (isThreeOfAKind(hand)){
            return "Three of a Kind";
        }
        else if (isTwoPairs(hand)){
            return "Two Pairs";
        }
        else if (isOnePair(hand)){
            return "One Pair";
        }
        else{
            return "Loser";
        }
    }
    private int numOfMatches(ArrayList<Card> hand){
        int match = 1;
        for (int i=0; i < fullHandSize -1; i++){
            for(int c=i+1; c < fullHandSize; c++){
                if(hand.get(i).getRank() == hand.get(c).getRank()){
                    match++;
                }
            }
        }
        return match;
    }
    private boolean isOnePair(ArrayList<Card> hand){
        if(this.numOfMatches(hand) == 2){
            return  true;
        }
        else{
            return false;
        }
    }
    private boolean isTwoPairs(ArrayList<Card> hand){
        if(this.numOfMatches(hand)== 3){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isThreeOfAKind(ArrayList<Card> hand){
        if(this.numOfMatches(hand)== 4){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isFullHouse(ArrayList<Card> hand){
        if(this.numOfMatches(hand)== 5){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isFourOfAKind(ArrayList<Card> hand){
        if(this.numOfMatches(hand) ==7){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isStraight(ArrayList<Card> hand){
        for(int i=0; i < fullHandSize -1;i++){
            int current= hand.get(i).getRank();
            int next = hand.get(i+1).getRank();
            if(current == 1 && next ==10){
                continue;
            }
            if(current != next -1){
                return false;
            }
        }
        return true;
    }
    private boolean isFlush(ArrayList<Card> hand){
        for(int i=0; i<fullHandSize -1; i++){
            int currentSuit = hand.get(i).getSuit();
            int nextSuit = hand.get(i+1).getSuit();
            if(currentSuit != nextSuit){
                return false;
            }
        }
        return true;
    }
    private boolean isStraightFlush(ArrayList<Card> hand){
        if(this.isFlush(hand) && this.isStraight(hand)){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isRoyalFLush(ArrayList<Card> hand){
        int lastCard = hand.get(fullHandSize-1).getRank();
        int firstCard = hand.get(0).getRank();
        if(this.isFlush(hand) && this.isStraight(hand)){
            return true;
        }
        return false;
    }
    private int getPayout(String combo){
        if(combo.equals("Royal Flush")){
            return 300;
        }
        else if(combo.equals("Straight FLush")){
            return 200;
        }
        else if(combo.equals("Four of a Kind")){
            return 100;
        }
        else if(combo.equals("Full House")){
            return 25;
        }
        else if(combo.equals("Flush")){
            return 10;
        }
        else if(combo.equals("Straight")){
            return 4;
        }
        else if(combo.equals("Three of a Kind")){
            return 3;
        }
        else if(combo.equals("Two Pairs")){
            return 2;
        }
        else if(combo.equals("One Pair")){
            return 1;
        }
        else{
            return 0;
        }
    }
}
