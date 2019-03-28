package game;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	// behöver ju inte vara faktiska squares i denna...
	private Square[][] squareMatrix = new Square[GameController.DIMENSION][GameController.DIMENSION];

	private final int TO_WIN = GameController.MARKS_IN_A_ROW_FOR_WIN;

	private final int DIMENSION = GameController.DIMENSION;

	private final int ALPHA = GameController.ALPHA;

	private final int BETA = GameController.BETA;

	private final int DRAW = GameController.DRAW;

	private int markersCount = 0;

	public Board() {

		// göra detta i en annan konstruktor istället? vad är bp?

		setLayout(new GridLayout(GameController.DIMENSION, GameController.DIMENSION));

		setUpSquares();

	}

	public Square getSquare(int row, int column) {
		return squareMatrix[row][column];
	}

	public boolean checkFiveInARow(boolean isHumanMarker, int row, int column) {

		return checkVertical(isHumanMarker, row, column) || checkHorizontal(isHumanMarker, row, column)
				|| checkDiagonals(isHumanMarker, row, column);
	}

	private boolean checkVertical(boolean isHumanMarker, int row, int column) {

		Square tempSquare;

		int count = 0;

		// for (int checkRow = row - 4; checkRow < row + 5; checkRow++) {

		for (int checkRow = row - (TO_WIN - 1); checkRow < row + TO_WIN; checkRow++) {

			// is within the board
			if (checkRow >= 0 && checkRow < DIMENSION) {
				tempSquare = squareMatrix[checkRow][column];

				if (tempSquare.isMarked() && tempSquare.isHumanMarker() == isHumanMarker) {
					count++;

					// if (count >= 5) {

					if (count >= TO_WIN) {
						return true;
					}
					// make the count 0 if encounter Square that is marked by opponent or is
					// unmarked
				} else
					count = 0;

			}

		}

		return false;
	}

	private boolean checkDiagonals(boolean isHumanMarker, int row, int column) {

		return checkDownToLeft(isHumanMarker, row, column) || checkDownToRight(isHumanMarker, row, column);

	}

	private boolean checkDownToLeft(boolean isHumanMarker, int row, int column) {

		Square tempSquare;

		int count = 0;

		// int checkRow = row - 4;
		int checkRow = row - (TO_WIN - 1);

		// int checkColumn = column + 4;
		int checkColumn = column + (TO_WIN - 1);

		// for (int i = 0; i < 9; i++) {
		for (int i = 0; i < ((TO_WIN * 2) - 1); i++) {

			if (isWithinBoard(checkRow, checkColumn)) {

				tempSquare = squareMatrix[checkRow][checkColumn];

				if (tempSquare.isMarked() && tempSquare.isHumanMarker()) {
					count++;

					// if (count >= 5) {
					if (count >= TO_WIN) {
						return true;
					}
				}

				else {
					count = 0;
				}

			}

			checkRow++;
			checkColumn--;

		}

		return false;

	}

	private boolean checkDownToRight(boolean isHumanMarker, int row, int column) {
		Square tempSquare;

		int count = 0;

		// int checkRow = row - 4;
		int checkRow = row - (TO_WIN - 1);

		// int checkColumn = column - 4;
		int checkColumn = column - (TO_WIN - 1);

		// for (int i = 0; i < 9; i++) {
		for (int i = 0; i < ((TO_WIN * 2) - 1); i++) {

			if (isWithinBoard(checkRow, checkColumn)) {
				tempSquare = squareMatrix[checkRow][checkColumn];

				if (tempSquare.isMarked() && tempSquare.isHumanMarker() == isHumanMarker) {
					count++;

					// if (count >= 5) {
					if (count >= TO_WIN) {
						return true;
					}
				} else {
					count = 0;
				}

			}

			checkRow++;
			checkColumn++;

		}

		return false;
	}

	private boolean isWithinBoard(int row, int column) {

		return row >= 0 && row < GameController.DIMENSION && column >= 0 && column < GameController.DIMENSION;

	}

	private boolean checkHorizontal(boolean isHumanMarker, int row, int column) {

		Square temp;

		int count = 0;

		// for (int compareColumn = column - 4; compareColumn < column + 5;
		// compareColumn++) {
		for (int compareColumn = column - (TO_WIN - 1); compareColumn < column + TO_WIN; compareColumn++) {

			if (compareColumn >= 0 && compareColumn < squareMatrix[row].length) {

				temp = squareMatrix[row][compareColumn];

				if (temp.isMarked() && temp.isHumanMarker() == isHumanMarker) {

					count++;

					// if (count >= 5) {
					if (count >= TO_WIN) {
						return true;
					}

				} else {
					count = 0;
				}

			}

		}

		return false;
	}

	public void increaseMarkersCount() {
		markersCount++;
	}

	public void decreaseMarkersCount() {
		markersCount--;
	}

	private boolean isFullBoard() {
		return markersCount == DIMENSION * DIMENSION;
	}

	public MoveInfo findCPUMove(int ALPHA, int BETA) {

		int i;
		int responseValue;
		// ska value verkligen vara 1 här också???
		int value = 1;
		Square bestMove = squareMatrix[0][0];

		MoveInfo quickWinInfo;

		if (isFullBoard()) {
			value = DRAW;

		} else if ((quickWinInfo = immediateCompWin()) != null) {
			return quickWinInfo;
		} else {

			value = ALPHA;

			Square s;
												// ?????????&& value < BETA;
			for (int column = 0; column < DIMENSION && value <BETA;  column++) {
				for (int row = 0; row < DIMENSION && value < BETA ; row++) {
					s = squareMatrix[row][column];
					if (!s.isMarked()) {
						s.markSquare(false);
						markersCount++;
						responseValue = findHumanMove(value, BETA).value;
						s.unmarkSquare();
						markersCount--;

						if (responseValue > value) {

							// update best move
							value = responseValue;
							bestMove = s;
						}

					}
				}
			}

		}

		return new MoveInfo(bestMove, value);
	}
	
	
	
	
	//Vilka ändringar måste göras i denna för att översätta den till alpha-beta??
	public MoveInfo findHumanMove(int ALPHA, int BETA) {
		
		int i;
		int responseValue;
		// ska value verkligen vara 1 här också???
		int value = 1;
		Square bestMove = squareMatrix[0][0];

		MoveInfo quickWinInfo;

		if (isFullBoard()) {
			value = DRAW;

		} else if ((quickWinInfo = immediateHumanWin()) != null) {
			return quickWinInfo;
		} else {

			value = BETA;

			Square s;
														//????
			for (int column = 0; column < DIMENSION && value <ALPHA;  column++) {
				
				for (int row = 0; row < DIMENSION && value <ALPHA; row++) {
					s = squareMatrix[row][column];
					if (!s.isMarked()) {
						s.markSquare(true);
						markersCount++;
						responseValue = findCPUMove(value, ALPHA).value;
						s.unmarkSquare();
						markersCount--;

						if (responseValue < value) {

							// update best move
							value = responseValue;
							bestMove = s;
						}

					}
				}
			}

		}

		return new MoveInfo(bestMove, value);
	}
	
	private MoveInfo immediateHumanWin() {
		Square s;
		
		for(int column = 0; column <DIMENSION; column++) {
			for(int row = 0; row<DIMENSION; row++) {
				s = squareMatrix[row][column];
				s.markSquare(true);
				markersCount++;
				
				if(checkFiveInARow(true, row, column)) {
					return new MoveInfo(s, BETA);
				}
				s.unmarkSquare();
				markersCount--;
			}
		}
		
		return null;
	}

	private MoveInfo immediateCompWin() {

		Square s;

		for (int column = 0; column < DIMENSION; column++) {
			for (int row = 0; row < DIMENSION; row++) {
				// markera

				s = squareMatrix[row][column];

				s.markSquare(false);
				markersCount++;

				if (checkFiveInARow(false, row, column)) {
					return new MoveInfo(s, ALPHA);
				}

				s.unmarkSquare();
				markersCount--;

			}
		}
		return null;
	}

	public void removeListenerFromSquares(MouseListener squareListener) {
		for (int row = 0; row < squareMatrix.length; row++) {
			for (int column = 0; column < squareMatrix[row].length; column++) {
				Square s = squareMatrix[row][column];
				s.removeMouseListener(squareListener);
			}
		}
	}

	public void addListenerToSquares(MouseListener squareListener) {
		for (int row = 0; row < squareMatrix.length; row++) {
			for (int column = 0; column < squareMatrix[row].length; column++) {
				Square s = squareMatrix[row][column];
				s.addMouseListener(squareListener);
			}
		}
	}

	private void setUpSquares() {

		for (int row = 0; row < squareMatrix.length; row++) {
			for (int column = 0; column < squareMatrix[row].length; column++) {
				Square s = new Square(row, column);
				squareMatrix[row][column] = s;
				add(s);

			}
		}
	}
}
