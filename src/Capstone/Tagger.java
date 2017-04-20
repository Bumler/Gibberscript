package Capstone;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public abstract class Tagger {
	public boolean isTagged = false;
	
	protected List<List<TaggedWord>> tagFile(String fileName) throws FileNotFoundException{
		 MaxentTagger tagger = new MaxentTagger("english-bidirectional-distsim.tagger");
		 List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(fileName)));
		 
		 List<List<TaggedWord>> tagged = new ArrayList<List<TaggedWord>>();
		 
		 for (List<HasWord> sentence : sentences) {
		      List<TaggedWord> tSentence = tagger.tagSentence(sentence);
		      toLower(tSentence);
		      tagged.add(tSentence);
		 }
		 
		 return tagged;
	}
	
	protected void toLower(List<TaggedWord> tSentence){
		for (TaggedWord tw : tSentence){
			if (!tw.tag().matches("NNP")){
				tw.setValue(tw.value().toLowerCase());
			}
		}
	}
	
	public boolean isTagged(){
		return isTagged;
	}
}
