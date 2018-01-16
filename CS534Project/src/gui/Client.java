package gui;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import model.Board;
import model.BoardBuilder;
import model.Card;
import model.DeckBuilder;
import model.Game;

public class Client {
	private static final int NUM_OF_PLAYERS = 2;
	public static Stack<Command> commands = new Stack<Command>();
	public static ObjectOutputStream writer;
	
	public static void main(String args[]) throws Exception {
		//System.out.print("Enter server IP:");
		//String serverIP = userInput.nextLine();
		String serverIP = "192.168.101.1";

		// When the socket object is created,
		// connection is made.
		Socket socket = new Socket(serverIP, Server.PORT);

		// create reader and writer
		writer = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
		System.out.println("Connected to Server");

		System.out.println("...Cartagena game started(Client)...");
		Board board = BoardBuilder.getBoard();
		List<Card> cards = DeckBuilder.getShuffledCards();
		Game game = new Game(board, cards, NUM_OF_PLAYERS, false);

		GUI gui = new GUI(game, "board.png", 6, 6, false);
		Controller controller = new Controller(game, gui, false);
		controller.start();

		while(true) {
			// read msg from client       
			Command msg = (Command)reader.readObject();
			commands.push(msg);
			msg.executeOn(game, gui);
		}	
	}
}