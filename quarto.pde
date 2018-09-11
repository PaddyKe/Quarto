import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;



final int FIGURE_SIZE = 75;
PGraphics background;
final int BOARD_WIDTH = 583;
final int CELL_SIZE = (100 + 10);
Map<Integer, PVector> indexToVector;
Board board;
boolean first = true;
boolean drawing = false;
Thread drawer;

void setup() {
  size(900,582);
  
  init(new HumanPlayer(), new HumanPlayer());
  background.save("backgound.png");
  //image(figureToImage(Figure.SQUARE_HOLE_WHITE_SMALL, FIGURE_SIZE),0,0);
  thread("gameLoop");
  
}

void draw() {
  background(0,66,0);
  image(background, 0, 0, BOARD_WIDTH, height);
  noStroke();
  fill(255);
  rect(BOARD_WIDTH, 300, width, height);
  drawRemainingFigures();
  drawBoard();
  Figure f = board.getToPlace();
  if(f != null) {
    PGraphics toP = figureToImage(f, 150);
    image(toP, BOARD_WIDTH + ((width - BOARD_WIDTH) / 2), 300);
  }
}
void gameLoop() {
  println(board.getRound());
  
  this.board.nextRound();
  while(this.board.getRound() < 16) {
    if(this.board.winn()) {
    } else {
      this.board.nextRound();
    }
  }
}

void init(Player p1, Player p2) {
  indexToVector = new HashMap<Integer, PVector>();
  background = makeBackground(loadImage("wood.jpg"));
  prepareMaps(indexToVector);
  board = new Board(p1, p2);
}























int field = -1;
void fieldClick() {
  board.handleFieldClick(field);
}

int figure = -1;
void figureClick() {
  board.handleFigureClick(figure);
}


void mouseClicked() {
 // println(mouseX + " " + mouseY);
  int temp = coordinateToFieldnumber(mouseX, mouseY);
  if(temp != -1) {
    field = temp;
    thread("fieldClick");
  }
  
  temp = coordinateToFigurenumber(mouseX, mouseY);
  if(temp != -1) {
    figure = temp;
    thread("figureClick");
  }
  //println();
}

void drawBoard() {
  Figure temp = null;
  PVector coordinates;
  for(int i = 0; i < 16; i++) {
    temp = board.getBoard(i);
    if(temp != null) {
      coordinates = indexToVector.get(i);
      //background.blend(figureToImage(temp, FIGURE_SIZE), 0, 0, FIGURE_SIZE, FIGURE_SIZE, (int)coordinates.x - FIGURE_SIZE / 2, (int)coordinates.y - FIGURE_SIZE / 2,  FIGURE_SIZE, FIGURE_SIZE, BLEND);
      image(figureToImage(Figure.SQUARE_NOHOLE_BLACK_LARGE, FIGURE_SIZE), (int)coordinates.x - FIGURE_SIZE / 2, (int)coordinates.y - FIGURE_SIZE / 2); 
    }
  }
}

