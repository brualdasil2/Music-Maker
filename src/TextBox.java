import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class TextBox {
	
	private int x, y, width, height;
	private String text = "";
	private boolean focused = false;
	private int currentKey = 0;
	private char[] allowedKeys = new char[64];
	private Color c = Color.lightGray;
	private Font font;
	private Editor editor;
	private boolean leftPressed = false;
	private int maxLength = 23;
	
	public TextBox(Editor editor, int x, int y, int width, int height, Font font) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.editor = editor;
		this.font = font;
		
		int key = 0;
		
		//numbers
		for (int i = 48; i <= 57; i++) {
			
			
			allowedKeys[key] = (char)i;
			key++;
		}
		//upper letters
		for (int i = 65; i <= 90; i++) {
			
			
			allowedKeys[key] = (char)i;
			key++;
		}
		//lower letters
		for (int i = 97; i <= 122; i++) {
			
			
			allowedKeys[key] = (char)i;
			key++;
		}
		
		allowedKeys[key] = ' ';
		key++;
		allowedKeys[key] = '_';
		key++;
		
	}
	
	public void tick() {
		

		if (!editor.getMouseManager().getLeftPressed()){
			
			leftPressed = false;
		}
		
		if (editor.getMouseManager().getMouseX() >= x && editor.getMouseManager().getMouseX() <= x + width) {
			
			if (editor.getMouseManager().getMouseY() >= y && editor.getMouseManager().getMouseY() <= y + height) {
				
				if (!leftPressed) {
					
					if (editor.getMouseManager().getLeftPressed()) {
						
						leftPressed = true;
						focused = true;
						c = Color.white;
					}
				}
			}
			else {
				
				if (editor.getMouseManager().getLeftPressed()) {
					
					leftPressed = true;
					focused = false;
					c = Color.lightGray;
				}
			}
		}
		else {
			
			if (editor.getMouseManager().getLeftPressed()) {
				
				leftPressed = true;
				focused = false;
				c = Color.lightGray;
			}
		}
		
		
		
		
		
		//System.out.println(focused);
		
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(c);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		Text.drawString(g, text, x + width/2, y + height/2, true, Color.black, font);
	}

	public boolean isFocused() {
		
		return focused;
	}
	
	private boolean isAllowed(char letter) {
		
		for (char key : allowedKeys) {
			
			if (letter == key) {
				return true;
			}
		}
		return false;
	}
	
	public void addNewChar(char letter) {
		
		
		if (isAllowed(letter)) {
			if (text.length() < maxLength) {
				text += letter;
			}
		}
		else {
			
			if (letter == '\b') {
				
				if (text.length() > 0) {
					text = text.substring(0, text.length() - 1);
				}
			}
		}
		
		//text[currentKey] = letter;
		//currentKey++;
	}
	
	public String getText() {
		
		return text;
	}
	
}
