package Capstone;

public class Main {

	  public static void main(String[] args) throws Exception {

		  POSLibrary lib = new POSLibrary("words.txt");
//		  lib.displayData();
		  
		  Gibberscript gs = new Gibberscript ("toConvert.txt", lib);
		  gs.displayData();

	  }
	  
	  public static boolean checkIfTagged(Tagger T){
		  return T.isTagged();
	  }

	}

