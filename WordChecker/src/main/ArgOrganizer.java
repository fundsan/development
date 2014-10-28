package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ArgOrganizer {
	private String delimiters = null;
	private int minlength= -1;
	public String getDelimiters() {
		return delimiters;
	}

	public void setDelimiters(String delimiters) {
		this.delimiters = delimiters;
	}

	public int getMinlength() {
		return minlength;
	}

	public void setMinlength(int minlength) {
		this.minlength = minlength;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String[] getSentences() {
		return sentences;
	}

	public void setSentences(String[] sentences) {
		this.sentences = sentences;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	private String path;
	private BufferedReader reader;
	private String[] sentences;
	private String[] args;
	private boolean error = false;
	
	public ArgOrganizer(String[] args){
		this.args = args;
		configureAttributes();
		
	}

private void configureAttributes(){

 if (args.length == 0){
	 System.out.println("Please put a path to the file as your first argument that you would like used after \"java WorkCheck \" ");
	 this.error = true;
	 return;
 }
 if (args.length == 1){
	 delimiters = "\\.|;|\\?|!";
	 minlength = 1; 
	 path = args[0];
	 
 }
 else{
	 path = args[0];
	 int i = 1;
	 String currentOption = null;
	 while(i < args.length){
		 String currentArg = args[i];
		 
		 if(currentOption != null){
			 if(((!currentOption.equals("-d"))&&(!currentOption.equals("w/-l")))){
				 System.out.println("Arguments given started to not make sense at "+currentArg+ 
				 		". If you want to set the end of the sentence markers use -d"
				 		+ " then the character that you want to use seperated by a single space.  For example: -d , . ;"
				 		+ " if you want to set the minimum length of a word to be counted use -l then a single space and the number."
				 		+ " For example: -l 4");
				 this.error = true;
				       return;
			 }
			 else {
				 if(currentOption.equals("-d")){
					 if(currentArg.matches("[\\.|,|;|\\?|!]")){
						 if (currentArg.equals(".")||currentArg.equals("?"))
							 currentArg = "\\"+currentArg;
					 delimiters =  currentArg+"|"+delimiters;
					 i++;
					 }
					 else if(currentArg.equals("w/-l")){
						 currentOption = "w/-l";
						 i++;
					 }
					 else{
						 System.out.printf("You cannot end sentences with character %s. You can only use \" . ? ! ; , \" to end a sentence.\n", currentArg);
					     this.error = true;
					     return;
					 }
				 }
				else if(currentOption.equals("w/-l")){
					
					if(currentArg.matches("[0-9]+")){
						if(!(minlength <= -1)){
							System.out.println("You gave more than one argument after the w/-l flag. The w/-l flag can only take one value after it.");

							this.error = true;
							return;
						}
						
						minlength = Integer.valueOf(currentArg);
						if(minlength == 0){
							minlength = 1;
							
						}
						i++;
					}
					else if(currentArg.equals("-d")){
						 currentOption = "-d";
						 i++;
					 }
					else{
						System.out.println("Invalid minimum lengt. argument is not a number or is negative.");

						this.error = true;
						return;
					}
		
		      }
		
				
			
				 
			 
			 
		 }
	 }
		 else{
			 if((!currentArg.equals("-d"))&&(!currentArg.equals("w/-l"))){
				 System.out.println("Invalid flag argument: "+currentArg);
				 return;
			 }
			 currentOption = currentArg;
			 i++;
		 }
		 
	 
 }
}
// After the if	 for the Args we need to read the file 
 {
	 String line;
	 
	 String file = "";
	 try {
		reader = new BufferedReader(new FileReader(path));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("Could not find file "+args[0]);
		e.printStackTrace();
		this.error = true;
		return;
	}
	 try {
		while((line = reader.readLine()) != null)
		 file = file+ line;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// if the delimiter was not set we need to set it ourselves. 
	if (delimiters == null){
		this.delimiters = "[\\.|;|\\?|!][\u0020]*";
	}
	else this.delimiters = this.delimiters+"[\u0020]*";
	// if the minlength was -1 or not set we need to set it ourselves.
	if (minlength < 1)this.minlength=1;
	this.sentences = file.split(delimiters);
	
 }
}
}
