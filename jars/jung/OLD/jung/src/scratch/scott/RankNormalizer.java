package scratch.scott;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;
import edu.uci.ics.jung.algorithms.importance.RandomWalkBetweenness;
import edu.uci.ics.jung.algorithms.importance.Ranking;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.decorators.Indexer;
import edu.uci.ics.jung.io.PajekNetReader;

/**
 * @author Scott White
 */
public class RankNormalizer {
    double[] mQuantiles;
    int mMin;

    public RankNormalizer(List rankings, int min, int max) {
        initialize(rankings, min, max);
    }

    private void initialize(List rankings, int min, int max) {
        mMin = min;
        DoubleArrayList scores = new DoubleArrayList();
        Collections.reverse(rankings);
        for (Iterator rIt = rankings.iterator(); rIt.hasNext();) {
            Ranking ranking = (Ranking) rIt.next();
            double score = ranking.rankScore;
            scores.add(score);
        }

        int range = max-min;
        mQuantiles = new double[range];
        double interval = 1.0/(double) range;
        double minScore = Descriptive.min(scores);
        double maxScore = Descriptive.max(scores);
        for (int i=1;i<=range;i++) {
            mQuantiles[i-1] = minScore + i*interval*(maxScore-minScore);
            //System.out.println(mQuantiles[i-1]);
        }
    }

    public int getNormalizedValue(double score) {
        for (int i=0;i<mQuantiles.length;i++) {
            //System.out.println(score);
            if (score < mQuantiles[i]) {
                return mMin + i;
            }
        }

        return mMin + mQuantiles.length;

    }

    public static void main(String[] args) throws IOException {
        PajekNetReader pnr = new PajekNetReader();
        Graph g = pnr.load("samples/datasets/zachary.net");
//        PajekNetFile reader = new PajekNetFile();
//        Graph g = reader.load("samples/datasets/zachary.net");
        Indexer id = Indexer.newIndexer(g,0);
        RandomWalkBetweenness ranker = new RandomWalkBetweenness((UndirectedGraph) g);
        ranker.evaluate();
        List rankings = ranker.getRankings();
        RankNormalizer normalizer = new RankNormalizer(rankings,10,20);

        for (Iterator rIt = rankings.iterator();rIt.hasNext();) {
            Ranking r = (Ranking) rIt.next();
            double score = r.rankScore;

            System.out.println(normalizer.getNormalizedValue(score) + " " + score);
        }


    }

}
