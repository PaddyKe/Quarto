package player;

import java.util.List;

import game.Figure;
import interfaces.Player;

public class HumanPlayer implements Player {

	public static enum State {
		NONE, PLACED_FIGURE, FIGURE_SELECTED;
	}
	
	public State playerState;
	private int field;
	private int figure;
	
	public void handleFieldClick(int index) {
		this.field = index;
	}

	public void handleFigureClick(int index) {
		this.figure = index;
	}

	@Override
	public int placeFigure(Figure f, Figure[][] board) {
		int row = (this.field / 4);
		int col = this.field % 4;
		if(board[row][col] == null)
			return this.field;
		throw new RuntimeException("Field already in use");
	}

	@Override
	public Figure selectFigure(List<Figure> remaining, Figure[][] board) {
		Figure temp = new Figure((byte)figure);
		if(remaining.contains(temp))
			return remaining.get(remaining.indexOf(temp));
		throw new RuntimeException("Figure already in use.");
	}
	
}
