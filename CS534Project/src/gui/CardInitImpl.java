package gui;

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
		if(isMyCard) {
			Player secondPlayer = game.getPlayers().get(1);
			secondPlayer.getCards().add(card);
			gui.myHand.add(new CardGUI(card, game, gui));	
			gui.myHand.repaint();
			gui.myHand.revalidate();
		}
		// hand of server
		else {
			Player firstPlayer = game.getPlayers().get(0);
			firstPlayer.getCards().add(card);
			gui.opponentHand.add(new CardGUI(card, game, gui));
			gui.opponentHand.repaint();
			gui.opponentHand.revalidate();
		}
		gui.repaint();
	}
}