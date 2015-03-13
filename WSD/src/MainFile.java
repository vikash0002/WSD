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
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import rita.wordnet.RiWordnet;
import wsd.Meanings;



public class MainFile {
    public static void pr(ArrayList<String> ss){
        for(int i=0; i<ss.size(); i++){
            System.out.println("ss = " + ss.get(i));
        }
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
    /*
    public static String remove(String s){
        String ans = "";
        String tmp = "";
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == ' '){
                System.out.println("tmp  =" + tmp+ "{");
                tmp = tmp.toLowerCase();
                if(tmp.equals("my") ||tmp.equals("by") ||tmp.equals("that") ||tmp.equals("whether") ||tmp.equals("which") ||tmp.equals("could") ||tmp.equals("our") ||tmp.equals("with") ||tmp.equals("for") ||tmp.equals("won't") ||tmp.equals("they") ||tmp.equals("don't") ||tmp.equals("of") ||tmp.equals("him") || tmp.equals("to") || tmp.equals("your") || tmp.equals("you") ||tmp.equals("are") || tmp.equals("am") || tmp.equals("an") || tmp.equals("the") || tmp.equals("a") || tmp.equals("has") || tmp.equals("had") || tmp.equals("have") || tmp.equals("was") || tmp.equals("were") || tmp.equals("be") || tmp.equals("been") || tmp.equals("is") ) {
                    tmp = "";
                    System.out.println("tmp made empty");
                }
                else {
                    if(tmp != " ") {
                        ans += tmp;
                        ans += ' ';
                        tmp = "";
                    }
                }
            }
            else {
                tmp += s.charAt(i);
            }
        }
        
        System.out.println("ans = }" + ans + "{");
        System.out.println("tmp remaining = " + tmp);
        if(tmp.equals("my") ||tmp.equals("that") ||tmp.equals("by") ||tmp.equals("whether") ||tmp.equals("which") ||tmp.equals("could") ||tmp.equals("our") ||tmp.equals("with") ||tmp.equals("for") ||tmp.equals("won't") ||tmp.equals("they") ||tmp.equals("don't") ||tmp.equals("of") ||tmp.equals("him") || tmp.equals("to") || tmp.equals("your") || tmp.equals("you") ||tmp.equals("are") || tmp.equals("am") || tmp.equals("an") || tmp.equals("the") || tmp.equals("a") || tmp.equals("has") || tmp.equals("had") || tmp.equals("have") || tmp.equals("was") || tmp.equals("were") || tmp.equals("be") || tmp.equals("been") || tmp.equals("is") ) 
                    tmp = "";
        if(tmp !=" " && tmp !=""){
            ans = ans + tmp + " ";
        }
        System.out.println("ans = }" + ans + "{");
        return ans;
    }*/
    public static String remove(String s){
        String ans = "";
        String tmp = "";
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == ' '){
                System.out.println("tmp  =" + tmp+ "{");
                tmp = tmp.toLowerCase();
                if(tmp.equals("by") ||tmp.equals("that") ||tmp.equals("whether") ||tmp.equals("which") ||tmp.equals("could") ||tmp.equals("our") ||tmp.equals("with") ||tmp.equals("for") ||tmp.equals("won't") ||tmp.equals("they") ||tmp.equals("don't") ||tmp.equals("of") ||tmp.equals("him") || tmp.equals("to") || tmp.equals("your") || tmp.equals("you") ||tmp.equals("are") || tmp.equals("am") || tmp.equals("an") || tmp.equals("the") ||tmp.equals("these")||tmp.equals("i")||tmp.equals("me")||tmp.equals("won't")||tmp.equals("his")||tmp.equals("she")||tmp.equals("if")||tmp.equals("from")||tmp.equals("with")||tmp.equals("everything")||tmp.equals("their")||tmp.equals("her")||tmp.equals("mom")||tmp.equals("and")||tmp.equals("them")||tmp.equals("could")||tmp.equals("for")||tmp.equals("how")||tmp.equals("when")||tmp.equals("my")||tmp.equals("should")||tmp.equals("can't") ||tmp.equals("this")||tmp.equals("they")||tmp.equals("hadn't")||tmp.equals("us")||tmp.equals("that")||tmp.equals("whether") ||tmp.equals("don't")||tmp.equals("our") ||tmp.equals("of") ||tmp.equals("him") || tmp.equals("to") || tmp.equals("your") || tmp.equals("you") ||tmp.equals("are") || tmp.equals("am") || tmp.equals("an") || tmp.equals("the") || tmp.equals("a") || tmp.equals("has") || tmp.equals("had") || tmp.equals("have") || tmp.equals("was") || tmp.equals("were") || tmp.equals("be") || tmp.equals("been") || tmp.equals("is") ) {
                    tmp = "";
                    System.out.println("tmp made empty");
                }
                else {
                    if(tmp != " ") {
                        ans += tmp;
                        ans += ' ';
                        tmp = "";
                    }
                }
            }
            else {
                tmp += s.charAt(i);
            }
        }
        System.out.println("ans = }" + ans + "{");
        System.out.println("tmp remaining = " + tmp);
        if(tmp.equals("by") ||tmp.equals("that") ||tmp.equals("whether") ||tmp.equals("which") ||tmp.equals("could") ||tmp.equals("our") ||tmp.equals("with") ||tmp.equals("for") ||tmp.equals("won't") ||tmp.equals("they") ||tmp.equals("don't") ||tmp.equals("of") ||tmp.equals("him") || tmp.equals("to") || tmp.equals("your") || tmp.equals("you") ||tmp.equals("are") || tmp.equals("am") || tmp.equals("an") || tmp.equals("the") ||tmp.equals("these") ||tmp.equals("i")||tmp.equals("me")||tmp.equals("won't") ||tmp.equals("her")||tmp.equals("his")||tmp.equals("she")||tmp.equals("if")||tmp.equals("from")||tmp.equals("with")||tmp.equals("everything")||tmp.equals("their")||tmp.equals("mom")||tmp.equals("them")||tmp.equals("and")||tmp.equals("could")||tmp.equals("for")||tmp.equals("can't")||tmp.equals("how")||tmp.equals("when")||tmp.equals("my")||tmp.equals("should") ||tmp.equals("this")||tmp.equals("they")||tmp.equals("that")||tmp.equals("hadn't")||tmp.equals("us")||tmp.equals("whether") ||tmp.equals("don't")||tmp.equals("our") ||tmp.equals("of") ||tmp.equals("him") || tmp.equals("to") || tmp.equals("your") || tmp.equals("you") ||tmp.equals("are") || tmp.equals("am") || tmp.equals("an") || tmp.equals("the") || tmp.equals("a") || tmp.equals("has") || tmp.equals("had") || tmp.equals("have") || tmp.equals("was") || tmp.equals("were") || tmp.equals("be") || tmp.equals("been") || tmp.equals("is") ) 
                    tmp = "";
        if(tmp !=" " && tmp !=""){
            ans = ans + tmp + " ";
        }
        System.out.println("ans = }" + ans + "{");
        return ans;
    }
    public void mainWork(String args[]) throws IOException{
        String inputFile ="src\\input\\myInput.txt";
        String HFile ="myHfile.txt";
        String mytrg = inputClass.tar;
        
        
        String target ="";
//        String text = MainFile.readFileAsString(inputFile);
        String text = inputClass.sent;
        String context = "";
        System.out.println("Given file are \n+" + text + "+");
        System.out.println("Removing unnecessary information :-");
        context = MainFile.remove(text);
        System.out.println("Given file is \n+" + context +"+ ");
        String[] words = new String[context.length()];
        String[] pos = new String[context.length()];
        String temp = "";
        int k = 0;
        RiWordnet wordnet2 = new RiWordnet(null);
        for(int i=0; i<context.length(); i++){
            if(context.charAt(i) == ' ' && (temp !=" " || temp != "  ") ){
                if(temp.charAt(temp.length() -1) == '.'){
                    temp = temp.substring(0, temp.length()-1);
//                    System.out.println("temp = " + "   " + temp);
                }
                words[k] = temp;
                temp = temp.toLowerCase();
                System.out.println("temp = " + temp + "   size" + temp.length());
  //              pos[k] = wsd.PosTagger.findPOS(temp);
                  pos[k] = wordnet2.getBestPos(temp);
                  System.out.println(k + "      pos = " + pos[k]);
                k++;
                temp = "";
            }
            temp += context.charAt(i);
        }
 // J = Adjective    N = noun   V = verb   R = adverb       
        String givenWords[] = new String[k];
        String givenPos[] = new String[k];
        int ind =0;
        for(int i=0; i < k; i++){
            System.out.println("pos[k] ==  " + pos[i]);
            if(pos[i].equals("a") || pos[i].equals("r") || pos[i].equals("n") || pos[i].equals("v") ){
                givenWords[ind] = words[i];
                givenPos[ind] = pos[i];
                ind++;
            }
        }
        ArrayList<String> al = new ArrayList<String>();
        ArrayList<Character> psal = new ArrayList<Character>();
        PrintWriter wr = new PrintWriter("file1.txt","UTF-8");
        PrintWriter wrc = new PrintWriter("context.txt","UTF-8");
        for(int i=0; i<ind; i++){
            System.out.println(givenWords[i] + " " + givenPos[i]);
            wr.print(givenWords[i] +" ");
            wrc.print(givenWords[i]+"\n");
            wr.print(givenPos[i].charAt(0));
            psal.add(givenPos[i].charAt(0));
            al.add(givenWords[i]);
        }
        GlobalClass.posal = psal;
        wr.print(" ");
        wr.close();
        wrc.close();
//        (new Meanings()).mymeanings();
        //----------------------------------------------------------------------------------------------------------
        (new FindMeanings()).findMeanings(args);
        
        for(int i=0; i<GlobalClass.labels.size(); i++){
            System.out.println(i  + " m = "+GlobalClass.labels.get(i).m + "  n  = " + GlobalClass.labels.get(i).n + "     wordnum = " + GlobalClass.labels.get(i).wn);  
        }
        
        ArrayList<Integer> inum = new ArrayList<Integer>();
        
        MakeGraph mg = new MakeGraph();
        double gr[][] = mg.graph();
     //   Graph_Wordnet gwnt = new Graph_Wordnet();
     //   double gr2[][] = gwnt.mygraph();
        Graph_Algos gob = new Graph_Algos();
        ArrayList<Double> ans = new ArrayList<Double>();
//        for(int i=0; i<GlobalClass.labels.size(); i++){
//            ans.set(i, Double.valueOf(0));
//        }
        PrintWriter cout = new PrintWriter("centrality_output.txt","UTF-8");
         
        ans = gob.getCentralityScores(gr);
        GlobalClass.gloans = ans;
        ArrayList<Pairvalues> finalans = new ArrayList<Pairvalues>();
        System.out.println("ans.size() = " + ans.size());
        for(int i=0; i<GlobalClass.labels.size(); i++){
  /*          if(ans.get(i) == null){
                ans.set(ind, Double.MIN_VALUE);
            }
             
    */      Pairvalues pv = new Pairvalues();
            System.out.println(i + "   centrality value = " + ans.get(i));
              if(ans.get(i) == null){
                  pv.d = 0;
                  pv.ind = i;
                  finalans.add(pv);
                  cout.print(0 + " ");
              }
              else {
                  pv.d = ans.get(i);
                  pv.ind = i;
                  finalans.add(pv);
                  cout.print(ans.get(i) + " ");
              }
        }
        cout.close();
        
                Double an[] = new Double[1000000];
        String cscore = readFileAsString("centrality_output.txt");
        
        String tmp = "";
        int kk=0;
        for(int i=0; i<cscore.length(); i++){
            if(cscore.charAt(i) == ' '){
                
                System.out.println(kk + "   tmp = " + tmp);
                an[kk] = Double.valueOf(tmp);
                System.out.println("ans[k] =  " + an[kk]);
                kk++;
                tmp = "";
            }else {
                tmp += cscore.charAt(i);
            }
        }
        
        System.out.println("----------------------------------------------------------------------------");
        for(int i=0; i<finalans.size(); i++){
            System.out.println("finalans.d == " + finalans.get(i).d  + "   "  + finalans.get(i).ind);
        }
        System.out.println("----------------------------------------------------------------------------");

        ArrayList<ArrayList<Pairvalues>> AllResults = new ArrayList<ArrayList<Pairvalues>>(); 
        RiWordnet wordnet1 = new RiWordnet(null);
        int y = 0;
        System.out.println("shubh");
        System.out.println("Glob   " + GlobalClass.nds);
        for(int i=0; i<GlobalClass.nds; i++){
            System.out.println("ss");
            System.out.println("i = " + i);
            ArrayList<Pairvalues> ad = new ArrayList<Pairvalues>();
            int x = GlobalClass.wdnum.get(i);
            for(int t=0; t<x; t++){
                System.out.println("t+y  = " + (int)(t + y) );
                ad.add(finalans.get(t+y));
            }
//            Comparator<Double> comparator = Collections.reverseOrder();
            Collections.sort(ad, new Comparator<Pairvalues>() {
            @Override public int compare(Pairvalues p1, Pairvalues p2) {
                if(p1.d < p2.d){
                    return 1;
                }
                else if(p1.d > p2.d){
                    return -1;
                }
                else {
                    return 0;
                }
            }

    } );
            for(int l=0; l<ad.size(); l++){
                System.out.println("ad[l]   =   "+ ad.get(l).d);     
            }
            y += x;
            System.out.println("______________________         addint to Allresults");
            AllResults.add(ad);
        }
                ArrayList<String> allstring = new ArrayList<String>();

        System.out.println("targetIndex Size = " + AllResults.size());
        int targetIndex = GlobalClass.trgindex;
        int totalans = AllResults.get(targetIndex).size();
        double bestans = AllResults.get(targetIndex).get(0).d;
        int yy = 0;
        if(bestans != 0) {
            while(AllResults.get(targetIndex).get(yy).d == bestans){
                int xy = AllResults.get(targetIndex).get(yy).ind;
                System.out.println(" ans = " + GlobalClass.labels.get(xy).m);
                System.out.println(" ans = " + wordnet1.getDescription(GlobalClass.labels.get(xy).n));
                allstring.add(wordnet1.getDescription(GlobalClass.labels.get(xy).n));
                yy++;
            }
        }
        else {
            
        }
        GlobalClass.glostr = allstring;
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String inputFile ="src\\input\\myInput.txt";
        String HFile ="myHfile.txt";
        String mytrg = inputClass.tar;
        
        
        String target ="";
        String text = MainFile.readFileAsString(inputFile);
//        String text = inputClass.sent;
        String context = "";
        System.out.println("Given file are \n+" + text + "+");
        System.out.println("Removing unnecessary information :-");
        context = MainFile.remove(text);
        System.out.println("Given file is \n+" + context +"+ ");
        String[] words = new String[context.length()];
        String[] pos = new String[context.length()];
        String temp = "";
        int k = 0;
        RiWordnet wordnet2 = new RiWordnet(null);
        for(int i=0; i<context.length(); i++){
            if(context.charAt(i) == ' ' && (temp !=" " || temp != "  ") ){
                if(temp.charAt(temp.length() -1) == '.'){
                    temp = temp.substring(0, temp.length()-1);
//                    System.out.println("temp = " + "   " + temp);
                }
                words[k] = temp;
                temp = temp.toLowerCase();
                System.out.println("temp = " + temp + "   size" + temp.length());
  //              pos[k] = wsd.PosTagger.findPOS(temp);
                  pos[k] = wordnet2.getBestPos(temp);
                  System.out.println(k + "      pos = " + pos[k]);
                k++;
                temp = "";
            }
            temp += context.charAt(i);
        }
 // J = Adjective    N = noun   V = verb   R = adverb       
        String givenWords[] = new String[k];
        String givenPos[] = new String[k];
        int ind =0;
        for(int i=0; i < k; i++){
            System.out.println("pos[k] ==  " + pos[i]);
            if(pos[i].equals("a") || pos[i].equals("r") || pos[i].equals("n") || pos[i].equals("v") ){
                givenWords[ind] = words[i];
                givenPos[ind] = pos[i];
                ind++;
            }
        }
        ArrayList<String> al = new ArrayList<String>();
        ArrayList<Character> psal = new ArrayList<Character>();
        PrintWriter wr = new PrintWriter("file1.txt","UTF-8");
        PrintWriter wrc = new PrintWriter("context.txt","UTF-8");
        for(int i=0; i<ind; i++){
            System.out.println(givenWords[i] + " " + givenPos[i]);
            wr.print(givenWords[i] +" ");
            wrc.print(givenWords[i]+"\n");
            wr.print(givenPos[i].charAt(0));
            psal.add(givenPos[i].charAt(0));
            al.add(givenWords[i]);
        }
        GlobalClass.posal = psal;
        wr.print(" ");
        wr.close();
        wrc.close();
//        (new Meanings()).mymeanings();
        //----------------------------------------------------------------------------------------------------------
        (new FindMeanings()).findMeanings(args);
        
        for(int i=0; i<GlobalClass.labels.size(); i++){
            System.out.println(i  + " m = "+GlobalClass.labels.get(i).m + "  n  = " + GlobalClass.labels.get(i).n + "     wordnum = " + GlobalClass.labels.get(i).wn);  
        }
        
        ArrayList<Integer> inum = new ArrayList<Integer>();
        
        MakeGraph mg = new MakeGraph();
        double gr[][] = mg.graph();
     //   Graph_Wordnet gwnt = new Graph_Wordnet();
     //   double gr2[][] = gwnt.mygraph();
        Graph_Algos gob = new Graph_Algos();
        ArrayList<Double> ans = new ArrayList<Double>();
//        for(int i=0; i<GlobalClass.labels.size(); i++){
//            ans.set(i, Double.valueOf(0));
//        }
        PrintWriter cout = new PrintWriter("centrality_output.txt","UTF-8");
         
        ans = gob.getCentralityScores(gr);
        GlobalClass.gloans = ans;
        ArrayList<Pairvalues> finalans = new ArrayList<Pairvalues>();
        System.out.println("ans.size() = " + ans.size());
        for(int i=0; i<GlobalClass.labels.size(); i++){
  /*          if(ans.get(i) == null){
                ans.set(ind, Double.MIN_VALUE);
            }
             
    */      Pairvalues pv = new Pairvalues();
            System.out.println(i + "   centrality value = " + ans.get(i));
              if(ans.get(i) == null){
                  pv.d = 0;
                  pv.ind = i;
                  finalans.add(pv);
                  cout.print(0 + " ");
              }
              else {
                  pv.d = ans.get(i);
                  pv.ind = i;
                  finalans.add(pv);
                  cout.print(ans.get(i) + " ");
              }
        }
        cout.close();
        
                Double an[] = new Double[1000000];
        String cscore = readFileAsString("centrality_output.txt");
        
        String tmp = "";
        int kk=0;
        for(int i=0; i<cscore.length(); i++){
            if(cscore.charAt(i) == ' '){
                
                System.out.println(kk + "   tmp = " + tmp);
                an[kk] = Double.valueOf(tmp);
                System.out.println("ans[k] =  " + an[kk]);
                kk++;
                tmp = "";
            }else {
                tmp += cscore.charAt(i);
            }
        }
        
        System.out.println("----------------------------------------------------------------------------");
        for(int i=0; i<finalans.size(); i++){
            System.out.println("finalans.d == " + finalans.get(i).d  + "   "  + finalans.get(i).ind);
        }
        System.out.println("----------------------------------------------------------------------------");

        ArrayList<ArrayList<Pairvalues>> AllResults = new ArrayList<ArrayList<Pairvalues>>(); 
        RiWordnet wordnet1 = new RiWordnet(null);
        int y = 0;
        System.out.println("shubh");
        System.out.println("Glob   " + GlobalClass.nds);
        for(int i=0; i<GlobalClass.nds; i++){
            System.out.println("ss");
            System.out.println("i = " + i);
            ArrayList<Pairvalues> ad = new ArrayList<Pairvalues>();
            int x = GlobalClass.wdnum.get(i);
            for(int t=0; t<x; t++){
                System.out.println("t+y  = " + (int)(t + y) );
                ad.add(finalans.get(t+y));
            }
//            Comparator<Double> comparator = Collections.reverseOrder();
            Collections.sort(ad, new Comparator<Pairvalues>() {
            @Override public int compare(Pairvalues p1, Pairvalues p2) {
                if(p1.d < p2.d){
                    return 1;
                }
                else if(p1.d > p2.d){
                    return -1;
                }
                else {
                    return 0;
                }
            }

    } );
            for(int l=0; l<ad.size(); l++){
                System.out.println("ad[l]   =   "+ ad.get(l).d);     
            }
            y += x;
            System.out.println("______________________         addint to Allresults");
            AllResults.add(ad);
        }
        ArrayList<String> allstring = new ArrayList<String>();
        System.out.println("targetIndex Size = " + AllResults.size());
        int targetIndex = GlobalClass.trgindex;
        int totalans = AllResults.get(targetIndex).size();
        double bestans = AllResults.get(targetIndex).get(0).d;
        int yy = 0;
        if(bestans != 0) {
            while(AllResults.get(targetIndex).get(yy).d == bestans){
                int xy = AllResults.get(targetIndex).get(yy).ind;
                System.out.println(" ans = " + GlobalClass.labels.get(xy).m);
                System.out.println(" ans = " + wordnet1.getDescription(GlobalClass.labels.get(xy).n));
                allstring.add(wordnet1.getDescription(GlobalClass.labels.get(xy).n));
                yy++;
            }
        }
        else {
            
        }
        GlobalClass.glostr = allstring;
        
        
//        wsd.Meanings nwM = new wsd.Meanings();
    }
    
}
