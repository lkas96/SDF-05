package src;
import java.io.*;
public class Main {
    
    public static void main(String[] args) throws IOException{
        FileReader r = new FileReader(args[0]);
        LanguageModel lm = new LanguageModel(r);
        lm.buildModel();
        // lm.dumpModel();

        Console cons = System.console();
        while(true){
            String startingWord = cons.readLine(">> Starting word: ");
            String numberOfWords = cons.readLine(">> Number of words: ");

            int numWords = Integer.parseInt(numberOfWords);

            String generated = lm.generate2(startingWord, numWords, 0.1f);

            System.out.printf("======================================\n%s\n", generated);
        }



    }
}
