import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import java.io.IOException;

import javax.imageio.ImageIO;

public class PictureGenerator {
	
	private int width = 500, height = 3580;
	private int topWall = 100, leftWall = 75, transition = 20, columnDistance = 25;
	private int radius = 10;
	private ArrayList<Note> notes;
	BufferedImage fullSheet;
	private int pages;
	
	
	public PictureGenerator(ArrayList<Note> notes) {
		
		this.notes = notes;
	}
	
	public void generateImage(boolean showLines, boolean showColumns) {
		
		pages = (getLargestNote() <= EditState.PAG1_COL - 1)? 1 : (getLargestNote() - EditState.PAG1_COL)/EditState.PAG_COL + 2;
		
		System.out.println(pages);
		
		fullSheet = new BufferedImage(pages*width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D image = fullSheet.createGraphics();
		
		for (int i = 0; i < pages; i++) {
			
			image.drawImage(generatePage(i, showLines, showColumns), i*width, 0, width, height, null);
		}
	}
	
	
	public BufferedImage generatePage(int page, boolean showLines, boolean showColumns) {
		
		
		
		//create image
		BufferedImage sheet = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D image = sheet.createGraphics();
		
		//make white
		image.fillRect(0, 0, width, height);
		
		if (showLines) {
			//draw note lines
			image.setColor(Color.gray);
			for (int i = -7; i <= 7; i++) {
				
				if (i == -5 || i == -3 || i == -1 || i == 1 || i ==3) {
					image.setColor(Color.black);
				}
				else {
					image.setColor(Color.lightGray);
				}
				
				image.fillRect(width/2 + 25*i - 2, 0, 5, height);
			}
		}
		
		if (showColumns) {
			//draw columns
			image.setColor(Color.gray);
			
			if (page == 0) {
				for (int i = 0; i < 72; i++) {
					
					image.fillRect(75, topWall + 20 + 25 + 50*(i - 1) - 2, 350, 5);
				}
			}
			else {
				for (int i = -9; i < 72; i++) {
					
					image.fillRect(75, transition*columnDistance + 20 + 25 + 50*(i - 1) - 2, 350, 5);
				}
			}
		}
		
		image.setColor(Color.lightGray);
		if (page == 0) {
			//draw triangle
			for (int i = 0; i < 10; i++) {
				image.drawLine(i, topWall, width/2 + i, 0);
				image.drawLine(width/2 - i, 0, width - i, topWall);
			}
		}
		else {
			//draw transition line
			image.fillRect(0, 528, width, 5);
		}
		
		//draw dotted lines
		image.setColor(Color.lightGray);
		for (int i = 0; i < 72; i++) {
			
			image.fillRect(0, 50*i - 10, 5, 20);
			image.fillRect(width - 5, 50*i - 10, 5, 20);
		}
		
		for (int i = 0; i < 11; i++) {
			
			image.fillRect(50*i - 10, 0, 20, 5);
			image.fillRect(50*i - 10, height - 5, 20, 5);
		}
		
		//draw notes
		image.setColor(Color.black);
		for (Note note : notes) {
			
			if (note.isPlaced()) {
				
				if (page == 0) {
					if (note.getColumn() >= EditState.PAG1_COL*page && note.getColumn() <= EditState.PAG1_COL*(page + 1)) {
						
						image.fillOval(leftWall + 25*note.getLine() - radius, topWall + 20 + (note.getColumn())*25 - radius, 2*radius, 2*radius);
						//System.out.println(topWall + 20 + (note.getColumn() - EditState.PAG1_COL*page)*25);
					}
				}
				else {
					
					image.fillOval(leftWall + 25*note.getLine() - radius, transition*columnDistance + 20 + (note.getColumn() - (EditState.PAG1_COL + EditState.PAG_COL*(page - 1)))*25 - radius, 2*radius, 2*radius);
					//System.out.println(transition*columnDistance + 20 + (note.getColumn() - (EditState.PAG1_COL + EditState.PAG_COL*(page - 1)))*25);
				}
				
			}
		}
		
		return sheet;
		
	}
	
	public void showImage(Graphics g, int x, int y) {
		
		g.drawImage(fullSheet, x, y, 100*(pages), 716, null);
		//g.drawImage(fullSheet, x, y, width*(getLargestNote()/EditState.PAG_COL + 1), height, null);
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
	
	public void exportImage(String fileName) throws IOException {
		
		ImageIO.write(fullSheet,"png",new File(fileName + ".png"));
	}
}
