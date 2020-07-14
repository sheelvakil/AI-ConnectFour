package connectFour;

public class Computer {
	private String character;
	private Board board;

	public Computer(String token, Board b) {
		character = token;
		board = b;
	}

	public void computerTurn() {
		int [] move = findBestCol(board);
		//int row = board.getFreeRow(col);
		board.board[move[0]][move[1]] = getToken();
	}

	public int [] findBestCol(Board b) { // this'll return column position
		int move [] = new int [2];
		//int move = -1;
		int bestVal = Integer.MIN_VALUE;
		for (int j = 0; j < b.board[0].length; j++) { // should only do minimax on avaialbe rows
			int row = board.getFreeRow(j);
			if (row != -1) {
				b.board[row][j] = getToken();
				int moveVal = miniMax(board, 6, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
				b.board[row][j] = "~"; // idk if we want to reset it
				if (moveVal > bestVal) {
					bestVal = moveVal;
					move[0] = row;
					move[1] = j;
					//move = j;
				}
			}
		}

		return move;
	}

	public int miniMax(Board b, int depth, int alpha, int beta, boolean isMax) {
		if (depth == 0 || b.isTie() || b.isWinner("O") || b.isWinner(getToken())) {
			return eval(b, isMax);
		}
		if (isMax) {
			int maxBest = Integer.MIN_VALUE;
			for (int j = 0; j < b.board[0].length; j++) {
				int row = b.getFreeRow(j);
				if (row != -1) {
					if (b.board[row][j].equals("~")) {
						b.board[row][j] = getToken();
						int score = miniMax(b, depth - 1, alpha, beta, !isMax);
						b.board[row][j] = "~";
						maxBest = Math.max(maxBest, score);
						alpha = Math.max(alpha, score);
						if (beta <= alpha) {
							break;
						}
					}
				}
			}
			return maxBest;
		} else {
			int minBest = Integer.MAX_VALUE;
			for (int j = 0; j < b.board[0].length; j++) {
				int row = b.getFreeRow(j);
				if (row != -1) {
					if (b.board[row][j].equals("~")) {
						b.board[row][j] = "O";
						int score = miniMax(b, depth - 1, alpha, beta, !isMax);
						b.board[row][j] = "~";
						minBest = Math.min(minBest, score);
						beta = Math.min(beta, score);
						if (beta <= alpha) {
							break;
						}
					}
				}
			}
			return minBest;
		}
	}

	public int eval(Board b, boolean isMax) {
		int score = 0;
		if(b.isTie()) {
			return score;
		}
		if (isMax) {
			if (b.isWinner(getToken())) {
				return Integer.MAX_VALUE;
			}
			for (int i = 1; i < b.board.length; i++) {
				for (int j = 0; j < b.board[i].length; j++) {
					if (b.board[i][j].equals(getToken())) {
						score += boardScore(i, j, getToken(), b);
					}
				}
			}
			return score;
		} else {
			if (b.isWinner("O")) {
				return Integer.MIN_VALUE;
			}
			for (int i = 1; i < b.board.length; i++) {
				for (int j = 0; j < b.board[i].length; j++) {
					if (b.board[i][j].equals("O")) {
						score += boardScore(i, j, "O", b);
					}
				}
			}
			score *= -1;
			return score;
		}
	}

	public int boardScore(int i, int j, String token, Board b) { // score is
		int row = i; // saving original row value
		int col = j; // saving original column value
		// horizontal check
		int score = 0;
		if(i==3) {
			score += 4;
		}
		int count = 0;
		while (inBounds(i, j, b) && b.board[i][j].equals(token)) { // go left
			count++;
			j--;
		}
		while (inBounds(i, j, b) && b.board[i][j].equals(token)) { // go right
			count++;
			j++;
		}
		if(count==2) {
			count += 2;
		}
		if(count==3) {
			score += (token.equals("O")) ? 5 : 100;
		}
		// vertical check
		count = 0;
		j = col; // resetting the column value
		i = row;
		while (inBounds(i, j, b) && b.board[i][j].equals(token)) { // go up
			count++;
			i--;
		}
		while (inBounds(i, j, b) && b.board[i][j].equals(token)) { // go down
			count++;
			i++;
		}
		if(count==2) {
			count += 2;
		}
		if(count==3) {
			score += (token.equals("O")) ? 5 : 100;
		}

		// left diagonal check
		count = 0;
		i = row; // resetting row value
		j = col;
		while (inBounds(i, j, b) && b.board[i][j].equals(token)) { // go upper left (both i and j --)
			i--;
			j--;
			count++;
		}
		i = row;
		j = col;
		count--;// prevent double count
		while (inBounds(i, j, b) && b.board[i][j].equals(token)) { // go lower right (both i and j ++)
			i++;
			j++;
			count++;
		}
		if(count==2) {
			count += 2;
		}
		if(count==3) {
			score += (token.equals("O")) ? 5 : 100;
		}

		// right diagonal check
		count = 0;
		i = row;
		j = col;
		while (inBounds(i, j, b) && b.board[i][j].equals(token)) { // go lower left, j--, i++
			i++;
			j--;
			count++;
		}
		i = row;
		j = col;
		count--;// = prevent double count
		while (inBounds(i, j, b) && b.board[i][j].equals(token)) { // go upper right, i--, j++
			i--;
			j++;
			count++;
		}
		if(count==2) {
			count += 2;
		}
		if(count==3) {
			score += (token.equals("O")) ? 5 : 100;
		}
		return score;
	}

	public boolean inBounds(int i, int j, Board b) {
		if (i < 1 || i >= b.board.length || j < 0 || j >= b.board[0].length) {
			return false;
		}
		return true;
	}

	public String getToken() {
		return character;
	}

	public boolean isWinner() {
		if (board.isWinner(getToken())) {
			System.out.println(getToken() + " won!");
			return true;
		}
		return false;
	}

	public boolean isTie() {
		if (board.isTie()) {
			System.out.println("The game has ended in the a tie.");
			return true;
		}
		return false;
	}
}
