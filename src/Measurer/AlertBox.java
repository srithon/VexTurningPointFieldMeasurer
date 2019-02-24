package Measurer;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox
{
	public static void display(String title, String message)
	{
		Stage window = new Stage();

		window.setTitle(title);
		window.setMinWidth(575);
		window.setMinHeight(200);
		window.initModality(Modality.APPLICATION_MODAL);
		//blocks user input to other windows until this is dealt with

		Label label = new Label();
		label.setText(message);

		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> window.close());

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait(); //waits for it to be hidden/closed
	}
}
