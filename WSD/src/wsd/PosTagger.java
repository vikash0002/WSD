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
import java.io.IOException;
 
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
 
public class PosTagger {
    public static String findPOS(String args) throws IOException,
            ClassNotFoundException {
 
        // Initialize the tagger
        MaxentTagger tagger = new MaxentTagger(
                "taggers\\left3words-wsj-0-18.tagger");
 
        // The sample string
        String sample = args;
        // The tagged string
        String tagged = tagger.tagString(sample);
        String temp="";
        for(int i=0; i<tagged.length(); i++){
            if(tagged.charAt(i) == '/'){
                for(int j=i+1; j<tagged.length(); j++){
                    temp += tagged.charAt(j);
                }
            }
        }
        // Output the result
        System.out.println(temp);
        return temp;
    }
    public static void main(String[] args) throws IOException,
            ClassNotFoundException {
 
        // Initialize the tagger
        MaxentTagger tagger = new MaxentTagger(
                "taggers\\left3words-wsj-0-18.tagger");
 
        // The sample string
        String sample = "working";
        // The tagged string
        String tagged = tagger.tagString(sample);
 
        // Output the result
        System.out.println(tagged);
        PosTagger.findPOS("working");
    }
}