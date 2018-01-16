package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Cell;
import model.Game;
import model.Pirate;
import model.Player;

public class GameView extends JPanel {
	private static final long serialVersionUID = 335356890633043342L;
	private Game game;
	private BufferedImage image;
	private int numRows;
	private int numCols;

	public GameView(Game game, String imagePath, int numRows, int numCols) throws IOException {
		this.game = game;
		this.image = ImageIO.read(new File(imagePath));
		this.numRows = numRows;
		this.numCols = numCols;

		// setLayout(new GridLayout(numCols, numCols));
		// Cell cell = game.getBoard().getFirstCell();
		// for (int i = 0; i < numCols; i++) {
		// for (int j = 0; j < numCols; j++) {
		// if (i == 0 && j == 0) {
		// } else {
		// cell = cell.getNext();
		// }
		// Icon icon = new ImageIcon(cell.getSymbol() + ".png");
		// JButton button = new JButton(icon);
		// add(button);
		// }
		// }
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintBackground(g);
		for (Player player : game.getPlayers()) {
			for (Pirate pirate : player.getPirates()) {
				Cell cell = pirate.getCell();
				int cellIndex = cell.getIndex();
				paintPawn(g, findCellTopLeftCorner(cellIndex), player.getIndex(), cell);
			}
		}
	}

	private void paintBackground(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		g.drawImage(image, 0, 0, width, height, this);
	}

	private Point findCellTopLeftCorner(int cellIndex) {
		int cellWidth = getWidth() / numCols;
		int cellHeight = getHeight() / numRows;
		Point locationOnGrid = translateIndexToZigZagLocation(cellIndex);
		int baseX = locationOnGrid.x * cellWidth;
		int baseY = locationOnGrid.y * cellHeight;
		return new Point(baseX, baseY);
	}

	private Point translateIndexToZigZagLocation(int cellIndex) {
		// Ordinary 1D -> 2D translation
		int x = cellIndex % numCols;
		int y = cellIndex / numCols;

		// Invert the X axis on odd-indexed rows
		if (y % 2 == 1)
			x = numCols - x - 1;

		// Invert the Y axis
		y = numRows - y - 1;

		return new Point(x, y);
	}

	private void paintPawn(Graphics g, Point cellTopLeftCorner, int playerIndex, Cell cell) {
		int cellWidth = getWidth() / numCols;
		int cellHeight = getHeight() / numRows;
		int pawnWidth = cellWidth / 4;
		int pawnHeight = cellHeight / 4;
		int pawnX = cellTopLeftCorner.x + playerIndex * pawnWidth;
		int pawnY = cellTopLeftCorner.y + playerIndex * pawnHeight;
		int numPlayers = game.getPlayers().size();
		g.setColor(pickPlayerColor(numPlayers, playerIndex));
		g.fillOval(pawnX, pawnY, pawnWidth, pawnHeight);
	}

	// Splits the HSB color space evenly among the players
	Color pickPlayerColor(float numPlayers, int i) {
		return new Color(Color.HSBtoRGB(i / numPlayers, 1.0f, 1.0f));
	}
}
