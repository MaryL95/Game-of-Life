import java.util.Arrays;
import java.util.Random;

/**
 * Game of Life Java Program 
 * Game randomly places cells in a grid and iterates
 * the game, and following set rules, cells either live or die.
 * 
 * 
 * @author Mary Lawrence
 * @version 06.02.19
 *
 */
public class GameOfLife {

	// Instance variables store grid dimensions, initial number of seeds, and 2D
	// array which stores the cells

	private int rows;
	private int columns;
	private int seeds;
	private boolean[][] originalGrid;

	/**
	 * Constructor for Game of Life: Creates a grid of specified proportions filled
	 * with a specified number of cells, randomly placed.
	 * 
	 * 
	 * @param rows    The number of rows in the grid
	 * @param columns The number of columns in the grid
	 * @param seeds   The number of seeds that the game is initialised with
	 */
	public GameOfLife(int rows, int columns, int seeds) {
		if (rows < 1) {
			throw new IllegalArgumentException("Must be at least one row");
		} else {
			this.rows = rows;
		}
		if (columns < 1) {
			throw new IllegalArgumentException("Must be at least one column");
		} else {
			this.columns = columns;
		}
		if (seeds < 0) {
			throw new IllegalArgumentException("Cannot have negative number of seeds"); // Allows for game to be
																						// initialised with 0 or more
																						// seeds
		} else if (seeds > (rows * columns)) {
			throw new IllegalArgumentException("Cannot have more seeds than grid spaces");
		} else {
			this.seeds = seeds;

			// Initialise 2D array with rows and columns
			// boolean to represent alive(true) or dead(false) states of each cell
			// Sets grid to all empty(dead) cells
			this.originalGrid = new boolean[rows][columns];
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < columns; c++) {
					originalGrid[r][c] = false;
				}
			}

			// Randomly place seeds within the grid to begin game
			// Otherwise could use scanner or buffered reader to get user input for seed
			// positions (x,y coordinates)
			Random random = new Random();
			int occupiedCells = 0;

			while (occupiedCells < seeds) {
				int x = random.nextInt(rows);
				int y = random.nextInt(columns);
				if (originalGrid[x][y] == false) {
					originalGrid[x][y] = true;
					occupiedCells++;
				}
			}
		}
	}

	/**
	 * Method to evolve the game from initial state
	 * 
	 * @param boolean[][] originalGrid
	 * @return boolean[][] next iteration of grid state
	 */
	public boolean[][] Iterator(boolean[][] originalGrid) {

		// New grid initialised to store the new state, copies from original grid
		boolean[][] newGrid = new boolean[rows][];
		for (int r = 0; r < rows; r++) {
			newGrid[r] = Arrays.copyOf(originalGrid[r], columns);
		}

		// Checks conditions of each cell to determine if it lives or dies.
		// If cell has 3 neighbours will remain alive or become alive, and if cell has 2
		// neighbours will carry that state(alive or dead) forward.
		// Otherwise cell is dead.

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (countNeighbours(originalGrid, r, c) == 3) {
					newGrid[r][c] = true;
				} else if (countNeighbours(originalGrid, r, c) == 2) {
					newGrid[r][c] = originalGrid[r][c];
				} else {
					newGrid[r][c] = false;
				}
			}

		}

		return newGrid;
	}

	/**
	 * 
	 * Helper method to count the number of neighbours a cell has
	 * 
	 * @param boolean[][] currentGrid The current iteration of the grid to operate
	 *        on
	 * @param int row The row position of the cell to be checked
	 * @param int col The column position of the cell to be checked
	 * @return int The number of neighbours the cell has
	 * 
	 */

	public int countNeighbours(boolean[][] currentGrid, int row, int col) {

		int neighbours = 0; // counter for number of neighbours initialised to 0

		// Method checks to ensure grid coordinates are valid and then tests cell for
		// boolean, if true neighbours counter increments

		// Look for above neighbour
		if ((row - 1 >= 0) && (col < columns)) {
			neighbours = currentGrid[row - 1][col] ? neighbours + 1 : neighbours;
		}

		// Look for above and right neighbour
		if ((row - 1 >= 0) && (col + 1 < columns)) {
			neighbours = currentGrid[row - 1][col + 1] ? neighbours + 1 : neighbours;
		}

		// Look for right neighbour
		if ((row < rows) && (col + 1 < columns)) {
			neighbours = currentGrid[row][col + 1] ? neighbours + 1 : neighbours;
		}

		// Look for below and right neighbour
		if ((row + 1 < rows) && (col + 1 < columns)) {
			neighbours = currentGrid[row + 1][col + 1] ? neighbours + 1 : neighbours;
		}

		// Look for below neighbour
		if ((row + 1 < rows) && (col < columns)) {
			neighbours = currentGrid[row + 1][col] ? neighbours + 1 : neighbours;
		}

		// Look for below and left neighbour
		if ((row + 1 < rows) && (col - 1 >= 0)) {
			neighbours = currentGrid[row + 1][col - 1] ? neighbours + 1 : neighbours;
		}

		// Look for left neighbour
		if ((row >= 0) && (col - 1 >= 0)) {
			neighbours = currentGrid[row][col - 1] ? neighbours + 1 : neighbours;
		}

		// Look for above and left neighbour
		if ((row - 1 >= 0) && (col - 1 >= 0)) {
			neighbours = currentGrid[row - 1][col - 1] ? neighbours + 1 : neighbours;
		}

		return neighbours;
	}

	/**
	 * Helper method to print 2D array in grid format and converts boolean values to
	 * more readable string symbols
	 * 
	 * @param boolean[][] grid The grid to print
	 */
	public void printGrid(boolean[][] grid) {

		String[][] readableGrid = new String[grid.length][grid[0].length]; // Initialises new 2D array of type String

		// Loop through every element in array and convert to String symbol
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == true) {
					readableGrid[i][j] = "o";
				} else {
					readableGrid[i][j] = "x";
				}
			}
		}

		// Loop through all rows in grid and print on separate line
		for (String[] row : readableGrid) {
			System.out.println(Arrays.toString(row));
		}
	}

	/**
	 * 
	 * Driver method to test implementation
	 * 
	 */
	public static void main(String[] args) {

		GameOfLife game1 = new GameOfLife(3, 3, 3); // Initial grid created

		boolean[][] iteration1 = game1.Iterator(game1.originalGrid);
		boolean[][] iteration2 = game1.Iterator(iteration1);
		boolean[][] iteration3 = game1.Iterator(iteration2);

		// Prints out 3 iterations of the game
		System.out.println("Initial state:   \n");
		game1.printGrid(game1.originalGrid);
		System.out.println("First Iteration:   \n");
		game1.printGrid(iteration1);
		System.out.println("Second Iteration:   \n");
		game1.printGrid(iteration2);
		System.out.println("Third Iteration:   \n");
		game1.printGrid(iteration3);

	}

}
