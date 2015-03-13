
import edu.smu.tspell.wordnet.AdjectiveSynset;
import edu.smu.tspell.wordnet.AdverbSynset;
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.VerbSynset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.WordSense;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import rita.wordnet.RiWordnet;
import rita.wordnet.*;
import wsd.Meanings;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shubh
 */

public class FindMeanings {
    private static String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
    
    private static ArrayList<String> solve(String s){
        ArrayList<String> ANS = new ArrayList<String>();
        int i=0;
        System.out.println("s = " + s);
        while(s.charAt(i) !='['){
            i++;
        }
        i++;
        String temp="";
        while(s.charAt(i) != ']'){
            if(s.charAt(i) == ','){
                ANS.add(temp);
                System.out.println("temp = " + temp);
                temp = "";
                i++;
            }
            else {
                temp +=s.charAt(i);
                i++;
            }        
        }
        System.out.println("temp now = " + temp);
        ANS.add(temp);
        System.out.println("done");
        return ANS;
    }
    public static void pr(ArrayList<String> ss) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter("myHfile.txt", "UTF-8");
        for(int i=0; i<ss.size(); i++){
            System.out.println("ss = " + ss.get(i));
            writer.write(ss.get(i));
            writer.write(" ");
        }
        writer.close(); 

    }
	public static void main (String[] args) {
		
		// Start with the word cat as a noun
		String word = "bank";
		String pos = "n";
		
		// An array for any results we get
		String[] result;  

		RiWordnet wordnet = new RiWordnet(null);

		// First we can look at all of the senses
		int[] ids = wordnet.getSenseIds(word, pos);
		System.out.println("\n\n====================== getSenseIds('cat', 'n'):getDescription(id) ============================");

		// And their descriptions
		for (int j = 0; j < ids.length; j++) {
			System.out.print("#"+ids[j]+":   ");
			System.out.println(wordnet.getDescription(ids[j]));
		}

		// This gets Hyponyms for first sense
		result = wordnet.getHyponyms(word, pos);
		System.out.println("\n\n======== getHyponyms('cat', 'n') =======");
		for (int i = 0; i < result.length; i++) {
			System.out.println("(first-sense)      "+result[i]);
		}

		// Hyponyms for all senses
		result = wordnet.getAllHyponyms(word, pos);
		System.out.println("\n\n====== getAllHyponyms('cat', 'n') ======");
		System.out.println("(all-senses)      ");
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}

		// Hyponyms per sense
		System.out.println("\n\n======== getHyponyms(id) =======");
		for (int j = 0; j < ids.length; j++) {
			result = wordnet.getHyponyms(ids[j]); 
			System.out.println("#"+ids[j]+": ");
			if (result == null) continue; 
			for (int i = 0; i < result.length; i++) {
				System.out.println(result[i]);
			}
		}

		// Hyponyms tree per sense
		System.out.println("\n\n======== getHyponymTree(id) ======");
		for (int j = 0; j < ids.length; j++) {
			result = wordnet.getHyponymTree(ids[j]);
			if (result == null) continue;
			System.out.println("#"+ids[j]+":");
			for (int i = 0; i < result.length; i++) {
				System.out.println(result[i]);    
			}
		}

	}
        
        public ArrayList<Integer> getMeanings(String word, String POS)
    {   
        ArrayList<String> res = new ArrayList<String>();
        System.out.println("word and Pos =  " + word + "   " + POS);
        ArrayList<Integer> allIds = new ArrayList<Integer>(); 
        String[] result;  
        RiWordnet wordnet = new RiWordnet(null);
	// First we can look at all of the senses
	int[] ids = wordnet.getSenseIds(word, POS.toLowerCase());
        for(int i=0; i < ids.length; i++){
            allIds.add(ids[i]);
        }    
            return allIds;
    }
    
        
        
        
	public static void findMeanings (String[] args) throws FileNotFoundException, UnsupportedEncodingException
       {        
                ArrayList<Pair> pr = new ArrayList<Pair>();
                RiWordnet wordnet = new RiWordnet(null);   
                ArrayList<String> posal = new ArrayList<String>();
                String[] b = new String[2];
                b[0] = "age";
                b[1] = "bank";
                String hs = "";
                String hs1 = "";
                try {
                    hs1 = readFileAsString("file1.txt");

                } catch (IOException ex) {
                    Logger.getLogger(Meanings.class.getName()).log(Level.SEVERE, null, ex);
                }
                int c =1;
                String t1= "";
                String t2 = "";
                String tmp = "";
                System.out.println("hs = +" + hs1+"+" );
                int ff = 0;
                while(hs1.charAt(ff) == ' '){
                    ff++;
                }
                
                for(int i=ff; i<hs1.length(); i++){
                    hs += hs1.charAt(i);
                }
//               System.out.println("hs = +" + hs+"+" + hs.charAt(0) );
                GlobalClass.trgindex = 0;
                inputClass.main_words = hs;
/*                int u =0;
                String hh = "";
                for(int i=0; i<hs.length(); i++){
                    System.out.println("i = " + i);
                    if(hs.charAt(i) == ' '){
                        System.out.println("hh = " + hh);
                        if(hh == inputClass.tar){
                            break;
                        }
                        u++;
                        hh = "";
                    }
                    else{
                        hh += hh.charAt(i);
                    }
                }
                
                
/*                
                System.out.println("target = " + inputClass.tar);
                for(int i=0; i<hs.length(); i++){
                    System.out.println("i " + i + "           " + hs.substring(i, i+inputClass.tar.length())) ;
                    if(hs.substring(i, i+inputClass.tar.length()).equals(inputClass.tar)){
                         = i;
                        break;
                    }
                }
*              System.out.println("global indwex = " +   u);
                GlobalClass.trgindex = u;
                System.out.println("---------------------------------- trgindex  =   " + GlobalClass.trgindex) ;
  */            int gy = 0;
                PrintWriter wrt = new PrintWriter("file2.txt", "UTF-8");
                ArrayList<Integer> a = new ArrayList<Integer>() ;
                int wrdnum = 1;
                int poscount = 0;
                for(int i=0; i < hs.length(); i++){
                    if(hs.charAt(i) == ' '){
                        System.out.println("tmp = " + tmp);
                        if(c %2 !=0){
                            t1 = tmp;
                            c++;
                            System.out.println("POSAL ====================================                      " + tmp);
                            if(tmp.equals(inputClass.tar)){
                                System.out.println("======================================================== YES  "+ gy);
                                GlobalClass.trgindex = gy;
                            }else{
                                gy++;
                            }
                            tmp = "";
                        }
                        else if(c %2 == 0){
                            t2 = tmp;
                            c++;
                            tmp = "";
                            int count1 = 0;
                            if(t2.length() >= 1){
                                System.out.println("t1 = " + t1+ "     t2 = "+ t2+"");
                                a =  (new FindMeanings()).getMeanings(t1, t2 );
        //                        pr(a);
                                for(int p=0; p<a.size(); p++){
                                    String res[] = wordnet.getHyponyms(a.get(p));
                                    if(res == null){
                                        continue;
                                    }
                                    System.out.println("a.get(p) = " + a.get(p));
                                    System.out.println("res size = ______________________________________________________________________________________" + res.length);
                                    System.out.println("res.length = " + res.length);
                                    for(int k=0; k<res.length; k++){
                                            System.out.println("res[k] = " + res[k]);
                                            wrt.print("["+res[k] +"] ");
                                            Pair newp = new Pair();
                                            newp.m = res[k];
                                            newp.n = a.get(p);
                                            newp.wn = wrdnum;
                                            pr.add(newp);
                                            count1++;
                                        }
                                }
                                if(count1 == 0){
                                    Pair newp = new Pair();
                                    newp.m = t1;
                                    newp.n = -1;
                                    newp.wn = wrdnum;
                                    pr.add(newp);
                                    wrt.print("\n["+t1+ "]\n");
                                    
                                }
                            }    
                            wrdnum++;
                        }
                        poscount++;
                        
                    } 
                    else {
                        tmp +=hs.charAt(i);
                    }
                }
        //        ArrayList<String> a = (new Meanings()).getMeanings("sleep", 'V');
                wrt.close();
                File f = new File("file2.txt");
                PrintWriter writer = new PrintWriter("output.txt");
                Scanner in = new Scanner(f);
                int k = 0;
                Set<String> set = new HashSet();

                while(in.hasNextLine()) {
                    String s = in.nextLine();
                    System.out.println("s.size()" + s.length());
                    //System.out.println("line is " + s);
                    String ss = "";
                    for(int i = 0; i < s.length()-1; i++) {
                        while(i >=0 && s.charAt(i) != '[') i++;
                        i++;
                        String ans = "";
                        while(s.charAt(i) != ']'){
                            ans += s.charAt(i);
                            i++;
                        }
                    System.out.println("-----------------------------------ans = " + ans);
                    set.add(ans);

                    }
                    Iterator it = set.iterator();
                    while(it.hasNext()){
                        writer.print( "[" + it.next() + "] ");
                    }
                    writer.print("\n");
                    set.clear();
                }
                writer.close();
                GlobalClass.labels = pr;
                if(wrdnum > 0){
                    GlobalClass.nds = wrdnum -1 ;
                }
                else {
                    wrdnum = 0;
                }
                System.out.println("wordnu ============================    " + wrdnum);
       }
}
		























