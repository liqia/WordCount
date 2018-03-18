package momo;

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
                if (Pattern.compile("\\w+\\.txt").matcher(args[i+1]).find())
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
                if (Pattern.compile("\\w+\\.txt").matcher(args[i+1]).find())
                {
                    stopListFile=args[i+1];
                    i++;
                }
                else {
                    erro("参数不匹配");
                }
            }
            else if (Pattern.compile("(\\w+|\\*)\\.\\w+").matcher(args[i]).find()) {
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
            String houzuiR=".*\\."+houzui;
            if (canshu[4] == 1) {
                ArrayList<File> fileList = findALLFile(new File("."), new ArrayList<File>());
                for (File i : fileList) {
                    if (i.getAbsolutePath().matches(houzuiR)) {
                        executOne(canshu,i.getAbsolutePath(),stopListFile,outputFile);
                    }
                }
            } else {
                String[] files=new File(".").list();
                for (String i : files) {
                    if (i.matches(houzuiR)) {
                        executOne(canshu,i,stopListFile,outputFile);
                    }
                }
            }
        }else {
            executOne(canshu,file,stopListFile,outputFile);
        }

    }

    public static ArrayList<File> findALLFile(File file,ArrayList<File> fileArrayList) {
        if(file.isDirectory())//判断file是否是目录
        {
            File [] lists = file.listFiles();
            if(lists!=null)
            {
                for(int i=0;i<lists.length;i++)
                {
                    findALLFile(lists[i],fileArrayList);//是目录就递归进入目录内再进行判断
                }
            }
        }
        fileArrayList.add(file);
        return fileArrayList;
    }

    //执行单个file
    static public void executOne(int[] canshu, String file, String stopListFIle, String outputFile) {
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
    static public String retext(String fileName) {
        if (fileName == null) {
            return null;
        }
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            erro("找不到指定文件");
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
    static public int reCount(String text) {
        if (text == null) {
            return 0;
        }
        return text.length();
    }

    //统计String单词数
    static public int reWorld(String text,String stopListFile) {
        if (text == null) {
            return 0;
        }
        String[] stopList=null;
        if (retext(stopListFile) != null) {
            stopList=retext(stopListFile).split(" ");
        }
        String[] strings = text.split("\\.|,|\\s");
        int stringNull = 0;
        for(int var5 = 0; var5 < strings.length; ++var5) {
            String i = strings[var5];
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
    static public int reLine(String text) {
        if (text == null) {
            return 0;
        }
        String[] strings = text.split("\n");
        return strings.length;
    }

    //返回代码行/空行/注释行数
    static public int[] reA(String fileName) {
        return new CountLine().reA(fileName);
    }

    //输出结果到指定文件
    static public void output(String text, String outputFile) {
        try {
            FileOutputStream fos = new FileOutputStream(outputFile,true);
            fos.write(text.getBytes());
            fos.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //检查单词是否在停用词表
    static public boolean checkIN(String world, String[] stopList) {
        for (String stopword : stopList) {
            if (stopword.equals(world)) return true;
        }
        return false;
    }

    //erro
    static public void erro(String e) {
        System.out.println(e);
        System.exit(0);
    }


}
