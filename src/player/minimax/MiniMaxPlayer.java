package player.minimax;

import game.Figure;
import interfaces.Player;
import player.TheMaster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class MiniMaxPlayer extends Player {

    // Implementation of the minimax algorithem... well not jet but maybe soon.

    private Stack<Byte> passedPieces;
    private Node move;
    private int depth;
    private TheMaster m;

    private static final boolean DEBUG = false;

    public MiniMaxPlayer() {
        super("Kleiner Max");
        this.passedPieces = new Stack<Byte>();
        this.m = new TheMaster();
    }


    @Override
    public int placeFigure(Figure f, Figure[] board, List<Figure> remaining) {
        int numRemSpots = getNumRemSpots(board);
        if(numRemSpots > 12) {
            return this.m.placeFigure(f, board, remaining);     // way to much to calculate...
        } else if(numRemSpots <= 12 && numRemSpots > 7) {
            this.depth = 1;
        } else if(numRemSpots <= 7 && numRemSpots > 1) {
            this.depth = 8;
        } else {
            return  this.m.placeFigure(f, board, remaining); // let em WIN!!!!
        }

        int     z         = numRemSpots * remaining.size();//getNumRemPieces(board);
        Node[] rootnodes = new Node[z];

        // build tree
        List<Integer>   remspots  = getRemSpots(board);
        List<Figure>   rempieces = remaining;
        Figure     passedPiece = f;

        int     m = 0;
        for (int i = 0 ; i < remspots.size(); i++) {
            for (int j = 0; j < rempieces.size(); j++) {
                rootnodes[m] = buildTree( passedPiece.getGenome(), remspots.get(i), rempieces.get(j).getGenome(), convertBoard(board), 'C', this.depth );
                m++;
            }
        }

        // display tree (debug)
        if (DEBUG) {
            for (int k=0;k<m;k++) {
                System.out.println( " ROOT:" + displayTree( rootnodes[k] ) );
            }
        }

        // compute minimax
        if (DEBUG) System.out.print( " Computing minimax: " );
        int[]   outcomes = new int[z];
        int     res = 0;

        for (int x=0;x<z;x++) {
            outcomes[x] = computeMiniMax(rootnodes[x]);

            if (DEBUG) System.out.print( rootnodes[x] + "=" + outcomes[x] + ((x<(z-1)) ? "," : " ") );

            // continues to upgrade, otherwise keeps res=0
            if (x>0 && outcomes[x] > outcomes[res]) {
                res = x;
            }
        }
        if (DEBUG) System.out.println();

        this.move = rootnodes[res];

        return this.move.spot;
    }

    @Override
    public Figure selectFigure(List<Figure> remaining, Figure[] board) {
        if(this.move == null) {
            return remaining.get(this.rnd.nextInt(remaining.size()));
        } else {
            Figure f = this.move.getFigure();
            if(remaining.contains(f)) {
                this.move = null;
                this.passedPieces.clear();
                return f;
            } else {
                throw new RuntimeException("Cant chose a figure");
            }
        }
    }


    public String displayTree( Node root ) {
        // returns decision tree in String form

        String str = "";

        str += root.toString();

        if (!root.isLeaf) {
            str += "-(";
            Iterator<Node> ci = root.children.iterator();
            while (ci.hasNext()) {
                str += displayTree(ci.next() );
            }
            str += ")";
        }

        return str;
    }

    private Node buildTree(byte f, int spot, byte nextFigure, byte[] boardState, char player, int depth) {
        Node n = new Node(spot, nextFigure, player);
        byte[] cboard = cloneBoard(boardState);

        this.passedPieces.push(f);

        if(!play(cboard, spot))
            System.err.println("Error");

        if(checkQuarto(cboard, spot)) {
            n.isLeaf = true;
            n.isQuarto = true;
            return n;
        }

        if(nextFigure == -1) {
            n.isLeaf = true;
            return n;
        }

        if(depth == 0) {
            n.isLeaf = true;
            return n;
        }

        // until now no leave, so we need to built children

        List<Integer> remSpots = getRemSpots(cboard);
        List<Byte> remPieces = getRemFigures(cboard);

        for(int i = 0; i < remSpots.size(); i++) {
            if(remPieces.size() > 0) {
                for (int j = 0; j < remPieces.size(); j++) {
                    n.addChild(buildTree(nextFigure, remSpots.get(i), remPieces.get(j), cboard, (player == 'C') ? 'U' : 'C', depth - 1));
                }
            } else {
                n.addChild(buildTree(nextFigure, remSpots.get(i), (byte)-1, cboard, (player == 'C') ? 'U' : 'C', depth - 1));
            }
        }
        return n;
    }
    /*
    private Node buildTree(Figure f, int spot, Figure nextFigure, Figure[] boardState, char player, int depth) {
        Node n = new Node(spot, nextFigure, player);
        byte[] cboard = convertBoard(boardState);

        this.passedPieces.push(f);

        if(!play(cboard, spot))
            System.err.println("Error");

        if(checkQuarto(cboard, spot)) {
            n.isLeaf = true;
            n.isQuarto = true;
            return n;
        }

        if(nextFigure == null) {
            n.isLeaf = true;
            return n;
        }

        if(depth == 0) {
            n.isLeaf = true;
            return n;
        }

        // until now no leave, so we need to built children

        List<Integer> remSpots = getRemSpots(cboard);
        List<Figure> remPieces = getRemFigures(cboard);

        for(int i = 0; i < remSpots.size(); i++) {
            if(remPieces.size() > 0) {
                for (int j = 0; j < remPieces.size(); j++) {
                    n.addChild(buildTree(nextFigure, remSpots.get(i), remPieces.get(j), cboard, (player == 'C') ? 'U' : 'C', depth - 1));
                }
            } else {
                n.addChild(buildTree(nextFigure, remSpots.get(i), null, cboard, (player == 'C') ? 'U' : 'C', depth - 1));
            }
        }
        return n;
    }
*/
    private static int computeMiniMax(Node node) {
        if(node.isLeaf) {
            if(node.isQuarto) {
                return (node.player == 'C') ? 1 : -1;
            } else {
                return 0;
            }
        }

        int tans = 0, ans = 0;
        Iterator<Node> i = node.children.iterator();
        while(i.hasNext()) {
            tans = computeMiniMax(i.next());
            if(node.player == 'C')
                ans = Math.min(ans, tans);
            else
                ans = Math.max(ans, tans);
        }
        return ans;
    }

    private static List<Figure> getRemFigures(Figure[] f) {
        List<Figure> rem = new ArrayList<Figure>(16);
        for (int i = 0; i < 16; i++) {
            rem.add(new Figure((byte)i));
        }

        rem = rem.stream().filter(x -> arrayContains(f, x)).collect(Collectors.toList());

        return rem;
    }

    private static List<Byte> getRemFigures(byte[] f) {
        List<Byte> rem = new ArrayList<Byte>(16);
        for (int i = 0; i < 16; i++) {
            rem.add((byte)i);
        }

        rem = rem.stream().filter(x -> arrayContains(f, x)).collect(Collectors.toList());
        return rem;
    }

    private static boolean arrayContains(final Figure[] f, final Figure fig) {
        for (Figure ff : f) {
            if(ff != null && ff.equals(fig)) {
                return true;
            }
        }
        return false;
    }

    private static boolean arrayContains(final byte[] f, final byte fig) {
        for (byte ff : f) {
            if(ff == fig) {
                return true;
            }
        }
        return false;
    }

    private static List<Integer> getRemSpots(Figure[] f) {
        List<Integer> rem = new ArrayList<Integer>();
        for (int i = 0; i < f.length; i++) {
            if(f[i] == null) {
                rem.add(i);
            }
        }
        return rem;
    }


    private static List<Integer> getRemSpots(byte[] f) {
        List<Integer> rem = new ArrayList<Integer>();
        for (int i = 0; i < f.length; i++) {
            if(f[i] == -1) {
                rem.add(i);
            }
        }
        return rem;
    }
    /*
    private boolean play(Figure[] board, int spot) {
        if(board[spot] == null) {
            board[spot] = this.passedPieces.pop();
            return true;
        } else {
            return false;
        }
    }
*/
    private boolean play(byte[] board, int spot) {
        if(board[spot] == -1) {
            board[spot] = this.passedPieces.pop();
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkQuarto(Figure[] board, int spot) {
        boolean checkRow = true;
        boolean checkCol = true;

        int rowhead = spot - (spot%4);
        int colhead = spot % 4;

        Figure r1 = board[rowhead],   r2 = board[rowhead+1],
                r3 = board[rowhead+2], r4 = board[rowhead+3];
        Figure c1 = board[colhead],   c2 = board[colhead+4],
                c3 = board[colhead+8], c4 = board[colhead+12];

        // check for empty spots in row or col
        if ( r1==null || r2==null ||  r3==null || r4==null ) {
            checkRow = false;
        }
        if ( c1==null || c2==null || c3==null || c4==null ) {
            checkCol = false;
        }
        if ( !checkRow && !checkCol ) {
            return false;
        }

        // no empty spots, start comparing pieces in row and col
        boolean result;

        // check rows
        if (checkRow) {
            result = Figure.hasGeneInCommon(r1,r2,r3,r4);
            if (result)
                return true;
        }

        // check columns
        if (checkCol) {
            result = Figure.hasGeneInCommon(c1,c2,c3,c4);
            if (result)
                return true;
        }

        // nothing found
        return false;
    }

    private static boolean checkQuarto(byte[] board, int spot) {
        boolean checkRow = true;
        boolean checkCol = true;

        int rowhead = spot - (spot%4);
        int colhead = spot % 4;

        byte r1 = board[rowhead],   r2 = board[rowhead+1],
                r3 = board[rowhead+2], r4 = board[rowhead+3];
        byte c1 = board[colhead],   c2 = board[colhead+4],
                c3 = board[colhead+8], c4 = board[colhead+12];

        // check for empty spots in row or col
        if ( r1==-1 || r2==-1 ||  r3==-1 || r4==-1 ) {
            checkRow = false;
        }
        if ( c1==-1 || c2==-1 || c3==-1 || c4==-1 ) {
            checkCol = false;
        }
        if ( !checkRow && !checkCol ) {
            return false;
        }

        // no empty spots, start comparing pieces in row and col
        byte result;

        // check rows
        if (checkRow) {
            result = comparePieces(r1,r2,r3,r4);
            if (result < 4)
                return true;
        }

        // check columns
        if (checkCol) {
            result = comparePieces(c1,c2,c3,c4);
            if (result < 4)
                return true;
        }

        for (int i = 0; i < 3; i++) {
            if(comparePieces(board[i], board[i + 1], board[i + 4], board[i + 5]) < 4) return true;
            if(comparePieces(board[i + 4], board[i + 5], board[i + 8], board[i + 9]) < 4) return true;
            if(comparePieces(board[i + 8], board[i + 9], board[i + 12], board[i + 13]) < 4) return true;

        }

        // nothing found
        return false;
    }

    private static byte comparePieces( byte p1, byte p2, byte p3, byte p4 ) {
        // returns attribute that made the first found quarto

        int tot = 0;
        // loop on type
        for (byte j=0;j<4;j++) {
            tot = ((p1 & (1<<j))>>j) + ((p2 & (1<<j))>>j) + ((p3 & (1<<j))>>j) + ((p4 & (1<<j))>>j) ;
            if (tot==4 || tot==0) {
                return j;
            }
            tot = 0;
        }

        return 4;
    }

    private static Figure[] cloneBoard(Figure[] f) {
        Figure[] board = new Figure[f.length];
        for (int i = 0; i < f.length; i++) {
            board[i] = f[i];//(f[i] == null) ? null : f[i].clone();
        }
        return board;
    }

    private static byte[] cloneBoard(byte[] f) {
        byte[] newB = new byte[f.length];
        for (int i = 0; i < f.length; i++) {
            newB[i] = f[i];
        }
        return newB;
    }

    private static byte[] convertBoard(Figure[] f) {
        byte[] board = new byte[f.length];
        for (int i = 0; i < f.length; i++) {
            board[i] = (f[i] == null) ? -1 : f[i].getGenome();
        }
        return board;
    }

    private static int getNumRemSpots(Figure[] f) {
        int counter = 0;
        for(Figure fig : f) {
            if(fig == null) {
                counter++;
            }
        }
        return counter;
    }

}
