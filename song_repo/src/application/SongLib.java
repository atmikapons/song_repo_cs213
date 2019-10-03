/**
 * authors: Audri Yoon and Atmika Ponnusamy
 */

package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class SongLib extends Application {
	Controller listController;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/song_repo.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		listController = loader.getController();
		listController.start(primaryStage);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Repository");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void stop() throws IOException{
	    System.out.println("Stage is closing");
	    listController.writeToCsv();
	}
}
