package Capstone;

public class Main {

	  public static void main(String[] args) throws Exception {

		  POSLibrary lib = new POSLibrary("words.txt");
//		  System.out.println(checkIfTagged(lib));
//		  lib.displayData();
		  
		  Gibberscript gs = new Gibberscript ("toConvert.txt", lib);
//		  System.out.println(checkIfTagged(gs));
		  gs.displayData();

	  }
	  
	  public static boolean checkIfTagged(Tagger T){
		  return T.isTagged();
	  }

	}

