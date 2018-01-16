package commands;

import gui.GUI;
import model.Board;
import model.Card;
import model.Cell;
import model.Color;
import model.Game;
import model.Pirate;
import model.Player;

public class PirateImpl implements Command {
	private Pirate pirate;
	private boolean isMyPirate;
	public PirateImpl(Pirate pirate, boolean isMyPirate) {
		this.pirate = pirate;
		this.isMyPirate = isMyPirate;
	}

	public void executeOn(Game game, GUI gui) {
		// pirates of client
		Player player;
		if (isMyPirate)
			player= game.getPlayers().get(1);
		else
			player= game.getPlayers().get(0);
		
		player.addPirate(pirate);

	}
}