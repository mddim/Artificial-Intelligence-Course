package bg.sofia.uni.fmi.ai.knn;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator2 implements Comparator<String> {

    private Map<String, Integer> map;

    public ValueComparator2(Map<String, Integer> map) {
        this.map = map;
    }

    public int compare(String a, String b) {
        return map.get(b).compareTo(map.get(a));
    }
}
