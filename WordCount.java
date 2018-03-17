import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class WordCount {
    public static void main(String[] args) {
        String text = retext(WordCount.class.getResourceAsStream("File.txt"));
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
}
