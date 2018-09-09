import java.util.List;
import java.util.ArrayList;



final int FIGURE_SIZE = 75;
PImage background;
final int BOARD_WIDTH = 583;
final int CELL_SIZE = (100 + 10);
List<Figure> remainingFigures;



void setup() {
  size(900,582);
  noLoop();
  remainingFigures = new ArrayList<Figure>();
  background = makeBackground(loadImage("wood.jpg"));
  addAllFiguresToList(remainingFigures);
  
  
  //image(figureToImage(Figure.SQUARE_HOLE_WHITE_SMALL, FIGURE_SIZE),0,0);
  
  
}

void draw() {
  println(frameRate);
  background(0,66,0);
  image(background, 0, 0, BOARD_WIDTH, height);
  noStroke();
  fill(255);
  rect(BOARD_WIDTH, 300, width, height);
  drawFigures(remainingFigures);
}

void mouseClicked() {
  println(mouseX + " " + mouseY);
  println(coordinateToFieldnumber(mouseX, mouseY));
  println();
}

int coordinateToFieldnumber(int x, int y) {
  int radius = CELL_SIZE / 2;
  // well crapy if part...
  // 16 TIMES D:
  if(Math.sqrt(Math.pow(291 - x, 2) + Math.pow(58 - y, 2)) <= radius) {
    return 0;
  } else {
    return -1;
  }
  

  
}


PImage makeBackground(PImage image) {
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
     
     img.fill(0);
     img.text(counter++, j * CELL_SIZE, i * CELL_SIZE);
   }
  }
  

  img.endDraw();
  return img;
}

PImage figureToImage(Figure f, int sideLength) {    
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
   return (PImage)img;
}

void drawFigures(List<Figure> list) {
  float x = BOARD_WIDTH + 4.5;
  float y = 5;
  
  for(int i = 0; i < 16; i++) {
    if(i % 4 == 0 && i != 0) {
      y += FIGURE_SIZE + 3;
      x = BOARD_WIDTH + 3.5;
    }
    image(figureToImage(list.get(i), FIGURE_SIZE), x, y, FIGURE_SIZE, FIGURE_SIZE);
    x += FIGURE_SIZE + 3.5;
  }
  
  
}

void addAllFiguresToList(List<Figure> list) {
  list.add(Figure.SQUARE_NOHOLE_WHITE_LARGE);
  list.add(Figure.ROUND_NOHOLE_WHITE_LARGE);
  list.add(Figure.SQUARE_NOHOLE_BLACK_LARGE);
  list.add(Figure.ROUND_NOHOLE_BLACK_LARGE);
  
  list.add(Figure.SQUARE_HOLE_WHITE_LARGE);
  list.add(Figure.ROUND_HOLE_WHITE_LARGE);
  list.add(Figure.SQUARE_HOLE_BLACK_LARGE);
  list.add(Figure.ROUND_HOLE_BLACK_LARGE);
  
  list.add(Figure.SQUARE_HOLE_WHITE_SMALL);
  list.add(Figure.ROUND_HOLE_WHITE_SMALL);
  list.add(Figure.SQUARE_HOLE_BLACK_SMALL);
  list.add(Figure.ROUND_HOLE_BLACK_SMALL);
  
  list.add(Figure.SQUARE_NOHOLE_WHITE_SMALL);
  list.add(Figure.ROUND_NOHOLE_WHITE_SMALL);
  list.add(Figure.SQUARE_NOHOLE_BLACK_SMALL);
  list.add(Figure.ROUND_NOHOLE_BLACK_SMALL);
  
}
