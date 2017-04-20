package Capstone;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import edu.stanford.nlp.ling.TaggedWord;

public class Gibberscript extends Tagger{
	HashMap<String, String> cipher;
	List<List<TaggedWord>> GScript;
	
	public Gibberscript(String fileName, POSLibrary lib) throws FileNotFoundException{
		cipher = new HashMap<String, String>();
		
		List<List<TaggedWord>> tagged = tagFile(fileName);
		GScript = convert(tagged, lib);
	
	}
	
	private List<List<TaggedWord>> convert (List<List<TaggedWord>> tagged, POSLibrary lib) {
		for (List<TaggedWord> tSentence : tagged) {
			for (TaggedWord tw : tSentence){
				if (!tw.tag().matches("NNP")){
					tw.setValue(tw.value().toLowerCase());
				}
				
				String convert;

				if (!cipher.containsKey(tw.value())){
					int randy = (int) (Math.random() * lib.size(tw.tag()));
					convert = lib.getString(tw.tag(), randy);
					lib.remove(tw.tag(), randy);
				}
				else{
					convert = cipher.get(tw.value());
				}
				cipher.put(tw.value(), convert);
				tw.setValue(convert);
			}
		}
		
		return tagged;
	}
	
	public void displayData(){
		for (List<TaggedWord> tSentence : GScript) {
			for (TaggedWord tw : tSentence){
				System.out.print(tw.value()+ " ");
			}
			System.out.println();
		}
	}
	
	
}
