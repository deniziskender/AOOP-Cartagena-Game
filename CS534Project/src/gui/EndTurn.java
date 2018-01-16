package gui;

import commands.Command;
import model.Game;

public class EndTurn implements Command {
	public void executeOn(Game game, GUI gui) {
		game.switchToNextPlayer();
		gui.repaint();
	}
}