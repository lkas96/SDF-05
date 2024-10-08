package src;
import java.io.*;
import java.security.SecureRandom;
import java.util.*;

public class LanguageModel {

    private Reader reader;
    private Map<String, Map<String, Integer>> nextWords;
    private Random rand = new SecureRandom();

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

    //Look through the keyset, to find the next word.
    //Give the first word, find the highest count next word.
    public String nextWord(String word){
        int wordCount = -1;
        String theWord = "";

        Map<String, Integer> dist = nextWords.get(word);
        if (null == dist){
            return "";
        }

        for (String w: dist.keySet()){
            if (dist.get(w) > wordCount){
                wordCount = dist.get(w);
                theWord = w;
            }
        }
        return theWord;
    }

    //METHOD TO GENERATE SENTENCE, TAKES IN A STARTING WORD AND SENTENCE LIMIT. 
    public String generate(String startingWord, int numberOfWords){

        String theSentence = startingWord;

        String curr = startingWord;
        String next = "";

        for (int i = 0; i < numberOfWords; i++){
            //Take in the first words
            //generate the whole paragraph and capped at number of words given. 
            // next = nextWord(curr);
            next = randomNext(curr);
            if(next.length() <= 0){
                return theSentence + ".";
            }
            theSentence += " " + next; //add on the next word to the sentence
            
            //continutes loop, the next word is now the first word, continue looking for next word. 
        }

        return theSentence + ".";
    }

    public String generate2(String startingWord, int numberOfWords, float hallucination){
        String theSentence = startingWord;
        
        String curr = startingWord;
        String next = "";

        float totalRandom = 1 - hallucination;

        for (int i = 0; i < numberOfWords; i++){

            if (rand.nextFloat() > totalRandom){
                next = nextWord(curr); //Exploration
            } else {
                next = randomNext(curr);
            }
            //Take in the first words
            //generate the whole paragraph and capped at number of words given. 
            // next = nextWord(curr);
            
            if(next.length() <= 0){
                return theSentence + ".";
            }
            theSentence += " " + next; //add on the next word to the sentence
            
            //continutes loop, the next word is now the first word, continue looking for next word. 
        }

        return theSentence + ".";
    }

    //Exploration
    public String randomNext(String word){
        Map<String, Integer> dist = nextWords.get(word);
        if (null == dist){
            return "";
        }
        int numWords = dist.size();
        //Randomly picka  number from the lsit of next words
        int theWord = rand.nextInt(numWords);
        int i = 0;
        String nw = "";
        for (String w: nextWords.keySet()){
            nw = w;
            if ( i > theWord){
                return w;
            }
            i++;
        }
        return nw;
    }
}