int coordinateToFigurenumber(int x, int y) {
  if(Math.abs(587.5 - x) <= FIGURE_SIZE && Math.abs(5 - y) <= FIGURE_SIZE)  {
    return 0;
  } else if(Math.abs(666 - x) <= FIGURE_SIZE && Math.abs(5 - y) <= FIGURE_SIZE) {
    return 1;
  } else if(Math.abs(744.5 - x) <= FIGURE_SIZE && Math.abs(5 - y) <= FIGURE_SIZE) {
    return 2;
  } else if(Math.abs(823 - x) <= FIGURE_SIZE && Math.abs(5 - y) <= FIGURE_SIZE) {
    return 3;
  } else if(Math.abs(586.5 - x) <= FIGURE_SIZE && Math.abs(83 - y) <= FIGURE_SIZE) {
    return 4;
  } else if(Math.abs(665 - x) <= FIGURE_SIZE && Math.abs(83 - y) <= FIGURE_SIZE) {
    return 5;
  } else if(Math.abs(743.5 - x) <= FIGURE_SIZE && Math.abs(83 - y) <= FIGURE_SIZE) {
    return 6;
  } else if(Math.abs(822 - x) <= FIGURE_SIZE && Math.abs(83 - y) <= FIGURE_SIZE) {
    return 7;
  } else if(Math.abs(586.5 - x) <= FIGURE_SIZE && Math.abs(161 - y) <= FIGURE_SIZE) {
    return 8;
  } else if(Math.abs(665 - x) <= FIGURE_SIZE && Math.abs(161 - y) <= FIGURE_SIZE) {
    return 9;
  } else if(Math.abs(743.5 - x) <= FIGURE_SIZE && Math.abs(161 - y) <= FIGURE_SIZE) {
    return 10;
  } else if(Math.abs(822 - x) <= FIGURE_SIZE && Math.abs(161 - y) <= FIGURE_SIZE) {
    return 11;
  } else if(Math.abs(586.5 - x) <= FIGURE_SIZE && Math.abs(239 - y) <= FIGURE_SIZE) {
    return 12;
  } else if(Math.abs(665 - x) <= FIGURE_SIZE && Math.abs(239 - y) <= FIGURE_SIZE) {
    return 13;
  } else if(Math.abs(743 - x) <= FIGURE_SIZE && Math.abs(239 - y) <= FIGURE_SIZE) {
    return 14;
  } else if(Math.abs(822 - x) <= FIGURE_SIZE && Math.abs(239 - y) <= FIGURE_SIZE) {
    return 15;
  } else {
    return -1;
  }
}

int coordinateToFieldnumber(int x, int y) {
  int radius = CELL_SIZE / 2;
  // well crapy if part...
  // 16 TIMES D:
  if(Math.sqrt(Math.pow(291 - x, 2) + Math.pow(58 - y, 2)) <= radius) {
    return 0;
  } else if(Math.sqrt(Math.pow(368 - x, 2) + Math.pow(134 - y, 2)) <= radius) {
    return 1;
  }  else if(Math.sqrt(Math.pow(446 - x, 2) + Math.pow(212 - y, 2)) <= radius) {
    return 2;
  } else if(Math.sqrt(Math.pow(523 - x, 2) + Math.pow(289 - y, 2)) <= radius) {
    return 3;
  } else if(Math.sqrt(Math.pow(213 - x, 2) + Math.pow(134 - y, 2)) <= radius) {
    return 4;
  } else if(Math.sqrt(Math.pow(291 - x, 2) + Math.pow(212 - y, 2)) <= radius) {
    return 5;
  } else if(Math.sqrt(Math.pow(368 - x, 2) + Math.pow(289 - y, 2)) <= radius) {
    return 6;
  } else if(Math.sqrt(Math.pow(446 - x, 2) + Math.pow(367 - y, 2)) <= radius) {
    return 7;
  } else if(Math.sqrt(Math.pow(135 - x, 2) + Math.pow(212 - y, 2)) <= radius) {
    return 8;
  } else if(Math.sqrt(Math.pow(213 - x, 2) + Math.pow(289 - y, 2)) <= radius) {
    return 9;
  } else if(Math.sqrt(Math.pow(291 - x, 2) + Math.pow(367 - y, 2)) <= radius) {
    return 10;
  } else if(Math.sqrt(Math.pow(368 - x, 2) + Math.pow(444 - y, 2)) <= radius) {
    return 11;
  } else if(Math.sqrt(Math.pow(58 - x, 2) + Math.pow(289 - y, 2)) <= radius) {
    return 12;
  } else if(Math.sqrt(Math.pow(135 - x, 2) + Math.pow(367 - y, 2)) <= radius) {
    return 13;
  } else if(Math.sqrt(Math.pow(213 - x, 2) + Math.pow(444 - y, 2)) <= radius) {
    return 14;
  } else if(Math.sqrt(Math.pow(291 - x, 2) + Math.pow(522 - y, 2)) <= radius) {
    return 15;
  } else {
    return -1;
  }
}


