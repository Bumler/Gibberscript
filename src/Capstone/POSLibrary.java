package Capstone;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.stanford.nlp.ling.TaggedWord;

public class POSLibrary extends Tagger{
	HashMap<String, ArrayList<String>> lib;
	HashMap<String, ArrayList<String>> accessable;
	
	public POSLibrary (String fileName) throws FileNotFoundException{
		lib = new HashMap<String, ArrayList<String>>();
		
		List<List<TaggedWord>> tagged = tagFile(fileName);
		populateLib(tagged);
		
		System.err.println("Terminated");
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
