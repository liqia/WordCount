import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class WordCount {
    public static void main(String[] args) {

        String text=null;
        try {
            text = retext(WordCount.class.getResourceAsStream("File.txt"));
        } catch (NullPointerException e) {
        }
        if (text == null) {
            System.out.println("没有找到文件，请重新输入文件路径");
        }

        int[] canshu=new int[5];//0-3分别表示字符、单词、行数、代码行数/空行数/注释行、递归处理 的参数存在不存在
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
                if (Pattern.compile("([a-z]|[A-Z]|[0-9])+.txt").matcher(args[i+1]).find())
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
                if (Pattern.compile("([a-z]|[A-Z]|[0-9])+.txt").matcher(args[i+1]).find())
                {
                    stopListFile=args[i+1];
                    i++;
                }
                else {
                    erro("参数不匹配");
                }
            }
            else if (Pattern.compile("([a-z]|[A-Z]|[0-9])+.txt").matcher(args[i]).find()) {
                flag=1;
                file=args[i];
            }
        }
        if (flag == 0) erro("参数不匹配");

        System.out.println("world:" + reWorld(text) + "\ncount:" + reCount(text) + "\n" + reLine(text));
    }

    //将文件转化为String
    static String retext(InputStream is) {
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
    static int reWorld(String text) {
        String[] strings = text.split(" |,|\n");
        int stringNull = 0;
        String[] var3 = strings;
        int var4 = strings.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String i = var3[var5];
            if (i.length() == 0) {
                ++stringNull;
            }
        }

        return strings.length - stringNull;
    }

    //统计行数
    static int reLine(String text) {
        String[] strings = text.split("\n");
        return strings.length + 1;
    }

    //erro
    static void erro(String e) {
        System.out.println(e);
        System.exit(0);
    }
}
