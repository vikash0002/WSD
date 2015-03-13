
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shubh
 */
public class MyResult {
        private static String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    public void getResults() throws IOException{
        String cscore = MyResult.readFileAsString("centrality_output.txt");
        String filename = "centrality_output.txt"; 
        Scanner in = new Scanner(filename).useDelimiter("\\n");

        double largest = 0;
        if (in.hasNextDouble())
            largest = in.nextDouble();

        while (in.hasNextDouble())
        {
            double input = in.nextDouble();
            if (input > largest)
            {
                largest = input;
            }
        }
   }
    public static void main(String[] args) throws IOException {
        Double ans[] = new Double[1000000];
        String cscore = MyResult.readFileAsString("centrality_output.txt");
        
        String tmp = "";
        int k=0;
        for(int i=0; i<cscore.length(); i++){
            if(cscore.charAt(i) == ' '){
                
                System.out.println(k + "   tmp = " + tmp);
                ans[k] = Double.valueOf(tmp);
                System.out.println("ans[k] =  " + ans[k]);
                k++;
                tmp = "";
            }else {
                tmp += cscore.charAt(i);
            }
        }
        
        int y = 0;
        System.out.println("shubh");
        System.out.println("Glob   " + GlobalClass.nds);
        for(int i=0; i<GlobalClass.nds; i++){
            System.out.println("ss");
            System.out.println("i = " + i);
            ArrayList<Double> ad = new ArrayList<>();
            int x = GlobalClass.wdnum.get(i);
            for(int t=0; t<x; t++){
                System.out.println("t+y  = " + (int)(t + y) );
                ad.add(ans[t+y]);
            }
            
            Collections.sort(ad);
            y += x;
        }
         
    }
    
}
