import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;


public class EditState extends State{

	public static int PAG1_COL = 138, PAG_COL = 122;
	
	private int[] lines = new int[15];
	private int[] columns = new int [PAG1_COL + 4*PAG_COL];
	private boolean[] markers = new boolean [PAG1_COL + 4*PAG_COL + 2];
	private static ArrayList<Note> notes = new ArrayList<Note>();
	private int[][] copiedNotes = new int[100][100];
	public int leftWall = 100;
	private int cameraOffset = 0;
	private Button pasteButton, smallLeftButton, smallRightButton, bigLeftButton, bigRightButton, maxLeftButton, maxRightButton, playButton, speedUpButton, speedDownButton, markerButton, cleanNotesButton, cleanMarkersButton, savePictureMenuButton, savePictureButton, loadProjectMenuButton, loadProjectButton, saveProjectMenuButton, saveProjectButton, returnButton, showLinesButton, showColumnsButton;
	private TextBox fileNameBox;
	private boolean playing = false;
	private int playCounter = 0, prepareCounter = 0;;
	private int speed = 0;
	private PictureGenerator pictureGenerator;
	private ProjectSaver projectSaver;
	private boolean savingPicture = false;
	private boolean savingProject = false;
	private boolean loadingProject = false;
	private boolean showLines = true, showColumns = true;
	private double theta = 0;
	private int largestNote;
	private boolean copied = false;
	private NoteSelector noteSelector;
	int numbCopiedNotes, smallestCopyCol;
	
	public EditState(Editor editor) {
		
		super(editor);
		
	}
	

	public void init() {
		
		SoundManager.play("sounds/Nothing.wav", false);
		
		for (int i = 0; i < 15; i++) {
			
			lines[i] = 500 - 20*i;
		}
		
		for (int i = 0; i < PAG1_COL + 4*PAG_COL; i++) {
			
			columns[i] = 30*i + 30;
		}
		
		cleanMarkers();
		
		
		createNote();
		
		maxLeftButton = new Button(editor, 490, 650, 50, 50, Color.black, "|<", Assets.font20, null, true);
		bigLeftButton = new Button(editor, 550, 650, 50, 50, Color.black, "<<", Assets.font20, null, true);
		smallLeftButton = new Button(editor, 610, 650, 50, 50, Color.black, "<", Assets.font20, null, true);
		smallRightButton = new Button(editor, 670, 650, 50, 50, Color.black, ">", Assets.font20, null, true);
		bigRightButton = new Button(editor, 730, 650, 50, 50, Color.black, ">>", Assets.font20, null, true);
		maxRightButton = new Button(editor, 790, 650, 50, 50, Color.black, ">|", Assets.font20, null, true);
		playButton = new Button(editor, 75, 550, 50, 50, Color.black, "", Assets.font20, null, true);
		markerButton = new Button(editor, 50, 650, 100, 50, Color.black, "MARCADOR", Assets.font15, null, true);
		speedDownButton = new Button(editor, 160, 550, 50, 50, Color.black, "v", Assets.font20, null, true);
		speedUpButton = new Button(editor, 230, 550, 50, 50, Color.black, "^", Assets.font20, null, true);
		savePictureMenuButton = new Button(editor, 60, 100, 200, 50, Color.black, "GERAR IMAGEM", Assets.font20, null, true);
		loadProjectMenuButton = new Button(editor, 300, 100, 200, 50, Color.black, "ABRIR PROJETO", Assets.font20, null, true);
		saveProjectMenuButton = new Button(editor, 540, 100, 200, 50, Color.black, "SALVAR PROJETO", Assets.font20, null, true);
		savePictureButton = new Button(editor, 600, 600, 200, 50, Color.black, "SALVAR IMAGEM", Assets.font20, null, true);
		saveProjectButton = new Button(editor, 600, 600, 200, 50, Color.black, "SALVAR PROJETO", Assets.font20, null, true);
		loadProjectButton = new Button(editor, 600, 600, 200, 50, Color.black, "ABRIR PROJETO", Assets.font20, null, true);
		returnButton = new Button(editor, 850, 600, 200, 50, Color.black, "VOLTAR", Assets.font20, null, true);
		showLinesButton = new Button(editor, 750, 400, 80, 50, Color.black, "SIM", Assets.font20, null, true);
		showColumnsButton = new Button(editor, 750, 470, 80, 50, Color.black, "SIM", Assets.font20, null, true);
		cleanMarkersButton = new Button(editor, 850, 550, 170, 50, Color.black, "LIMPAR MARCADORES", Assets.font13, null, true);
		cleanNotesButton = new Button(editor, 1060, 550, 170, 50, Color.black, "LIMPAR NOTAS", Assets.font13, null, true);
		pasteButton = new Button(editor, 590, 160, 100, 30, Color.blue, "COLAR", Assets.font15, null, true);
		
		
		fileNameBox = new TextBox(editor, 650, 300, 300, 50, Assets.font15);
		
		//pVpButton = new Button(game, 550, 500, 80, 40, Color.black, "P vs P", Assets.font15, null, false);
		//botButton = new Button(game, 650, 500, 80, 40, Color.black, "P vs BOT", Assets.font15, null, false);
		//keysButton = new Button(game, 510, 560, 120, 40, Color.black, "CONTROLES", Assets.font15, null, false);
		//minigamesButton = new Button(game, 650, 560, 120, 40, Color.black, "MINIGAMES", Assets.font15, null, false);
		
		
		//rendered = false;
		//SoundManager.play("sounds/Nothing.wav", false);
		
		projectSaver = new ProjectSaver(editor, lines, columns);
		
		noteSelector = new NoteSelector(editor);
		
		//notes = projectSaver.loadProject("teste.txt");
		
		//System.out.println("a\nb");
		
	}
	
