package commands;

import java.awt.Component;

import gui.CardGUI;
import gui.GUI;
import model.Card;
import model.Game;
import model.Pirate;

public class MoveForwardImpl implements Command {
	Card selectedCard;
	Pirate randomPirate;
	public MoveForwardImpl(Card card, Pirate randomPirate) {
		this.selectedCard = card;
		this.randomPirate = randomPirate;
	}

	public void executeOn(Game game, GUI gui) {
		// hand of client
		game.setSelectedCard(selectedCard);
		game.setSelectedPirate(randomPirate);
		for (Component component : gui.opponentHand.getComponents()) {
			if (component instanceof CardGUI) {
				CardGUI cardGUI = (CardGUI) component;
				String cardName = selectedCard.getFrontFace().getSymbol().toString();
				if(cardGUI.getName().equals(cardName)) {
					gui.opponentHand.remove(cardGUI);
					gui.opponentHand.repaint();
					break;
				}
			}
		}
		game.currentPlayer().moveForward(game);
		gui.repaint();
	}
}