/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wsd;

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

/**
 *
 * @author Shubh
 */
public class Meanings {

    public void mymeanings() throws FileNotFoundException, UnsupportedEncodingException {
                //a[0] = {"smart"};
        String[] b = new String[2];
        b[0] = "age";
        b[1] = "bank";
        String hs = "";
        try {
            hs = readFileAsString("file1.txt");
            
        } catch (IOException ex) {
            Logger.getLogger(Meanings.class.getName()).log(Level.SEVERE, null, ex);
        }
        int c =1;
        String t1= "";
        String t2 = "";
        String tmp = "";
        PrintWriter wrt = new PrintWriter("file2.txt", "UTF-8");
        ArrayList<String> a = new ArrayList<String>() ;
        for(int i=1; i < hs.length(); i++){
            if(hs.charAt(i) == ' '){
                System.out.println("tmp = " + tmp);
                if(c %2 !=0){
                    t1 = tmp;
                    c++;
                    tmp = "";
                }
                else if(c %2 == 0){
                    t2 = tmp;
                    c++;
                    tmp = "";
                    if(t2.length() >= 1){
                        a =  (new Meanings()).getMeanings(t1, t2.charAt(0));
//                        pr(a);
                        for(int p=0; p<a.size(); p++){
                            wrt.print("["+a.get(p) + "] ");
                        }
                        wrt.print("\n");
                    }    
                }
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
    }
    

    
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
    public ArrayList<String> getMeanings(String word, char POS)
    {   
        ArrayList<String> res = new ArrayList<String>();
        System.out.println("word and Pos =  " + word + "   " + POS);
        if(POS == 'N'){
            System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict\\");
            NounSynset nounSynset;
            NounSynset[] hypernyms;
            WordNetDatabase database = WordNetDatabase.getFileInstance();
            Synset[] synsets = database.getSynsets(word, SynsetType.NOUN);
            
            System.out.println("*********************************************" + synsets.length);
            for (int i = 0; i < synsets.length; i++) {
                nounSynset = (NounSynset)(synsets[i]);
                hypernyms = nounSynset.getHypernyms();
                System.out.println("size = " + hypernyms.length);
                System.out.println("HYPERNYM = " + hypernyms[0].toString());
                ArrayList<String> Media = new ArrayList<String>();
                Media = Meanings.solve(hypernyms[0].toString());
                for(int l=0; l<Media.size(); l++){
                    System.out.println("Media element =  " + Media.get(l));
                    res.add(Media.get(l));
                }
                for(int j=0; j<hypernyms.length; j++){
                }
            }
             return res;
        }
        else if (POS == 'V'){
            System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict\\");
            VerbSynset verbSynset;
            VerbSynset[] hypernyms;
            WordNetDatabase database = WordNetDatabase.getFileInstance();
            Synset[] synsets = database.getSynsets(word, SynsetType.VERB);
            System.out.println("*********************************************");
            for (int i = 0; i < synsets.length; i++) {
                verbSynset = (VerbSynset)(synsets[i]);
                hypernyms = verbSynset.getHypernyms();
                for(int j=0; j<hypernyms.length; j++){
                    System.out.println("Hypers ==   " +  hypernyms[j]);
                    ArrayList<String> Media = new ArrayList<String>();
                    Media = Meanings.solve(hypernyms[j].toString());
                    for(int l=0; l<Media.size(); l++){
                        res.add(Media.get(l));
                    }
                }
//                System.err.println(verbSynset.getWordForms()[0] +": " + verbSynset.getDefinition() + ") has " + hypernyms.length + " hyponyms");
            }
             return res;
        }
        else if(POS == 'R'){
            System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict\\");
            AdverbSynset adverbSynset;
            WordSense[] hypernyms;
            WordNetDatabase database = WordNetDatabase.getFileInstance();
            Synset[] synsets = database.getSynsets(word, SynsetType.ADVERB);
            System.out.println("*********************************************");
            for (int i = 0; i < synsets.length; i++) {
                adverbSynset = (AdverbSynset)(synsets[i]);
                hypernyms = adverbSynset.getPertainyms(word);
                for(int j=0; j<hypernyms.length; j++){
                    System.out.println("Hypers ==   " +  hypernyms[j]);
                    ArrayList<String> Media = new ArrayList<String>();
                    Media = Meanings.solve(hypernyms[j].toString());
                    for(int l=0; l<Media.size(); l++){
                        res.add(Media.get(l));
                    }
                }
//                System.err.println(adverbSynset.getWordForms()[0] +": " + adverbSynset.getDefinition() + ") has " + hypernyms.length + " hyponyms");
            }
             return res;        
        }
        else if(POS == 'J'){
            System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict\\");
            AdjectiveSynset adjectiveSynset;
            AdjectiveSynset[] hypernyms;
            WordNetDatabase database = WordNetDatabase.getFileInstance();
            Synset[] synsets = database.getSynsets(word, SynsetType.ADJECTIVE);
            System.out.println("*********************************************");
            for (int i = 0; i < synsets.length; i++) {
                adjectiveSynset = (AdjectiveSynset)(synsets[i]);
                hypernyms = adjectiveSynset.getSimilar();
                for(int j=0; j<hypernyms.length; j++){
                    System.out.println("Hypers ==   " +  hypernyms[j]);
                    ArrayList<String> Media = new ArrayList<String>();
                    Media = Meanings.solve(hypernyms[j].toString());
                    for(int l=0; l<Media.size(); l++){
                        res.add(Media.get(l));
                    }
                }
//                System.err.println(adjectiveSynset.getWordForms()[0] +": " + adjectiveSynset.getDefinition() + ") has " + hypernyms.length + " hyponyms");
            }
             return res;
        }
//        Meanings.pr(res);
        return res;
    }
    
     
    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        //a[0] = {"smart"};
        String[] b = new String[2];
        b[0] = "age";
        b[1] = "bank";
        String hs = "";
        try {
            hs = readFileAsString("file1.txt");
            
        } catch (IOException ex) {
            Logger.getLogger(Meanings.class.getName()).log(Level.SEVERE, null, ex);
        }
        int c =1;
        String t1= "";
        String t2 = "";
        String tmp = "";
        PrintWriter wrt = new PrintWriter("file2.txt", "UTF-8");
        ArrayList<String> a = new ArrayList<String>() ;
        for(int i=1; i < hs.length(); i++){
            if(hs.charAt(i) == ' '){
                System.out.println("tmp = " + tmp);
                if(c %2 !=0){
                    t1 = tmp;
                    c++;
                    tmp = "";
                }
                else if(c %2 == 0){
                    t2 = tmp;
                    c++;
                    tmp = "";
                    if(t2.length() >= 1){
                        a =  (new Meanings()).getMeanings(t1, t2.charAt(0));
//                        pr(a);
                        for(int p=0; p<a.size(); p++){
                            wrt.print("["+a.get(p) + "] ");
                        }
                        wrt.print("\n");
                    }    
                }
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
        
    }

    public static void check(String[] wds, String[] pos, int n) throws FileNotFoundException, UnsupportedEncodingException {
        //a[0] = {"smart"};
        for(int i=0; i<n; i++){
            
        }
    }
    
}
