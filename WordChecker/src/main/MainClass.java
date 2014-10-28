package main;


public class MainClass {
	
	
	 public static void main(String args[]){
		   
	 ArgOrganizer myOrganizer = new ArgOrganizer(args);
	 if(myOrganizer.isError()){
		 System.out.println("Terminating program due to error");
		 return;
	 }
	 WordCount myWordCount = new WordCount(myOrganizer.getMinlength(),myOrganizer.getSentences());
	 System.out.printf("The average word count rounded down in your document is %d counting words with at least %d letters.\n",myWordCount.getAverage(),myWordCount.getMinLength());
	 
	
	}   
}
