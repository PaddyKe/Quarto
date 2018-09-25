package player.minimax;

import game.Figure;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public int spot;
    public byte passedPiece;
    public char player;
    public List<Node> children;
    public boolean isLeaf;
    public boolean isQuarto;

    public Node(int spot, Figure f, char p) {
        this.passedPiece = f.getGenome();
        this.spot = spot;
        this.player = p;
        this.children = new ArrayList<Node>();
        this.isLeaf = true;
        this.isQuarto = false;
    }

    public Node(int spot, byte f, char p) {
        this.passedPiece = f;
        this.spot = spot;
        this.player = p;
        this.children = new ArrayList<Node>();
        this.isLeaf = true;
        this.isQuarto = false;
    }

    public Figure getFigure() {
        return new Figure(this.passedPiece);
    }

    public boolean addChild(Node n) {
        if(this.children.add(n)) {
            this.isLeaf = false;
            return true;
        } else {
            System.err.println("Error adding a childNode");
            return false;
        }
    }

    @Override
    public String toString() {
        String str = "<" + spot + ", " + this.passedPiece + ", " + this.player;
        if(this.isQuarto)
            str += ", Q!";
        str += ">";
        return str;
    }

}
