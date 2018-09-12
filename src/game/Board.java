package game;

import interfaces.Player;
import interfaces.PlayerNotificatior;
import player.HumanPlayer;
import player.HumanPlayer.State;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private Figure[] remaining;
	private Figure[] board;
	private Player p1;
	private Player p2;
	private Player onTurn;
	private int round;
	private boolean inRound;
	private Figure selectedFigure;
	private boolean firstRound = true; 
	private PlayerNotificatior notifier;

	public Board(Player p1, Player p2, PlayerNotificatior not) {
		this.board = new Figure[16];
		this.remaining = new Figure[16];
		this.p1 = p1;
		this.p2 = p2;
		this.notifier = not;
		this.onTurn = Math.random() > 0.5 ? this.p1 : this.p2;
		for (int i = 0; i < this.remaining.length; i++) {
			this.remaining[i] = new Figure((byte) i);
		}
	}

	public void handleFieldClick(int index) {
		if(this.onTurn instanceof HumanPlayer) {
			((HumanPlayer)this.onTurn).handleFieldClick(index);
		}
	}

	public void handleFigureClick(int index) {
		if(this.onTurn instanceof HumanPlayer) {
			((HumanPlayer)this.onTurn).handleFigureClick(index);
		}
	}
	
	public List<Figure> getRemainingFigures() {
		List<Figure> temp = new ArrayList<Figure>();
		for (Figure figure : this.remaining) {
			if (figure != null)
				temp.add(figure.clone());

		}
		return temp;
	}

	public boolean win() {
		boolean winns = false;
		// diagnoals
		winns |= Figure.hasGeneInCommon(this.board[0], this.board[5], this.board[10], this.board[15]);
		winns |= Figure.hasGeneInCommon(this.board[3], this.board[6], this.board[9], this.board[12]);
		if (winns)
			return true;

		// rows and columns
		for (int i = 0; i < 4; i++) {
			winns |= Figure.hasGeneInCommon(this.board[i], this.board[i + 1], this.board[i + 2], this.board[i + 3]);
			winns |= Figure.hasGeneInCommon(this.board[i], this.board[i + 4], this.board[i + 8], this.board[i + 12]);
			if (winns)
				return true;
		}

		// squares
		for (int i = 0; i < 3; i++) {
			winns |= Figure.hasGeneInCommon(this.board[i], this.board[i + 1], this.board[i + 4], this.board[i + 5]);
			winns |= Figure.hasGeneInCommon(this.board[i + 4], this.board[i + 5], this.board[i + 8], this.board[i + 9]);
			winns |= Figure.hasGeneInCommon(this.board[i + 8], this.board[i + 9], this.board[i + 12],
					this.board[i + 13]);
			if (winns)
				return true;
		}

		return false;
	}

	public Figure[][] get2DBoard() {
		Figure[][] f = new Figure[4][4];
		for (int i = 0; i < 4; i++) {
			f[0][i] = this.board[i].clone();
			f[1][i] = this.board[i + 4].clone();
			f[2][i] = this.board[i + 8].clone();
			f[3][i] = this.board[i + 12].clone();
		}
		return f;
	}

	public Figure[] getRemainingFiguresArray() {
	    return this.remaining;
    }

    public Figure[] getField() {
	    return this.board;
    }

	public void nextRound() {
		if(firstRound) {
			if(this.onTurn instanceof HumanPlayer) {
				HumanPlayer hp = (HumanPlayer) this.onTurn;
				switch (hp.playerState) {
				    case NONE:
                        //let the player know to selekt a figure
                        this.notifier.notifyPlayer("Bitte wähle eine Figur!");
                        hp.playerState = HumanPlayer.State.FIGURE_SELECTED;
                        return;
                    default:
                        this.selectFigure(hp.selectFigure(this.getRemainingFigures(), this.get2DBoard()));
                        this.notifier.resetNotification();
                        this.firstRound = false;
                        this.round++;
                        this.nextRound();
                        return;
				}
			} else {
				this.selectFigure(this.onTurn.selectFigure(this.getRemainingFigures(), this.get2DBoard()));
				this.round++;
				this.firstRound = false;
				this.nextRound();
				return;
			}
		}
		
		
		if(inRound && (this.onTurn instanceof HumanPlayer)) {
			HumanPlayer hp = (HumanPlayer)this.onTurn;
			switch (hp.playerState) {
			case NONE:
				this.placeFigure(this.selectedFigure, hp.placeFigure(this.selectedFigure, this.get2DBoard()));
				//let the user know to select a fugure
				this.notifier.notifyPlayer("Bitte wähle eine Figur!");
				hp.playerState = State.PLACED_FIGURE;
				return;
			case PLACED_FIGURE:
				this.selectFigure(hp.selectFigure(this.getRemainingFigures(), this.get2DBoard()));
				//round over
				this.notifier.resetNotification();
				this.inRound = false;
				this.nextRound();
				return;
			default:
				return;
			}
			
			
		} else {
			
			if(this.round % 2 == 0) {
				this.onTurn = p1;
			} else {
				this.onTurn = p2;
			}
			
			this.round++;
			
			if(!(this.onTurn instanceof HumanPlayer)) {
				this.placeFigure(this.selectedFigure, this.onTurn.placeFigure(this.selectedFigure, this.get2DBoard()));
				this.selectFigure(this.onTurn.selectFigure(this.getRemainingFigures(), this.get2DBoard()));
				this.nextRound();
			} else {
				((HumanPlayer)this.onTurn).playerState = HumanPlayer.State.NONE;
				this.notifier.notifyPlayer("Wähle ein Feld für diese Figur");
				//let the user knot to place place the figure and press the accept button
				this.inRound = true;
				return;
			}
			
		
		
		}
	}
	
	private void selectFigure(Figure f) {
		if(getRemainingFigures().contains(f))
			this.selectedFigure = f;
	}
	
	private void placeFigure(Figure f, int index) {
		if(this.board[index] != null)
			this.board[index] = f;
	}

	public Player getP1() {
		return this.p1;
	}

	public Player getP2() {
		return this.p2;
	}

}
