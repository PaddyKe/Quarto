package player;

import game.Figure;
import interfaces.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PerfectKI extends Player {
    private static final Predicate<Figure> EXCLUDE_ROUND = x -> !x.isRound();
    private static final Predicate<Figure> EXCLUDE_SQUARE = x -> x.isRound();
    private static final Predicate<Figure> EXCLUDE_HOLES = x -> !x.hasHole();
    private static final Predicate<Figure> EXCLUDE_NOHOLES = x -> x.hasHole();
    private static final Predicate<Figure> EXCLUDE_BLACK = x -> !x.isBlack();
    private static final Predicate<Figure> EXCLUDE_WHITE = x -> x.isBlack();
    private static final Predicate<Figure> EXCLUDE_SMALL = x -> !x.isSmall();
    private static final Predicate<Figure> EXCLUDE_TALL = x -> x.isSmall();
    private List<Predicate<Figure>> filters;
    private Predicate<Figure> allFilters = new Predicate<Figure>() {
        @Override
        public boolean test(Figure figure) {
            for(Predicate<Figure> pf : filters) {
                if(!pf.test(figure))
                    return false;
            }
            return true;
        }
    };


    public PerfectKI() {
        super("PerfectKI");
        this.filters = new ArrayList<Predicate<Figure>>();
    }

    @Override
    public Figure selectFigure(List<Figure> remaining, Figure[] board) {
        if(remaining.size() == 0) return null;
        List<Figure> allowedFigures = remaining.stream().filter(allFilters).collect(Collectors.toList());
        if(remaining.size() > 0 && allowedFigures.size() == 0) // well... i have lost :(
            return remaining.get(0);
        else {
            // TODO think about a better stratergy... (maybe look that i choose a figure so that a winning situation could be created)

            return allowedFigures.get(this.rnd.nextInt(allowedFigures.size()));
        }
    }

    @Override
    public int placeFigure(Figure f, Figure[] state, List<Figure> remaining) {
        List<int[]> possibleWins = possibleWinningSituations(state);
        
        if(possibleWins.size() > 0) {   // if the bot is able to win... well then winn ;)
            Figure[] winChecker = clone(state);
            for(int[] temp : possibleWins) {
                int place = temp[temp.length - 1];
                winChecker[place] = f;
                if(Figure.hasGeneInCommon(winChecker[temp[0]], winChecker[temp[1]], winChecker[temp[2]], winChecker[temp[3]])) {
                    return place;
                } else {
                    winChecker[place] = null;
                }
            }
        }

        if(possibleWins.size() > 0) {  // cant win anything with the given figure, but maybe we can destoy a winnig situarion
            //TODO i should try to make a move, that doesn't creates a "bad situation" (a situation in which the other player can winn with every figure)
            //TODO i should also set the filters (maybe in a own method?!)
            state[possibleWins.get(0)[4]] = f;
            this.setFilters(state);
            return possibleWins.get(0)[4];
        } else { // there are no situations which are "usefull", so make a random move
            List<Integer> remainingField = new ArrayList<Integer>();
            for (int i = 0; i < state.length; i++) {    // get all possible moves
                if(state[i] == null)
                    remainingField.add(i);
            }
            if(remainingField.size() == 0) return -1;   // this sould later not occur. this means we got a draw
            int move = remainingField.get(this.rnd.nextInt(remainingField.size())); // make a random move from all possible moves
            state[move] = f;
            this.setFilters(state);
            return move;
        }

    }


    private void setFilters(Figure[] board) {

    }

    private static int[] countFigures(List<Figure> figures) {
        int[] counter = new int[4];
        for(Figure f : figures) {
            if(f.isSmall()) counter[0]++;
            if(f.isBlack()) counter[1]++;
            if(f.isRound()) counter[2]++;
            if(f.hasHole()) counter[3]++;
        }
        return counter;
    }

    private static List<int[]> possibleWinningSituations(Figure[] state) {
       ArrayList<int[]> ws = new ArrayList<int[]>();
       int temp = 0;
       temp = isOneEmpty(state[0], state[5], state[10], state[15]);
       if(temp >= 0)
           ws.add(new int[]{0, 5, 10, 15, temp});
       temp = isOneEmpty(state[3], state[6], state[9], state[12]);
       if(temp >= 0)
           ws.add(new int[]{3, 6, 9, 12, temp});

        // rows and columns
       for (int i = 0; i < 4; i++) {
           temp = isOneEmpty(state[(i * 4)], state[(i * 4) + 1], state[(i * 4) + 2], state[(i * 4) + 3]);
           if(temp >= 0)
               ws.add(new int[]{(i * 4), (i * 4) + 1, (i * 4) + 2, (i * 4) + 3, temp});

           temp = isOneEmpty(state[i], state[i + 4], state[i + 8], state[i + 12]);
           if(temp >= 0)
               ws.add(new int[]{i, (i + 4), (i + 8), (i + 12), temp});

       }

        // squares
        for (int i = 0; i < 3; i++) {
            temp = isOneEmpty(state[i], state[i + 1], state[i + 4], state[i + 5]);
            if(temp >= 0)
                ws.add(new int[]{i, (i + 1), (i + 4), (i + 5), temp});
            
            temp = isOneEmpty(state[i + 4], state[i + 5], state[i + 8], state[i + 9]);
            if(temp >= 0)
                ws.add(new int[]{(i + 4), (i + 5), (i + 8), (i + 9), temp});
            
            temp = isOneEmpty(state[i + 8], state[i + 9], state[i + 12], state[i + 13]);
            if(temp >= 0)
                ws.add(new int[]{(i + 8), (i + 9), (i + 12), (i + 13), temp});
        }
        return ws;
    }

    private static Figure[] clone(Figure[] f) {
        Figure[] clone = new Figure[f.length];
        for (int i = 0; i < f.length; i++) {
            clone[i] = f[i].clone();
        }
        return clone;
    }

    private static int isOneEmpty(Figure... fs) {
        int nullCounter = 0;
        int index = -1;
        for (int i = 0; i < fs.length; i++) {
            if(fs[i] == null) {
                nullCounter++;
                index = -1;
            }
        }
        return (nullCounter != 1) ? -1 : index;
    }
    
}
