
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Implements a Minesweeper game. First upload to git hub. TODO: Bomb search,
 * shift click flagging, win/lose scenario TODO: Link mousepressed with
 * drawboard in a way where the correct output is drawn. - If user clicks bomb,
 * game over - If user flags hidden box, make it so the box can no longer be
 * clicked on until unflagged - Only hidden boxes should be flaggable, shown
 * boxes should not be flaggable
 * 
 * TODO: ALMOST DONE, COMPLETE FLAGGING AND GAME ENDING, SOME BUGS IN DRAWBOARD
 * METHOD
 */
public class Minesweeper extends Application {

	private GraphicsContext g; // For drawing on the canvas that displays the Minesweeper board.
	// (This variable is assigned its value in the start() method.)

	private int[][] minesweeperModel; // Holds the data for the game board
	private int[][] minesweeperBombCount; // Holds number of bombs around each square
	private boolean[][] minesweeperisClicked; // Holds hidden/shown info about boxes (if user hasn't clicked on box it's
												// false, if user has it's true)
	private boolean[][] minesweeperisFlagged; // Holds flagged/not flagged info about boxes ( if user hasn't shift
												// clicked on box it's false, if user has it's true)

	private double canvasWidth, canvasHeight; // Width and Height of the canvas we're drawing on
	private double boxWidth, boxHeight; // Width and height of each individual box
	private int numberOfBoxes; // Number of boxes in each row and column
	private boolean gameOver; // Keeps track of whether game is over

	/**
	 * Starts a new game, and initializes all of the global data for the game. This
	 * is called by the start() method when the program first start up, and it is
	 * called when the user selects the "New Game" command from the "Control" menu.
	 */
	private void newGame() {
		// REPLACE THE FOLLOWING CODE!
		initVariables(10);
		initBoard();
		drawBoard();
		/*
		 * g.setFill(Color.WHITE); g.fillRect(0, 0, 400, 400); g.setFill(Color.BLACK);
		 * g.fillText("newGame() was called", 50, 50);
		 */
	} // end newGame()

	/**
	 * This initializes and randomly assigns 10 "bombs" to the minesweeper board.
	 */
	private void initBoard() {
		// 0 = no bomb
		// 1 = bomb

		int bombCount = 0; // Number of bombs placed on board

		// Populates board with bomb's
		for (int i = 0; i < numberOfBoxes; i++) {
			for (int d = 0; d < numberOfBoxes; d++) {

				double bombPlacer = Math.random();

				// Only places a bomb if there are less than 10 bombs and if random number is
				// less than .125
				if (bombPlacer < .125 && bombCount < 10) {
					minesweeperModel[i][d] = 1;
					bombCount++;
				} else {
					minesweeperModel[i][d] = 0;
				}

				// User hasn't clicked on any boxes yet
				minesweeperisClicked[i][d] = false;
				minesweeperisFlagged[i][d] = false;
			}
		}

		bombScout();

		// Debug statement
		System.out.println(bombCount + " bomb's placed.");
	} // end initBoard()

	/**
	 * Initializes the global variables used in other methods. This includes
	 * canvasWidth, canvasHeight, boxWidth, boxHeight, and numberOfBoxes. Specify
	 * the number of boxes wanted so can vary size of board.
	 * 
	 * @param boxes number of boxes in each row and column (e.g. 10 = 100 total
	 *              boxes)
	 */
	private void initVariables(int boxes) {

		numberOfBoxes = boxes;
		gameOver = false;

		minesweeperModel = new int[numberOfBoxes][numberOfBoxes];
		minesweeperBombCount = new int[numberOfBoxes][numberOfBoxes];
		minesweeperisClicked = new boolean[numberOfBoxes][numberOfBoxes];
		minesweeperisFlagged = new boolean[numberOfBoxes][numberOfBoxes];

		canvasWidth = g.getCanvas().getWidth();
		canvasHeight = g.getCanvas().getHeight();

		boxWidth = canvasWidth / numberOfBoxes;
		boxHeight = canvasHeight / numberOfBoxes;

	} // end initVariables()

