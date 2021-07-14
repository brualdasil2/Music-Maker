import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Button {
	
	private int x, y, width, height;
	private Editor editor;
	public static boolean leftPressed;
	private Color c;
	private String name;
	private BufferedImage image;
	private boolean outline;
	private Font font;
	
	public Button(Editor editor, int x, int y, int width, int height, Color c, String name, Font font, BufferedImage image, boolean outline) {
		
		this.editor = editor;
		this.c = c;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.font = font;
		this.image = image;
		this.outline = outline;
		
	}
	
	
	
	public boolean buttonPressed() {
		
	
		if (!editor.getMouseManager().getLeftPressed()){
			
			leftPressed = false;
		}
		
		if (editor.getMouseManager().getMouseX() >= x && editor.getMouseManager().getMouseX() <= x + width) {
			
			if (editor.getMouseManager().getMouseY() >= y && editor.getMouseManager().getMouseY() <= y + height) {
				
				if (!leftPressed) {
					
					if (editor.getMouseManager().getLeftPressed()) {
						
						leftPressed = true;
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	public void drawButton(Graphics g) {
		
		if (c != null) {
			
			g.setColor(c);
			g.fillRect(x, y, width, height);
		}
		
		if (image != null)
			g.drawImage(image, x, y, width, height, null);
		
		if (outline) {
			g.setColor(Color.black);
			g.drawRect(x, y, width, height);
		}
		
		if (name != null)
			Text.drawString(g, name,x + width/2, y + height/2, true, Color.white, font);
	}
	
	public void setColor(Color c) {
		
		this.c = c;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	
	
	
	
}
