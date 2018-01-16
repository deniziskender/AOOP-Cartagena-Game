package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import commands.CardInitImpl;
import commands.PirateImpl;
import commands.PlayerImpl;
import network.Server;

public class Game {

	private static final int CARD_COUNT_FOR_EACH_PLAYER = 6;
	private static final int MAX_PLAY_IN_A_TURN = 2;
	private static final int NUM_OF_PIRATES_FOR_EACH_PLAYER = 6;

	private Card selectedCard;
	private Pirate selectedPirate;
	
	private Board board;
	private List<Card> cardsWillBeDrawn;
	private List<Card> usedCards;
	private List<Player> players;
	private int currentPlayerIndex;

	public Game(Board board, List<Card> cards, int numPlayers, boolean isServer) throws IOException {
		this.board = board;
		this.players = new ArrayList<Player>(numPlayers);
		this.cardsWillBeDrawn = cards;
		this.usedCards = new ArrayList<Card>();
		if (isServer) {
			for (int i = 0; i < numPlayers; i++) {
				System.out.println("---Player " + i + " creation started.---");
				Player player = new Player(i);
				Server.writer.writeObject(new PlayerImpl(player));
				// cards given to players
				List<Card> defaultPlayerCards = new ArrayList<Card>();
				int cardCountForEachPlayer = 0;
				Iterator<Card> it = cards.iterator();
				while (it.hasNext()) {
					if (cardCountForEachPlayer == CARD_COUNT_FOR_EACH_PLAYER) {
						break;
					}
					Card card = (Card) it.next();
					defaultPlayerCards.add(card);
					// send card info to player 0
					if (i == 0) {
						Server.writer.writeObject(new CardInitImpl(card, false));
					}
					// send card info to player 1
					if (i == 1) {
						Server.writer.writeObject(new CardInitImpl(card, true));
					}
					cardCountForEachPlayer++;
					System.out.println("Player " + i + " is given card " + card.getFrontFace().getSymbol());
					it.remove();
				}
				player.setCards(defaultPlayerCards);
				for (int j = 0; j < NUM_OF_PIRATES_FOR_EACH_PLAYER; j++) {
					Pirate pirate = new Pirate(j, board, false);
					player.addPirate(pirate);
					if (i == 0)
						Server.writer.writeObject(new PirateImpl(pirate, false));
					else
						Server.writer.writeObject(new PirateImpl(pirate, true));
				}
				players.add(player);
				System.out.println("---Player " + i + " creation ended.---");
			}
		}
		if (isServer)
			this.currentPlayerIndex = 0;
		else
			this.currentPlayerIndex = 1;
	}

	public Card getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(Card card) {
		this.selectedCard = card;
	}
	
	public Pirate getSelectedPirate() {
		return selectedPirate;
	}
	
	public void setSelectedPirate(Pirate pirate) {
		this.selectedPirate = pirate;
	}
	public List<Card> getCardsWillBeDrawn() {
		return cardsWillBeDrawn;
	}

	public void setCardsWillBeDrawn(List<Card> cardsWillBeDrawn) {
		this.cardsWillBeDrawn = cardsWillBeDrawn;
	}

	public List<Card> getUsedCards() {
		return usedCards;
	}

	public void setUsedCards(List<Card> usedCards) {
		this.usedCards = usedCards;
	}
	
	public boolean getIsMyTurn() {
		return currentPlayerIndex == 0;
	}
	

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	public Player currentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public void switchToNextPlayer() {
		currentPlayer().setPlayCountInATurn(0);
		currentPlayerIndex = (++currentPlayerIndex) % players.size();
		System.out.println(".....Switching to next player.....");
	}

	public boolean isFinished() {
		return getWinner() != null;
	}

	public Player getWinner() {
		for (Player player : getPlayers()) {
			boolean isAllPiratesReachedToBoat = true;
			for (Pirate pirate : player.getPirates()) {
				if (!pirate.isReachedToBoat())
					isAllPiratesReachedToBoat = false;
			}
			if (isAllPiratesReachedToBoat) {
				System.out.println("The winner is player " + player.getIndex());
				return player;
			}
		}
		return null;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Board getBoard() {
		return board;
	}

	public void moveForward() {
		if (!hasReachedMaxPlay()) {
			Player currentPlayer = currentPlayer();
			currentPlayer.moveForward(this);
		} else {
			JOptionPane.showMessageDialog(null, "No more moves, , end turn!");
		}
	}

	public int moveBackward() {
		if (!hasReachedMaxPlay()) {
			Player currentPlayer = currentPlayer();
			int numOfPiratesInACellBeforeMove = currentPlayer.moveBackward(this);
			for (int i = 0; i < numOfPiratesInACellBeforeMove; i++) {
				if (deckCompletelyConsumed()) {
					shuffleUsedCards();
				}
				currentPlayer.drawCard(this);
			}
			return numOfPiratesInACellBeforeMove;
		} else {
			JOptionPane.showMessageDialog(null, "No more moves, end turn!");
			return 0;

		}
	}

	public boolean hasReachedMaxPlay() {
		Player currentPlayer = currentPlayer();
		if (currentPlayer.getPlayCountInATurn() == MAX_PLAY_IN_A_TURN) {
			return true;
		}
		return false;
	}

	public boolean deckCompletelyConsumed() {
		boolean isAnyCardLeftOnBoard = false;
		if (cardsWillBeDrawn.size() != 0) {
			isAnyCardLeftOnBoard = true;
		}
		if (!isAnyCardLeftOnBoard) {
			System.out.println("---Deck completely consumed.---");
			return true;
		}
		return false;
	}

	public void shuffleUsedCards() {
		Collections.shuffle(usedCards);
		cardsWillBeDrawn.addAll(usedCards);
		System.out.println("---Used cards shuffled.---");
		System.out.println("Card count to draw: " + cardsWillBeDrawn.size());
		usedCards.clear();
	}
}
