package momo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CountLine {
    private int cntCode=0, cntNode=0, cntSpace=0;
    private boolean flagNode = false;
    public int[] reA(String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line=null;
            while((line = br.readLine()) != null)
                pattern(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("注释行： " + cntNode);
        System.out.println("空行： " + cntSpace);
        System.out.println("代码行： " + cntCode);
        System.out.println("总行： " + (cntNode+cntSpace+cntCode));
        return new int[]{cntCode, cntSpace, cntNode};
    }

    private  void pattern(String line) {
        // TODO Auto-generated method stub
        String regxNodeBegin = "\\s*/\\*.*";
        String regxNodeEnd = ".*\\*/\\s*";
        String regx = "\\s*//.*|\\}//.*}";
        String regxSpace = "(\\s*)|(\\s*(\\{|\\})\\s*)";
        int i=line.length();
        if(line.matches(regxNodeBegin) && line.matches(regxNodeEnd)){
            ++cntNode;
            return ;
        }
        if(line.matches(regxNodeBegin)){
            ++cntNode;
            flagNode = true;
        } else if(line.matches(regxNodeEnd)){
            ++cntNode;
            flagNode = false;
        } else if(line.matches(regxSpace)||line.equals("\uFEFF"))
            ++cntSpace;
        else if(line.matches(regx))
            ++cntNode;
        else if(flagNode)
            ++cntNode;
        else ++cntCode;
    }
}
