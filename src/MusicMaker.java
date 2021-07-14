import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MusicMaker {

	public static void main(String[] args) throws IOException {
		

		Editor editor = new Editor("Te amo!", 1280, 720);	
		editor.start(); //Start thread

	/*
		BufferedImage arrow = ImageLauncher.loadImage("/textures/Arrow.png");

		Graphics2D baseSheet = arrow.createGraphics();


		baseSheet.setColor(Color.black);
		baseSheet.fillOval(0,0,5,5); 
		
		ImageIO.write(arrow,"png",new File("NewArrow.png"));
		
		
		System.out.println("Image created!");
	*/
	}

}
