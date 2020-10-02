package bg.sofia.uni.fmi.ai;

import java.util.Comparator;

public class FinalCostComparator implements Comparator<Node> {

    @Override
    public int compare(Node firstNode, Node secondNode) {
        return Integer.compare(firstNode.getFinalScore(), secondNode.getFinalScore());
    }
}
