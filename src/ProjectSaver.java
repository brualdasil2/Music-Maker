import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import com.sun.java_cup.internal.runtime.Scanner;

public class ProjectSaver {
	
	private Editor editor;
	private int[] lines;
	private int[] columns;
	
	public ProjectSaver(Editor editor, int[] lines, int[] columns) {
		
		this.editor = editor;
		this.lines = lines;
		this.columns = columns;
	}
	
	public void saveProject(String fileName, ArrayList<Note> notes) {
		
		try {
		      FileWriter myWriter = new FileWriter(fileName + ".txt");
		      
		      //myWriter.write("Files in Java might be tricky, but it is fun enough!");
		      for (Note note : notes) {
		    	  
		    	  if (note.isPlaced()) {
		    		  myWriter.write("" + note.getLine() + " " + note.getColumn() + "\n");
		    	  }
		      }
		      
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	public ArrayList<Note> loadProject(String fileName) {
		
		ArrayList<Note> notes = new ArrayList<Note>();
		Note note;
	

        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName + ".txt");

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	
            	//line.indexOf(' ') //index of separator
            	//line.substring(0, line.indexOf(' ')) //first number as string (line)
            	//line.substring(line.indexOf(' ') + 1, line.length()) //last number as string (column)
            	
            	
            	
            	note = new Note(editor, lines, columns);
            	note.setLine(Integer.parseInt(line.substring(0, line.indexOf(' '))));
            	note.setColumn(Integer.parseInt(line.substring(line.indexOf(' ') + 1, line.length())));
            	note.place();
            	notes.add(note);
            	
            }   
            
            notes.add(new Note(editor, lines, columns));

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");        
            notes.add(new Note(editor, lines, columns));
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
            notes.add(new Note(editor, lines, columns));
        }
		
		
		return notes;
	}
}
