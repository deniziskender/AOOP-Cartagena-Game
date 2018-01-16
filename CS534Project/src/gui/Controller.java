package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import commands.EndTurnImpl;
import commands.MoveForwardImpl;
import model.Card;
import model.Game;
import model.Pirate;
import model.Player;
import network.Client;
import network.Server;

public class Controller implements ActionListener {
	private Random random = new Random();

	private Game game;
	private GUI gui;
	private boolean isServer;

	public Controller(Game game, GUI gui, boolean isServer) {
		this.game = game;
		this.gui = gui;
		this.isServer = isServer;
		gui.addActionListener(this);
	}

	public void start() {
		JFrame frame = null;
		if(isServer)
			frame = new JFrame("...Cartagena board game(Server)...");
		else 
			frame = new JFrame("...Cartagena board game(Client)...");
		frame.setSize(700, 600);
		frame.setLayout(new BorderLayout());
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(game.getIsMyTurn()) {
			String actionCmd = e.getActionCommand();
			if (actionCmd.equals("MoveForward")) {
				moveForward();
			} else if (actionCmd.equals("MoveBackward")) {
				moveBackward();
			} else if (actionCmd.equals("EndTurn")) {
				game.switchToNextPlayer();
				gui.repaint();
				try {
					if (isServer)
						Server.writer.writeObject(new EndTurnImpl());
					else
						Client.writer.writeObject(new EndTurnImpl());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Not your turn!");			
		}
	}

	private void moveForward() {
		if (!game.hasReachedMaxPlay()) {
			if (gui.selectedButton != null) {
				gui.myHand.remove(gui.selectedButton);
				gui.myHand.repaint();
				// 
				Card selectedCard = game.getSelectedCard();
				Player currentPlayer = game.currentPlayer();
				int numRandomPirate = random.nextInt(currentPlayer.getPirates().size());
				Pirate randomPirate = currentPlayer.getPirates().get(numRandomPirate);
				game.setSelectedPirate(randomPirate);
				try {
					if(isServer) 
						Server.writer.writeObject(new MoveForwardImpl(selectedCard, randomPirate));
					else
						Client.writer.writeObject(new MoveForwardImpl(selectedCard, randomPirate));						
				} catch (IOException e) {
					e.printStackTrace();
				}
				 // oynadiktan sonra secilen buton'un bosa cikmasi icin
				gui.selectedButton = null;
				game.moveForward();
				gui.repaint();
				if (game.isFinished()) {
					JOptionPane.showMessageDialog(null, "We have a winner!");
					System.exit(0);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Choose card!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "No more moves, , end turn!");
		}
	}

	private void moveBackward() {
		int numOfPiratesInACellBeforeMove = game.moveBackward();
		int counter = game.currentPlayer().getCards().size() - 1;
		for (int i = 0; i < numOfPiratesInACellBeforeMove; i++) {
			gui.myHand.add(new CardGUI(game.currentPlayer().getCards().get(counter), game, gui));
			gui.myHand.repaint();
			counter--;
		}
		gui.repaint();
	}
}