PGraphics makeBackground(PImage image) {
  PImage temp = createImage(image.width, image.height, ARGB);
  temp.loadPixels();
  image.loadPixels();
  for(int i = 0; i < image.pixels.length; i++) {
    temp.pixels[i] = color(red(image.pixels[i]), green(image.pixels[i]), blue(image.pixels[i]), 255);
  }
  image.updatePixels();
  temp.updatePixels();
  
  
  image = temp;
  image.resize(BOARD_WIDTH, height);
  
  PGraphics img = createGraphics(image.width, image.height);
  img.beginDraw();
  img.background(image);
  img.fill(255, 128);
  img.stroke(255, 229, 147);
  img.strokeWeight(5);
  img.ellipseMode(RADIUS);
  img.smooth();
  
  int counter = 0;
  
  img.translate((CELL_SIZE / 2) * 5 + 16,CELL_SIZE / 2 + 2);
  img.rotate(PI / 4);
  
  for(int i = 0; i < 4; i++) {
   for(int j = 0; j < 4; j++) {
     img.stroke(255, 229, 147);
     img.fill(255, 128);
     img.ellipse(j * CELL_SIZE, i * CELL_SIZE , CELL_SIZE / 2, CELL_SIZE / 2);
     
     //img.fill(0);
     //img.text(counter++, j * CELL_SIZE, i * CELL_SIZE);
   }
  }
  

  img.endDraw();
  return img;
}

PGraphics figureToImage(Figure f, int sideLength) {    
    if(sideLength < 50) sideLength = 50;
    
    PGraphics cutout = createGraphics(sideLength, sideLength);
    cutout.beginDraw();
    cutout.ellipseMode(RADIUS);
    cutout.rectMode(RADIUS);
    cutout.background(0,0);
    cutout.fill(255);
    cutout.smooth();
    int size = sideLength;
    PGraphics img = createGraphics(sideLength, sideLength);
    img.beginDraw();
    img.noStroke();
    img.background(0, 0);
    img.ellipseMode(RADIUS);
    img.rectMode(RADIUS);
    img.smooth();
    if(f.isBlack())
      img.fill(45, 23, 0);
    else
      img.fill(255, 216, 91);
    
    if(f.isSmall()) {
      sideLength = sideLength / 2;
    }
   
   if(f.isRound()) {
     img.ellipse(size / 2, size / 2, sideLength / 2, sideLength / 2);
     cutout.ellipse(size / 2, size / 2, sideLength / 2, sideLength / 2);
     cutout.fill(0);
     cutout.ellipse(size / 2, size / 2, sideLength / 4, sideLength/ 4);
   } else {
     img.rect(size / 2, size / 2, sideLength / 2, sideLength / 2, 10);
     cutout.rect(size / 2, size / 2, sideLength / 2, sideLength / 2, 10);
     cutout.fill(0);
     cutout.ellipse(size / 2, size / 2, sideLength / 4, sideLength/ 4);
   }
   
   cutout.endDraw();
   if(f.hasHole()) { 
     img.mask(cutout);  
   }
   img.endDraw();
   return img;
}

void drawRemainingFigures() {
  float x = BOARD_WIDTH + 4.5;
  float y = 5;
  Figure f = null;
  for(int i = 0; i < 16; i++) {
    if(i % 4 == 0 && i != 0) {
      y += FIGURE_SIZE + 3;
      x = BOARD_WIDTH + 3.5;
    }
    //println(x + " | " + y + " ==> " +  i);
    f = board.getRemaining(i);
    if(f != null)
      image(figureToImage(f, FIGURE_SIZE), x, y, FIGURE_SIZE, FIGURE_SIZE);
    x += FIGURE_SIZE + 3.5;
  }
  
  
}

void prepareMaps(Map<Integer, PVector> indexMapping) {
  indexMapping.put(0, new PVector(291, 58));
  indexMapping.put(1, new PVector(368, 134));
  indexMapping.put(2, new PVector(446, 212));
  indexMapping.put(3, new PVector(523, 289));
  indexMapping.put(4, new PVector(213, 134));
  indexMapping.put(5, new PVector(291, 212));
  indexMapping.put(6, new PVector(368, 289));
  indexMapping.put(7, new PVector(446, 367));
  indexMapping.put(8, new PVector(135, 212));
  indexMapping.put(9, new PVector(213, 289));
  indexMapping.put(10, new PVector(291, 367));
  indexMapping.put(11, new PVector(368, 444));
  indexMapping.put(12, new PVector(58, 289));
  indexMapping.put(13, new PVector(135, 367));
  indexMapping.put(14, new PVector(213, 444));
  indexMapping.put(15, new PVector(291, 522));
  
}
