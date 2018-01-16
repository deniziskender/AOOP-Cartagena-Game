package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckBuilder {
	private static final int NUM_OF_TOTAL_CARDS = 180;

	public static List<Card> getShuffledCards() {
		System.out.println("---Deck creation started.---");
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < NUM_OF_TOTAL_CARDS; i++) {
			Symbol frontFaceSymbol = Symbol.values()[i % Symbol.values().length];
			Face frontFace = new Face(frontFaceSymbol);
			
			Symbol backFaceSymbol = null;
			Face backFace = new Face(backFaceSymbol);
			Card card = new Card(frontFace, backFace);
			cards.add(card);
		}
		System.out.println("---Deck creation ended.---");
		Collections.shuffle(cards);
		System.out.println("---Deck shuffled, new deck: ---");
		System.out.print("[");
		for (int i = 0; i < cards.size(); i++) {
			if(i % 10 == 0) {
				System.out.println();
			}
			if(i != cards.size() -1) {
				System.out.print(i + " " + cards.get(i).getFrontFace().getSymbol() + ", ");
			} else { 
				System.out.print(i + " " + cards.get(i).getFrontFace().getSymbol());
			}
		}
		System.out.println();
		System.out.println("]");
		return cards;
	}

}
