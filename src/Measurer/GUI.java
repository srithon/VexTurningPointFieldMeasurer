package Measurer;

import java.io.InputStream;
import java.io.PrintStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application
{
	public static PrintStream stream;
	private TextArea log;
	
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
		primaryStage.setTitle("Vex Turning Point Field Measurer - Sridaran Thoniyil");
		
		//check();
		
		//AnchorPane layout = new AnchorPane();
		VBox layout = new VBox();
		
		double bufferAmount = 0.0;
		
		InputStream imageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Measurer/field.png");
		
		//System.out.println(imageStream == null);
		
		Image x = new Image(imageStream);
		
		TurningPointScene.setImage(x);
		
		AnchorPane.setBottomAnchor(TurningPointScene.image, bufferAmount);
		AnchorPane.setTopAnchor(TurningPointScene.image, bufferAmount);
		AnchorPane.setLeftAnchor(TurningPointScene.image, bufferAmount);
		AnchorPane.setRightAnchor(TurningPointScene.image, bufferAmount);
		
		TurningPointScene.pane2.getChildren().add(TurningPointScene.image);
		
		log = new TextArea();
		GUI.stream = new PrintStream(new Console(log), true);
		
		log.setEditable(false);
		log.setMouseTransparent(true);
		log.setFocusTraversable(false);
		
		TurningPointScene.pane1.getChildren().add(log);
		
		layout.getChildren().addAll(TurningPointScene.pane2, TurningPointScene.pane1);
		
		//check();
		
		Scene tpScene = new TurningPointScene(layout, bufferAmount);
		
		primaryStage.setScene(tpScene);
		primaryStage.show();
		
		AlertBox.display("Vex Turning Point Field Measurer - Help", "Type \"help\" to bring up this window.\n"
				+ "Click on two points and you will be given the distance between them in pixels, feet and inches.\n"
				+ "Right click to reset 1 click at a time.\n"
				+ "Sridaran Thoniyil - 750E/750Z");
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