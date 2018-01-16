package gui;

import model.Board;
import model.Card;
import model.Cell;
import model.Color;
import model.Game;
import model.Pirate;

public class PirateImpl implements Command {
	private Pirate pirate;
	private boolean isMyPirate;
	public PirateImpl(Pirate pirate, boolean isMyPirate) {
		this.pirate = pirate;
		this.isMyPirate = isMyPirate;
	}

	public void executeOn(Game game, GUI gui) {
		// pirates of client
		if (isMyPirate)
			game.getPlayers().get(1).addPirate(pirate);
		else
			game.getPlayers().get(0).addPirate(pirate);
	}
}