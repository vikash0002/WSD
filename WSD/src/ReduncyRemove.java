/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shubh
 */



import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Vikash
 */
public class ReduncyRemove {

    /**
     * @param args the command line arguments
     */
    
    public void removeReduncy(String file) throws FileNotFoundException{
        File f = new File(file);
        PrintWriter writer = new PrintWriter("output.txt");
        Scanner in = new Scanner(f);
        int k = 0;
        Set<String> set = new HashSet();
        
        while(in.hasNextLine()) {
            String s = in.nextLine();
            System.out.println("s.size()" + s.length());
            //System.out.println("line is " + s);
            String ss = "";
            for(int i = 0; i < s.length()-1; i++) {
                while(i >=0 && s.charAt(i) != '[') i++;
                i++;
                String ans = "";
                while(s.charAt(i) != ']'){
                    ans += s.charAt(i);
                    i++;
                }
            System.out.println("-----------------------------------ans = " + ans);
            set.add(ans);
            
            }
            Iterator it = set.iterator();
            while(it.hasNext()){
                writer.print( "[" + it.next() + "] ");
            }
            writer.print("\n");
            set.clear();
        }
        writer.close();
        
    }
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        
        String fliename = "file2.txt";
        ReduncyRemove ob = new ReduncyRemove();
        ob.removeReduncy(fliename);
    }
    
}