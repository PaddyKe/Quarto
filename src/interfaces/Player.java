package interfaces;

import game.Figure;

import java.util.List;

public abstract class Player {

	private String name;

	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract int placeFigure(Figure f, Figure[][] board);
	public abstract Figure selectFigure(List<Figure> remaining, Figure[][] board);
	
}
