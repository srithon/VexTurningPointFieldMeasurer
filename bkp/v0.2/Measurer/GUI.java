package Measurer;

import java.io.InputStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GUI extends Application
{
	public static void main(String[] args)
	{
		//System.out.println("file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator + "field.png");
		launch(args);
	}
	
	public GUI()
	{
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Vex Turning Point Field Measurer - 750E");
		
		//check();
		
		AnchorPane layout = new AnchorPane();
		
		double bufferAmount = 0.0;
		
		InputStream imageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Measurer/field.png");
		
		//System.out.println(imageStream == null);
		
		Image x = new Image(imageStream);
		
		TurningPointScene.setImage(x);
		
		AnchorPane.setBottomAnchor(TurningPointScene.image, bufferAmount);
		AnchorPane.setTopAnchor(TurningPointScene.image, bufferAmount);
		AnchorPane.setLeftAnchor(TurningPointScene.image, bufferAmount);
		AnchorPane.setRightAnchor(TurningPointScene.image, bufferAmount);
		
		layout.getChildren().add(TurningPointScene.image);
		
		//check();
		
		Scene tpScene = new TurningPointScene(layout, bufferAmount);
		
		primaryStage.setScene(tpScene);
		primaryStage.show();
		
		AlertBox.display("Vex Turning Point Field Measurer - Help", "Type \"help\" to bring up this window.\n"
				+ "Click on two points and you will be given the distance between them in pixels, feet and inches.\n"
				+ "Right click to reset 1 click at a time.");
	}
	
	public static void check()
	{
		System.out.println("\nWORKING\n");
	}
}

/*

---------
-> x++
|
y++

*/