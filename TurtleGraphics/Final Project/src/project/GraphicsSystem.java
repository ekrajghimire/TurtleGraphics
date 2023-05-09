package project;

//Import all the essential extensions required to execute the code
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import uk.ac.leedsbeckett.oop.LBUGraphics;

//class GraphicsSystem is created which extends LBUGraphics
public class GraphicsSystem extends LBUGraphics {
	private boolean fill; //Setting the fill status
	public GraphicsSystem() 
	{
		//Declaring all these in order to display the output as a frame
		JFrame MainFrame = new JFrame();       //Creating JFrame object called MainFrame 
		MainFrame.setLayout(new FlowLayout());  
		MainFrame.add(this);
		MainFrame.pack();
		MainFrame.setVisible(true);
		
		//Calling already defined method to make the turtle face down and also make the pendown
		               
		penDown();
		  
	}
	
	//An array is created to store all the command required 
	final String[] array = {"about","penup","pendown","turnleft","turnright","forward","backward","reset","new","black","green","red","white","load","save","help","circle","etriangle","triangle","rectangle","square","random","fill","display"};
	
	// New array list is created for storing the entered command by the user
	public ArrayList<String> list= new ArrayList<String>() ;
	
	//Method in order to save the command entered by the user in a text file
	public void saveCommand(ArrayList arr) 
	{
		//Determining the path for text file to save in
		Path output = Paths.get("C:\\Users\\panth\\eclipse-workspace\\Final Project\\TextCommand.txt");
		
		//Trying to write the command of the user in a text file and if this wont happen catch part will be executed
		try 
		{
			Files.write(output,arr);
		}
		catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "File not saved!!","Save error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Method to save command or to save image
	public void save() 
	{
		String choose[]= {"Save Commands","Save Image"};           //Storing two options in array
		int value = JOptionPane.showOptionDialog(null, "What to save ?\nSave Commands \nSave Image ", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choose, choose[1]);
		if (value==0)     //If the value clicked by user is save command this part will be executed
		{
			saveCommand(list);
			JOptionPane.showMessageDialog(null, "Command saved sucessfully!!","Save Command",JOptionPane.PLAIN_MESSAGE);
		}
		else if (value==1)  //If the value clicked by user is save image this part will be executed
		{
			try 
			{	
				Thread.sleep(120);
				Robot r =new Robot();
				
//				It saves screenshot to desired path
				String path ="C:\\Users\\panth\\eclipse-workspace\\Final Project\\image.png";
				
//				Used to get Screen size and capture image	
				Rectangle capture=new Rectangle(0,0,1015,440);
				
				//Capture the command as image
				BufferedImage Image=r.createScreenCapture(capture);
				ImageIO.write(Image, "png", new File(path));     //Write the image captured in that file
				JOptionPane.showMessageDialog(null, "Image saved sucessfully!!","Save Image",JOptionPane.PLAIN_MESSAGE);
			} 
			catch(AWTException|IOException|InterruptedException ex)           //If the image is not saved in a file then execute catch part for error dialog box
			{
				JOptionPane.showMessageDialog(null, "Current Image not saved!!","ImageSave error",JOptionPane.ERROR_MESSAGE);
			}
		}
		else        //If both the options are not clicked by the user this part will be executed
		{
			JOptionPane.showMessageDialog(null, "No any request passed!!","Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Method to load command or to load image
	public void load()
	{
      String options[]= {"Load commands","Load Image"};            //Storing two options in array
      int store = JOptionPane.showOptionDialog(null,"What to load ? \nCommand File \nImage File","Load", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
      if(store==0)                        //If the value clicked by user is load command this part will be executed
      {
  		File commandFile = new File("C:\\Users\\panth\\eclipse-workspace\\Final Project\\TextCommand.txt");
  		try
  		{
  			Scanner scan = new Scanner (commandFile);             //Created a object called scan
  			while(scan.hasNextLine())
  			{
  				String line =scan.nextLine();                     //Check each and every line
  				processCommand(line);                             //loads the command in screen which was passed by user itself
  				JOptionPane.showMessageDialog(null, "Command loaded sucessfully!!","Load Command",JOptionPane.PLAIN_MESSAGE);
  			}
  			scan.close();
  		}
  		catch (FileNotFoundException e)           //If the command is not loaded in a file then execute catch part for error dialog box
  		{
  			JOptionPane.showMessageDialog(null, "File couldn't load!!","Fileload error",JOptionPane.ERROR_MESSAGE);
  		}
      }
      else if (store==1)                            //If the value clicked by user is load image this part will be executed
      {
     		File imageFile = new File("C:\\Users\\panth\\eclipse-workspace\\Final Project\\image.png");
  		try 
          {
  			JFrame fr =new JFrame();                  //New JFrame object fr is created
  			
  			//Loads the file where the image is saved and displays in screen
              fr.setContentPane(new JLabel(new ImageIcon(ImageIO.read(imageFile))));
              fr.pack();
              fr.setVisible(true);
              JOptionPane.showMessageDialog(null, "Image loaded sucessfully!!","Image Command",JOptionPane.PLAIN_MESSAGE);
          } 
          catch (IOException e)                         //If the image is not loaded in a file then execute catch part for error dialog box
          {
          	JOptionPane.showMessageDialog(null, "Image not loaded!!","ImageLoad error",JOptionPane.ERROR_MESSAGE);
          }
       }
      else                             //If both the options are not clicked by the user this part will be executed 
      {
      	JOptionPane.showMessageDialog(null, "No any request passed!!","Error",JOptionPane.ERROR_MESSAGE);
      }
  }
	

	
	
	@Override
	//Overriding the processCommand method where all the process of the user command are executed and checked
	public void processCommand(String command) 
	{
//		System.out.println("You Typed: "+ command);
		try
		{
			if(command.contains(" "))      //If the entered command contains space in middle the this part will be executed
			{
				String storeCommand[] = command.split(" ");	       //storeCommand stores the splitted command in two different index of the list

				//If the forward command is passed with parameter this will execute
				if(storeCommand[0].equals("forward")==true && Integer.parseInt(storeCommand[1])>0 )    
				{
					forward(Integer.parseInt(storeCommand[1]));
					list.add(command);          //add the command is saved in text file
				}
				
				//If the turn left command is passed with parameter this will execute
				else if(storeCommand[0].equals("turnleft")==true && Integer.parseInt(storeCommand[1])>0 ) 
				{
					turnLeft(Integer.parseInt(storeCommand[1]));
					list.add(command);          //add the command is saved in text file
				}
				
				//If the turn right command is passed with parameter this will execute
		 		else if(storeCommand[0].equals("turnright")==true && Integer.parseInt(storeCommand[1])>0) 
		 		{
		 			turnRight(Integer.parseInt(storeCommand[1]));
		 			list.add(command);	        //add the command is saved in text file
		 		} 
				
				//If the backward command is passed with parameter this will execute
		 		else if(storeCommand[0].equals("backward")==true && Integer.parseInt(storeCommand[1])>0) 
		 		{
		 			forward(-(Integer.parseInt(storeCommand[1])));
		 			list.add(command);          //add the command is saved in text file
		 		}
				
				//If the circle command is passed with parameter this will execute
		 		else if(storeCommand[0].equals("circle")==true && Integer.parseInt(storeCommand[1])>0) 
		 		{
		 			circle(Integer.parseInt(storeCommand[1]));
		 		}
				
				//If the triangle command is passed with parameters this will execute
		 		else if(storeCommand[0].equals("triangle")==true && storeCommand.length==4 && Integer.parseInt(storeCommand[1])>0 && Integer.parseInt(storeCommand[2])>0 && Integer.parseInt(storeCommand[3])>0) 
		 		{
		 			triangle(Integer.parseInt(storeCommand[1]), Integer.parseInt(storeCommand[2]), Integer.parseInt(storeCommand[3]));
		 		}
				
				//If the triangle command is passed with only one parameter this will execute
		 		else if(storeCommand[0].equals("triangle")==true && storeCommand.length==2 && Integer.parseInt(storeCommand[1])>0) 
		 		{
		 			triangle(Integer.parseInt(storeCommand[1]));
		 		}
				
				/*Code and conditions for Requirements 6
				 * For extra 20 marks
				*/
				
				//If the rectangle command is passed with parameters this will execute
		 		else if(storeCommand[0].equals("rectangle")==true && Integer.parseInt(storeCommand[1])>0 && Integer.parseInt(storeCommand[2])>0)
		 		{
		 			rectangle(Integer.parseInt(storeCommand[1]), Integer.parseInt(storeCommand[2]));
		 		}
				
				//If the square command is passed with parameter this will execute
		 		else if(storeCommand[0].equals("square")==true && Integer.parseInt(storeCommand[1])>0)
		 		{
		 			square(Integer.parseInt(storeCommand[1]));
		 		}
				
				//If the forward command is passed with parameters this will execute
		 		else if(storeCommand[0].equals("pencolour")==true && Integer.parseInt(storeCommand[1])>0 && Integer.parseInt(storeCommand[2])>0 && Integer.parseInt(storeCommand[3])>0) 
		 		{
		 			if(Integer.parseInt(storeCommand[1])>255 || Integer.parseInt(storeCommand[2])>255 || Integer.parseInt(storeCommand[3])>255)
		 				JOptionPane.showMessageDialog(null, "Color value can only be in the range of 0-255!!","Error",JOptionPane.ERROR_MESSAGE);
		 			else
		 				setPenColour(new Color(Integer.parseInt(storeCommand[1]), Integer.parseInt(storeCommand[2]), Integer.parseInt(storeCommand[3])));
		 		}
				
				
				
				//If the passed parameter is less that 0 this part will be executed
		 		else if (Integer.parseInt(storeCommand[1])<0)
		 		{
		 			JOptionPane.showMessageDialog(null, "Parameter Not Bounded!!","Error",JOptionPane.ERROR_MESSAGE);
		 		}
				
				//If the command passed is not in the array list this part will execute
		 		else if(storeCommand[0].equals(array)==false) 
		 		{
		 			JOptionPane.showMessageDialog(null, "Not a Valid Command!!","Error",JOptionPane.ERROR_MESSAGE);
		 		}		
			}
			else         //This part will execute for the command which don't need parameters
			{
				if(command.equals("about")==true)         //If user passes about command this part will execute
				{
					about();
					list.add(command);                    //add the command is saved in text file
				}
				else if(command.equals("penup")==true)    //If user passes penup command this part will execute
				{
					penUp();
					list.add(command);                    //add the command is saved in text file
				}
				else if(command.equals("pendown")==true)  //If user passes pendown command this part will execute
				{
		 			penDown();
		 			list.add(command);                    //add the command is saved in text file
		 		}
				else if(command.equals("reset")==true)    //If user passes reset command this part will execute
				{
		 			reset();
		 			penDown();
		 			list.add(command);                    //add the command is saved in text file
		 		}
		 		else if(command.equals("clear")==true)      //If user passes new command this part will execute
		 		{
		 			clear();
		 			list.add(command);                    //add the command is saved in text file
		 		}
		 		else if(command.equals("black")==true)    //If user passes black command this part will execute
		 		{
		 			setPenColour(Color.black);
		 			list.add(command);                    //add the command is saved in text file
		 		}
		 		else if(command.equals("red")==true)      //If user passes red command this part will execute
		 		{
		 			setPenColour(Color.red);
		 			list.add(command);                    //add the command is saved in text file
		 		}
		 		else if(command.equals("green")==true)    //If user passes green command this part will execute
		 		{
		 			setPenColour(Color.green);
		 			list.add(command);                    //add the command is saved in text file
		 		}
		 		else if(command.equals("white")==true)    //If user passes white command this part will execute
		 		{
		 			setPenColour(Color.white);
		 			list.add(command);                    //add the command is saved in text file
		 		}
		 		else if(command.equals("help")==true)     //If user passes help command this part will execute
		 		{
		 			Help();
		 		}
		 		else if(command.equals("display")==true)     //If user passes display command this part will execute
		 		{
		 			Display();
		 		}
		 		else if(command.equals("random")==true)   //If user passes random command this part will execute
		 		{
		 			randomColor();
		 		}
		 		else if(command.equals("fill")==true)     //If user passes fill command this part will execute
		 		{
		 			fill = !fill;
		 		}
		 		else if(command.equals("load")==true)     //If user passes load command this part will execute
		 		{
		 			load();
		 		}
		 		else if(command.equals("save")==true)     //If user passes save command this part will execute
		 		{
		 			save();
		 		}
				
				//If user passes forward with no parameters this part will execute which will display error dialog box
		 		else if(command.equals("forward")==true)  
		 		{
		 			JOptionPane.showMessageDialog(null, "No Parameter Passed!!","Error",JOptionPane.ERROR_MESSAGE);
		 		}
				
				//If user passes turn left with no parameters this part will execute which will display error dialog box
		 		else if(command.equals("turnleft")==true) 
		 		{
		 			JOptionPane.showMessageDialog(null, "No Parameter Passed!!","Error",JOptionPane.ERROR_MESSAGE);
		 		}
				
				//If user passes turn right with no parameters this part will execute which will display error dialog box
		 		else if(command.equals("turnright")==true) 
		 		{
		 			JOptionPane.showMessageDialog(null, "No Parameter Passed!!","Error",JOptionPane.ERROR_MESSAGE);
		 		}
				
				//If user passes backward with no parameters this part will execute which will display error dialog box
		 		else if(command.equals("backward")==true) 
		 		{
		 			JOptionPane.showMessageDialog(null, "No Parameter Passed!!","Error",JOptionPane.ERROR_MESSAGE);
		 		}
				
				//If user passes pen with no parameters this part will execute which will display error dialog box
		 		else if(command.equals("pencolour")==true) 
		 		{
		 			JOptionPane.showMessageDialog(null, "No Parameter Passed!!","Error",JOptionPane.ERROR_MESSAGE);
		 		}
				
				//If user passes triangle with no parameters this part will execute which will display error dialog box
		 		else if(command.equals("triangle")==true) 
		 		{
		 			JOptionPane.showMessageDialog(null, "No Parameter Passed!!","Error",JOptionPane.ERROR_MESSAGE);
		 		}
				
				//If user passes circle with no parameters this part will execute which will display error dialog box
		 		else if(command.equals("circle")==true) 
		 		{
		 			JOptionPane.showMessageDialog(null, "No Parameter Passed!!","Error",JOptionPane.ERROR_MESSAGE);
		 		}
				
				//If the command passed is not included in array command not found error dialog box will be displayed
		 		else if(command.equals(array)==false) 
		 		{
		 			JOptionPane.showMessageDialog(null, command+" not on the commands list above","Error",JOptionPane.ERROR_MESSAGE);
		 		}
			}
		}
		catch(Exception e)     //If all this does not matches the criteria this part will execute
		{
			JOptionPane.showMessageDialog(null, "Non-Numeric Value passed!!","Error",JOptionPane.ERROR_MESSAGE);
		}				
	}
	

	
	/*Triangle is drawn in the screen below the turtle
	 * Parameter passed is of three sides of the triangle which can be of variable length
	 */
	private void triangle(int sideA, int sideB, int sideC)
	{
		Graphics canvas = getGraphicsContext();       //Creating a Graphics object called canvas
		canvas.setColor(Color.PINK);                  //sets the color to pink
		
		//Creating triangle on the basis of the coordinates passed
		int[] xPoints = {sideA, sideB, sideC};
		int[] yPoints = {sideB, sideC, sideA};
		
		if (fill)      //if fill is passed by the user and triangle is called, filled triangle will be displayed
		{
			canvas.fillPolygon(xPoints, yPoints, 3);
		}
		else          //if fill is not passed then normal triangle is drawn without the filled color 
			canvas.drawPolygon(xPoints, yPoints, 3);
	}
	
	/*Equilateral Triangle is drawn in the screen below the turtle
	 * Parameter passed is one side of triangle which is equal to all side
	 */
	private void triangle(int oneside)
	{
		//Finding out the value of new coordinates of triangle
		int v = oneside/2;
		int w = (int) Math.round(Math.sqrt(Math.pow(oneside, 2)-Math.pow(v, 2)));
		
		Graphics canvas = getGraphicsContext();     //Creating a Graphics object called canvas
		canvas.setColor(Color.PINK);                //sets the color to orange
		
		//Creating triangle on the basis of new coordinates passed
		int[] xPoints = { xPos, xPos+v, xPos-v};
		int[] yPoints = { yPos, yPos+w, yPos+w};
		
		if (fill)        //if fill is passed by the user and triangle is called, filled equilateral triangle will be displayed
		{
			canvas.fillPolygon(xPoints, yPoints, 3);
		}
		else            //if fill is not passed then normal equilateral triangle is drawn without the filled color 
			canvas.drawPolygon(xPoints, yPoints, 3);
	}
	


	@Override
	//Overriding about method which appends author name along with the turtle dance
	public void about()
	{
		//Inheriting about method from LBUGraphics
		super.about(); 
		
		//Append the name passed
		getGraphicsContext().drawString("Pooja Pantha", 250,320);
	}
	
	@Override
	//Overriding the circle method which has just been defined in LBUGraphics and modifying the method
	public void circle(int radius)
	{
		Graphics canvas = getGraphicsContext();   //Creating a Graphics object called canvas
		canvas.setColor(Color.red);               //sets the color to red
		
		if (fill)         //if fill is passed by the user and circle is called, filled circle will be displayed
		{
			canvas.fillOval(xPos, yPos ,radius*2, radius*2 );
		}
		else             //if fill is not passed then normal circle is drawn without the filled color
			canvas.drawOval(xPos, yPos ,radius*2, radius*2 );
	}
	
	
	/*
	 *    REQURINMENT 6 
	 */
	//Display Method displays the images that is stored in a folder which is not assigned anywhere 
	public void Display()
	{
		File imageFile = new File("C:\\Users\\panth\\eclipse-workspace\\Final Project\\Happy.jpg");
		try 
      {
			JFrame screen =new JFrame();                  //New JFrame object fr is created
			
			//Loads the file where the image is saved and displays in screen
          screen.setContentPane(new JLabel(new ImageIcon(ImageIO.read(imageFile))));
          screen.pack();
          screen.setVisible(true);
          JOptionPane.showMessageDialog(null, "Image loaded sucessfully!!","Image Command",JOptionPane.PLAIN_MESSAGE);
      } 
      catch (IOException e)                         //If the image is not loaded in a file then execute catch part for error dialog box
      {
      	JOptionPane.showMessageDialog(null, "Image not loaded!!","ImageLoad error",JOptionPane.ERROR_MESSAGE);
      }
	}
	
	/*
	 *    REQURINMENT 6 
	 */
	//Creating extra method randomColor as of the requrinment 6
	private void randomColor()    //Its sets the pen color to a random color as soon as this function is called
	{
		Random rgb = new Random();    // Creating a Random object called rgb
		setPenColour(new Color(rgb.nextInt(256),rgb.nextInt(256),rgb.nextInt(256)));	 		
	}
	
	/*
	 *    REQURINMENT 6 
	 */
	//Creating extra method Help as of the requirement 6
	private void Help()    //Its displays the help detail of this whole program
	{
		//All detail stored in string
		String detail = "about: Display the turtle dance moving round and the name of the author\n"+
						"penup: Lifts the pen from the canvas, so that movement does not get shown\n"+
						"pendown: Places the pen down on the canvas so movement gets shown as a drawn line\n"+
						"black: Make the pen color black\n"+
						"green: Makes the pen color green\n"+
						"red: Makes the pen color red\n"+
						"white: Makes the pen color white\n"+
						"clear: Clears the whole output screen\n"+
						"reset: Resets the canvas to its initial state with turtle pointing down but does not clear the display\n"+
						"save: It provides options to save command or to save image\n"+
						"load: It provides options to load command or to load image\n"+
						"help: Display this menu!\n(REQ-6)"+
						"pencolour: It takes three diferent color value to make RGB color\n"+
						"random: sets the color of the pen to random color(REQ-6)"+
						"display: It displays the images which is downloaded in your folder(REQ-6)"+
						"\n"+
						"DRAWINGS"+
						"\n"+
						"circle SIDE: It draws the circle of the radius user enters\n"+
						"rectangle BREADTH AND HEIGHT: Draws a rectangle\n(REQ-6)"+
						"square SIDE: Draws a square with the same length of all sides\n(REQ-6)"+
						"triangle 1POINT: Equilateral triangle is drawn\n"+
						"triangle 3POINTS:Three parameter is passed it draws normal traingle\n"+
						"\n"+
						"LINES BY PASSING PARAMETERS"+
						"\n"+
						"forward UNITS: Forwards the turtle by the units passed\n"+
						"backward UNITS: Trutle will move backwards by the units passed\n"+
						"turnleft DEGREES: Turn the turtle to left by degrees passed\n"+
						"turnright DEGREES: Turn the turtle to right by degree passed\n";
		
		//Displays all the detail of help as a dialog box
		JOptionPane.showMessageDialog(null, detail);     
	}
	
	/*
	 *    REQURINMENT 6 
	 */
	/*Rectangle is drawn in the screen below the turtle
	 * Parameter passed is Breadth and Height to draw rectangle
	 */
	private void rectangle(int breadth, int height)
	{
		Graphics canvas = getGraphicsContext();      //Creating a Graphics object called canvas
		canvas.setColor(Color.yellow);               //sets the color to yellow
		
		if (fill)           //if fill is passed by the user and rectangle is called, filled rectangle will be displayed
		{
			canvas.fillRect(xPos - breadth/2, yPos, breadth, height);
		}
		else                //if fill is not passed then normal rectangle is drawn without the filled color
			canvas.drawRect(xPos - breadth/2, yPos, breadth,height);
	}
	
	/*Square is drawn in the screen below the turtle
	 * Parameter passed is Length of the sides
	 */
	private void square(int side) 
	{
		Graphics canvas = getGraphicsContext();    //Creating a Graphics object called canvas
		canvas.setColor(Color.blue);               //sets the color to blue
		
		if (fill)        //if fill is passed by the user and square is called, filled square will be displayed
		{
			canvas.fillRect(xPos - side / 2, yPos, side, side);
		}
		else            //if fill is not passed then normal square is drawn without the filled color
			canvas.drawRect(xPos - side / 2, yPos, side, side);
	}
	
	//Main Method which starts the program execution
	public static void main(String[] args)
	{
		new GraphicsSystem(); //Creating instance of a class that extends LBUGraphics
	}
}

