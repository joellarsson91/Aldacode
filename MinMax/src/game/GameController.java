package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JOptionPane;

//Vid game over: styr upp dialogruta om man vill spela igen
//fixa storleken i GUI-klassen på nått bra sätt

public class GameController {

	// kolla upp hur använda detta. Blir grötigt i de andra klasserna nu...
	public final static int DIMENSION = 3;
	
	public final static int MARKS_IN_A_ROW_FOR_WIN = 3;

	//ändra markersCount i board!!
	
//	public final static int CPU_LOSS = -1;
	public final static int ALPHA = -1;
//	public final static int CPU_WIN = 1;
	public final static int BETA = 1;
	
	public final static int DRAW = 0;
	
	private SquareListener squareListener = new SquareListener();

	private Board board = new Board();

	private boolean isHumanTurn;


	
	private class SquareListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent event) {

			Object eventSource = event.getSource();

			if (eventSource instanceof Square) {
				Square square = (Square) eventSource;

				if (!square.isMarked()) {

					turnActions(square);

				}
			}
		}
	}

	public GameController() {

		int humanStart = JOptionPane.showConfirmDialog(null, "Vill du börja?", "Five In A Row",
				JOptionPane.YES_NO_OPTION);

		new GUI(board);

		startGame(humanStart);

	}

	private void startGame(int humanStart) {

		if (humanStart == JOptionPane.YES_OPTION) {

			beginHumanTurn();
		}

		else {
			isHumanTurn = false;
			beginCPUTurn();
		}
	}

	private void turnActions(Square selectedSquare) {

		selectedSquare.markSquare(isHumanTurn);

		if (isHumanTurn) {

			if (isGameOver(selectedSquare)) {
				gameOverActions();

			} else {

				endHumanTurn();

				beginCPUTurn();
			}

		} else {

			if (isGameOver(selectedSquare)) {
				gameOverActions();

			} else {

				beginHumanTurn();
			}

		}

	}

	private void gameOverActions() {

		int startNewGame;

		if (isHumanTurn) {

			startNewGame = JOptionPane.showConfirmDialog(null, "Du vann! Spela igen?", "Game Over!",
					JOptionPane.YES_NO_OPTION);

		} else {
			startNewGame = JOptionPane.showConfirmDialog(null, "Datorn vann :/ Spela igen?", "Game Over!",
					JOptionPane.YES_NO_OPTION);

		}

		if (startNewGame == JOptionPane.YES_OPTION) {
			System.out.println("starta nytt spel!");

		}
	}

	private void beginHumanTurn() {
		isHumanTurn = true;

		board.addListenerToSquares(squareListener);
	}

	private void endHumanTurn() {

		isHumanTurn = false;

		board.removeListenerFromSquares(squareListener);
	}

	private boolean isGameOver(Square latestMarked) {

		boolean isHumanMarker = latestMarked.isHumanMarker();
		int row = latestMarked.getRow();
		int column = latestMarked.getColumn();

		return board.checkFiveInARow(isHumanMarker, row, column);

	}

	private void beginCPUTurn() {

//		Random rnd = new Random();
//		Square s = board.getSquare(rnd.nextInt(DIMENSION), rnd.nextInt(DIMENSION));
		
		Square s = board.findCPUMove(ALPHA, BETA).square;

//		while (s.isMarked()) {
//
//			s = board.getSquare(rnd.nextInt(DIMENSION), rnd.nextInt(DIMENSION));
//		}
		
		
		while (s.isMarked()) {
			s= board.findCPUMove(ALPHA, BETA).square;
		}

		turnActions(s);

	}


	
	
	public static void main(String[] args) {
		
		new GameController();
	}

}
