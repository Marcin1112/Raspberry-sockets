package socket.client;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Main class
 * 
 * @author Marcin
 *
 */
public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;

	/**
	 * main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Override method
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Pomiar");
		initRootLayout();
		runMaximize(primaryStage);
	}

	/**
	 * Maximize main window
	 * 
	 * @param primaryStage
	 *            main stage
	 */
	private void runMaximize(Stage primaryStage) {
		// Get current screen of the stage
		ObservableList<Screen> screens = Screen.getScreensForRectangle(new Rectangle2D(primaryStage.getX(),
				primaryStage.getY(), primaryStage.getWidth(), primaryStage.getHeight()));

		// Change stage properties
		Rectangle2D bounds = screens.get(0).getVisualBounds();
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
	}

	/**
	 * initialize view and controller
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/socket/client/view/MainView.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
