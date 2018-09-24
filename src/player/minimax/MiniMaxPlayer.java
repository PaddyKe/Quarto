package player.minimax;

import game.Figure;
import interfaces.Player;

import java.util.List;

public class MiniMaxPlayer extends Player {

    public MiniMaxPlayer() {
        super("Kleiner Max");
    }

    @Override
    public int placeFigure(Figure f, Figure[] board, List<Figure> remaining) {
        return -1;
    }

    @Override
    public Figure selectFigure(List<Figure> remaining, Figure[] board) {
        return null;
    }

}
