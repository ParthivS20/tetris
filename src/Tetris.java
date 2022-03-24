/**
 * Tetris.java  3/23/2022
 *
 * @author - Parthiv Shah
 *
 */

// Represents a Tetris game.
public class Tetris implements ArrowListener {
	private final BoundedGrid<Block> grid;	// The grid containing the Tetris pieces.
	private final BlockDisplay display;		// Displays the grid.
	private Tetrad activeTetrad;		// The active Tetrad (Tetris Piece).
	private int score;
	private int level;

	// Constructs a Tetris Game
	public Tetris() {
		grid = new BoundedGrid<>(20, 10);
		score = 0;
		level = 1;
		display = new BlockDisplay(grid);
		display.setTitle("Tetris • Score: " + score + " • Level: " + level);
		display.setArrowListener(this);
		activeTetrad = new Tetrad(grid);
		display.showBlocks();
	}

	// Play the Tetris Game
	public void play() {
		while (true) {
			nextFrame();
		}
	}

	private void nextFrame() {
		sleep(1);
		boolean stopped = !activeTetrad.translate(1, 0);
		if(stopped) {
			activeTetrad = new Tetrad(grid);
			clearCompletedRows();
		}
		display.showBlocks();
	}

	// Precondition:  0 <= row < number of rows
	// Postcondition: Returns true if every cell in the given row
	//                is occupied; false otherwise.
	private boolean isCompletedRow(int row) {
		for(int i = 0; i < grid.getNumCols(); i++) {
			if(grid.get(new Location(row, i)) == null) return false;
		}
		return true;
	}

	// Precondition:  0 <= row < number of rows;
	//                The given row is full of blocks.
	// Postcondition: Every block in the given row has been removed, and
	//                every block above row has been moved down one row.
	private void clearRow(int row) {
		for(int i = 0; i < grid.getNumCols(); i++) {
			grid.get(new Location(row, i)).removeSelfFromGrid();
		}

		for(int i = row - 1; i >= 0; i--) {
			for(int j = 0; j < grid.getNumCols(); j++) {
				Block b = grid.get(new Location(i, j));
				if(b != null) b.moveTo(new Location(i + 1, j));
			}
		}
	}

	// Postcondition: All completed rows have been cleared.
	private void clearCompletedRows() {
		for(int i = 0; i < grid.getNumRows(); i++) {
			if(isCompletedRow(i)) {
				clearRow(i);
			}
		}
	}

	// Sleeps (suspends the active thread) for duration seconds.
	private void sleep(double duration) {
		final int MILLISECONDS_PER_SECOND = 1000;

		int milliseconds = (int)(duration * MILLISECONDS_PER_SECOND);

		try
		{
			Thread.sleep(milliseconds);
		}
		catch (InterruptedException e)
		{
			System.err.println("Can't sleep!");
		}
	}

	@Override
	synchronized public void upPressed() {
		activeTetrad.rotate();
		display.showBlocks();
	}

	@Override
	synchronized public void downPressed() {
		activeTetrad.translate(1, 0);
		display.showBlocks();
	}

	@Override
	synchronized public void leftPressed() {
		activeTetrad.translate(0, -1);
		display.showBlocks();
	}

	@Override
	synchronized public void rightPressed() {
		activeTetrad.translate(0, 1);
		display.showBlocks();
	}

	@Override
	synchronized public void spacePressed() {
		while(true) {
			boolean done = !activeTetrad.translate(1, 0);
			if(done) {
				break;
			}
		}
		display.showBlocks();
	}

	// Creates and plays the Tetris game.
	public static void main(String[] args) {
		Tetris game = new Tetris();
		game.play();
	}
}
