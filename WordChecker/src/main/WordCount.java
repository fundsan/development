package main;

public class WordCount {
private int average;
private int numberOfSentences;
private int minLength;
private String[] sentences;
public WordCount(int minLength, String[] sentences){
	this.sentences = sentences;
	this.numberOfSentences = sentences.length;
	this.minLength = minLength;
	average = computeAverage();
	
}
private int computeAverage(){
	int sum = 0;
	
	for(String sentence: sentences){
		String[] words = sentence.replace("[^a-zA-Z0-9\\-]","").split("\u0020");
		for(String word: words){
			
			if (word.length() < this.minLength){
				sum--;
				
			}
		}
		
		
		sum += words.length;
	}
	
	return sum/this.numberOfSentences;
}
public int getAverage() {
	return average;
}
public void setAverage(int average) {
	this.average = average;
}
public int getNumberOfSentences() {
	return numberOfSentences;
}
public void setNumberOfSentences(int numberOfSentences) {
	this.numberOfSentences = numberOfSentences;
}
public int getMinLength() {
	return minLength;
}
public void setMinLength(int minLength) {
	this.minLength = minLength;
}
public String[] getSentences() {
	return sentences;
}
public void setSentences(String[] sentences) {
	this.sentences = sentences;
}

}
