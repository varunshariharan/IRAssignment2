import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 11/8/12
 * Time: 3:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class IndexData {
    int documentFrequency;
    int termFrequency;
    List<Integer> documentList = new ArrayList<Integer>();

    public IndexData(int documentFrequency, int termFrequency, List<Integer> documentList) {
        this.documentFrequency = documentFrequency;
        this.termFrequency = termFrequency;
        this.documentList = documentList;
    }
}
