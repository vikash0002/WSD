
package wordnetsimilarity;

/**
 *
 * @author Shubh
 */
import java.util.ArrayList;
import java.util.List;

import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.Relatedness;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.util.PathFinder.Subsumer;

public class LeacockChodorow extends RelatednessCalculator {

        protected static double min = 0; // or -Double.MAX_VALUE ?
        protected static double max = Double.MAX_VALUE;

        @SuppressWarnings("serial")
        private static List<POS[]> posPairs = new ArrayList<POS[]>(){{
                add(new POS[]{POS.n,POS.n});
                add(new POS[]{POS.v,POS.v});
        }};
        
        public LeacockChodorow(ILexicalDatabase db) {
                super(db);
        }

        @Override
        protected Relatedness calcRelatedness( Concept synset1, Concept synset2 ) {
                StringBuilder tracer = new StringBuilder();
                if ( synset1 == null || synset2 == null ) return new Relatedness( min, null, illegalSynset );
                
                StringBuilder subTracer = enableTrace ? new StringBuilder() : null;
                List<Subsumer> lcsList = pathFinder.getLCSByPath( synset1, synset2, subTracer );
                if ( lcsList.size() == 0 ) return new Relatedness( min );
                
                int maxDepth = 1;
                if ( synset1.getPos().equals( POS.n ) ) {
                        maxDepth = 10;
                } else if ( synset1.getPos().equals( POS.v ) ) {
                        maxDepth = 5;
                }
                
                int length = lcsList.get( 0 ).length;
                System.out.println("legth = " + length );
                double score = -Math.log( (double)length / (double)( 2 * maxDepth ) );
                
                if ( enableTrace ) {
                        tracer.append( subTracer.toString() );
                        for ( Subsumer lcs : lcsList ) {
                                tracer.append( "Lowest Common Subsumer(s): ");
                                tracer.append( db.conceptToString( lcs.subsumer.getSynset() )+" (Length="+lcs.length+")\n" );
                        }
                }
                                
                return new Relatedness( score, tracer.toString(), null );
        }
        
        @Override
        public List<POS[]> getPOSPairs() {
                return posPairs;
        }
}

