package src;
import java.io.*;
public class Main {
    
    public static void main(String[] args) throws IOException{
        FileReader r = new FileReader(args[0]);
        LanguageModel lm = new LanguageModel(r);
        lm.buildModel();
        lm.dumpModel();




    }
}
