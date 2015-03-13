/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.scott.optimization;

import edu.uci.ics.jung.algorithms.IterativeProcess;
import cern.jet.random.Uniform;

/**
 * A general implementation of simulated annealing.
 * Requires the user to provide his/her own <code>Configuration</code> implementation representing the current best solution
 * instance. In addition several parameters are required to instruct the annealer how best to proceed. These include:
 * <ul>
 * <li>the starting temperature where the annealer should begin its annealing process</li>
 * <li>the freezinng point at which the annealer should no more annealing can take place</li>
 * <li>cooling rate indicating how fast the annealer should cool down after each iteration</li>
 * <li>how many times the annealer should look for a new solution between iterations</li>
 * <li>the acceptance rate threshold</li>
 * </ul>
 * <p>
 * In most cases, significant tweaking of the parameters is required before fast convergence is achieved.
 *
 * @author Scott White
 */
public class SimulatedAnnealer extends IterativeProcess {
    private double mStartingTemperature;
    private double mFreezingPoint;
    private double mCurrentTemperature;
    private Configuration mCurrentBestConfiguration;
    private double mCoolingRate;
    private double mNumTries;
    private Uniform mRandomNumGenerator;
    private double mAcceptanceRate;

    public SimulatedAnnealer(Configuration solution,double startingTemperature, double freezingPoint,double coolingRate, double nTries,double acceptanceRate, int seed) {
        mCurrentBestConfiguration = solution;
        mFreezingPoint = freezingPoint;
        mStartingTemperature = mCurrentTemperature = startingTemperature;
        mRandomNumGenerator = new Uniform(0,1, seed);
        mCoolingRate = coolingRate;
        mNumTries = nTries;
        mAcceptanceRate = Math.max(0,Math.min(acceptanceRate,1));
    }

    protected double evaluateIteration() {
        return anneal();
    }

    private int anneal() {
        double errorChange = 0;
        Configuration possibleConfiguration = null;
        int numSuccesses = 0;
        double currentEnergy = mCurrentBestConfiguration.computeEnergy();
        double newEnergy = 0;

        for (int timeStep=0;timeStep<mNumTries;timeStep++) {
            possibleConfiguration = mCurrentBestConfiguration.melt(mCurrentTemperature,mStartingTemperature,mFreezingPoint);
            newEnergy = possibleConfiguration.computeEnergy();
            errorChange = newEnergy - currentEnergy;

            if (oracleVotesToChange(errorChange)) {
                mCurrentBestConfiguration = possibleConfiguration;
                currentEnergy = newEnergy;
                numSuccesses++;
            }
        }

        double currentAcceptanceRate = numSuccesses/mNumTries;

        mCurrentTemperature = Math.pow(mCoolingRate,(mAcceptanceRate-currentAcceptanceRate)*mNumTries)*mCurrentTemperature;

        if (mCurrentTemperature <= mFreezingPoint) {
            return 0;
        }

        return numSuccesses;
    }

    /**
     * Returns the best solution found so far.
     * @return the solution configuration
     */
    public Configuration getBestConfiguration()
    {
        return mCurrentBestConfiguration;
    }

    private boolean oracleVotesToChange(double errorChange) {
        if (errorChange < 0) {
            return true;
        }

        double randomNum = mRandomNumGenerator.nextDouble();
        double annealingProbability = Math.exp(-1*errorChange/mCurrentTemperature);

        if (randomNum < Math.min(1,annealingProbability)) {
            return true;
        }

        return false;
    }
}
