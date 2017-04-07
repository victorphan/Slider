package environment;

import java.math.BigInteger;

/**
 * The current position of the game space
 * 
 * @author TB VP
 *
 */
public class Position {
	public GameState gs = GameState.PLAYING;
	public static final int PIECE_TYPES = 3;
	public static final int BIG_INT_CASE = 8;
	public Side sidePlaying;
	public static int dimen;
	public MoveList ml = null;
	private long[] pieces = new long[PIECE_TYPES];
	private BigInteger[] bigPieces = new BigInteger[PIECE_TYPES];
	
	/**
	 * Constructor initializing dimension and initial position
	 * 
	 * @param dimen: the dimension of the board
	 * @param pieces: the position of all pieces on the current board
	 */
	public Position(int dimen, Side sidePlaying, long [] pieces) {
		Position.dimen = dimen;
		this.pieces = pieces;
		updateBoard(sidePlaying);
	}

	/**
	 * Constructor for position after the first state
	 * 
	 * @param pieces: the position of all pieces on the current board
	 */
	public Position(long [] pieces, Side sidePlaying) {
		this.pieces = pieces;
		updateBoard(sidePlaying);
	}

	/**
	 * Constructor for big integer case
	 * 
	 * @param dimen: the dimension of the board
	 * @param bigPieces: the position of all pieces on the current board
	 */
	public Position(int dimen, BigInteger[] bigPieces) {
		Position.dimen = dimen;
		this.bigPieces = bigPieces;
		updateBoard(sidePlaying);
	}
	
	public void updateBoard(Side playing) {
		sidePlaying = playing;
		updateMoveList();
		checkGameState();
	}
	
	public void checkGameState() {
		if(Long.bitCount(pieces[MoveList.H])==0) {
			gs = GameState.H_WON;
		}
		else if(Long.bitCount(pieces[MoveList.V])==0) {
			gs = GameState.V_WON;
		}
		// add case where draws
	}
	
	public void updateMoveList() {
		if(ml==null) {
			if(Position.dimen <= Position.BIG_INT_CASE) {
				ml = new MoveList(pieces, sidePlaying, dimen);
			}
			else {
				ml = new MoveList(bigPieces, sidePlaying, dimen);
			}
		}
		else {
			ml.updateMoveList(pieces, sidePlaying);
		}
	}

	/**
	 * Constructor for position after the first state
	 * 
	 * @param bigPieces: the position of all pieces on the current board
	 */
	public Position(BigInteger[] bigPieces) {
		this.bigPieces = bigPieces;
		ml = new MoveList(bigPieces, sidePlaying);
	}

	/**
	 * Gets a specific piece type's position for long
	 * 
	 * @param i: index postion of the piece type
	 * @return a specific piece type by i
	 */
	public long getPieces(int i) {
		return pieces[i];
	}

	/**
	 * Gets a specific piece type's position for big integer
	 * 
	 * @param i: index postion of the piece type
	 * @return a specific piece type by i
	 */
	public BigInteger getBigPieces(int i) {
		return bigPieces[i];
	}

	/**
	 * Gets all the pieces of long type
	 * 
	 * @return all the pieces
	 */
	public long[] getPieces() {
		return pieces;
	}

	/**
	 * Gets all the pieces of big integer type
	 * 
	 * @return all the pieces
	 */
	public BigInteger[] getBigPieces() {
		return bigPieces;
	}

	public BigInteger[] getBigPieces() {
		return bigPieces;
	}

	public void setBigPieces(BigInteger[] bigPieces) {
		this.bigPieces = bigPieces;
	}
	
	public void draw() {
		System.out.println(Parse.boardToString(this));
	}
	
	public long getCurrPieces() {
		if(sidePlaying == Side.H) {
			return pieces[MoveList.H];
		}
		else if(sidePlaying == Side.V) {
			return pieces[MoveList.V];
		}
		else {
			throw new Error("Game state not in playing!");
		}
	}
	
	public void setCurrPieces(long newPieces, Side opponent) {
		if(sidePlaying == Side.H) {
			pieces[MoveList.H] = newPieces;
		}
		else if(sidePlaying == Side.V) {
			pieces[MoveList.V] = newPieces;
		}
		else {
			throw new Error("Game state not in playing!");
		}
		updateBoard(opponent);
	}
}
