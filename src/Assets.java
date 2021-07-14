import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;

public class Assets {

	public static BufferedImage clave;
	public static Font font30, font25, font20, font17, font15, font13, font10;
	
	
	
	
	public static void init() {
		
		clave = ImageLauncher.loadImage("/textures/Clave.png");
		//musicLogo = ImageLauncher.loadImage("/textures/musicLogo.png");
		
	

	
		
		font30 = new Font("Times new roman", Font.PLAIN, 30);
		font25 = new Font("Times new roman", Font.PLAIN, 25);
		font20 = new Font("Times new roman", Font.PLAIN, 20);
		font17 = new Font("Times new roman", Font.PLAIN, 17);
		font15 = new Font("Times new roman", Font.PLAIN, 15);
		font13 = new Font("Times new roman", Font.PLAIN, 13);
		font10 = new Font("Times new roman", Font.PLAIN, 10);
		

		
	}
	
	

}
