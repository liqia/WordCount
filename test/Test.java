package test;


import momo.WordCount;

public class Test {
    static public void main(String[] args) {
        //测试函数retext
        String text = WordCount.retext("src/momo/File.txt");
//        String text1 = WordCount.retext(" ");
        System.out.println(text);;
        //测试函数reWord
        int testReWord1 = WordCount.reWorld(null, null);
        int testReWord2 = WordCount.reWorld(text, "whetu.txt");
        //测试reCount函数
        int testReCount = WordCount.reCount(null);
        int testReCount1 = WordCount.reCount(text);
        //测试reLine函数
        int testReLine = WordCount.reLine(null);
        int testReLine1 = WordCount.reLine(text);
        //测试output函数
        WordCount.output(null,"whetu.txt");
        WordCount.output("123","whetu.txt");
    }
}
