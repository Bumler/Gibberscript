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
		GScript = clean(GScript);
	}
	
	private List<List<TaggedWord>> convert (List<List<TaggedWord>> tagged, POSLibrary lib) {
		//go through each tagged sentence
		for (List<TaggedWord> tSentence : tagged) {
			//go through each word in each tagged sentence
			for (TaggedWord tw : tSentence){
				//we'll make everything lower case except for proper nouns to ensure consistent translation
				if (!tw.tag().matches("NNP")){
					tw.setValue(tw.value().toLowerCase());
				}
				
				String convert;
				//we want to keep punctuation the same so that value is not changed
				if(tw.tag().equals(".")){
					convert = tw.value();
				}
				//we will also maintain proper nouns
				else if(tw.tag().equals("NNP")){
					convert = tw.value();
				}
				//finally checks to see if the value has already been encoded in our cipher
				//if it hasn't it grabs a key at random
				else if (!cipher.containsKey(tw.value())){
					int randy = (int) (Math.random() * lib.size(tw.tag()));
					convert = lib.getString(tw.tag(), randy);
					lib.remove(tw.tag(), randy);
				}
				//if it has it just uses the previously made key
				else{
					convert = cipher.get(tw.value());
				}
				//puts this value into the cipher using the encoded word as the key
				//at this point we dont care about the PoS of the word
				cipher.put(tw.value(), convert);
				//changes the word to its conversion
				tw.setValue(convert);
			}
		}
		
		return tagged;
	}
	
	private List<List<TaggedWord>> clean (List<List<TaggedWord>> GScript) {
		//go through each Gibberscript sentence
		for (List<TaggedWord> gSentence : GScript) {
			//go through each word in each Gibberscript sentence
			boolean firstWord = true;
			for (TaggedWord gw : gSentence){
				//capitalize the first word in a sentence
				if(firstWord){
					gw.setValue(gw.value().substring(0, 1).toUpperCase() + gw.value().substring(1));
					firstWord = false;
				}
				//as long as the value is not punctuation or equal to .!? we can add a space in front
				else if((!gw.tag().equals(".")) 
						|| ((!(gw.value().equals("."))) 
						&& (!(gw.value().equals("!"))) 
						&& (!(gw.value().equals("?"))))) {
					gw.setValue(" "+gw.value());
				}
				//if the value is a period or question mark we don't have to add a space
				//we also don't need to set first word because we're looping by sentence
			}
		}
		return GScript;
	}
	
	//simply prints everything out
	public void displayData(){
		for (List<TaggedWord> tSentence : GScript) {
			for (TaggedWord tw : tSentence){
				System.out.print(tw.value());
			}
			System.out.println();
		}
	}
}
