// IMPORTS
// These are some classes that may be useful for completing the project.
// You may have to add others.
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The main class for Week8Program. Week8Program constructs the JavaFX window and
 * handles interactions with the dynamic components contained therein.
 */
public class Week8Program extends Application {
	// INSTANCE VARIABLES
	// These variables are included to get you started.
	private Stage stage = null;
	private BrowserView borderPane = null;

	// HELPER METHODS
	/**
	 * Retrieves the value of a command line argument specified by the index.
	 *
	 * @param index - position of the argument in the args list.
	 * @return The value of the command line argument.
	 */
	private String getParameter( int index ) {
		Parameters params = getParameters();
		List<String> parameters = params.getRaw();
		return !parameters.isEmpty() ? parameters.get(index) : "";
	}
	
	public Stage getStage() {
		return stage;
	}

	// REQUIRED METHODS
	/**
	 * The main entry point for all JavaFX applications. The start method is
	 * called after the init method has returned, and after the system is ready
	 * for the application to begin running.
	 *
	 * NOTE: This method is called on the JavaFX Application Thread.
	 *
	 * @param primaryStage - the primary stage for this application, onto which
	 * the application scene can be set.
	 */
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		
		borderPane = new BrowserView(this);
		Scene scene = new Scene(borderPane, 1000, 800);
		
		borderPane.titleProperty().addListener((observe, oldValue, newValue) -> stage.setTitle("Neil Browser - " + newValue));
		
		stage.setMinWidth(500);
		stage.setMinHeight(500);
		
		stage.setScene(scene);
		stage.show();
	}
	
	private void setTitle() {
		
	}

	/**
	 * The main( ) method is ignored in JavaFX applications.
	 * main( ) serves only as fallback in case the application is launched
	 * as a regular Java application, e.g., in IDEs with limited FX
	 * support.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