	private void cleanMarkers() {
		
		for (int i = 0; i < PAG1_COL + 4*PAG_COL; i++) {
			
			markers[i] = false;
		}
	}
	
	private void cleanNotes() {
		
		for (int i = notes.size() - 1; i >= 0; i--) {
			
			if (notes.get(i).isPlaced()) {
				notes.remove(i);
			}
		}
	}

	
	private void preparePlay() {
		
		
		if (prepareCounter == 0) {
			SoundManager.play("sounds/Nothing.wav", false);
			largestNote = getLargestNote();
		}
		if (prepareCounter < 60)
			prepareCounter++;
		
	}
	
	private void playMusic() {
		
		playCounter++;
		
		if (playCounter == 60 - 3*speed) {
			
			playCounter = 0;
			cameraOffset -= 1;
			
			
			for (Note note : notes) {
				
				if (note.isPlaced()) {
				
					if (note.getColumn() + cameraOffset == -1) {
						
						switch(note.getLine()) {
						
							case 0:
								SoundManager.play("sounds/Caixa0.wav", false);
								break;
							case 1:
								SoundManager.play("sounds/Caixa1.wav", false);
								break;
							case 2:
								SoundManager.play("sounds/Caixa2.wav", false);
								break;
							case 3:
								SoundManager.play("sounds/Caixa3.wav", false);
								break;
							case 4:
								SoundManager.play("sounds/Caixa4.wav", false);
								break;
							case 5:
								SoundManager.play("sounds/Caixa5.wav", false);
								break;
							case 6:
								SoundManager.play("sounds/Caixa6.wav", false);
								break;
							case 7:
								SoundManager.play("sounds/Caixa7.wav", false);
								break;
							case 8:
								SoundManager.play("sounds/Caixa8.wav", false);
								break;
							case 9:
								SoundManager.play("sounds/Caixa9.wav", false);
								break;
							case 10:
								SoundManager.play("sounds/Caixa10.wav", false);
								break;
							case 11:
								SoundManager.play("sounds/Caixa11.wav", false);
								break;
							case 12:
								SoundManager.play("sounds/Caixa12.wav", false);
								break;
							case 13:
								SoundManager.play("sounds/Caixa13.wav", false);
								break;
							case 14:
								SoundManager.play("sounds/Caixa14.wav", false);
								break;
						}
						
						//if (note.getColumn() == largestNote) {
							
						//	pauseMusic();
					//	}
					}
				}
			}
			
			if (cameraOffset == -PAG1_COL - 4*PAG_COL) {
				
				pauseMusic();
				cameraOffset = -PAG1_COL - PAG_COL*4 + 38;
			}
		}
	}
	