	/**
	 * Draws the mine sweeper board.
	 */
	private void drawBoard() {

		// Initialize variables
		double xCoord, yCoord;

		xCoord = 0;
		yCoord = 0;

		for (int i = 0; i < numberOfBoxes; i++) {
			for (int d = 0; d < numberOfBoxes; d++) {

				boolean isClicked, isFlagged;
				int isBomb;

				isClicked = minesweeperisClicked[i][d];
				isFlagged = minesweeperisFlagged[i][d];
				isBomb = minesweeperModel[i][d];

				// START FROM SCRATCH

				// NOTHING CLICKED

				// user clicked box, and hasn't been flagged
				if (!isClicked && !isFlagged) {
					g.setLineWidth(4);
					g.strokeRect(xCoord, yCoord, boxWidth, boxHeight);
					g.setFill(Color.DARKGREEN);
					g.fillRect(xCoord, yCoord, boxWidth, boxHeight);
					// g.setStroke(Color.BLACK);
					// g.setLineWidth(1);
					// g.strokeText("*", xCoord + (boxWidth / 2.25), yCoord + (boxHeight / 1.5));
					xCoord += boxWidth;
				}

				// user clicked box, hasn't been flagged, no bomb in box
				else if (isClicked && !isFlagged && (isBomb == 0)) {
					// Fills the box with dark green
					g.setLineWidth(4);
					g.strokeRect(xCoord, yCoord, boxWidth, boxHeight);
					g.setFill(Color.LIGHTGREEN);
					g.fillRect(xCoord, yCoord, boxWidth, boxHeight);

					// draw bomb count in each box
					if (minesweeperBombCount[i][d] > 0) {
						g.setStroke(Color.BLACK);
						g.setLineWidth(1);
						g.strokeText(minesweeperBombCount[i][d] + "", xCoord + (boxWidth / 2.25),
								yCoord + (boxHeight / 1.5));
					}

					xCoord += boxWidth;

					// debug statement
					// System.out.println("Clicked, not flagged has been executed.");
				}

				// user shift-clicked box, box hasn't been clicked before
				else if (isFlagged && !isClicked) {
					g.setLineWidth(4);
					g.strokeRect(xCoord, yCoord, boxWidth, boxHeight);
					g.setFill(Color.DEEPPINK);
					g.fillRect(xCoord, yCoord, boxWidth, boxHeight);

					xCoord += boxWidth;
				}

				// user clicked box, is a bomb in box
				else if (isClicked && !isFlagged && (isBomb == 1)) {
					// Fills the box with dark red
					g.setLineWidth(4);
					g.strokeRect(xCoord, yCoord, boxWidth, boxHeight);
					g.setFill(Color.DARKRED);
					g.fillRect(xCoord, yCoord, boxWidth, boxHeight);

					xCoord += boxWidth;

					g.strokeText("BOOOOOOM", 200, 200, 100);
					
					gameOver = true;
					// debug statement
					// System.out.println("Clicked, not flagged has been executed.");
				}

			}

			xCoord = 0;
			yCoord += boxHeight;
		}
	} // end drawBoard()

	/**
	 * Looks vertically, horizontally, and diagonally on clicked square and sums the
	 * amount of bombs found.
	 */
	private void bombScout() {

		int rowLength, columnLength, bombCounter;

		rowLength = minesweeperModel.length;
		columnLength = minesweeperModel.length;

		bombCounter = 0;

		// Go through each box in the matrix
		for (int r = 0; r < rowLength; r++) {
			for (int c = 0; c < columnLength; c++) {

				/*
				 * This is where we check around the current box for bombs and then put the bomb
				 * count in the minesweeperBombCount array.
				 * 
				 * THIS IS FOR EDGE CASES
				 */
				if ((r == 0 || r == 9) || (c == 0 || c == 9)) {

					/*** Corners ***/

					// Only 3 boxes to check when dealing with a corner

					// upper left corner (0, 0)
					if (r == 0 && c == 0) {

						if (minesweeperModel[r + 1][c] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r + 1][c + 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r][c + 1] == 1) {
							bombCounter++;
						}

					}

					// lower left corner (9, 0)
					if (r == 9 && c == 0) {

						if (minesweeperModel[r - 1][c] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r - 1][c + 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r][c + 1] == 1) {
							bombCounter++;
						}
					}

					// upper right corner (0, 9)
					if (r == 0 && c == 9) {

						if (minesweeperModel[r + 1][c] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r + 1][c - 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r][c - 1] == 1) {
							bombCounter++;
						}

					}

					// lower right corner (9, 9)
					if (r == 9 && c == 9) {
						if (minesweeperModel[r - 1][c] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r - 1][c - 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r][c - 1] == 1) {
							bombCounter++;
						}
					}

					/*** Everything other than corners ***/

					// Only 5 boxes to check when dealing with sides

