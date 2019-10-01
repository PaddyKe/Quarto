package player;

import game.Figure;
import interfaces.Player;

import java.util.ArrayList;
import java.util.List;

/*
  Plays random moves.
*/

public class RandomPlayer extends Player {

    public RandomPlayer() {
        super("Rainer Zufall");
    }

    @Override
    public int placeFigure(Figure f, Figure[] board, List<Figure> remaining) {
        List<Integer> field = new ArrayList<Integer>();
        for (int i = 0; i < board.length; i++) {
            if(board[i] == null) {
                field.add(i);
            }
        }
        return field.get(this.rnd.nextInt(field.size()));
    }

    @Override
    public Figure selectFigure(List<Figure> remaining, Figure[] board) {
        return remaining.get(this.rnd.nextInt(remaining.size()));
    }
}