	private void pauseMusic() {
		
		playing = false;
		playCounter = 0;
		prepareCounter = 0;
		playButton.setName("");
		
		if ((-cameraOffset)%2 == 1) {
			
			cameraOffset++;
		}
	}
	
	@Override
	public void tick() {


		if (!savingPicture) {
			

			playButton.setColor(Color.black);
			if (playButton.buttonPressed()) {
				
				
				
				if (!playing) {
					
					playing = true;
					playButton.setName("| |");
				}
				else {
					
					pauseMusic();
				}
				
			}

		}

		
		if (playing) {
			
			preparePlay();
			if (prepareCounter == 60)
				playMusic();
			
			smallRightButton.setColor(Color.lightGray);
			smallLeftButton.setColor(Color.lightGray);
			speedDownButton.setColor(Color.lightGray);
			speedUpButton.setColor(Color.lightGray);
			bigLeftButton.setColor(Color.lightGray);
			bigRightButton.setColor(Color.lightGray);
			maxLeftButton.setColor(Color.lightGray);
			maxRightButton.setColor(Color.lightGray);
			cleanMarkersButton.setColor(Color.lightGray);
			cleanNotesButton.setColor(Color.lightGray);
			savePictureMenuButton.setColor(Color.lightGray);
			loadProjectMenuButton.setColor(Color.lightGray);
			saveProjectMenuButton.setColor(Color.lightGray);
			
			if (cameraOffset != 0) {
				
				markerButton.setColor(Color.black);
				if (markerButton.buttonPressed()) {
					
					if (playCounter < (60 - 3*speed)/2) {
						
						markers[-cameraOffset - 1] = true;
						System.out.println("PC: " + playCounter + "; round down");
					}
					else {
						
						markers[-cameraOffset] = true;
						System.out.println("PC: " + playCounter + "; round up");
					}
				}
			}
			else {
				
				markerButton.setColor(Color.lightGray);
			}
		}
		else {
			
			if (!savingPicture && !loadingProject && !savingProject) {
				
				System.out.println(noteSelector.getLeftPressed());
				if (!noteSelector.getLeftPressed()) {
					
					if (cameraOffset - 38 > -PAG1_COL - PAG_COL*4) {
						
						smallRightButton.setColor(Color.black);
						if (smallRightButton.buttonPressed()) {
							
							
							cameraOffset -= 2;
							
						}
					}
					else {
						
						smallRightButton.setColor(Color.lightGray);
					}
					
					if (cameraOffset < -1) {
					
						smallLeftButton.setColor(Color.black);
						if (smallLeftButton.buttonPressed()) {
							
							
							cameraOffset += 2;
						}
					}
					else {
						
						smallLeftButton.setColor(Color.lightGray);
					}
					
					if (cameraOffset - 38 > -PAG1_COL - PAG_COL*4) {
						
						bigRightButton.setColor(Color.black);
						if (bigRightButton.buttonPressed()) {
							
							if (cameraOffset - 38 - (-PAG1_COL - PAG_COL*4) > 19) {
								cameraOffset -= 20;
							}
							else {
								cameraOffset = -PAG1_COL - PAG_COL*4 + 38;
							}
							
						}
					}
					else {
						
						bigRightButton.setColor(Color.lightGray);
					}
					
					
					
					if (getLargestNote() > 37) {
						maxRightButton.setColor(Color.black);
						if (maxRightButton.buttonPressed()) {
							
						
							cameraOffset = -getLargestNote() + 37;
						}
					}
					else {
						
						maxRightButton.setColor(Color.lightGray);
					}
					
					
					if (cameraOffset < -1) {
					
						bigLeftButton.setColor(Color.black);
						if (bigLeftButton.buttonPressed()) {
							
							if (cameraOffset < -19) {
								cameraOffset += 20;
							}
							else {
								cameraOffset = 0;
							}
						}
					}
					else {
						
						bigLeftButton.setColor(Color.lightGray);
					}
					
					if (cameraOffset < -1) {
						
						maxLeftButton.setColor(Color.black);
						if (maxLeftButton.buttonPressed()) {
							
							cameraOffset = 0;
						}
					}
					else {
						
						maxLeftButton.setColor(Color.lightGray);
					}
					
					if (speed > 0) {
						
						speedDownButton.setColor(Color.black);
						if (speedDownButton.buttonPressed()) {
							
							speed--;
						}
					}
					else {
						
						speedDownButton.setColor(Color.lightGray);
					}
					
					if (speed < 19) {
						
						speedUpButton.setColor(Color.black);
						if (speedUpButton.buttonPressed()) {
							
							speed++;
						}
					}
					else {
						
						speedUpButton.setColor(Color.lightGray);
					}
					
					
					cleanMarkersButton.setColor(Color.black);
					if (cleanMarkersButton.buttonPressed()) {
						
						cleanMarkers();
					}
					
					cleanNotesButton.setColor(Color.black);
					if (cleanNotesButton.buttonPressed()) {
						
						cleanNotes();
					}
					
					savePictureMenuButton.setColor(Color.black);
					if (savePictureMenuButton.buttonPressed()) {
						
						pictureGenerator = new PictureGenerator(notes);
						pictureGenerator.generateImage(showLines, showColumns);
						savingPicture = true;
					}
					
					loadProjectMenuButton.setColor(Color.black);
					if (loadProjectMenuButton.buttonPressed()) {
						
						loadingProject = true;
					}
					
					saveProjectMenuButton.setColor(Color.black);
					if (saveProjectMenuButton.buttonPressed()) {
						
						savingProject = true;
					}
				
					for (int i = notes.size() - 1; i >= 0; i--) {
						
						notes.get(i).tick();
					}
					
					if (copied) {
						
						if (pasteButton.buttonPressed()) {
							
							pasteNotes();
						}
					}
				}
				
				if (!unplacedNoteOnSheet()) {
					
					noteSelector.tick();
				}
			}
			
			else if (savingPicture) {
				
				if (fileNameBox.getText().length() > 0) {
					
					savePictureButton.setColor(Color.black);
					if (savePictureButton.buttonPressed()) {
						
						try {
							pictureGenerator.exportImage(fileNameBox.getText());
						} catch (IOException e) {
							
							e.printStackTrace();
						}
						
						savingPicture = false;
					}
				}
				else {
					
					savePictureButton.setColor(Color.lightGray);
				}
				
				if (returnButton.buttonPressed()) {
					
					savingPicture = false;
				}
				
				if (showLinesButton.buttonPressed()) {
					
					if (showLines) {
						showLines = false;
						showLinesButton.setName("NÃO");
					}
					else {
						showLines = true;
						showLinesButton.setName("SIM");
					}
					
					pictureGenerator.generateImage(showLines, showColumns);
				}
				
				if (showColumnsButton.buttonPressed()) {
					
					if (showColumns) {
						showColumns = false;
						showColumnsButton.setName("NÃO");
					}
					else {
						showColumns = true;
						showColumnsButton.setName("SIM");
					}
					
					pictureGenerator.generateImage(showLines, showColumns);
					
				}
				
				fileNameBox.tick();
				
			}
			
			else if (savingProject) {
				
				if (fileNameBox.getText().length() > 0) {
					
					saveProjectButton.setColor(Color.black);
					if (saveProjectButton.buttonPressed()) {
						
						
						projectSaver.saveProject(fileNameBox.getText(), notes);
					
						
						savingProject = false;
					}
				}
				else {
					
					saveProjectButton.setColor(Color.lightGray);
				}
				
				if (returnButton.buttonPressed()) {
					
					savingProject = false;
				}
				
				
				fileNameBox.tick();
			}
			
			else if (loadingProject) {
				
				if (fileNameBox.getText().length() > 0) {
					
					loadProjectButton.setColor(Color.black);
					if (loadProjectButton.buttonPressed()) {
						
						
						notes = projectSaver.loadProject(fileNameBox.getText());
						noteSelector.setLeftPressed(false);
						
						loadingProject = false;
					}
				}
				else {
					
					loadProjectButton.setColor(Color.lightGray);
				}
				
				if (returnButton.buttonPressed()) {
					
					loadingProject = false;
				}
				
				
				fileNameBox.tick();
			}
			
		}
		
		
		
		

	}

