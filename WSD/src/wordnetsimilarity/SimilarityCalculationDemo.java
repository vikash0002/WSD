package wordnetsimilarity;

/**
 *
 * @author Shubh
 */
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.util.DepthFinder;
import edu.cmu.lti.ws4j.util.PathFinder;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;
import edu.cmu.lti.ws4j.util.WordSimilarityCalculator;
import java.util.List;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

public class SimilarityCalculationDemo {
        
        private static ILexicalDatabase db = new NictWordNet();
        private static RelatednessCalculator[] rcs = {
                         new JiangConrath(db)
                        };
        
         private static double run( String word1, String word2 ) {
                double bestans = 0.0;
                WS4JConfiguration.getInstance().setMFS(true);
                for ( RelatednessCalculator rc : rcs ) {
                       Concept c1 = new Concept(word1);
                       Concept c2 = new Concept(word2);
//                       rc.calcRelatednessOfSynset(c1, c2);
                       bestans  = Math.max(bestans,rc.calcRelatednessOfWords(word1, word2));
//                       System.out.println( rc.getClass().getName()+"\t"+bestans );
                }
                return bestans;
        }
        public static double getDependencyScore(String m1, String m2){
            double ans = 0.0;
            String temp1 = "";
            for(int i=0; i<m1.length(); i++){
                if(m1.charAt(i) == ' '){
                    String temp2 = "";
                    for(int j=0; j<m2.length(); j++){
                        if(m2.charAt(j) == ' '){
//                            System.out.println("www = "  + temp1 + "             " + temp2);
                            ans = Math.max(ans, run(temp1, temp2));
                            temp2 = "";
                        }
                        else {
                            temp2 += m2.charAt(j);
                        }
                    }
                    if(temp2.length() > 0){
                        ans = Math.max(ans, run(temp1, temp2));
//                        System.out.println("www = "  + temp1 + "             " + temp2);
                    }
                    temp1 = "";
                }
                else {
                    temp1 += m1.charAt(i);
                }
                
            }
            if(temp1.length() > 0){
                    String temp2 = "";
                    for(int j=0; j<m2.length(); j++){
                        if(m2.charAt(j) == ' '){
//                            System.out.println("www = "  + temp1 + "             " + temp2);
                            ans = Math.max(ans, run(temp1, temp2));
                            temp2 = "";
                        }
                        else {
                            temp2 += m2.charAt(j);
                        }
                    }
                    if(temp2.length() > 0){
                        ans = Math.max(ans, run(temp1, temp2));
//                        System.out.println("www = "  + temp1 + "             " + temp2);
                    }
                    
                } 
            
            return ans;
        }
        public static void main(String[] args) {
                long t0 = System.currentTimeMillis();
//                run("financial","time");
                System.out.println("\n");
//                run( "age","period" );
                long t1 = System.currentTimeMillis();
                System.out.println( "Done in "+(t1-t0)+" msec." );
                double ans = getDependencyScore("financial organisation","medium of exchange");
                System.out.println("ans = " + ans);
        }
}
