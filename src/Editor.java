import java.awt.*;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.awt.color.*;

public class Editor implements Runnable {
	
	private Display display;
	private int height, width;
	private String title;
	private boolean running = false;
	
	private boolean slow = false;
	
	private Thread gameThread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private State editState;
	
	private KeyManager keyManager;
	//private KeyManager keyManager2;
	
	private MouseManager mouseManager;
	
	
	public Editor (String title, int width, int height) {
		
		this.height = height;
		this.width = width;
		this.title = title;
				
		keyManager = new KeyManager(this);
		//keyManager2 = new KeyManager(2);
		
		mouseManager = new MouseManager();
	
		

	}
	
	
	
	private void init() {
		
		display = new Display(title, width, height);
		
		display.getFrame().addKeyListener(keyManager);
		//display.getFrame().addKeyListener(keyManager2);
		
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		Assets.init();

		
	
		editState = new EditState(this);
		
		State.setState(editState);
		((EditState)(editState)).init();
		
		
		
	}
	
	//Update variables
	private void tick() {
		
		keyManager.tick();
		//keyManager2.tick();
		if (State.getState() != null)
			State.getState().tick();
		
		
		
	}
	
	//Print on screen
	private void render() {
		
		//sets buffers
		
		bs = display.getCanvas().getBufferStrategy();

		if (bs == null) {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    GraphicsDevice gs = ge.getDefaultScreenDevice();
		    GraphicsConfiguration gc = gs.getDefaultConfiguration();
		    
		    
		    display.getCanvas().createBufferStrategy(2);
		    
			return;
		}
		
		
		
		g = bs.getDrawGraphics();
		
		
		//clears screen
		
		
		
		//Start Draw
		
		
		if (State.getState() != null)
			State.getState().render(g);
		
		
		
		//End Draw
		
		g.dispose();
		bs.show();
		
		//Toolkit.getDefaultToolkit().sync();
		
	}
	
	public void run() {
		
		init();
		
		int tickFPS = 60;
		int renderFPS = 60;
		double timePerTick = 1000000000 / tickFPS;
		double timePerRender = 1000000000/renderFPS;
		long now;
		long lastTick = System.nanoTime();
		long lastRender = System.nanoTime();
		
		boolean skipTick = false;
		boolean skipRender = false;
		
		
		while(running) {
			
			try {
				now = System.nanoTime();
				
				if (now - lastTick >= timePerTick) {
					
					tick();
					lastTick = now;
				}
				
				if (now - lastRender >= timePerRender) {
					
					render();
					lastRender = now;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		stop();
	}
	
	public KeyManager getKeyManager() {
		
		
		return keyManager;
		
	}
	
	public MouseManager getMouseManager() {
		
		return mouseManager;
	}
	
	public Display getDisplay() {
		
		return display;
	}
	
	public EditState getEditState() {
		
		return (EditState)editState;
	}

	
	//Starts thread
	public synchronized void start() {
		if (running)
			return;
		
		running = true;
		gameThread = new Thread(this);
		gameThread.start(); //Goes to "run()"
	}
	
	//Ends thread
	public synchronized void stop() {
		if (!running)
			return;
		
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	

}