	@Override
	public void render(Graphics g) {

		
			
		g.clearRect(0, 0, 1280, 720);
		
		if (savingPicture) {
			
			pictureGenerator.showImage(g, 10, 0);
			
			Text.drawString(g, "Essa será sua imagem.", 800, 200, true, Color.black, Assets.font30);
			Text.drawString(g, "Lembre-se de imprimí-la com exatamente 29cm de altura.", 800, 230, true, Color.black, Assets.font20);
			Text.drawString(g, "Nome do arquivo:", 800, 290, true, Color.black, Assets.font20);
			Text.drawString(g, "Mostrar linhas verticais", 640, 425, true, Color.black, Assets.font20);
			Text.drawString(g, "Mostrar linhas horizontais", 630, 495, true, Color.black, Assets.font20);
			
			savePictureButton.drawButton(g);
			returnButton.drawButton(g);
			showLinesButton.drawButton(g);
			showColumnsButton.drawButton(g);
			
			fileNameBox.render(g);
		}
		else if (savingProject) {
			
			//Text.drawString(g, "Essa será sua imagem.", 800, 200, true, Color.black, Assets.font30);
			//Text.drawString(g, "Lembre-se de imprimí-la com exatamente 29cm de altura.", 800, 230, true, Color.black, Assets.font20);
			Text.drawString(g, "Nome do arquivo:", 800, 290, true, Color.black, Assets.font20);
			//Text.drawString(g, "Mostrar linhas verticais", 640, 425, true, Color.black, Assets.font20);
			//Text.drawString(g, "Mostrar linhas horizontais", 630, 495, true, Color.black, Assets.font20);
			
			saveProjectButton.drawButton(g);
			returnButton.drawButton(g);
			//showLinesButton.drawButton(g);
			//showColumnsButton.drawButton(g);
			
			fileNameBox.render(g);
		}
		else if (loadingProject) {
			
			//Text.drawString(g, "Essa será sua imagem.", 800, 200, true, Color.black, Assets.font30);
			//Text.drawString(g, "Lembre-se de imprimí-la com exatamente 29cm de altura.", 800, 230, true, Color.black, Assets.font20);
			Text.drawString(g, "Nome do arquivo:", 800, 290, true, Color.black, Assets.font20);
			//Text.drawString(g, "Mostrar linhas verticais", 640, 425, true, Color.black, Assets.font20);
			//Text.drawString(g, "Mostrar linhas horizontais", 630, 495, true, Color.black, Assets.font20);
			
			loadProjectButton.drawButton(g);
			returnButton.drawButton(g);
			//showLinesButton.drawButton(g);
			//showColumnsButton.drawButton(g);
			
			fileNameBox.render(g);
		}
		else {
			
			noteSelector.render(g);
		
			//draw lines
			for (int i = 0; i < 15; i++) {
				
				if (i%2 == 0 && i >= 2 && i <= 10) {
					g.setColor(Color.black);
				}
				else {
					g.setColor(Color.lightGray);
				}
				
				g.drawLine(0, lines[i], 1280, lines[i]);
			}
			
			
			//draw columns
			for (int i = 0; i < 20; i++) {
				
				if (2*i - cameraOffset - 2 < PAG1_COL + 4*PAG_COL) {
					if (2*i - cameraOffset <= PAG1_COL) {
						if ((2*i - cameraOffset) == PAG1_COL) {
							g.setColor(Color.black);
							g.drawLine(leftWall + i*60 + 30, lines[0] + 5, leftWall + i*60 + 30, lines[14] - 5);
						}
					}
					else {
						if ((2*i - cameraOffset - PAG1_COL)%PAG_COL == 0 && i != 0) {
							
							g.setColor(Color.black);
							g.drawLine(leftWall + i*60 + 30, lines[0] + 5, leftWall + i*60 + 30, lines[14] - 5);
						}
					}
					
					for (int j = 0; j < 15; j++) {
						
						g.setColor(Color.lightGray);
						g.drawLine(leftWall + i*60, lines[j] - 5, leftWall + i*60, lines[j] + 5);
					}
					
					if (i != 0)
						Text.drawString(g, "" + (i - cameraOffset/2), leftWall + i*60, 205, true, Color.black, Assets.font10);
				}
			}
			
			//draw markers
			for (int i = 0; i < 38; i++) {
				
				if (i - cameraOffset - 2 < PAG1_COL + 4*PAG_COL) {
					if (markers[i - cameraOffset]) {
						
						g.setColor(Color.red);
						g.drawLine(leftWall + i*30 + 30, lines[0] + 5, leftWall + i*30 + 30, lines[14] - 5);
					}
				}
			}
			
			
			
			for (int i = notes.size() - 1; i >= 0; i--) {
				
				notes.get(i).render(g);
			}
			
			g.setColor(Color.black);
			g.drawLine(leftWall, 220, leftWall, 500);
			
			g.drawImage(Assets.clave, -20, 240, 150, 300, null);

			
			
			smallLeftButton.drawButton(g);
			smallRightButton.drawButton(g);
			bigLeftButton.drawButton(g);
			bigRightButton.drawButton(g);
			maxLeftButton.drawButton(g);
			maxRightButton.drawButton(g);
			speedDownButton.drawButton(g);
			speedUpButton.drawButton(g);
			playButton.drawButton(g);
			savePictureMenuButton.drawButton(g);
			cleanMarkersButton.drawButton(g);
			cleanNotesButton.drawButton(g);
			loadProjectMenuButton.drawButton(g);
			saveProjectMenuButton.drawButton(g);
			
			if (copied) {
				pasteButton.drawButton(g);
				g.setColor(Color.blue);
				g.drawLine(columns[17] + leftWall, lines[14] - 10, columns[17] + leftWall, lines[0] + 10);
			}
			
			if (playing) {
				markerButton.drawButton(g);
			}
			else {
				//75, 550
				g.setColor(Color.white);
				g.drawLine(90, 565, 90, 585);
				g.drawLine(90, 565, 110, 575);
				g.drawLine(90, 585, 110, 575);
			}
			
			Text.drawString(g, "Vel: " + (speed + 1), 290, 575, false, Color.black, Assets.font20);
			
			spinHandle(g);
			
			
			
			
			/*
			g.setColor(Color.DARK_GRAY);
			g.fillOval(100, 360 - 10, 20, 20);
			g.fillOval(100, 340 - 10, 20, 20);
			 */
			
			//Text.drawString(g, "SUPER SMASH BALAS", 640, 300, true, Color.black, Assets.font30);
			//Text.drawString(g, "Ver. Beta 9.0", 10, 710, false, Color.black, Assets.font15);
			//pVpButton.drawButton(g);
			//botButton.drawButton(g);
			//keysButton.drawButton(g);
			//minigamesButton.drawButton(g);
		}
		
	}
	
