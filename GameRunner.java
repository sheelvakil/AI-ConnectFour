package connectFour;

public class GameRunner {

	public static void main(String[] args) {

		Board game = new Board();
		game.setBoard();
		Computer computer = new Computer("X", game);
		Player human = new Player("O", game); 

		System.out.println("Starting board");
		game.printBoard();
		System.out.println("Computer token: " + computer.getToken());
		System.out.println("Player token: " + human.getToken());

		while (true) {
			computer.computerTurn(); // computer player <--- main method/function to fix
			if (computer.isWinner() || computer.isTie()) { // this stays the same cuz ur just checking for the win/tie
				break;
			}
			game.printBoard();
			
			human.playerTurn();
			if (human.isWinner() || human.isTie()) {
				break;
			}
			game.printBoard(); // human player
		}

		System.out.println("Final Board");
		game.printBoard();

	}

}
