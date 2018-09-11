import java.util.List;
public abstract class Player {
  protected BoardInterface board;
  public Player(BoardInterface r) {
    this.board = r;
  }
  public Figure selectFigure(List<Figure> remaining) {
    return remaining.get(0);
  }
  
  public int selectFigureIndex(List<Figure> remaining) {
    return this.board.figureToIndex(this.selectFigure(remaining));
  }
  
  public abstract int placeFigure(Figure f);
	
}
