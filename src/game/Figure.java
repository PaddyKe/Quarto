package game;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Figure {

    private byte genome;
    
    private static final byte SMALL_MASK = 0b1000;
    private static final byte BLACK_MASK = 0b0100;
    private static final byte HOLE_MASK = 0b0010;
    private static final byte ROUND_MASK = 0b0001;
    private static final byte MASK = 0b1111;
    private static final byte MOD = 16;

    public enum Phenotype {
        BLACK, WHITE,
        SMALL, TALL,
        HOLE, NO_HOLE,
        ROUND, SQUARE;

        //@Override
        //public String toString() { return this.name(); }

    }

    public Figure(byte genome) {
        this.genome = (byte) (genome % MOD);
    }
    
    public Figure(boolean round, boolean black, boolean hole, boolean small) {
        this.genome |= round ? ROUND_MASK : 0;
        this.genome |= small ? SMALL_MASK : 0;
        this.genome |= hole ? HOLE_MASK : 0;
        this.genome |= black ? BLACK_MASK : 0;
    }
    
    public boolean isRound() {
        return (this.genome & ROUND_MASK) == 1;
    }
    
    public boolean hasHole() {
        return (this.genome & HOLE_MASK) == 2;
    }
    
    public boolean isBlack() {
        return (this.genome & BLACK_MASK) == 4;
    }
    
    public boolean isSmall() {
        return (this.genome & SMALL_MASK) == 8;
    }

    public Image toImage(int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)img.getGraphics();
        g.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0,0, size, size);
        g.setComposite(AlphaComposite.Src);
        if(this.isBlack())
            g.setColor(new Color(45, 23, 0, 255));
        else
            g.setColor(new Color(216, 180, 79, 255));

        boolean small = this.isSmall();

        if(this.isRound()) {
            if(small) {
                g.fillOval(size / 4, size / 4, size / 2, size / 2);
            } else {
                g.fillOval(0, 0, size, size);
            }
        } else {
            if(small) {
                g.fillRoundRect(size / 4, size / 4, size / 2, size / 2, 15, 15);
            } else {
                g.fillRoundRect(0, 0, size, size , 30,30);
            }
        }

        if(this.hasHole()) {
            g.setColor(new Color(0,0,0,0));
            g.setComposite(AlphaComposite.Clear);
            if(small) {
                g.fillOval(size / 4 + size / 8 + 1, size / 4 + size / 8 , size / 4, size / 4);
            } else {
                g.fillOval(size / 4 + 1, size / 4 + 1, size / 2, size / 2);
            }

        }

        return SwingFXUtils.toFXImage(img, null);
    }

    public byte getGenome() {
        return this.genome;
    }

    @Override
    public int hashCode() {
        return this.genome;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof Figure)) return false;
        return (this.genome & MASK) == (((Figure)o).genome & MASK);
    }

    @Override
    public Figure clone() {
        return new Figure(this.genome);
    }

    public static boolean hasGeneInCommon(Figure... figures) {
        Figure f = figures[0];
        if(f == null) return false;
        
        boolean round = true;
        boolean small = true;
        boolean black = true;
        boolean hole  = true;
        for (int i = 1; i < figures.length; i++) {
            f = figures[i];
            if(f == null) return false;
            round &= (f.isRound() == figures[i - 1].isRound());
            black &= (f.isBlack() == figures[i - 1].isBlack());
            hole  &= (f.hasHole() == figures[i - 1].hasHole());
            small &= (f.isSmall() == figures[i - 1].isSmall());
        }

        return round | small | hole | black;
    }

    public static List<Phenotype> getSimilarities(Figure... figures) {
        if(figures.length == 0) return null;
        boolean round  = true;//figures[0].isRound();
        boolean square = true;//!round;
        boolean hole   = true;//figures[0].hasHole();
        boolean noHole = true;//!hole;
        boolean black  = true;//figures[0].isBlack();
        boolean white  = true;//!black;
        boolean small  = true;//figures[0].isSmall();
        boolean tall   = true;//!small;


        for (int i = 0; i < figures.length; i++) {
            if(figures[i] != null) {
                round &= (round == figures[i].isRound());
                square &= (square == !figures[i].isRound());
                hole &= (hole == figures[i].hasHole());
                noHole &= (noHole == !figures[i].hasHole());
                black &= (black == figures[i].isBlack());
                white &= (white == !figures[i].isBlack());
                small &= (small == figures[i].isSmall());
                tall &= (tall == !figures[i].isSmall());
            }
        }

        List<Phenotype> simulatities = new ArrayList<Phenotype>();

        if(round) simulatities.add(Phenotype.ROUND);
        if(square) simulatities.add(Phenotype.SQUARE);
        if(hole) simulatities.add(Phenotype.HOLE);
        if(noHole) simulatities.add(Phenotype.NO_HOLE);
        if(black) simulatities.add(Phenotype.BLACK);
        if(white) simulatities.add(Phenotype.WHITE);
        if(small) simulatities.add(Phenotype.SMALL);
        if(tall) simulatities.add(Phenotype.TALL);

        return simulatities;
    }

    @Override
    public String toString() {
        return this.genome + "";
    }

}
