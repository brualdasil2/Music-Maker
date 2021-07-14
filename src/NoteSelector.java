import java.awt.Color;
import java.awt.Graphics;

public class NoteSelector {
	
	int x1, y1, x2, y2, temp;
	public static boolean leftPressed = false;
	private Editor editor;

	
	public NoteSelector(Editor editor) {
		
		this.editor = editor;
	}
	
	public void tick() {

		
		if (!leftPressed) {
			
			if (editor.getMouseManager().getLeftPressed()) {
				
				leftPressed = true;
				x1 = editor.getMouseManager().getMouseX();
				y1 = editor.getMouseManager().getMouseY();
				x2 = x1;
				y2 = y1;
			}
		}
		else {
			
			x2 = editor.getMouseManager().getMouseX();
			y2 = editor.getMouseManager().getMouseY();
			
			if(!editor.getMouseManager().getLeftPressed()) {
				
				leftPressed = false;
				editor.getEditState().copyNotesBetween(x1, y1, x2, y2);
			}
		}
		
		if (x1 > x2) {
			temp = x1;
			x1 = x2;
			x2 = temp;
		}
		if (y1 > y2) {
			temp = y1;
			y1 = y2;
			y2 = temp;
		}
	}
	
	public void render(Graphics g) {
		
		if (leftPressed) {
			
			g.setColor(new Color(0, 0, 200, 100));
			g.fillRect(x1, y1, x2 - x1, y2 - y1);

		}
	}
	
	public boolean getLeftPressed() {
		
		return leftPressed;
	}
	public void setLeftPressed(boolean pressed) {
		this.leftPressed = pressed;
	}
}
