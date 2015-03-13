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

/**
 * Interface used by <code>SimulatedAnnealer</code> which represents the current 
 * best solution found during a search. 
 * Classes that implement this interface will typically have some
 * idea of the overall fitness or energy of any given solution and so will implement <code>computeEnergy</code> accordingly.
 * Similarly, they will have some idea of how to permute the configuration into another possible solution based
 * on the current "temperature" and the min,max values of the possible range of temperature and thus will implement
 * melt(...) accordingly.
 * 
 * @author Scott White
 */
public interface Configuration {

    /**
     * Computes the energy or fitness of the current configuration.
     * @return <code>double</code> value representing the computed energy 
     */
    double computeEnergy();

    /**
     * Randomly perturbs the current configuration in some way.
     * @param temperature the current temperature of the system
     * @param maxTemp the highest temperature the system can go to
     * @param minTemp the lowest temperature the system can go to
     * @return the newly perturbed configuration
     */
    Configuration melt(double temperature, double maxTemp, double minTemp);
}
