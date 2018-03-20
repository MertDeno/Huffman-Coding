package hufman;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

class HuffmanNode{
	 
    int data;
    char c;
 
    HuffmanNode left;
    HuffmanNode right;
}
class MyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y)
    {
 
        return x.data - y.data;
    }
}

public class readFile {	
	
	static TreeMap<Character, String> codes = new TreeMap<>();
	
    public static void printCode(HuffmanNode root, String s,HashMap<Character, String> codeMap)
    {

        if (root.left == null && root.right == null) {
        		System.out.println(root.c+":"+s);
        		codeMap.put(root.c, s);
            return;
        }
        
        printCode(root.left, s + "0",codeMap);
        printCode(root.right, s + "1",codeMap);
    }
	
	public static void main(String[] args) throws IOException{
        Map<Character, Integer> hashMap = new TreeMap<Character, Integer>();
        File file = new File("C://Users/mertd/mertdenizgungor/Huffmann/src/a1.txt");
        Scanner scanner = new Scanner(file,"utf-8");
        Scanner scanner1 = new Scanner(file,"utf-8");
        HashMap<Character, String> codeMap=new HashMap<Character, String>();
        String strToEnc="";
        FileReader fr = new FileReader(file);
        int i3;
        FileWriter writer = new FileWriter("C://Users/mertd/mertdenizgungor/Huffmann/src/hufman/a2.txt");
        
        
       while (scanner.hasNext()) {
        	char[] chars = scanner.nextLine().toCharArray();
        	
            for (Character c : chars) {
                if(!Character.isLetter(c) && Character.valueOf(c).equals(" ")){
                    continue;
                }
                else if (hashMap.containsKey(c)) {
                    hashMap.put(c, hashMap.get(c) + 1);
                } else {
                    hashMap.put(c, 1);
                }
            }
       }
 
//       while (scanner1.hasNext()) {
//    	   strToEnc+=scanner1.nextLine();
//       }
 
        int total=0,i=0,j=0;
        double total2=0,total3=0;
        double[] entropy = new double[60];
        int[] value=new int[60];
        String[] str=new String[60];
        
        System.out.println("Symbols"+" 	Frequency "+" 		Probability"+" 		     Entropy");
        writer.write("Symbols"+" 	Frequency "+" 		Probability"+" 		     Entropy");
        for (Entry<Character, Integer> entry : hashMap.entrySet()) {
            total+=entry.getValue();
            value[j]=entry.getValue();
            j++;
        }
       
        for (Entry<Character, Integer> entry : hashMap.entrySet()) {
        	entropy[i]=(double)entry.getValue()/total*(Math.log((double)entry.getValue()/total)/Math.log(2.0))*(-1);
        	System.out.println("  "+entry.getKey()+"		 "+value[i]+"			"+(double)entry.getValue()/total+" 		"+entropy[i]);
            total3+=entropy[i];
            total2+=(double)entry.getValue()/total;
            i++;
        }
        
        double max=Math.log(29)/Math.log(2);
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Summation Of Characters:"+total);
        System.out.println("Summation Of Probablilites:"+total2);
        System.out.println("Entropy:"+total3);	
        System.out.println("Maximnum Entropy:"+max); 
        System.out.println("Redundancy:"+(1-(total3/max)));

        PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(29, new MyComparator());
        
        for (Entry<Character, Integer> entry : hashMap.entrySet()) {
        	HuffmanNode hn = new HuffmanNode();
        	hn.c = entry.getKey();
            hn.data = entry.getValue();
            hn.left = null;
            hn.right = null;

            q.add(hn);
        }

        HuffmanNode root = null;

        while (q.size() > 1) {
 
            HuffmanNode x = q.peek();
            q.poll();
 
            HuffmanNode y = q.peek();
            q.poll();
 
            HuffmanNode f = new HuffmanNode();
 
            f.data = x.data + y.data;
            f.c = '-';
            f.left = x;
            f.right = y;
            root = f;
            q.add(f);
        }
        
        printCode(root, "",codeMap);
        String encoded = "";
        for (int k = 0; k < strToEnc.length(); k++) {
        	encoded +=codeMap.get(strToEnc.charAt(k));
        	System.out.println(k);
		}
        System.out.println(encoded);
        
	}

}
