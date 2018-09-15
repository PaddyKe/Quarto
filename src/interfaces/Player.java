package interfaces;

import game.Figure;

import java.util.List;

public abstract class Player {

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
			"Heavenly Hiraani Tiger Lily", "Heide Witzka", "Heidi Kraut", "Hein Blöd", "Heinz Ellmann", "Heinz Elmann", "Heinz Fiction", "Hella Kot",
			"Hella Wahnsinn", "Henry Günther Ademola Dashtu Samuel", "Herr Bert Herbert", "Herta Wurst", "Herta Zuschlag", "Hugo Slawien", "Ireland Eliesse",
			"Jake Daniel", "James Bond", "Jesus Halleluja", "Jim Panse", "Jimi Blue", "Jo Ghurt", "Jo Ker", "Johannes Beer", "Johannes Brodt", "Johannes Burg",
			"Johannes Kraut", "Johannis Bär", "Johannis Burg", "John Glör", "John Jack", "Johnny Walker", "K. Melle", "Kai Mauer", "Kai Sehr", "Kai Ser", "Karel Ofen",
			"Karl Auer", "Karl Ender", "Karl Kopf", "Karl Sruhe", "Karo Kästchen", "Ken Tucky", "Kid Zler", "Klara Bach", "Klara Fall", "Klara Geist",
			"Klara Himmel", "Klara Korn", "Klaus Thaler", "Klaus Trophobie", "Klaus Uhr", "Knut Schfleck", "Lars Samenström", "Lee Kör", "Licher Export",  "Lilly Putaner",
			"Lisa Bonn", "Manne Quinn", "Marga Käse", "Marga Milch", "Maria Kron", "Maria Zell", "Marie Huana", "Marie Juana", "Mario Nese", "Mark Aber",
			"Mark Graf", "Markus Platz", "Marta Pfahl", "Martha Hari", "Mary Huana", "Melitta Mann", "Mira Bellenbaum", "Miss Raten", "Muh Barack",
			"Nick Olaus", "Olga Machslochoff", "Olle Schleuder", "Otto Päde", "Pan Tau", "Paul Ahner", "Paul Lahner", "Peer Verser", "Pepsi-Carola",
			"Perry Ode", "Peter Pan", "Peter Petersilie", "Peter Silie", "Phil Fraß", "Philip Morris", "Polly Zist", "Rainer Hohn", "Rainer Müll",
			"Rainer Stoff", "Rainer Verlust", "Rainer Wein", "Rainer Zufall", "Reiner Korn", "Reiner Zorn", "Reiner Zufall", "Rob Otter",
			"Roman Schreiber", "Roman Ticker", "Roman Tisch", "Ron Dell", "Rosa Fingernagel", "Rosa Fleisch", "Rosa Himmel", "Rosa Hirn",
			"Rosa Panter", "Rosa Rosenbusch", "Rosa Roth", "Rosa Schwein", "Rosa Wolke", "Rosa Wurst", "Rosi Ne", "Rudi Mentation", "Sham Paine",
			"Speck Wildhorse", "Sunny Täter", "Teddy Baer", "Thor Schuß", "Tim Buktu", "Tscherno Bill", "Ute Russ", "Wilma Bier", "Wilma Gern",
			"Wilma Haschen", "Wilma Pfannkuchen", "Wilma Ruhe", "Wilma Streit", "Wolfgang See"};

	public Player(String name) {
		this.name = name;
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


    // abstract methods
	public abstract int placeFigure(Figure f, Figure[][] board);
	public abstract Figure selectFigure(List<Figure> remaining, Figure[][] board);

}
