import java.awt.Color;
import java.awt.Graphics;

public class Note {
	
	private int line, column, radius = 10;
	private Editor editor;
	private int[] lines;
	private int[] columns;
	private boolean onSheet = true;
	private boolean placed = false;
	private boolean leftPressed;
	
	public Note(Editor editor, int[] lines, int[] columns) {
		
		this.editor = editor;
		this.lines = lines;
		this.columns = columns;
	}
	
	private void setPosition() {
		
		onSheet = false;
		for (int i = 0; i < 15; i++) {
			
			if (columns[column] >= radius) {
				if (Math.abs(editor.getMouseManager().getMouseY() - lines[i]) <= 10) {
					
					line = i;
					onSheet = true;
					break;
				}
			}
		}
		
		for (int i = 0; i < EditState.PAG1_COL + EditState.PAG_COL*4; i++) {
			
			if (Math.abs(editor.getMouseManager().getMouseX() - editor.getEditState().leftWall - columns[i] - editor.getEditState().getCameraOffset()*30) <= 15) {
				
				column = i;
				break;
			}
			
		}
		
		//x = editor.getMouseManager().getMouseX() - editor.getEditState().leftWall;
	}
	
	public boolean mouseOnNote() {
		
		if (Math.pow(editor.getMouseManager().getMouseX() - editor.getEditState().leftWall - columns[column] - editor.getEditState().getCameraOffset()*30, 2) + Math.pow(editor.getMouseManager().getMouseY() - lines[line], 2) <= Math.pow(radius,  2)) {
			
			return true;
		}
		
		return false;
	}
	
	public boolean noteNearNote(Note note) {
		
		if (line == note.getLine() && Math.abs(column - note.getColumn()) < 4) {
			
			return true;
		}
		
		return false;
	}
	
	private boolean clicked() {
		
		if (!editor.getMouseManager().getLeftPressed()){
			
			leftPressed = false;
		}
		
		if (mouseOnNote()) {
			
			if (!leftPressed) {
				
				if (editor.getMouseManager().getLeftPressed()) {
					
					leftPressed = true;
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	public void tick() {
		
		if (!placed) {
			
			setPosition();
		}
		
		if (onSheet) {
			
			if (clicked()) {
					
				if (!placed) {
				
						if (!editor.getEditState().noteNearAnyOtherNote(this)) {
							placed = true;
							editor.getEditState().createNote();
							//System.out.println(column);
						}
				}
				else {
					
					editor.getEditState().removeNote(this);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		
		if (onSheet) {
			
			if (!placed) {
				
				if (!editor.getEditState().noteNearAnyOtherNote(this)) {
					
					g.setColor(new Color(0, 0, 0, 100));
					g.fillOval(columns[column] - radius + editor.getEditState().leftWall + editor.getEditState().getCameraOffset()*30, lines[line] - radius, 2*radius, 2*radius);
				}
				
			}
			else {
				
				if (columns[column] + editor.getEditState().getCameraOffset()*30 > 0) {
				
					if (mouseOnNote()) {
						
						g.setColor(Color.black);
					}
					else {
						g.setColor(Color.darkGray);
					}
					
					g.fillOval(columns[column] - radius + editor.getEditState().leftWall + editor.getEditState().getCameraOffset()*30, lines[line] - radius, 2*radius, 2*radius);
				}
			}
			
			
		}
	}
	
	
	public int getLine() {
		
		return line;
	}
	
	public int getColumn() {
		
		return column;
	}
	
	public boolean isPlaced() {
		
		return placed;
	}
	
	public boolean isOnSheet() {
		
		return onSheet;
	}
	
	public boolean getLeftPressed() {
		
		return leftPressed;
	}
	
	public void setLine(int line) {
		
		this.line = line;
	}
	
	public void setColumn(int column) {
		
		this.column = column;
	}
	
	public void place() {
		
		placed = true;
		onSheet = true;
	}
	
}
