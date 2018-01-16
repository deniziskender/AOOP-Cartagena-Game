package gui;

import model.Game;
import model.Player;

public class PlayerImpl implements Command {
	Player player;
	public PlayerImpl(Player player) {
		this.player = player;
	}
	
	public void executeOn(Game game, GUI gui) {
		game.getPlayers().add(player);
	}
}