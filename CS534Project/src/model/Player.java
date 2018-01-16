package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class Player implements Serializable{
	private Random random = new Random();
	private List<Card> cards;
	private List<Pirate> pirates;
	private int index;
	private int playCountInATurn;

	public Player(int index) {
		this.index = index;
		pirates = new ArrayList<Pirate>();
		cards = new ArrayList<Card>();
	}

	public void addPirate(Pirate pirate) {
		pirates.add(pirate);
	}

	public int getIndex() {
		return index;
	}

	public List<Pirate> getPirates() {
		return pirates;
	}

	public void setPirates(List<Pirate> pirates) {
		this.pirates = pirates;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public int getPlayCountInATurn() {
		return playCountInATurn;
	}

	public void setPlayCountInATurn(int playCountInATurn) {
		this.playCountInATurn = playCountInATurn;
	}

	public void moveForward(Game game) {
		Pirate randomPirate = game.getSelectedPirate();
		Card playingCard = game.getSelectedCard();
		int startPos = randomPirate.getCell().getIndex();
		int lastPos = BoardBuilder.NUM_OF_CELLS;
		moveForward(game, randomPirate, playingCard, startPos, lastPos, randomPirate.getIndex());
		game.setSelectedCard(null);
		game.setSelectedPirate(null);
	}

	public int moveBackward(Game game) {
		int numRandomPirate = random.nextInt(pirates.size());
		Pirate randomPirate = pirates.get(numRandomPirate);
		int numOfPiratesInACellBeforeMove = 0;
		if (randomPirate.moveBackward()) {
			Cell currentCell = randomPirate.getCell();
			numOfPiratesInACellBeforeMove = currentCell.getNumOfPirates() - 1;
			System.out.println("There are " + numOfPiratesInACellBeforeMove + " pirates before move in a cell.");
			playCountInATurn++;
		} else {
			JOptionPane.showMessageDialog(null, "No backward available for pirate " + numRandomPirate);
		}
		return numOfPiratesInACellBeforeMove;
	}

	private void moveForward(Game game, Pirate randomPirate, Card playingCard, int startPos, int lastPos,
			int numRandomPirate) {
		if (playingCard != null) {
			System.out.println("---------------" + " Player " + index + "'s turn with pirate " + (numRandomPirate)
					+ "-------------------");
			cards.remove(playingCard);
			int sizeBeforeMove = game.getUsedCards().size();
			System.out.println("playing a " + playingCard.getFrontFace().getSymbol() + ".");
			randomPirate.moveForward(playingCard, startPos, lastPos);
			game.getUsedCards().add(playingCard);
			int sizeAfterMove = game.getUsedCards().size();
			System.out.println("Used cards' count " + sizeBeforeMove + " -> " + sizeAfterMove);
			if (randomPirate.isReachedToBoat()) {
				pirates.remove(randomPirate);
				JOptionPane.showMessageDialog(null,
						"Pirate reached to boat, player " + getIndex() + " owns " + pirates.size() + " pirates now.");
			}
			playCountInATurn++;
			System.out.println("Cards in my hand -> " + cards.size());
			System.out.println("Play count in this turn -> " + playCountInATurn);
		}
	}

	public void drawCard(Game game) {
		List<Card> cardsWillBeDrawn = game.getCardsWillBeDrawn();
		System.out.println("owns " + cards.size() + " cards.");
		System.out.println("drawing a card. Deck cards' count: " + cardsWillBeDrawn.size());
		Card drewCard = cardsWillBeDrawn.get(0);
		cards.add(drewCard);
		Symbol drewSymbol = drewCard.getFrontFace().getSymbol();
		cardsWillBeDrawn.remove(0);
		System.out.println("drew a " + drewSymbol + " . Deck cards' count: " + cardsWillBeDrawn.size());
		System.out.println("owns " + cards.size() + " cards.");
	}
}
