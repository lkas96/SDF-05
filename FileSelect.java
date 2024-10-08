import java.io.*;

public class FileSelect {
    public static void main(String[] args) throws IOException {

        // Read the file
        File f = new File(args[0]);

        if (!f.exists() || !f.isFile()) {
            System.out.println("File does not exists or is it not a file.");
            System.exit(-1);
        }

        String filename = f.getName();

        System.out.printf("File selected for processing is %s. Processing file now.", filename);

        // Read file
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter("cleanedFile.txt"); // overwrite existing file
        BufferedWriter bw = new BufferedWriter(fw);

        String lines;
        String cleanedLines;

        while (((lines = br.readLine()) != null)) { // while not end of text file last line - continues a new line loop
            cleanedLines = lines.replaceAll("[^a-zA-Z\n]", " "); // removes everything except A-Z and line breaks

            // After cleaning lines, save to somewhere or overwrite existing file.
            System.out.print(cleanedLines.toLowerCase() + " "); // write all words to lowercase
            // PRINTING TO TEST OUTPUT ONLY.

            bw.append(cleanedLines.toLowerCase() + " "); // add lines to the new text file - plus a new line instead of line break.
            // bw.newLine();
        }

        // Close the buffer readers/writers
        bw.flush();
        bw.close();
        br.close();

        // Process the cleaned text file
        // Word 1 > Word 2 - find the word pairs and keep a record.
        // Word 1 has next word, word1 pair word2 count + 1,
        // use hashmap or some shit maybe a nest hashmap.
        
        

    }

}
