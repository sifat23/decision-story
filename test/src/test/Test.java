package test;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import java.io.File;
import java.io.FileWriter;
import java.time.chrono.IsoEra;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;



public class Test {
    
static Set<String> adjectivePhrases = new HashSet<>();
 static HashSet<String> add = new HashSet<>();

private static String line = "";
 
 public void getNounPhrases(Parse p) {

  if (p.getType().equals("JJ") || p.getType().equals("JJR") || p.getType().equals("JJS")) {
      adjectivePhrases.add(p.getCoveredText());
  }
     
  for (Parse child : p.getChildren()) {
          getNounPhrases(child);
  }
}
    public void parserAction() throws Exception {
 InputStream is = new FileInputStream("C:\\Users\\Namikaze\\Downloads\\Compressed\\compiler\\en-parser-chunking.bin");
 ParserModel model = new ParserModel(is);
 Parser parser = ParserFactory.create(model);
 Parse topParses[] = ParserTool.parseLine(line, parser, 1);
 for (Parse p : topParses){
  //p.show();
  getNounPhrases(p);
 }
}
    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
        
    public static int getLineCount(String text){
        return text.split("\\.").length;
    }
    
    public static String readFileAsString(String fileName)throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
    
    

    public static void main(String[] args) throws Exception {
        //read the story file & count lines in full stop
        String data = readFileAsString("C:\\Users\\Namikaze\\Downloads\\Compressed\\compiler\\all story\\romanti2.txt");
        String data1 = readFileAsString("C:\\Users\\Namikaze\\Downloads\\Compressed\\compiler\\all story\\Sentiment analysis\\love.txt");
        String data2 = readFileAsString("C:\\Users\\Namikaze\\Downloads\\Compressed\\compiler\\all story\\Sentiment analysis\\comedy.txt");
        String data3 = readFileAsString("C:\\Users\\Namikaze\\Downloads\\Compressed\\compiler\\all story\\Sentiment analysis\\horro.txt");//ends here
        System.out.println("Get All resouce file location...\n\n\n");
        System.out.println("Get the story file location...\n\n\n");
        
        //count the main file in line
        int count = getLineCount(data);
        int range = (count / 15) + 1;//end here
        System.out.println("All data way to modified...\n\n\n");
        //store file line count
        int c1 = getLineCount(data1);
        int c2 = getLineCount(data2);
        int c3 = getLineCount(data3);//end here
        
        //clear the punctuation from lines
        String clear = data.replaceAll("[\\[()\\]{',!*&^%'}+\\\\\\/-]", "").replaceAll(" +", " ");
        clear = clear.replaceAll("\\s", " ").trim();//end here
        System.out.println("All data is modified...\n\n\n");
        
        //store file in a string
        String love[]= new String[1000];
        for(int m = 0 ; m < c1; m++){
            love[m] = (data1.split("\\.")[0]+"");
            String result = "";
            int i = 0;
            for (String s : data1.split("\\.")) {
                if (i++ == 0) continue;
                result += s+".";
            }
            data1=result;
        }
        
         
        String comedy[] = new String[1000];
        for(int m = 0 ; m < c2; m++){
            comedy[m] = (data2.split("\\.")[0]+"");
            String result = "";
            int i = 0;
            for (String s : data2.split("\\.")) {
                if (i++ == 0) continue;
                result += s+".";
            }
            data2=result;
        }
        
       
        String horror[] = new String[1000];
        for(int m = 0 ; m < c3; m++){
            horror[m] = (data3.split("\\.")[0]+"");
            String result = "";
            int i = 0;
            for (String s : data3.split("\\.")) {
                if (i++ == 0) continue;
                result += s+".";
            }
            data3=result;
        }//ends here
        System.out.println("All resouce file parsing complete...\n\n\n");

        //dividing main story into small para
        String[] para = new String[100];
        for(int n = 0 ; n < 15; n++){
            for(int m = 0 ; m < range; m++){
                para[n] += (clear.split("\\.")[0]+"");
                String result = "";
                int i = 0;
                for (String s : clear.split("\\.")) {
                    if (i++ == 0) continue;
                    result += s+".";
                }
                clear = result;
            }
       }//ends here
        
        
        System.out.print("Done Hash Tagging");
        //gether adjective from paras
        for(int m = 0 ; m < 15; m++){
            line = para[m];
            new Test().parserAction();
            add.addAll(adjectivePhrases);
            System.out.print(".");
        }//ends here
       
        //System.err.println(add);
           
        String[] array = new String[add.size()];
     add.toArray(array);
     
       // System.out.println(array[3]);
        int v = 0;
        for(String temp : array){
        v++;
     }
        //System.err.println(v);
        

        //start matching
        int lovematch = 0;        
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < c1; j++) {
                if(array[i].equals(love[j])){
                    lovematch++;
                }                    
            }
        }
        
        
        int comedymatch = 0;        
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < c2; j++) {
                if(array[i].equals(comedy[j])){
                    comedymatch++;
                }
            }
        }
        
        
        int horrormatch = 0;        
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < c3; j++) {
                if(array[i].equals(horror[j])){
                    horrormatch++;
                }
            }
        }
        
        System.out.println("\n\n\n");
        System.out.println("The resilt is publishing...\n\n\n");
        if (lovematch > comedymatch && lovematch > horrormatch){
            System.out.println("This is a love story");
        } else if (comedymatch > lovematch && comedymatch > horrormatch){
            System.err.println("This is a comedy story");
        } else if (horrormatch > lovematch && horrormatch > comedymatch){
            System.out.println("This is a horror sotry");
        }
    }
}
    

   


