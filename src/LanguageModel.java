package src;
import java.io.*;
import java.util.*;

public class LanguageModel {

    private Reader reader;
    private Map<String, Map<String, Integer>> nextWords;

    public LanguageModel(Reader reader) {
        this.reader = reader;
        this.nextWords = new HashMap<>();
    }

    public void buildModel() throws IOException {
        BufferedReader br = new BufferedReader(reader);
        String line;

        while ((line = br.readLine()) != null){
            //Clean the line, remove puntuation
            line = line.trim().replaceAll("\\p{Punct}", ""); //remove all punctuations but not whitespaces
            // line = line.trim().replaceAll(" +", " "); //remove multiple spaces 2 and more and replace to 1 space
            String[] words = line.split("\\s+"); //put all the string lines words into an array, removes multiples spaces too

            for (int i = 0; i < words.length -1 ; i++){
                String curWord = words[i];
                String nxtWord = words[i+1];

                addToWordDistribution(curWord, nxtWord);
                // System.out.printf("Current word: %s ----- Next word: %s\n", curWord, nxtWord);
            }

        }
    }

    // Map<String, Map<String, Integer>> nextWords;
    private void addToWordDistribution(String curr, String next){

        /*
         * int count = 0;
         * if (map.containsKey('abc'))
         * count = map.get("abc")
         * count++
         * map.put("abc", count)
         */
        Map<String, Integer> wordDistribution;

        if(nextWords.containsKey(curr)){
            wordDistribution = nextWords.get(curr);
        } else {
            //if dont have, create the nested hashmap.
            wordDistribution = new HashMap<>();
        }

        int count = 0;

        if(wordDistribution.containsKey(next)){
            count = wordDistribution.get(next);
        }  
        count++;

        wordDistribution.put(next, count);
        nextWords.put(curr, wordDistribution);

    }

    public void dumpModel() {
        for (String curr: nextWords.keySet()){
            System.out.println("---------------------------------------------");
            System.out.printf(">> %s\n", curr);
            Map<String, Integer> dist = nextWords.get(curr);
            for (String next: dist.keySet())
                System.out.printf("\t\t%s = %d\n", next, dist.get(next));
        }
        System.out.println("---------------------------------------------");
    }
}
