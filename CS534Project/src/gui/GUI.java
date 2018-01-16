package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Card;
import model.Game;
import model.Player;

public class GUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private Game game;
	private GameView gameView;
	// east panel part
	private JLabel deckCardsInfo;
	private JLabel deckCardsCount;
	private JLabel usedCardsInfo;
	private JLabel usedCardsCount;
	private JButton moveForward;
	private JButton moveBackward;
	private JButton endTurn;
	private JLabel nextPlayer;
	//
	private JPanel northPanel;
	private JPanel eastPanel;
	private JPanel southPanel;
	//
	public JPanel myHand;
	public JPanel opponentHand;
	//
	public JButton selectedButton;
	//
	JLabel firstplayerInfo = new JLabel("Player 0");
	JLabel secondplayerInfo = new JLabel("Player 1");
	//
	boolean isServer;

	public GUI(Game game, String boardImagePath, int numRows, int numCols, boolean isServer) throws IOException {
		this.game = game;
		this.isServer = isServer;

		setLayout(new BorderLayout());

		initGameView(game, boardImagePath, numRows, numCols);

		initEastPanel();
		initNorthPanel();
		initSouthPanel();

		createHands(game);
	}

	private void initGameView(Game game, String boardImagePath, int numRows, int numCols) throws IOException {
		gameView = new GameView(game, boardImagePath, numRows, numCols);
		add(gameView, BorderLayout.CENTER);
	}

	private void initSouthPanel() {
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		add(southPanel, BorderLayout.SOUTH);
	}

	private void initNorthPanel() {
		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		add(northPanel, BorderLayout.NORTH);
	}

	private void createHands(Game game) {
		if (isServer) {
			myHand = new HandPanel(game, this, game.getPlayers().get(0));
			opponentHand = new HandPanel(game, this, game.getPlayers().get(1));

			myHand.add(firstplayerInfo);
			opponentHand.add(secondplayerInfo);
		} else {
			myHand = new JPanel();
			opponentHand = new JPanel();

			myHand.add(secondplayerInfo);
			opponentHand.add(firstplayerInfo);
		}
		southPanel.add(myHand);
		northPanel.add(opponentHand);
	}

	private void initEastPanel() {
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		add(eastPanel, BorderLayout.LINE_END);

		deckCardsInfo = new JLabel("Deck card count:");
		eastPanel.add(deckCardsInfo);

		deckCardsCount = new JLabel();
		eastPanel.add(deckCardsCount);

		usedCardsInfo = new JLabel("Used cards count:");
		eastPanel.add(usedCardsInfo);

		usedCardsCount = new JLabel();
		eastPanel.add(usedCardsCount);

		moveForward = new JButton("MoveForward");
		eastPanel.add(moveForward);

		moveBackward = new JButton("MoveBackward");
		eastPanel.add(moveBackward);

		endTurn = new JButton("EndTurn");
		eastPanel.add(endTurn);

		nextPlayer = new JLabel("Player");
		nextPlayer.setOpaque(true);
		eastPanel.add(nextPlayer);
	}

	public void addActionListener(ActionListener listener) {
		moveForward.addActionListener(listener);
		moveBackward.addActionListener(listener);
		endTurn.addActionListener(listener);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (game.getPlayers() != null && game.getPlayers().size() == 2) {
			Color playerColor = gameView.pickPlayerColor(game.getPlayers().size(), game.currentPlayer().getIndex());
			nextPlayer.setBackground(playerColor);
			deckCardsCount.setText(game.getCardsWillBeDrawn().size() + " ");
			usedCardsCount.setText(game.getUsedCards().size() + " ");
			super.paintComponent(g);
		}
	}
}

class HandPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Game game;
	GUI gui;
	Player player;

	HandPanel(Game game, GUI gui, Player player) {
		this.game = game;
		this.gui = gui;
		this.player = player;
		if (player != null) {
			for (Card card : player.getCards()) {
				add(new CardGUI(card, game, gui));
			}
		}
	}
}
