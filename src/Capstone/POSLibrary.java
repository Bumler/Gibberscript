package Capstone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.stanford.nlp.ling.TaggedWord;

public class POSLibrary extends Tagger{
	HashMap<String, ArrayList<String>> lib;
	HashMap<String, ArrayList<String>> accessable;
	
	String LIB_TXT = "lib.txt"; 
	
	public POSLibrary (String fileName) throws IOException{
		initializeLib(fileName);
		
		System.err.println("Terminated");
	}
	
	private void initializeLib (String fileName) throws IOException{
		lib = new HashMap<String, ArrayList<String>>();
		
		//if it is initialized it will grab the data from a file
		//otherwise it will create the lib and write it to a file
		if (!isInitialized()){
			List<List<TaggedWord>> tagged = tagFile(fileName);
			populateLib(tagged);
			writeLibToFile();
		}
	}
	
	private boolean isInitialized() throws IOException{
		BufferedReader reader = null;
		try{
			File file = new File(LIB_TXT);
			reader = new BufferedReader(new FileReader(file)); 
			
			String line;
			while ((line = reader.readLine()) != null){
				String[] words = line.split(" ");
				lib.put(words[0], new ArrayList<String>());
				
				for (int i = 1; i < words.length; i++){
					lib.get(words[0]).add(words[i]);
				}
			}
			
			reader.close();
			return true;
			
		} catch (FileNotFoundException e){
			return false;
		}
	}
	
	private void populateLib (List<List<TaggedWord>> tagged){
		 for (List<TaggedWord> tSentence : tagged){
			 for (TaggedWord tw : tSentence){

				 lib.putIfAbsent(tw.tag(), new ArrayList<String>());
				 if(!lib.get(tw.tag()).contains(tw.value())){
					 lib.get(tw.tag()).add(tw.value());
				 }
			 }
		 }
	}
	
	private void writeLibToFile(){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(LIB_TXT))) {

		    for (Entry<String, ArrayList<String>> entry : lib.entrySet()) {
		        bw.write(entry.getKey()+" ");
		        for(String word : entry.getValue()){
		            bw.write(word+" ");
		        }
		        bw.newLine();
		    }

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	public void displayData(){
    for (Entry<String, ArrayList<String>> entry : lib.entrySet()) {
        System.out.print(entry.getKey()+" | ");
        for(String word : entry.getValue()){
            System.out.print(word+" ");
        }
        System.out.println();
    }
	}
	
	
	public String getString (String k, int i){
		return lib.get(k).get(i);
	}
	
	public int size(String k){
		return lib.get(k).size();
	}
	
	public void remove (String k, int i){
		lib.get(k).remove(i);
	}
}
