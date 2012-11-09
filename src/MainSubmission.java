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
    static Map<String,IndexData> invertedIndexMap = new HashMap<String, IndexData>();
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

    public static void tokenize(int documentId, String fileString){
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
            if(!token.equals(""))
                totalNumberOfOccurances++;
            if(!token.equals("") && !stopWordsMap.containsKey(token)){
                String stemmedToken = porterStemmer.stem(token);
                int count = 1;
                if(tokenMap.containsKey(stemmedToken)) {
                    count = tokenMap.get(stemmedToken)+1;
                }
                if (count > maxFrequency)
                    maxFrequency = count;
                tokenMap.put(stemmedToken,count);
                //create inverted index for stemmed word
                int documentFrequency = 0;
                int documentTermCount = 1;
                Map<Integer,Integer> termFrequencyMap = new HashMap<Integer, Integer>();
                IndexData indexData;
                if (!invertedIndexMap.containsKey(stemmedToken)){
                    documentFrequency = 1;
                    termFrequencyMap.put(documentId, documentTermCount);
                    indexData = new IndexData(documentFrequency,termFrequencyMap);
                }
                else {
                    indexData = invertedIndexMap.get(stemmedToken);
                    int termFrequencyForThisDocument = 0;
                    termFrequencyMap = indexData.termFrequencyMap;
                    documentFrequency = indexData.documentFrequency;
                    //if the tempIndexData already contains this document
                    //increase the termFrequency for this particular document
                    if (termFrequencyMap.containsKey(documentId)){
                        termFrequencyForThisDocument = indexData.termFrequencyMap.get(documentId);
                        //donot increase document frequency
                    }
                    else{
                        //else increment documentFrequency and set the term frequency for the document to be 1
                        documentFrequency++;
                    }
                    termFrequencyForThisDocument++;
                    termFrequencyMap.put(documentId,termFrequencyForThisDocument);
                    indexData.documentFrequency = documentFrequency;
                    indexData.termFrequencyMap = termFrequencyMap;
                }
                invertedIndexMap.put(stemmedToken,indexData);
            }
        }
        document.maxTermFrequency = maxFrequency;
        document.totalNumberOfOccurances = totalNumberOfOccurances;
        documentList.add(document);
    }
}
