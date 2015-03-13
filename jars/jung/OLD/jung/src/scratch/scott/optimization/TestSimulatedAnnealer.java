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

import java.util.Random;

import cern.jet.random.Normal;
import cern.jet.random.engine.DRand;

/**
 * @author Scott White
 */
public class TestSimulatedAnnealer //extends TestCase {
{
//	public static Test suite() {
//		return new TestSuite(TestSimulatedAnnealer.class);
//	}

	protected void setUp() {

	}

//	public void testSimpleAnnealer() {
//        DoubleArrayList values = new DoubleArrayList();
//        for (int i=0; i<50; i++) {
//            TestConfiguration config = new TestConfiguration(-5.0);
//            SimulatedAnnealer annealer = new SimulatedAnnealer(config,10,0,0.999,200,0.9,2);
//            annealer.evaluate();
//            TestConfiguration solution = (TestConfiguration) annealer.getBestConfiguration();
//            values.add(solution.xValue);
//        }
//        Assert.assertTrue(Math.abs(Descriptive.mean(values)) < 1.0);

//    }

    public class TestConfiguration implements Configuration {
        Normal normal;
        Random random;
        double xValue;

        public TestConfiguration(double xval) {
            normal = new Normal(0,3.0,new DRand(2));
            xValue = xval;
            random = new Random(5);
        }

        public double computeEnergy() {
            return 1.0/normal.pdf(xValue);
        }

        public Configuration melt(double temperature, double maxTemp, double minTemp) {
            double newXVal = xValue;
            newXVal +=random.nextDouble()*Math.sqrt(temperature/(maxTemp-minTemp));
            if (random.nextDouble() < 0.5) {
                newXVal *= -1;
            }
            TestConfiguration newConfig = new TestConfiguration(newXVal);

            return newConfig;
        }

    }
}
