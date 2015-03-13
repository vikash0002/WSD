
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shubh
 */
public class GlobalClass {
    public static int nds;      // total number of all nodes 
    public static int nodesSize;
    public static int trgindex ;
    public static ArrayList<Double> gloans;
    public static ArrayList<String> glostr;
    public static ArrayList<ArrayList<Pairvalues>> gloAllResults;
    public static ArrayList<Integer> wdnum;
    public static ArrayList<Character> posal = new ArrayList<Character>();
    public ArrayList<String> wrdstr;
    public static ArrayList<Pair> labels;
    public int getNodesSize() {
        return nodesSize;
    }

    public ArrayList<String> getWrdstr() {
        return wrdstr;
    }

    public void setWrdstr(ArrayList<String> wrdstr) {
        this.wrdstr = wrdstr;
    }

    public void setNodesSize(int nodesSize) {
        this.nodesSize = nodesSize;
    }

    public ArrayList<Pair> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Pair> labels) {
        this.labels = labels;
    }
}
