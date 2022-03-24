/**
 * Tetrad.java  4/30/2014
 *
 * @author - Jane Doe
 * @author - Period n
 * @author - Id nnnnnnn
 *
 * @author - I received help from ...
 *
 */

import java.awt.Color;

// Represents a Tetris piece.
public class Tetrad {
	private Block[] blocks;	// The blocks for the piece.

	// Constructs a Tetrad.
	public Tetrad(BoundedGrid<Block> grid) {
		blocks = new Block[4];
		for(int i = 0; i < blocks.length; i++) {
			blocks[i] = new Block();
		}

		Color tempColor = Color.white;
		Location[] tempLocs = new Location[4];
		int type = (int) (Math.random() * 7);

		switch(type) {
			default:
			case 0:
				tempColor = new Color(243, 14, 25);
				tempLocs = new Location[]{new Location(0, 5), new Location(1, 5), new Location(2, 5), new Location(3, 5)};
				setRotatePivot(tempLocs, 2);
				break;
			case 1:
				tempColor = new Color(133, 133, 133);
				tempLocs = new Location[]{new Location(0, 3), new Location(0, 4), new Location(0, 5), new Location(1, 4)};
				setRotatePivot(tempLocs, 2);
				break;
			case 2:
				tempColor = new Color(91, 254, 254);
				tempLocs = new Location[]{new Location(0, 4), new Location(0, 5), new Location(1, 4), new Location(1, 5)};
				break;
			case 3:
				tempColor = new Color(247, 255, 6);
				tempLocs = new Location[]{new Location(0, 4), new Location(1, 4), new Location(2, 4), new Location(2, 5)};
				setRotatePivot(tempLocs, 2);
				break;
			case 4:
				tempColor = new Color(251, 0, 255);
				tempLocs = new Location[]{new Location(0, 5), new Location(1, 5), new Location(2, 5), new Location(2, 4)};
				setRotatePivot(tempLocs, 2);
				break;
			case 5:
				tempColor = new Color(75, 0, 255);
				tempLocs = new Location[]{new Location(1, 3), new Location(1, 4), new Location(0, 4), new Location(0, 5)};
				setRotatePivot(tempLocs, 2);
				break;
			case 6:
				tempColor = new Color(51, 255, 0);
				tempLocs = new Location[]{new Location(0, 3), new Location(0, 4), new Location(1, 4), new Location(1, 5)};
				setRotatePivot(tempLocs, 3);
				break;
		}

		for(Block b : blocks) {
			b.setColor(tempColor);
		}

		addToLocations(grid, tempLocs);
		translate(0, -1);
	}


	// Postcondition: Attempts to move this tetrad deltaRow rows down and
	//						deltaCol columns to the right, if those positions are
	//						valid and empty.
	//						Returns true if successful and false otherwise.
	public boolean translate(int deltaRow, int deltaCol) {
		BoundedGrid<Block> g = blocks[0].getGrid();
		Location[] curLocs = removeBlocks();;
		Location[] newLocs = new Location[curLocs.length];
		for(int i = 0; i < newLocs.length; i++) {
			newLocs[i] = new Location(curLocs[i].getRow() + deltaRow, curLocs[i].getCol() + deltaCol);
		}

		if(areEmpty(g, newLocs)) {
			addToLocations(g, newLocs);
			return true;
		}

		addToLocations(g, curLocs);
		return false;
	}

	// Postcondition: Attempts to rotate this tetrad clockwise by 90 degrees
	//                about its center, if the necessary positions are empty.
	//                Returns true if successful and false otherwise.
	public boolean rotate() {
		BoundedGrid<Block> g = blocks[0].getGrid();
		Location[] curLocs = removeBlocks();;
		Location[] newLocs = new Location[curLocs.length];
		newLocs[0] = curLocs[0];
		for(int i = 1; i < newLocs.length; i++) {
			newLocs[i] = new Location(curLocs[0].getRow() - curLocs[0].getCol() + curLocs[i].getCol(), curLocs[0].getRow() + curLocs[0].getCol() - curLocs[i].getRow());
		}

		if(areEmpty(g, newLocs)) {
			addToLocations(g, newLocs);
			return true;
		}

		addToLocations(g, curLocs);
		return false;
	}


	// Precondition:  The elements of blocks are not in any grid;
	//                locs.length = 4.
	// Postcondition: The elements of blocks have been put in the grid
	//                and their locations match the elements of locs.
	private void addToLocations(BoundedGrid<Block> grid, Location[] locs) {
		for(int i = 0; i < blocks.length; i++) {
			blocks[i].putSelfInGrid(grid, locs[i]);
		}
	}

	// Precondition:  The elements of blocks are in the grid.
	// Postcondition: The elements of blocks have been removed from the grid
	//                and their old locations returned.
	private Location[] removeBlocks() {
		Location[] locs = new Location[4];
		for(int i = 0; i < blocks.length; i++) {
			locs[i] = blocks[i].getLocation();
			blocks[i].removeSelfFromGrid();
		}
		return locs;
	}

	// Postcondition: Returns true if each of the elements of locs is valid
	//                and empty in grid; false otherwise.
	private boolean areEmpty(BoundedGrid<Block> grid, Location[] locs) {
		for (Location loc : locs) {
			if (!grid.isValid(loc) || grid.get(loc) != null) {
				return false;
			}
		}
		return true;
	}

	private void setRotatePivot(Location[] locs, int pivot) {
		Location temp = locs[0];
		locs[0] = locs[pivot - 1];
		locs[pivot - 1] = temp;
	}
}
