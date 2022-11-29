package com.example.poker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


public class PokerApplication {

	public static void main(String[] args) {
		if(args.length<1){
			Game game = new Game();
			game.play();
		}
		else{
			Game game= new Game(args);
			game.play();
		}
	}

}
