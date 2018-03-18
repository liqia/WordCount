import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCount {
    public static void main(String[] args) {
        int[] canshu=new int[5];//0-4分别表示字符、单词、行数、代码行数/空行数/注释行、递归处理 的参数存在不存在
        String file = new String();
        String outputFile = new String();
        String stopListFile = new String();
        int flag=0;
        for(int i=0;i<args.length;i++) {
            if (args[i].equals("-c")) canshu[0]=1;
            else if (args[i].equals("-w")) canshu[1]=1;
            else if (args[i].equals("-l")) canshu[2]=1;
            else if (args[i].equals("-a")) canshu[3]=1;
            else if (args[i].equals("-s")) canshu[4]=1;
            else if (args[i].equals("-o"))
            {
                if (i==args.length-1) erro("参数不匹配");
                if (Pattern.compile("([a-z]|[A-Z]|[0-9])+\\.txt").matcher(args[i+1]).find())
                {
                    outputFile=args[i+1];
                    i++;
                }
                else {
                    erro("输出文件名不正确");
                }
            }
            else if (args[i].equals("-e"))
            {
                if (i==args.length-1) erro("参数不匹配");
                if (Pattern.compile("([a-z]|[A-Z]|[0-9])+\\.txt").matcher(args[i+1]).find())
                {
                    stopListFile=args[i+1];
                    i++;
                }
                else {
                    erro("参数不匹配");
                }
            }
            else if (Pattern.compile("([a-z]|[A-Z]|[0-9])+\\.txt").matcher(args[i]).find()) {
                if (i==0) erro("输入参数不正确");
                flag=1;
                file=args[i];
            }
            else {
                erro("参数不匹配");
            }
        }
        if (flag == 0) erro("参数不匹配");

        execute(canshu,file,stopListFile,outputFile);
    }
    //根据参数进行计算
    //0-4分别表示字符、单词、行数、代码行数/空行数/注释行、递归处理 的参数存在不存在
    static void execute(int[] canshu, String file, String stopListFile, String outputFile) {
        String outputString=null;

        if (file.matches("\\*\\..+")){
            String houzui = file.substring(file.lastIndexOf(".")+1, file.length());
            String houzuiR="\\."+houzui;
            String[] files=new File(".").list();
            for (String i : files) {
                if (i.matches(houzuiR)) {
                    executOne(canshu,i,stopListFile,outputFile);
                }
            }
        }else {
            executOne(canshu,file,stopListFile,outputFile);
        }
    }

    //执行单个file
    static void executOne(int[] canshu, String file, String stopListFIle, String outputFile) {
        StringBuffer outputString=new StringBuffer();
        String text = retext(file);
        if (canshu[0] == 1) {
            outputString.append(file + ",字符数：" + reCount(text)+"\n");
        }
        if (canshu[1] == 1) {
            outputString.append(file + ",单词数：" + reWorld(text,stopListFIle)+"\n");
        }
        if (canshu[2] == 1) {
            outputString.append(file + ",行数：" + reLine(text)+"\n");
        }
        if (canshu[3] == 1) {
            outputString.append(file + ",代码行数/空行数/注释行：" + reA(file)+"\n");
        }
        System.out.println(outputString);
        output(outputString.toString(),outputFile);
    }

    //将文件转化为String
    static String retext(String fileName) {
        InputStream is= null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String text = null;
        try {
            byte[] b = new byte[is.available()];
            is.read(b);
            text = new String(b);
            is.close();
        } catch (FileNotFoundException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
        return text;
    }

    //统计String字符数
    static int reCount(String text) {
        return text.length() - 1;
    }

    //统计String单词数
    static int reWorld(String text,String stopListFile) {
        String[] stopList=retext(stopListFile).split(" ");
        String[] strings = text.split(" |,|\n");
        int stringNull = 0;
        String[] var3 = strings;
        int var4 = strings.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String i = var3[var5];
            if (i.length() == 0) {
                ++stringNull;
            }
            if (stopList.length==0)continue;
            if (checkIN(i, stopList)) {
                stringNull++;
            }
        }
        return strings.length - stringNull>=0?strings.length - stringNull:0;
    }

    //统计行数
    static int reLine(String text) {
        String[] strings = text.split("\n");
        return strings.length + 1;
    }

    //返回代码行/空行/注释行数
    static int[] reA(String fileName) {
        return new CountLine().reA(fileName);
    }

    //输出结果到指定文件
    static void output(String text, String outputFile) {
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(text.getBytes());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //检查单词是否在停用词表
    static boolean checkIN(String world, String[] stopList) {
        for (String stopword : stopList) {
            if (stopword.equals(world)) return true;
        }
        return false;
    }

    //erro
    static void erro(String e) {
        System.out.println(e);
        System.exit(0);
    }


}
