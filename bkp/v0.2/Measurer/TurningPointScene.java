package Measurer;

import java.io.InputStream;

import com.sun.xml.internal.ws.api.ResourceLoader;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class TurningPointScene extends Scene
{
	private static Image field;//new Image(System.getProperty("user.dir") + File.pathSeparator + "src" + File.pathSeparator + "field.png");//getImage();//new Image(getFieldImagePath());
	public static ImageView image;// = new ImageView(field);;
	
	private static double imageXDim;// = field.getWidth();
	
	private static final double realFieldLengthInches = 144;//144;//140.500; //wall to wall
	
	private double[][] eventsXY = new double[2][2];
	private int totalEvents = 0;
	
	private double bufferAmount;
	
	private Parent layout;
	
	private String helpStr = "";
	
	public TurningPointScene(Parent layout, double bufferAmount) {
		super(layout, imageXDim + (2 * bufferAmount), imageXDim + (2 * bufferAmount));
		
		//System.out.println(convertToFeet(new Point2D(0, 0).distance(new Point2D(getHeight(), getWidth()))));
		
		this.layout = layout;
		this.bufferAmount = bufferAmount;
		
		setOnMousePressed(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	if (event.getButton().equals(MouseButton.PRIMARY))
	        	{
	        		//System.out.println("Primary click detected! " + "\nx - " + event.getX() + "\ny - " + event.getY() + "\n" /*+event.getSource()*/);
	        		
	        		if (totalEvents == 2)
	        		{
	        			eventsXY[1] = new double[] { event.getX(), event.getY() };//normalize(new double[] { event.getX(), event.getY() }, event.getSource().equals(image));
	        		}
	        		else
	        		{
	        			eventsXY[totalEvents++] = new double[] { event.getX(), event.getY() };//normalize(new double[] { event.getX(), event.getY() }, event.getSource().equals(image));
	        		}
	        	}
	        	else if (event.getButton().equals(MouseButton.SECONDARY))
	        	{
	        		//System.out.println("Secondary click detected! " + "\nx - " + event.getX() + "\ny - " + event.getY() + "\n" /*+event.getSource()*/);
	        		
	        		if (totalEvents > 0)
	        		{
	        			eventsXY[--totalEvents] = new double[] { -Double.MAX_VALUE, -Double.MAX_VALUE };
	        		}
	        		else
	        		{
	        			System.out.println("NO EVENTS!");
	        			return;
	        		}
	        	}
	        	else
	        	{
	        		System.out.println("Tertiary click detected! " + "\nx - " + event.getX() + "\ny - " + event.getY() + "\n" /*+event.getSource()*/);
	        	}
	        	
	        	System.out.println(totalEvents + " click(s).");
	        	
	        	update();
	        }});
		setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event)
			{
				helpStr += event.getCharacter();
				
				if (helpStr.length() > 3 && helpStr.substring(helpStr.length() - 4).equalsIgnoreCase("help"))
				{
					AlertBox.display("Vex Turning Point Field Measurer - Help", "Type \"help\" to bring up this window.\n"
							+ "Click on two points and you will be given the distance between them in pixels, feet and inches.\n"
							+ "Right click to reset 1 click at a time.\n"
							+ "Made by 750E.");
					
					helpStr = "";
				}
			}
		});
	}
	
	private double[] normalize(double[] e, boolean onImage)
	{
		if (!onImage)
		{
			Boolean x, y;
			
			if (e[0] > getWidth() - 30)
			{
				x = true;
			}
			else if (e[0] < bufferAmount)
			{
				x = false;
			}
			else
			{
				x = null;
			}
			
			if (e[1] > getHeight() - 30)
			{
				y = true; //large
			}
			else if (e[1] < bufferAmount)
			{
				y = false; //small
			}
			else
			{
				y = null; //good?
			}
			
			
			if (x == null)
			{
				if (y != null && y.equals(true))
				{
					return new double[] { getWidth() - bufferAmount, getHeight() - bufferAmount };
				}
				else if (y != null && y.equals(false))
				{
					return new double[] { getWidth() - bufferAmount, bufferAmount };
				}
				else
				{
					return new double[] { getWidth() - bufferAmount, e[1] };
				}
			}
			else if (x == false)
			{
				if (y != null && y.equals(true))
				{
					return new double[] { bufferAmount, getHeight() - bufferAmount };
				}
				else if (y != null && y.equals(false))
				{
					return new double[] { bufferAmount, bufferAmount };
				}
				else
				{
					return new double[] { bufferAmount, e[1] };
				}
			}
			else
			{
				System.out.println("MESSED UP!");
				return new double[] { -Double.MAX_VALUE, -Double.MAX_VALUE };
			}
		}
		
		return e;
	}
	
	private void update()
	{
		if (totalEvents == 2)
		{
			Point2D dot = new Point2D(eventsXY[totalEvents - 1][0], eventsXY[totalEvents - 1][1]);
			
			double distance = dot.distance(new Point2D(eventsXY[0][0], eventsXY[0][1]));
			
			System.out.println("Distance in px : " + distance);
			System.out.println("Distance in inches : " + convertToInches(distance));
			System.out.println("Distance in feet : " + convertToFeet(distance));
			System.out.println();
		}
	}
	
	private double getInchesPerPixel() {
		/*
		 * imageXDim = 600 px
		 * 140.500 inches long
		 * 600 pixels long
		 * 
		 * return 140.500 / 600
		 * 
		 */
		
		//System.out.println("Inches per pixel : " + realFieldLengthInches / imageXDim);
		
		return realFieldLengthInches / imageXDim;
	}
	
	private double convertToInches(double pixels) {
		return pixels * getInchesPerPixel();
	}
	
	private double convertToFeet(double pixels)
	{
		return convertToInches(pixels) / 12;
	}
	
	private String getFieldImagePath()
	{
		String userDir = System.getProperty("user.dir");
		
		InputStream in = ResourceLoader.class.getResourceAsStream("field.png");
		
		Image t = new Image(in);
		
		return "";
	}
	
	public static void setImage(Image i)
	{
		field = i;
		image = new ImageView(field);
		imageXDim = field.getWidth();
	}
}