package interfaces;

import java.util.List;

import game.Figure;

public interface Player {

	public int placeFigure(Figure f, Figure[][] board);
	public Figure selectFigure(List<Figure> remaining, Figure[][] board);
	
}