	private void spinHandle(Graphics g) {
		
		int centerX = 220, centerY = 675, radius = 30;

		if (playing)
			theta += (2*Math.PI)/(3*(60 - 3*speed));
		g.setColor(Color.black);
		g.drawLine(centerX, centerY, (int)(centerX + Math.sin(theta)*radius), centerY - (int)(Math.cos(theta)*radius));
		g.fillOval((int)(centerX + Math.sin(theta)*radius) - 5, centerY - (int)(Math.cos(theta)*radius) - 5, 10, 10);
		
	}
	
	private boolean unplacedNoteOnSheet() {
		
		for (Note note: notes) {
			
			if (!note.isPlaced()) {
				if (note.isOnSheet()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void createNote() {
		
		notes.add(new Note(editor, lines, columns));
	}
	
	private void placeNewNote(int line, int column) {
		
		Note note;
    	note = new Note(editor, lines, columns);
    	note.setLine(line);
    	note.setColumn(column);
    	note.place();
    	notes.add(note);
	}
	
	public void copyNotesBetween(int x1, int y1, int x2, int y2) {
		
		int i = 0;
		
		for (Note note: notes) {
			
			if (note.isPlaced()) {
				//columns[column] - radius + editor.getEditState().leftWall + editor.getEditState().getCameraOffset()*30
				if (columns[note.getColumn()] + editor.getEditState().leftWall + editor.getEditState().getCameraOffset()*30 >= x1 && columns[note.getColumn()] + editor.getEditState().leftWall + editor.getEditState().getCameraOffset()*30 <= x2) {
					if (lines[note.getLine()] >= y1 && lines[note.getLine()] <= y2) {
						
						copiedNotes[i][0] = note.getLine();
						copiedNotes[i][1] = note.getColumn();
						//System.out.println(copiedNotes[i][0] + " " + copiedNotes[i][1]);
						i++;
					}
				}
			}
		}
		
		
		
		if (i > 0) {
			
			numbCopiedNotes = i;
			findFirstCopiedNoteColumn();
			copied = true;
		}
	}
	
	private void findFirstCopiedNoteColumn() {
		
		int i;
		smallestCopyCol = copiedNotes[0][1];
		
		for (i = 1; i < numbCopiedNotes; i++) {
			
			if (copiedNotes[i][1] < smallestCopyCol) {
				
				smallestCopyCol = copiedNotes[i][1];
			}
		}
	}
	
	private void pasteNotes() {
		
		int i;
		
		for (i = 0; i < numbCopiedNotes; i++) {
			
			placeNewNote(copiedNotes[i][0], copiedNotes[i][1] - smallestCopyCol + 17 - cameraOffset);
			//System.out.println(copiedNotes[i][0] + " " + (copiedNotes[i][1] - smallestCopyCol + 17 - cameraOffset));
		}
		copied = false;
	}
	
	public void removeNote(Note note) {
		
		for (int i = notes.size() - 1; i >= 0; i--) {
			
			if (notes.get(i) == note) {
				
				notes.remove(i);
			}
		}
	}
	
	public boolean mouseOnAnyOtherNote(Note note) {
		
		for (int i = notes.size() - 1; i >= 0; i--) {
	
			if (!notes.get(i).equals(note)) {
				
				if (notes.get(i).mouseOnNote()) {
						
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean noteNearAnyOtherNote(Note note) {
		
		for (int i = notes.size() - 1; i >= 0; i--) {
			
			if (!notes.get(i).equals(note)) {
				
				if (notes.get(i).noteNearNote(note)) {
						
					return true;
				}
			}
		}
		
		return false;
	}
	
	private int getLargestNote() {
		
		int largest = 0;
		
		for (Note note : notes) {
			
			if (note.isPlaced()) {
				if (note.getColumn() > largest) {
					
					largest = note.getColumn();
				}
			}
		}
		
		return largest;
	}
	
	public int getCameraOffset() {
		
		return cameraOffset;
	}
	
	public TextBox getFileNameBox() {
		
		return fileNameBox;
	}

	
}
