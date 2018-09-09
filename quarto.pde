final int FIGURE_SIZE = 75;
PImage background;
final int BOARD_WIDTH = 583;
void setup() {
  size(900,582);
  stroke(0);
  
  background(0,66,0);
  background = makeBackground(loadImage("wood.jpg"));
  image(background, 0, 0, BOARD_WIDTH, height);
  //image(figureToImage(Figure.SQUARE_HOLE_WHITE_SMALL, FIGURE_SIZE),0,0);
  noStroke();
  fill(255);
  rect(BOARD_WIDTH, 300, width, height);
  noLoop();  
}

void draw() {
  
}

PImage makeBackground(PImage image) {
  PImage temp = createImage(image.width, image.height, ARGB);
  final int CELL_SIZE = (100 + 10);
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
     img.ellipse(j * CELL_SIZE, i * CELL_SIZE , CELL_SIZE / 2, CELL_SIZE / 2);
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
     
    int size = sideLength;
    PGraphics img = createGraphics(sideLength, sideLength);
    img.beginDraw();
    img.noStroke();
    img.background(0, 0);
    img.ellipseMode(RADIUS);
    img.rectMode(RADIUS);

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
     //TODO create maskImage to crop out the whole
     //img.fill(0,0,0,1);
     //img.ellipse(size / 2, size / 2, size / 4, size / 4);  
     img.mask(cutout);  
   }
   img.endDraw();
   return (PImage)img;
}
