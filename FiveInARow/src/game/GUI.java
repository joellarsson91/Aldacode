package game;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class GUI extends JFrame{

	private final int WIDTH = 761;
	private final int HEIGHT = 758;
	
	
	
	public GUI(Board board) {

		super("Five In A Row");
		
		//ev i en annan konstruktor?
		add(board);
		setSize(WIDTH, HEIGHT);		
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		setVisible(true);
		
	
	}
}
