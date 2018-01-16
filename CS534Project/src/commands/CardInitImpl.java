package commands;

import javax.swing.JPanel;

import gui.CardGUI;
import gui.GUI;
import model.Card;
import model.Game;
import model.Player;

public class CardInitImpl implements Command {
	Card card;
	boolean isMyCard;

	public CardInitImpl(Card card, boolean isMyCard) {
		this.card = card;
		this.isMyCard = isMyCard;
	}

	public void executeOn(Game game, GUI gui) {
		// hand of client
		Player player;
		if (isMyCard) {
			player = game.getPlayers().get(1);
			player.getCards().add(card);
			updateView(gui, game, card, gui.myHand);
		}
		// hand of server
		else {
			player = game.getPlayers().get(0);
			player.getCards().add(card);
			updateView(gui, game, card, gui.opponentHand);
		}
		gui.repaint();
	}
	
	public void updateView(GUI gui, Game game, Card card, JPanel panel) {
		panel.add(new CardGUI(card, game, gui));
		panel.repaint();
		panel.revalidate();
	}
}