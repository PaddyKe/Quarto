package game;

import interfaces.Player;
import interfaces.PlayerNotificatior;
import javafx.application.Platform;
import javafx.scene.paint.Color;
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
    public boolean gameRunning = true;


    public Board(Player p1, Player p2, PlayerNotificatior not) {
        this.board = new Figure[16];
        this.remaining = new Figure[16];



        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(gameRunning) {
                    if(isOver()) {
                        gameRunning = false;
                        handleEnd(onTurn);
                    }
                }
            }
        });
        //t.setDaemon(true);
        //t.start();

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
    
    private List<Figure> getRemainingFigures() {
        List<Figure> temp = new ArrayList<Figure>();
        for (Figure figure : this.remaining) {
            if (figure != null)
                temp.add(figure.clone());

        }
        return temp;
    }

    private boolean win() {
        boolean winns = false;
        // diagnoals
        winns |= Figure.hasGeneInCommon(this.board[0], this.board[5], this.board[10], this.board[15]);
        winns |= Figure.hasGeneInCommon(this.board[3], this.board[6], this.board[9], this.board[12]);
        if (winns)
            return true;

        // rows and columns
        for (int i = 0; i < 4; i++) {
            winns |= Figure.hasGeneInCommon(this.board[(i * 4)], this.board[(i * 4) + 1], this.board[(i * 4) + 2], this.board[(i * 4) + 3]);
            winns |= Figure.hasGeneInCommon(this.board[i], this.board[i + 4], this.board[i + 8], this.board[i + 12]);
            if (winns)
                return true;
        }

        // squares
        for (int i = 0; i < 3; i++) {
            winns |= Figure.hasGeneInCommon(this.board[i], this.board[i + 1], this.board[i + 4], this.board[i + 5]);
            winns |= Figure.hasGeneInCommon(this.board[i + 4], this.board[i + 5], this.board[i + 8], this.board[i + 9]);
            winns |= Figure.hasGeneInCommon(this.board[i + 8], this.board[i + 9], this.board[i + 12], this.board[i + 13]);
            if (winns)
                return true;
        }

        return false;
    }

    public Figure[][] get2DBoard() {
        Figure[][] f = new Figure[4][4];
        for (int i = 0; i < 4; i++) {
            if(this.board[i] != null) f[0][i] = this.board[i].clone();
            if(this.board[i + 4] != null) f[1][i] = this.board[i + 4].clone();
            if(this.board[i + 8] != null) f[2][i] = this.board[i + 8].clone();
            if(this.board[i + 12] != null) f[3][i] = this.board[i + 12].clone();
        }
        return f;
    }

    private Figure[] get1DBoard() {
        Figure[] fs = new Figure[this.board.length];
        for(int i = 0; i < fs.length; i++) {
            fs[i] = (board[i] == null) ? null : board[i].clone();
        }
        return fs;
    }

    public Figure[] getRemainingFiguresArray() {
        return this.remaining;
    }

    public Figure[] getField() {
        return this.board;
    }

    public Figure getSelectedFigure() {
        return this.selectedFigure;
    }

    public void nextRound() {
        if(!this.gameRunning) return;
        if(firstRound) {
            if(this.onTurn instanceof HumanPlayer) {
                HumanPlayer hp = (HumanPlayer) this.onTurn;
                switch (hp.playerState) {
                    case NONE:
                        //let the player know to selekt a figure
                        this.notifier.setPlayer(this.onTurn.getName());
                        this.notifier.notifyPlayer("Bitte wähle eine Figur!");
                        hp.playerState = HumanPlayer.State.FIGURE_SELECTED;
                        return;
                    default:
                        this.selectFigure(hp.selectFigure(this.getRemainingFigures(), this.get1DBoard()));
                        this.notifier.resetNotification();
                        this.firstRound = false;
                        this.round++;
                        this.notifier.updateView();
                        if(this.isOver())
                            handleEnd(this.onTurn);
                        else
                            this.nextRound();
                        return;
                }
            } else {
                this.selectFigure(this.onTurn.selectFigure(this.getRemainingFigures(), this.get1DBoard()));
                this.round++;
                this.firstRound = false;
                this.notifier.updateView();
                if(this.isOver())
                    handleEnd(this.onTurn);
                else
                    this.nextRound();
                return;
            }
        }
        
        
        if(inRound && (this.onTurn instanceof HumanPlayer)) {
            HumanPlayer hp = (HumanPlayer)this.onTurn;
            switch (hp.playerState) {
            case NONE:
                this.placeFigure(this.selectedFigure, hp.placeFigure(this.selectedFigure, this.get1DBoard(), this.getRemainingFigures()));
                //this.placeFigure(this.selectedFigure, this.onTurn);
				this.selectedFigure = null;
                this.notifier.updateView();
                //let the user know to select a fugure
                if(this.isOver()) {
                    this.handleEnd(this.onTurn);
                    return;
                }
                this.notifier.setPlayer(this.onTurn.getName());
                this.notifier.notifyPlayer(onTurn.getName() + " Bitte wähle eine Figur!");
                hp.playerState = State.PLACED_FIGURE;
                return;
            case PLACED_FIGURE:
                this.selectFigure(hp.selectFigure(this.getRemainingFigures(), this.get1DBoard()));
                //round over
                this.notifier.resetNotification();
                this.inRound = false;
                this.notifier.updateView();
                if(this.isOver())
                    handleEnd(this.onTurn);
                else
                    this.nextRound();
                return;
            default:
                return;
            }
            
            
        } else {
            /*
            if(this.round % 2 == 0) {
                this.onTurn = p1;
            } else {
                this.onTurn = p2;
            }
            */

            this.swapPlayer();

            this.round++;
            
            if(!(this.onTurn instanceof HumanPlayer)) {
                this.placeFigure(this.selectedFigure, this.onTurn.placeFigure(this.selectedFigure, this.get1DBoard(), this.getRemainingFigures()));
                //this.placeFigure(this.selectedFigure, this.onTurn);
				this.selectFigure(this.onTurn.selectFigure(this.getRemainingFigures(), this.get1DBoard()));
                this.notifier.updateView();
                if(this.isOver())
                    handleEnd(this.onTurn);
                else
                    this.nextRound();
            } else {
                ((HumanPlayer)this.onTurn).playerState = HumanPlayer.State.NONE;
                this.notifier.setPlayer(this.onTurn.getName());
                this.notifier.notifyPlayer("Wähle ein Feld für diese Figur");
                //let the user knot to place place the figure and press the accept button
                this.notifier.updateView();
                this.inRound = true;
                //return;
            }
            
        
        
        }
    }

    private void swapPlayer() {
        if(this.p1 == this.onTurn) {
            this.onTurn = this.p2;
        } else {
            this.onTurn = this.p1;
        }
    }

    private void selectFigure(Figure f) {
        if(getRemainingFigures().contains(f)) {
            this.remaining[f.getGenome()] = null;
            this.selectedFigure = f;
        }
    }
    
    private void placeFigure(Figure f, int index) {
        if(this.board[index] == null) {
            this.board[index] = f;
        }
    }

    private void placeFigure(Figure f, Player p) {
    	Figure[] board = this.get1DBoard();
    	List<Figure> fs = this.getRemainingFigures();
    	int index = p.placeFigure(f, board, fs);
        while(index < 0 || this.board[index] != null) {
        	index = p.placeFigure(f, board, fs);
		}
		this.board[index] = f;
    }
    
    public Player getP1() {
        return this.p1;
    }

    public Player getP2() {
        return this.p2;
    }

    public Player getLastPlayer() {
        return this.onTurn;
    }

    public int[] getWinningSituation() {
        if(Figure.hasGeneInCommon(this.board[0], this.board[5], this.board[10], this.board[15]))
            return new int[] {0, 5, 10, 15};

        if(Figure.hasGeneInCommon(this.board[3], this.board[6], this.board[9], this.board[12]))
            return new int[] {3, 6, 9, 12};

        // rows and columns
        for (int i = 0; i < 4; i++) {
            if(Figure.hasGeneInCommon(this.board[(i * 4)], this.board[(i * 4) + 1], this.board[(i * 4) + 2], this.board[(i * 4) + 3]))
                return new int[]{(i * 4), (i * 4) + 1, (i * 4) + 2, (i * 4) + 3};
            if(Figure.hasGeneInCommon(this.board[i], this.board[i + 4], this.board[i + 8], this.board[i + 12]))
                return new int[] {i, i + 4, i + 8, i + 12};

        }

        // squares
        for (int i = 0; i < 3; i++) {
            if(Figure.hasGeneInCommon(this.board[i], this.board[i + 1], this.board[i + 4], this.board[i + 5]))
                return new int[] {i, i + 1, i + 4, i + 5};
            if(Figure.hasGeneInCommon(this.board[i + 4], this.board[i + 5], this.board[i + 8], this.board[i + 9]))
                return new int[] {i + 4, i + 5, i + 8, i + 9};
            if(Figure.hasGeneInCommon(this.board[i + 8], this.board[i + 9], this.board[i + 12], this.board[i + 13]))
                return new int[] {i + 8, i + 9, i + 12, i + 13};
        }

        return null;
    }

    public Player getHumanPlayer() {
        if (this.p1 instanceof HumanPlayer)
            return this.p1;
        else if(this.p2 instanceof HumanPlayer)
            return this.p2;
        else
            return null;
    }

    public boolean is101() {
        return this.p1 instanceof HumanPlayer && this.p2 instanceof HumanPlayer;
    }

    public Player getNotOnTurn() {
        if(this.p2.equals(this.onTurn))
            return p1;
        else
            return p2;
    }

    public void setP1(Player p) {
        if(this.onTurn.equals(p1))
            this.onTurn = p;
        this.p1 = p;
    }

    public void setP2(Player p) {
        if(this.onTurn.equals(p))
            this.onTurn = p;
        this.p2 = p;
    }

    private boolean simpleDraw() {
        int counter = 0;
        for(Figure f : this.board) {
            if(f == null)
                counter++;
        }
        return counter == 0 && !this.win();
    }
    
    private boolean draw() {
        int counter = 0;
        for(Figure f : this.board) {
            if(f == null)
                counter++;
        }
        return counter == 0;
    }
    
    private boolean isOver() {
        return this.draw() || this.win();
    }
    
    private void handleEnd(Player lastMove) {
        if(this.win()) {
            this.gameRunning = false;
            Platform.runLater(() -> this.notifier.setStyle(Color.RED));
            Platform.runLater(() -> this.notifier.setPlayer(lastMove.getName() + " hat gewonnen."));
            Platform.runLater(() -> this.notifier.notifyPlayer("Klicke auf das Feld um neu zu beginnen."));
            System.out.println(lastMove.getName() + " hat gewonnen");
            //System.out.println(Arrays.toString(this.getWinningSituation()));
            Platform.runLater(() -> this.notifier.showWin(this.getWinningSituation()));
        } else if(this.simpleDraw()) {
            this.gameRunning = false;
            Platform.runLater(() -> this.notifier.setStyle(Color.YELLOW));
            Platform.runLater(() -> this.notifier.setPlayer("Unentschieden..."));
            Platform.runLater(() -> this.notifier.notifyPlayer("Klicke auf das Feld um neu zu beginnen."));
        }
    }

}
