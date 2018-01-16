package commands;

import java.io.Serializable;

import gui.GUI;
import model.Game;

public interface Command extends Serializable {
	public abstract void executeOn(Game game, GUI gui);
}














