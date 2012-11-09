import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
public class MainSubmission {
    static Map<String,Integer> stopWordsMap = new HashMap<String, Integer>();
    static String directoryPath = "./data/";
    static String stopWordsFilePath = "./stopWords";
    static ArrayList<Document> documentList = new ArrayList<Document>();
    public static void main(String[] args) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        Parser parser = new Parser(stopWordsFilePath);
        for (File file : files) {
            try {
                System.out.println(file.getName());
                String fileContent = FileUtils.readFileToString(file);
                String taglessContent = parser.removeTags(fileContent);
//                String finalFileContent = parser.removeStopWords(taglessContent);
                int documentId = Integer.parseInt(file.getName().substring(9));
                tokenize(documentId,taglessContent);
                System.out.println(taglessContent);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

    public static Map<String,Integer> tokenize(int documentId, String fileString){
        File stopWordsFile = new File(stopWordsFilePath);
        Document document = new Document(documentId);
        int maxFrequency = 0;
        int totalNumberOfOccurances = 0;
        try {
            List<String> stopWords = FileUtils.readLines(stopWordsFile);
            for (String stopWord : stopWords) {
                if(!stopWordsMap.containsKey(stopWord))
                    stopWordsMap.put(stopWord,1);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String[] tokens = fileString.split(" ");
        Map<String,Integer> tokenMap = new HashMap<String, Integer>();
        PorterStemmer porterStemmer = new PorterStemmer();
        for (String token : tokens) {
            totalNumberOfOccurances++;
            String stemmedToken = porterStemmer.stem(token);
            if(!stemmedToken.equals("") || !stopWordsMap.containsKey(stemmedToken)){
                int count = 1;
                if(tokenMap.containsKey(stemmedToken)) {
                    count = tokenMap.get(stemmedToken)+1;
                }
                if (count > maxFrequency)
                    maxFrequency = count;
                tokenMap.put(stemmedToken,count);
            }
        }
        document.maxTermFrequency = maxFrequency;
        document.totalNumberOfOccurances = totalNumberOfOccurances;
        documentList.add(document);
        return tokenMap;
    }
}
