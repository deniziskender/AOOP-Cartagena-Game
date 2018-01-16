package commands;

import gui.GUI;
import model.Game;

public class EndTurnImpl implements Command {
	private static final long serialVersionUID = 1L;

	public void executeOn(Game game, GUI gui) {
		game.switchToNextPlayer();
		gui.repaint();
	}
}