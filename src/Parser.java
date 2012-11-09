import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 11/8/12
 * Time: 1:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Parser {

    Map<String,Integer> stopWordsMap = new HashMap<String, Integer>();

    public Parser(String stopWordsFilePath){
        File stopWordsFile = new File(stopWordsFilePath);
        try {
            List<String> stopWords = FileUtils.readLines(stopWordsFile);
            for (String stopWord : stopWords) {
                if(!stopWordsMap.containsKey(stopWord))
                    stopWordsMap.put(stopWord,1);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public String removeTags(String inputString){
        String returnString = inputString.replaceAll("<.*>","").replaceAll("\n"," ").replaceAll("\t"," ")
                .replaceAll("\'s","")
                .replaceAll("\\."," ")
                .replaceAll("-"," ")
                .replaceAll(","," ")
                ;
        return returnString;
    }

//    public String removeStopWords(String inputString){
//        String returnString = "";
//        for (String stopWord : stopWordsMap.keySet()) {
//            if(inputString.contains(stopWord))
////                System.out.println("got it");
//            returnString = inputString.replaceAll(stopWord,"");
//        }
//        returnString.replaceAll("  "," ");
//        return returnString;
//    }


}
