package gui;

import java.io.Serializable;

import model.Game;

public interface Command extends Serializable {
	public abstract void executeOn(Game game, GUI gui);
}














