/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wsd;

/**
 *
 * @author Shubh
 */
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class Hypernyms 
 {
    String a[]=new String[2];
    
    public static void findHypernyms(String a[])
    {
        int j=0;
        while(j<2)
        {
        
            System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict\\");
            NounSynset nounSynset;
            NounSynset[] hyponyms;
            WordNetDatabase database = WordNetDatabase.getFileInstance();
            Synset[] synsets = database.getSynsets(a[j], SynsetType.NOUN);
            System.out.println("*********************************************");
            for (int i = 0; i < synsets.length; i++)
                {
            nounSynset = (NounSynset)(synsets[i]);
            hyponyms = nounSynset.getHypernyms();
                for(int jj=0; jj<hyponyms.length; jj++){
//                    System.out.println("i = " + i +"  jj = " + jj  );
                    System.out.println("Hypers ==   " +  hyponyms[jj].toString());
                }
           
            System.err.println(nounSynset.getWordForms()[0] +": " + nounSynset.getDefinition() + ") has " + hyponyms.length + " hypernyms");
           
                }
            j++;
        }
         System.out.println("*********************************************");
    }
    public static void main(String[] args) {
        //a[0] = {"smart"};
        String[] b = new String[2];
        b[0] = "river";
        b[1] = "age";
        System.out.println("finding Hypernyms and Meanings for " + b[0] +" and "  + b[1]);
        Hypernyms.findHypernyms(b);
        
    }
}
