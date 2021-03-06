package player;

import game.Figure;
import interfaces.Player;

import java.util.List;

/*
 This class provides a Interfce for two humans to play against each other.
 Currently playing via network is not supported.
*/

public class HumanPlayer extends Player {

    public enum State {
        NONE, PLACED_FIGURE, FIGURE_SELECTED;
    }

    private static int counter = 0;
    public State playerState;
    private int field = -1;
    private int figure = -1;


    public HumanPlayer() {
        super();
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
    public void reset() {
        this.playerState = State.NONE;
        this.field = -1;
        this.figure = -1;
    }

    @Override
    public int placeFigure(Figure f, Figure[][] board, List<Figure> remaining) {
        int row = (this.field / 4);
        int col = this.field % 4;
        if(this.field >= 0) {
            if (board[row][col] == null) {
                int temp = this.field;
                this.field = -1;
                return temp;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(board[i][j] == null) {
                    this.field = -1;
                    return i * 4 + j;
                }
            }
        }

        throw new RuntimeException("Field already in use");
    }

    @Override
    public Figure selectFigure(List<Figure> remaining, Figure[] board) {
        Figure temp = new Figure((byte) figure);
        if (remaining.contains(temp)) {
            this.figure = -1;
            return remaining.get(remaining.indexOf(temp));
        }
        this.figure = -1;
        return remaining.get(0);
    }

}
