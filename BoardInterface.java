public interface BoardInterface {
 public Figure[][] getBoard();
 public void placeFigure(int i);
 public Figure[] cloneBoard();
 public int figureToIndex(final Figure f);
}
