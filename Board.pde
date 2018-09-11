import java.util.List;
import java.util.ArrayList;

class Board implements BoardInterface {
  private Figure[] board;
  private Figure[] remainingFigures;
  private Figure toPlace;
  private final Player p1;
  private final Player p2;
  private Player onTurn;
  private int round;
  
  public Board(Player p1, Player p2) {
    this.round = 0;
    this.p1 = p1;
    this.p2 = p2;
    this.board = new Figure[16];
    this.remainingFigures = new Figure[16];
    remainingFigures[0] = Figure.SQUARE_NOHOLE_WHITE_LARGE;
    remainingFigures[1] = Figure.ROUND_NOHOLE_WHITE_LARGE;
    remainingFigures[2] = Figure.SQUARE_NOHOLE_BLACK_LARGE;
    remainingFigures[3] = Figure.ROUND_NOHOLE_BLACK_LARGE;
    remainingFigures[4] = Figure.SQUARE_HOLE_WHITE_LARGE;
    remainingFigures[5] = Figure.ROUND_HOLE_WHITE_LARGE;
    remainingFigures[6] = Figure.SQUARE_HOLE_BLACK_LARGE;
    remainingFigures[7] = Figure.ROUND_HOLE_BLACK_LARGE;
    remainingFigures[8] = Figure.SQUARE_HOLE_WHITE_SMALL;
    remainingFigures[9] = Figure.ROUND_HOLE_WHITE_SMALL;
    remainingFigures[10] = Figure.SQUARE_HOLE_BLACK_SMALL;
    remainingFigures[11] = Figure.ROUND_HOLE_BLACK_SMALL;
    remainingFigures[12] = Figure.SQUARE_NOHOLE_WHITE_SMALL;
    remainingFigures[13] = Figure.ROUND_NOHOLE_WHITE_SMALL;
    remainingFigures[14] = Figure.SQUARE_NOHOLE_BLACK_SMALL;
    remainingFigures[15] = Figure.ROUND_NOHOLE_BLACK_SMALL;
    
    
  }
  
  public int getRound() {
    return this.round;
  }
  
  public void nextRound() {
    if(this.round % 2 == 0)
      this.onTurn = p1;
    else
      this.onTurn = p2;
    
    println("waiting for input in round " + round);
    
    if(round > 0) {
      this.placeFigure(this.onTurn.placeFigure(this.toPlace));
      this.selectFigure(this.onTurn.selectFigureIndex(getRemainingFigures())); 
    } else {
      this.selectFigure(this.onTurn.selectFigureIndex(getRemainingFigures())); 
      
    }
    round++;
  }

  private List<Figure> getRemainingFigures() {
   List<Figure> list = new ArrayList<Figure>();
    for(Figure f : this.remainingFigures) {
     if(f != null)
       list.add(f);
   }
   return list;
  }

  public Figure getRemaining(int i) {
    return this.remainingFigures[i];
  }
  
  public Figure getBoard(int i) {
    return this.board[i];
  }
  
  public void removeRemaininfFigure(int i) {
    this.board[i] = null;
  }
  
  public void selectFigure(int i) {
    if(this.remainingFigures[i] != null) {
      this.toPlace = this.remainingFigures[i];
      this.remainingFigures[i] = null;
    } else {
      throw new RuntimeException("This is no Figure");
    }
 }
 
 public int figureToIndex(final Figure f) {
  for(int i = 0; i < 16; i++) {
    if(this.remainingFigures[i].equals(f))
      return i;
   }
   throw new RuntimeException("Figure already in use");
 }
 
  public void placeFigure(int i) {
    if(this.toPlace == null) throw new RuntimeException("No figure to place");
    if(this.board[i] != null) throw new RuntimeException("There already is a Figure");
    this.board[i] = this.toPlace;
    this.toPlace = null;
  }
  
  public Figure getToPlace() {
   return this.toPlace; 
  }
  
  public void handleFigureClick(int x) {
    if(this.onTurn instanceof HumanPlayer)
      ((HumanPlayer) this.onTurn).handleFigureClick(x);
      
  }
  
