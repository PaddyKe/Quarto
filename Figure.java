class Figure {
  
  private boolean isRound;
  private boolean hasHole;
  private boolean isBlack;
  private boolean isSmall;
  
  public static final Figure ROUND_HOLE_BLACK_SMALL = new Figure(true, true, true, true);
  public static final Figure ROUND_HOLE_BLACK_LARGE = new Figure(true, true, true, false);
  public static final Figure ROUND_HOLE_WHITE_SMALL = new Figure(true, true, false, true);
  public static final Figure ROUND_HOLE_WHITE_LARGE = new Figure(true, true, false, false);
  public static final Figure ROUND_NOHOLE_BLACK_SMALL = new Figure(true, false, true, true);
  public static final Figure ROUND_NOHOLE_BLACK_LARGE = new Figure(true, false, true, false);
  public static final Figure ROUND_NOHOLE_WHITE_SMALL = new Figure(true, false, false, true);
  public static final Figure ROUND_NOHOLE_WHITE_LARGE = new Figure(true, false, false, false);
  public static final Figure SQUARE_HOLE_BLACK_SMALL = new Figure(false, true, true, true);
  public static final Figure SQUARE_HOLE_BLACK_LARGE = new Figure(false, true, true, false);
  public static final Figure SQUARE_HOLE_WHITE_SMALL = new Figure(false, true, false, true);
  public static final Figure SQUARE_HOLE_WHITE_LARGE = new Figure(false, true, false, false);
  public static final Figure SQUARE_NOHOLE_BLACK_SMALL = new Figure(false, false, true, true);
  public static final Figure SQUARE_NOHOLE_BLACK_LARGE = new Figure(false, false, true, false);
  public static final Figure SQUARE_NOHOLE_WHITE_SMALL = new Figure(false, false, false, true);
  public static final Figure SQUARE_NOHOLE_WHITE_LARGE = new Figure(false, false, false, false);
  
  public static final Figure DUMMY = new Figure(true, true, true, true);
  
  public Figure(boolean round, boolean hole, boolean black, boolean small) {
    this.isRound = round;
    this.hasHole = hole;
    this.isBlack = black;
    this.isSmall = small;
  }
  
  public void setRound(boolean v) {
    this.isRound = v;
  }
 
  public boolean isRound() {
    return this.isRound;
  }


  public void setHole(boolean v) {
    this.hasHole = v;
  }
 
  public boolean hasHole() {
    return this.hasHole;
  }

  
  public void setBlack(boolean v) {
    this.isBlack = v;
  }
 
  public boolean isBlack() {
    return this.isBlack;
  }
  
  
  public void setSmall(boolean v) {
    this.isSmall = v;
  }
 
  public boolean isSmall() {
    return this.isSmall;
  }
  
  @Override
  public Figure clone() {
    return new Figure(this.isRound, this.isBlack, this.hasHole, this.isSmall);
  }
  
  @Override
  public boolean equals(Object o) {
    if(o == null) return false;
    if(!(o instanceof Figure)) return false;
    Figure f = (Figure) o;
    return this.isRound == f.isRound && this.isBlack == f.isBlack && this.hasHole == f.hasHole && this.isSmall == f.isSmall;
  }
  
}