					// left side (1 to 8, 0)
					if ((r >= 1 && r <= 8) && c == 0) {
						if (minesweeperModel[r - 1][c] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r - 1][c + 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r][c + 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r + 1][c + 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r + 1][c] == 1) {
							bombCounter++;
						}
					}

					// right side (1 to 8, 9)
					if ((r >= 1 && r <= 8) && c == 9) {
						if (minesweeperModel[r - 1][c] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r - 1][c - 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r][c - 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r + 1][c - 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r + 1][c] == 1) {
							bombCounter++;
						}
					}

					// top side (0, 1 to 8)
					if (r == 0 && (c >= 1 && c <= 8)) {
						if (minesweeperModel[r][c - 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r + 1][c - 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r + 1][c] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r + 1][c + 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r][c + 1] == 1) {
							bombCounter++;
						}
					}

					// bottom side (9, 1 to 8)
					if (r == 9 && (c >= 1 && c <= 8)) {
						if (minesweeperModel[r][c - 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r - 1][c - 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r - 1][c] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r - 1][c + 1] == 1) {
							bombCounter++;
						}
						if (minesweeperModel[r][c + 1] == 1) {
							bombCounter++;
						}
					}

				}

				/*
				 * This is where we check around the current box for bombs and then put the bomb
				 * count in the minesweeperBombCount array.
				 * 
				 * THIS IS FOR NOT EDGE CASES
				 */
				else {
					// Vertical
					if (minesweeperModel[r - 1][c] == 1) {
						bombCounter++;
					}
					if (minesweeperModel[r + 1][c] == 1) {
						bombCounter++;
					}

					// Horizontal
					if (minesweeperModel[r][c - 1] == 1) {
						bombCounter++;
					}
					if (minesweeperModel[r][c + 1] == 1) {
						bombCounter++;
					}

					// Diagonal
					if (minesweeperModel[r - 1][c - 1] == 1) {
						bombCounter++;
					}
					if (minesweeperModel[r - 1][c + 1] == 1) {
						bombCounter++;
					}
					if (minesweeperModel[r + 1][c - 1] == 1) {
						bombCounter++;
					}
					if (minesweeperModel[r + 1][c + 1] == 1) {
						bombCounter++;
					}
				}

				// Add bomb count to bomb count array and reset the bomb counter
				minesweeperBombCount[r][c] = bombCounter;
				System.out.println("[" + r + "]" + "[" + c + "]" + ":" + bombCounter);
				bombCounter = 0;

			}

		}

		// System.out.println("Bomb Scout test - Number of rows: " + numOfRows);

	} // end bombScout()
	
	/**
	 * This method is called when the user presses the mouse on the canvas where the
	 * minesweeper board is drawn. (The start() method sets this method to be the
	 * handler for a mousePressed event on the canvas.)
	 * 
	 * @param evt this event object contains information about the more press event.
	 */
	private void doMousePressed(MouseEvent evt) {
		double x, y; // The coordinates where the user pressed the mouse.
		int row, column; // The [row][column] that user clicks

		boolean shift; // Tells whether shift key was held down while mouse was pressed.
		boolean isFlagged; // Tells whether box is flagged
		boolean isClicked; // Tells whether box has been click or not

		x = evt.getX();
		y = evt.getY();
		shift = evt.isShiftDown();

		// Computes row,column from x,y coordinates
		row = (int) (y / boxHeight);
		column = (int) (x / boxWidth);

		//
		isFlagged = minesweeperisFlagged[row][column];
		isClicked = minesweeperisClicked[row][column];

		// regular click
		if (!isClicked && !isFlagged && !shift) {
			minesweeperisClicked[row][column] = true;
		}

		// User is trying to flag a box
		// Changes from flagged to not flagged.
		else if (shift && !isClicked) {

			if (isFlagged) {
				minesweeperisFlagged[row][column] = false;
			} else if (!isFlagged) {
				minesweeperisFlagged[row][column] = true;
			}
		}

		drawBoard();
		
		if(gameOver) {
			
		}
		
		// Debug statements
		System.out.println("Mouse was pressed at: (" + x + ", " + y + ")");
		System.out.println("Row: " + row + " Column: " + column + "\n");

		// REPLACE THE FOLLOWING CODE!
		/*
		 * g.setFill(Color.WHITE); g.fillRect(0, 0, 400, 400); g.setFill(Color.BLACK);
		 * g.fillText("mouse was pressed at (" + x + "," + y + ")", 50, 25);
		 * g.fillText("shift key pressed was " + shift, 50, 50);
		 */
	} // end doMousePressed()

	/**
	 * Creates a menu bar for the program, which will be placed at the top of the
	 * window. This method is called by the start() method to create the menu bar.
	 * The start() method adds it to the window.
	 */
	private MenuBar createMenuBar() {

		MenuBar menuBar; // The menu bar.
		Menu menu; // For adding a menu to the menu bar.
		MenuItem newGameItem, quitItem; // For adding items to the menu.

		menuBar = new MenuBar();

		menu = new Menu("Control"); // Create a Control menu and place it in the menu bar
		menuBar.getMenus().add(menu);

		newGameItem = new MenuItem("New Game"); // Create New Game item and add it to the menu.
		menu.getItems().add(newGameItem);
		newGameItem.setOnAction(evt -> newGame()); // Selecting this item will call newGame().

		quitItem = new MenuItem("Quit"); // Create Quit item and add it to the menu.
		menu.getItems().add(quitItem);
		quitItem.setOnAction(evt -> System.exit(0)); // Selecting this item will end program.

		return menuBar;

	} // end createMenuBar()

	/**
	 * The start() method of a JavaFX application sets up the GUI. and can
	 * initialize global variables.
	 */
	public void start(Stage stage) {
		int size = 400;
		Canvas canvas = new Canvas(size, size);
		BorderPane root = new BorderPane(canvas);
		root.setTop(createMenuBar());
		root.setStyle("-fx-border-width: 4px; -fx-border-color: #444");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Minesweeper");
		stage.show();
		stage.setResizable(false);
		g = canvas.getGraphicsContext2D();
		newGame(); // Start the first game.
		canvas.setOnMousePressed(evt -> doMousePressed(evt));
	}

	/**
	 * The main routine of a JavaFX program just launches the application.
	 */
	public static void main(String[] args) {
		launch();
	}

} // end Minesweeper