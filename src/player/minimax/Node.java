package player.minimax;

import game.Figure;
import interfaces.Player;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public int spot;
    public Figure passedPiece;
    public Player player;
    public List<Node> children;
    public boolean isLeave;
    public boolean isQuarto;

    public Node(int spot, Figure f, Player p) {
        this.passedPiece = f;
        this.spot = spot;
        this.player = p;
        this.children = new ArrayList<Node>();
        this.isLeave = true;
        this.isQuarto = false;
    }

    public boolean addChild(Node n) {
        if(this.children.add(n)) {
            this.isLeave = false;
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
