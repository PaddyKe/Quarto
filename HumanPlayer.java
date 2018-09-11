import java.util.List;
public class HumanPlayer extends Player {
  
  private int lastFigureClick = -1;
  private int lastFieldClick = -1;
  private HumanPlayer(BoardInterface b) {
    super(b);
  }
  
  public HumanPlayer() {
    super(null);
  }
  
  public void handleFigureClick(int x) {
      this.lastFigureClick = x;
  }
  
  public void handleFieldClick(int x) {
     this.lastFieldClick = x;
  }
  
  @Override
  public int selectFigureIndex(List<Figure> remaining) {
   while(lastFigureClick == -1);
   int temp = lastFigureClick;
   lastFigureClick = -1;
   return temp;
  }
  
   @Override
  public int placeFigure(Figure f) {
   while(lastFieldClick == -1);
   int temp = lastFieldClick;
   return temp;
  }
  
}
