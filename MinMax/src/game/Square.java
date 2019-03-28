package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Square extends JPanel {

	private Color backgroundColor = Color.LIGHT_GRAY;

	private int row;
	private int column;
	
	private boolean isMarked = false;

	private boolean humanMarker = false;

	public Square(int row, int column) {

		this.row = row;
		this.column = column;
		
		setBackground(backgroundColor);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(null);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (isMarked) {
			if (humanMarker) {
				drawXMarker(g);

			} else {

				drawOMarker(g);
			}
		}

	}

	private void drawXMarker(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));
		g2.setColor(Color.RED);
		g2.drawLine(0, 0, getWidth(), getHeight());
		g2.drawLine(0, getHeight(), getWidth(), 0);
	}

	private void drawOMarker(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));
		g2.setColor(Color.BLACK);
		g2.drawOval(2, 2, getWidth() - 4, getHeight() - 4);
	}

	public boolean isMarked() {
		return isMarked;
	}
	
	public boolean isHumanMarker() {
		return humanMarker;
	}

	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void markSquare(boolean humanMarker) {

		if (!isMarked) {

			this.humanMarker = humanMarker;
			isMarked = true;
			repaint();
		}
	}
	
	public void unmarkSquare() {
		isMarked = false;
		this.humanMarker = false;
		repaint();
	}

}
