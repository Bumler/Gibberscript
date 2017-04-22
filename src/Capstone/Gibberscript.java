package Capstone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.stanford.nlp.ling.TaggedWord;

public class Gibberscript extends Tagger{
	HashMap<String, String> file_cipher;
	HashMap<String, String> cipher;
	List<List<TaggedWord>> GScript;
	
	String CIPHER_TXT = "cipher.txt";
	
	public Gibberscript(String fileName, POSLibrary lib) throws IOException{
		//reads the cipher in from a file
		file_cipher = new HashMap<String, String>();
		readCipher();
		
		//we will store new translations into a new cipher
		cipher = new HashMap<String, String>();
		
		//tags the inputed text and then converts it
		List<List<TaggedWord>> tagged = tagFile(fileName);
		GScript = convert(tagged, lib);
		
		//finally writes any new ciphers to the file
		writeCipher();
		
		//makes it prettier
		GScript = clean(GScript);
	}
	
	private void readCipher(){
		BufferedReader reader = null;
		try{
			File file = new File(CIPHER_TXT);
			reader = new BufferedReader(new FileReader(file)); 
			
			String line;
			while ((line = reader.readLine()) != null){
				String[] words = line.split(" ");
				file_cipher.put(words[0], words[1]);
			}
			
			reader.close();
			
		} catch (IOException e){
			
		}
	}
	
	private void writeCipher() throws IOException{
		if(cipher.size() != 0){
			System.out.println("check");
			File f = new File(CIPHER_TXT);
			
			if(f.exists()){
				System.out.println("F Exists");
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(CIPHER_TXT, true))){
				    for (Entry<String, String> entry: cipher.entrySet()) {
				    	System.out.println(entry.getKey() + " " + entry.getValue());
				        bw.write(entry.getKey()+" ");
				        bw.write(entry.getValue());
				        bw.newLine();
				    }
				}
			}
			else{
				System.out.println("F Does Not Exist");
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(CIPHER_TXT))){
				    for (Entry<String, String> entry: cipher.entrySet()) {
				    	System.out.println(entry.getKey() + " " + entry.getValue());
				        bw.write(entry.getKey()+" ");
				        bw.write(entry.getValue());
				        bw.newLine();
				    }
				}
			}
		}
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
				
				//checks both our ciphers for the word
				else if(file_cipher.containsKey(tw.value())){
					convert = file_cipher.get(tw.value());
				}
				else if(cipher.containsKey(tw.value())){
					convert = cipher.get(tw.value());
				}
				
				//finally if neither cipher has it it creates a value and adds it to the new cipher
				else{
					int randy = (int) (Math.random() * lib.size(tw.tag()));
					convert = lib.getString(tw.tag(), randy);
					lib.remove(tw.tag(), randy);
					
					//puts this value into the cipher using the encoded word as the key
					cipher.put(tw.value(), convert);
				}

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
