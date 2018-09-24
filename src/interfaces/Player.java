package interfaces;

import game.Figure;

import java.util.List;
import java.util.Random;

public abstract class Player {
	protected Random rnd;
	private String name;
	private static final String[] noNameNames = {"Albert Tross", "Alexander Platz", "Ali Baba", "Alter", "Fritz", "Andi Mauer",
			"Andreas Kreuz", "Ann Geber", "Ann Zug", "Anna Bolika", "Anna Gramm", "Anna Liese", "Anna Nass", "Anna Theke", "Anne Wand",
			"Apple Blythe Allison", "Aquinnah Kathleen", "Audio Science Clayton", "Axel Flecken", "Axel Haar", "Axel Höhle", "Axel Schweiß",
			"Bernhard Diener", "Bill Ich", "Bill Yard", "Billibald", "Blue Angel", "Bluebell Madonna", "Bob Fahrer", "Bren Essel",
			"Bronx Mowgli", "Chastitiy Sun", "Cheyenne Savannah", "Claire Bazyn", "Claire Grube", "Claire Waßer", "Daisy Boo", "Deinhard",
			"Demut", "Dick Erchen", "Dick S.Ding", "Dick Tator", "Diva Thin Muffin Pigeen", "Donalde Duck", "Dr.Acula", "Ed Ding", "Eitelfritz",
			"Elijah Blue", "Elijah Bob Patricius Guggi", "Ellen Lang", "Elli Fant", "Eric fik van Hinten", "Erlfried König", "Ernst Fall", "Ernst Haft",
			"Eutropia", "Eva Adam", "Eva N.Gelium", "Fanny Knödel", "Fifi Trixibelle", "Florida", "Frank Reich", "Frankobert", "Franz Brandwein",
			"Franz Mann", "Franz Ohse", "Georg Asmus", "Gerd Nehr", "Gerold Steiner", "Gracie Fan", "Gregor Janisch", "Grier Hammond Henchy",
			"Gutfried Wurst", "Hans A. Bier", "Hans Dampf", "Hans Maul", "Hans Wurst", "Harlow Winter Kate", "Harry Bo", "Hasta La vista",
			"Heavenly Hiraani Tiger Lily", "Heide Witzka", "Heidi Kraut", "Heinz Ellmann", "Heinz Elmann", "Heinz Fiction", "Hella Kot",
			"Hella Wahnsinn", "Henry Günther Ademola Dashtu Samuel", "Herr Bert Herbert", "Herta Wurst", "Herta Zuschlag", "Hugo Slawien", "Ireland Eliesse",
			"Jake Daniel", "James Bond", "Jesus Halleluja", "Jim Panse", "Jimi Blue", "Jo Ghurt", "Jo Ker", "Johannes Beer", "Johannes Brodt", "Johannes Burg",
			"Johannes Kraut", "Johannis Bär", "Johannis Burg", "John Glör", "John Jack", "Johnny Walker", "K. Melle", "Kai Mauer", "Kai Sehr", "Kai Ser", "Karel Ofen",
			"Karl Auer", "Karl Ender", "Karl Kopf", "Karl Sruhe", "Karo Kästchen", "Ken Tucky", "Kid Zler", "Klara Bach", "Klara Fall", "Klara Geist",
			"Klara Himmel", "Klara Korn", "Klaus Thaler", "Klaus Trophobie", "Klaus Uhr", "Knut Schfleck", "Lars Samenström", "Lee Kör", "Licher Export",  "Lilly Putaner",
			"Lisa Bonn", "Manne Quinn", "Marga Käse", "Marga Milch", "Maria Kron", "Maria Zell", "Marie Huana", "Marie Juana", "Mario Nese", "Mark Aber",
			"Mark Graf", "Markus Platz", "Marta Pfahl", "Martha Hari", "Mary Huana", "Melitta Mann", "Mira Bellenbaum", "Miss Raten", "Muh Barack",
			"Nick Olaus", "Olga Machslochoff", "Olle Schleuder", "Otto Päde", "Pan Tau", "Paul Ahner", "Paul Lahner", "Peer Verser", "Pepsi-Carola",
			"Perry Ode", "Peter Pan", "Peter Petersilie", "Peter Silie", "Phil Fraß", "Philip Morris", "Polly Zist", "Rainer Hohn", "Rainer Müll",
			"Rainer Stoff", "Rainer Verlust", "Rainer Wein", "Reiner Korn", "Reiner Zorn", "Reiner Zufall", "Rob Otter", "Nimsi Hartmann",
			"Roman Schreiber", "Roman Ticker", "Roman Tisch", "Ron Dell", "Rosa Fingernagel", "Rosa Fleisch", "Rosa Himmel", "Rosa Hirn",
			"Rosa Panter", "Rosa Rosenbusch", "Rosa Roth", "Rosa Schwein", "Rosa Wolke", "Rosa Wurst", "Rosi Ne", "Rudi Mentation", "Sham Paine",
			"Speck Wildhorse", "Sunny Täter", "Teddy Baer", "Thor Schuß", "Tim Buktu", "Tscherno Bill", "Ute Russ", "Wilma Bier", "Wilma Gern",
			"Wilma Haschen", "Wilma Pfannkuchen", "Wilma Ruhe", "Wilma Streit", "Wolfgang See"};

	public Player(String name) {
		this.rnd = new Random();
		this.rnd.nextInt(); this.rnd.nextInt(); this.rnd.nextInt();
		this.name = name;
	}

	public Player() {
		this.rnd = new Random();
		this.rnd.nextInt(); this.rnd.nextInt(); this.rnd.nextInt();
		this.name = getFunnyName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public static String getFunnyName()  {
        Math.random(); Math.random(); Math.random();
        return noNameNames[(int)(Math.random() * noNameNames.length)];
    }

    protected static int coordinatesToIndex(int x, int y) {
		return 4 * y + x;
	}

	private static Figure[][] from1Dto2D(Figure[] f) {
		Figure[][] fs = new Figure[4][4];

		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				fs[i][j] = f[Player.coordinatesToIndex(j, i)];
			}
		}
		return fs;
	}

	public int placeFigure(Figure f, Figure[] board, List<Figure> remaining) {
		return placeFigure(f, from1Dto2D(board), remaining);
	}

	public Figure selectFigure(List<Figure> remaining, Figure[] board) {
		return selectFigure(remaining, from1Dto2D(board));
	}

	public int placeFigure(Figure f, Figure[][] board, List<Figure> remaining) {
		return -1;
	}

	public Figure selectFigure(List<Figure> remaining, Figure[][] board) {
		return null;
	}

	public void reset() { }

	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Player)) return false;
		Player p = (Player) o;
		return p.name.equals(this.name);
	}

}
