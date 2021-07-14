import java.awt.Graphics;

public abstract class State {
	
	//state manager
	public static State currentState = null;
	
	public static void setState(State state) {
		
		currentState = state;
	}
	
	public static State getState() {
		
		return currentState; 
	}
	
	
	
	//class 
	protected Editor editor;
	
	public State(Editor editor) {
		
		this.editor = editor;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	

	

	
}