  public void handleFieldClick(int x) {
    if(this.onTurn instanceof HumanPlayer)
      ((HumanPlayer) this.onTurn).handleFieldClick(x);
      
  }
  
  public void removeRemaininfFigure(final Figure f) {
    for(int i = 0; i < 16; i++) {
      if(f.equals(remainingFigures[i])) {
        this.removeRemaininfFigure(i);
        return;
      }
    }
  }
  
  public List<Integer> getFreeFields() {
    List<Integer> temp = new ArrayList<Integer>();
    for(int i = 0; i < 16; i++) {
      if(this.board[i] != null)
        temp.add(i);
    }
    return temp;
  }

  public Figure[] cloneBoard() {
    Figure[] temp = new Figure[16];
    for(int i = 0; i < 16; i++) {
      temp[i] = board[i].clone();
    }
    return temp;
  }
  
  public int[] getWinningSituation() {
   if(Figure.haveSomethingInCommon(this.board[0], this.board[5], this.board[10], this.board[15])) return new int[] {0, 5, 10, 15};
   if(Figure.haveSomethingInCommon(this.board[0], this.board[5], this.board[10], this.board[15])) return new int[] {3, 6, 9, 12};
   
   for(int i = 0; i < 4; i++) {
      if(Figure.haveSomethingInCommon(this.board[i], this.board[i + 1], this.board[i + 2], this.board[i + 3])) return new int[] {i, i + 1, 1 + 2, i + 3};
      if(Figure.haveSomethingInCommon(this.board[i], this.board[i + 4], this.board[i + 8], this.board[i + 12])) return new int[] {i, i + 4, 1 + 8, i + 12};
    }
    
    
    //squares
    for(int i = 0; i < 3; i++) {
      if(Figure.haveSomethingInCommon(this.board[i], this.board[i + 1], this.board[i + 4], this.board[i + 5])) return new int[] {i, i + 1, 1 + 4, i + 5};
      if(Figure.haveSomethingInCommon(this.board[i + 4], this.board[i + 5], this.board[i + 8], this.board[i + 9])) return new int[] {i + 4, i + 5, 1 + 8, i + 9};
      if(Figure.haveSomethingInCommon(this.board[i + 8], this.board[i + 9], this.board[i + 12], this.board[i + 13])) return new int[] {i + 8, i + 9, 1 + 12, i + 13};
    }
    
    return null;
  }
  
  public boolean winn() {
    boolean winns = false;
    //diagnoals
    winns |= Figure.haveSomethingInCommon(this.board[0], this.board[5], this.board[10], this.board[15]);
    winns |= Figure.haveSomethingInCommon(this.board[3], this.board[6], this.board[9], this.board[12]);
    if(winns)
      return true;
    
    // rows and columns
    for(int i = 0; i < 4; i++) {
      winns |= Figure.haveSomethingInCommon(this.board[i], this.board[i + 1], this.board[i + 2], this.board[i + 3]);
      winns |= Figure.haveSomethingInCommon(this.board[i], this.board[i + 4], this.board[i + 8], this.board[i + 12]);
      if(winns)
        return true;
    }
    
    
    //squares
    for(int i = 0; i < 3; i++) {
      winns |= Figure.haveSomethingInCommon(this.board[i], this.board[i + 1], this.board[i + 4], this.board[i + 5]);
      winns |= Figure.haveSomethingInCommon(this.board[i + 4], this.board[i + 5], this.board[i + 8], this.board[i + 9]);
      winns |= Figure.haveSomethingInCommon(this.board[i + 8], this.board[i + 9], this.board[i + 12], this.board[i + 13]);
      if(winns)
        return true;
    }
    
    return false;
  }
  
  
  public Figure[][] getBoard() {
   Figure[][] f = new Figure[4][4];
   for(int i = 0; i < 4; i++) {
     f[0][i] = this.board[i].clone();
     f[1][i] = this.board[i + 4].clone();
     f[2][i] = this.board[i + 8].clone();
     f[3][i] = this.board[i + 12].clone();
   }
   return f;
  }

}
