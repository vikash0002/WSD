import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import rita.wordnet.RiWordnet;

public class Graph_Wordnet {
    
    
    public double[][] mygraph() throws FileNotFoundException{
        
        RiWordnet wordnet = new RiWordnet(null);
        int graphN = GlobalClass.labels.size();
        double Grp[][] = new double[graphN][];
        for(int i=0; i<graphN; i++){
            Grp[i] = new double[graphN];
            for(int j=0; j<graphN; j++){
    //            System.out.println("hii------");
                Grp[i][j] = 0;
            }
        }
        ArrayList<Integer> wnum = new ArrayList<Integer>();
        int num = 1;
        System.out.println("grahn = " + graphN);
        for(int i=0; i<graphN; i++){
            int ans = 0;
            int j = i;
            while(GlobalClass.labels.get(j).wn == num && j < graphN){
                ans++;
                j++;
                if(j >= graphN){
                    break;
                }
            }
            System.out.println("ans = " + ans);
            wnum.add(ans);
            num++;
            i = j - 1;
        }
        
        GlobalClass.wdnum  = wnum;
        int iinc = 0;
        for(int i=0; i<GlobalClass.nds; i++){                // For all Nodes Possible.
            
            int jinc = wnum.get(i);
            for(int j = i+1; j < GlobalClass.nds; j++){
                int x = wnum.get(i);         // Number of nodes in the ith word
                int y = wnum.get(j);    
                for(int t = 0; t<x; t++){
                    int src =t+iinc;
                    for(int s = 0; s<y; s++){
                        int trg =s+jinc;
                        double ans = 0;
                        double ans1 = wordnet.getDistance(GlobalClass.labels.get(src).m,GlobalClass.labels.get(trg).m, "v");
                        double ans2 = wordnet.getDistance(GlobalClass.labels.get(src).m,GlobalClass.labels.get(trg).m, "n");
                        if(ans1 == 1 && ans2 == 1){
                            ans = 0;
                        }
                        else if(ans1 == 1 && ans2 != 1){
                            ans = ans2;
                        }
                        else if(ans1 !=1 && ans2 == 1) {
                            ans = ans1;
                        }
                        else {
                            ans = Math.max(ans1, ans2);
                        }
                        System.out.println("src = " + src + "  trg = " + trg);
                        Grp[src][trg] = ans;
                        Grp[trg][src] = ans;
                    }
                }
                jinc += wnum.get(j);
            }
            iinc += wnum.get(i);
        }
        
        
        for(int i=0; i<graphN; i++){
            for(int j=0; j<graphN; j++){
                System.out.print(Grp[i][j] + " ");
            }
            System.out.println("");
        }
        
        
        
     return Grp;
    }
        
  }
