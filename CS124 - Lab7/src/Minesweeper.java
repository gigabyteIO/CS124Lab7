
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
 * Implements a Minesweeper game.
 */
public class Minesweeper extends Application {

	private GraphicsContext g;  // For drawing on the canvas that displays the Minesweeper board.  
	// (This variable is assigned its value in the start() method.)

	/**
	 * Starts a new game, and initializes all of the global data
	 * for the game.  This is called by the start() method when
	 * the program first start up, and it is called when the user
	 * selects the "New Game" command from the "Control" menu.
	 */
	private void newGame() {
		// REPLACE THE FOLLOWING CODE!
		g.setFill(Color.WHITE);
		g.fillRect(0, 0, 400, 400);
		g.setFill(Color.BLACK);
		g.fillText("newGame() was called", 50, 50);
	} // end newGame()


	/**
	 * This method is called when the user presses the mouse on the canvas
	 * where the minesweeper board is drawn.  (The start() method sets this
	 * method to be the handler for a mousePressed event on the canvas.)
	 * @param evt this event object contains information about the more press event.
	 */
	private void doMousePressed(MouseEvent evt) {
		double x,y; // The coordinates where the user pressed the mouse.
		boolean shift;  // Tells whether shift key was held down while mouse was pressed.
		x = evt.getX();
		y = evt.getY();
		shift = evt.isShiftDown();

		// REPLACE THE FOLLOWING CODE!
		g.setFill(Color.WHITE);
		g.fillRect(0, 0, 400, 400);
		g.setFill(Color.BLACK);
		g.fillText("mouse was pressed at (" + x + "," + y + ")", 50, 25);
		g.fillText("shift key pressed was " + shift, 50, 50);
	} // end doMousePressed()


	/**
	 * Creates a menu bar for the program, which will be placed at the 
	 * top of the window.  This method is called by the start() method
	 * to create the menu bar.  The start() method adds it to the window.
	 */
	private MenuBar createMenuBar() {

		MenuBar menuBar;  // The menu bar.
		Menu menu;  // For adding a menu to the menu bar.
		MenuItem newGameItem, quitItem;  // For adding items to the menu.

		menuBar = new MenuBar();

		menu = new Menu("Control");  // Create a Control menu and place it in the menu bar
		menuBar.getMenus().add(menu);

		newGameItem = new MenuItem("New Game"); // Create New Game item and add it to the menu.
		menu.getItems().add(newGameItem);
		newGameItem.setOnAction( evt -> newGame() ); // Selecting this item will call newGame().

		quitItem = new MenuItem("Quit"); // Create Quit item and add it to the menu.
		menu.getItems().add(quitItem);
		quitItem.setOnAction( evt -> System.exit(0) ); // Selecting this item will end program.

		return menuBar;

	} // end createMenuBar()


	/**
	 * The start() method of a JavaFX application sets up the GUI.
	 * and can initialize global variables.
	 */
	public void start(Stage stage) {
		int size = 400;
		Canvas canvas = new Canvas(size,size);
		BorderPane root = new BorderPane(canvas);
		root.setTop(createMenuBar());
		root.setStyle("-fx-border-width: 4px; -fx-border-color: #444");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Minesweeper"); 
		stage.show();
		stage.setResizable(false);
		g = canvas.getGraphicsContext2D();
		newGame();  // Start the first game.
		canvas.setOnMousePressed( evt -> doMousePressed(evt) );
	} 

	/**
	 * The main routine of a JavaFX program just launches the application.
	 */
	public static void main(String[] args) {
		launch();
	}

} // end Minesweeper