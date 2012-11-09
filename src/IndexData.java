import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 11/8/12
 * Time: 3:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class IndexData {
    int documentFrequency;
    Map<Integer,Integer> termFrequencyMap = new HashMap<Integer, Integer>();

    public IndexData(int documentFrequency, Map<Integer, Integer> termFrequencyMap) {
        this.documentFrequency = documentFrequency;
        this.termFrequencyMap = termFrequencyMap;
    }
}
