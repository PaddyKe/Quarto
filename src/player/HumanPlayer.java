package player;

import game.Figure;
import interfaces.Player;

import java.util.List;

public class HumanPlayer extends Player {

	public static enum State {
		NONE, PLACED_FIGURE, FIGURE_SELECTED;
	}
	
	public State playerState;
	private int field = -1;
	private int figure = -1;

	public HumanPlayer() {
	    super("NoName");
		this.playerState = State.NONE;
	}

	public HumanPlayer(String name) {
		super(name);
	    this.playerState = State.NONE;
	}

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
		if(board[row][col] == null) {
            int temp = this.field;
            this.field = -1;
		    return temp;
        }
		throw new RuntimeException("Field already in use");
	}

	@Override
	public Figure selectFigure(List<Figure> remaining, Figure[][] board) {
		Figure temp = new Figure((byte)figure);
		if(remaining.contains(temp)) {
		    this.figure = -1;
            return remaining.get(remaining.indexOf(temp));
		}
		throw new RuntimeException("Figure already in use.");
	}
	
}
