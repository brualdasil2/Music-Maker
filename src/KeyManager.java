import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private Editor editor;
	private char[] text = new char[50];
	
	public KeyManager(Editor editor) {
		
		this.editor = editor;
		
	}
	
	public void tick() {
		/*
		pause = keys[KeyEvent.VK_P];
		
		if (playerNumb == 1) {
			
			up = keys[KeyEditState.getp1Up()];
			left = keys[KeyEditState.getp1Left()];
			right = keys[KeyEditState.getp1Right()];
			attack = keys[KeyEditState.getp1Attack()];
			shield = keys[KeyEditState.getp1Shield()];
			jump = keys[KeyEditState.getp1Jump()];
			special = keys[KeyEditState.getp1Special()];
			

		
		}
		
		else if (playerNumb == 2) {
			
			up = keys[KeyEditState.getp2Up()];
			left = keys[KeyEditState.getp2Left()];
			right = keys[KeyEditState.getp2Right()];
			attack = keys[KeyEditState.getp2Attack()];
			shield = keys[KeyEditState.getp2Shield()];
			jump = keys[KeyEditState.getp2Jump()];
			special = keys[KeyEditState.getp2Special()];
		}*/
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		if (editor.getEditState().getFileNameBox().isFocused()) {
			
			editor.getEditState().getFileNameBox().addNewChar(e.getKeyChar());
			System.out.println(e.getKeyChar());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
		
	}
	
	
	
	

}
