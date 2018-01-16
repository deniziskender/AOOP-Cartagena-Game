package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.Card;
import model.Game;
import model.Symbol;

public class CardGUI extends JButton {
	private static final long serialVersionUID = 1L;
	String name;

	public CardGUI(Card card, Game game, GUI gui) {
		// rule 5
		Symbol symbol = card.getFrontFace().getSymbol();
		name = symbol.toString();
		setIcon(new ImageIcon(name + ".png"));
		addActionListener(new CardHandler(this, game, gui, card));
	}

	public String getName() {
		return name;
	}
}

class CardHandler implements ActionListener {
	JButton button;
	Game game;
	GUI gui;
	Card card;

	public CardHandler(JButton button, Game game, GUI gui, Card card) {
		this.button = button;
		this.game = game;
		this.gui = gui;
		this.card = card;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// kullanicin kendi turn'u ise
		if (game.getIsMyTurn()) {
			// kullanici kendi kartina tikliyorsa
			if (button.getParent() == gui.myHand) {
				// 2 kere hamle yapmamissa
				if (!game.hasReachedMaxPlay()) {
					Component[] components = button.getParent().getComponents();
					for (Component component : components) {
						if (component instanceof JButton)
							component.setBackground(null);
					}
					button.setBackground(Color.GREEN);
					game.setSelectedCard(card);
					gui.selectedButton = button;
				} else {
					JOptionPane.showMessageDialog(null, "No more moves, end turn!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Not your card, select from your card!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Not your turn!");
		}
	}
}