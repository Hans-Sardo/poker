package com.example.poker;

public class Card implements Comparable<Card> {

    private int suit;
    private int rank;

    public Card(int suit, int rank){
        this.suit=suit;
        this.rank=rank;
    }
    public int compareTo(Card card){
        if (rank>card.rank){
            return 1;
        }
        else if (rank < card.rank){
            return -1;
        }
        else{
            if(suit==card.suit){
                return 0;
            }
            else if (suit> card.suit){
                return 1;
            }else
                return -1;
        }
    }
    public String toString(){
        String suitStr,rankStr;

        switch(suit){
            case 1:
                suitStr = "♣";
                break;
            case 2:
                suitStr = "♦";
                break;
            case 3:
                suitStr = "♥";
                break;
            case 4:
                suitStr = "♠";
                break;
            default:
                suitStr = "Error";
                break;
        }
        switch(rank){
            case 1:
                rankStr = "A";
                break;
            case 2:
                rankStr = "2";
                break;
            case 3:
                rankStr = "3";
                break;
            case 4:
                rankStr = "4";
                break;
            case 5:
                rankStr = "5";
                break;
            case 6:
                rankStr = "6";
                break;
            case 7:
                rankStr = "7";
                break;
            case 8:
                rankStr = "8";
                break;
            case 9:
                rankStr = "9";
                break;
            case 10:
                rankStr = "10";
                break;
            case 11:
                rankStr = "J";
                break;
            case 12:
                rankStr = "Q";
                break;
            case 13:
                rankStr = "K";
                break;
            default:
                rankStr = "ERROR";
                break;

        }

    return String.format("[%s,%s]", suitStr,rankStr);
    }

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }
}