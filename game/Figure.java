package game;

public class Figure {

	private byte genome;
	
	private static final byte SMALL_MASK = 0b1000;
	private static final byte BLACK_MASK = 0b0100;
	private static final byte HOLE_MASK = 0b0010;
	private static final byte ROUND_MASK = 0b0001;
	private static final byte MASK = 0b1111;
	private static final byte MOD = 16;
	
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
		
		boolean round = f.isRound();
		boolean small = f.isSmall();
		boolean black = f.isBlack();
		boolean hole  = f.hasHole();
		
		for (int i = 1; i < figures.length; i++) {
			f = figures[i];
			if(f == null) return false;
			round &= f.isRound();
			black &= f.isBlack();
			hole  &= f.hasHole();
			small &= f.isSmall();
		}

		return round | small | hole | black;
	}

}
