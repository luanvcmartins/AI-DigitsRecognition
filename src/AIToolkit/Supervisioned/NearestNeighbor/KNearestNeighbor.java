package AIToolkit.Supervisioned.NearestNeighbor;

import AIToolkit.Distances.Distance;
import AIToolkit.Supervisioned.KnowledgeBase.KnowledgeBase;
import AIToolkit.Supervisioned.KnowledgeBase.KnowledgeBaseItem;
import AIToolkit.Utils.InstanceOf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * K-Nearest Neightbor is an supervisioned algorithm for classification.
 * 
 * @author luan
 */
public class KNearestNeighbor<T, T1> extends NearestNeighbor<T, T1> {

    public KNearestNeighbor() {
        super();
    }

    public KNearestNeighbor(int k, KnowledgeBase<T> knowledgedBase, Distance distance) {
        super(k, knowledgedBase, distance);
    }

    public T1 classify(KnowledgeBaseItem<T> item) {
        return getWinner(item);
    }

    /**
     * Process and returns the nearest neighbor.
     * @param item The current item to be classified.
     * @return The nearest identified items
     */
    @Override
    protected ArrayList<KnowledgeBaseItem<T>> processNearestNeighbor(KnowledgeBaseItem<T> item) {
        ArrayList<NearestItem<T>> nearestItems = new ArrayList<>();

        for (KnowledgeBaseItem<T> cItem : knowledgeBase.getItems()) {
            Double[] o1Doubles = new Double[cItem.getItems().size()];
            Double[] o2Doubles = new Double[item.getItems().size()];
            int o1Index = 0, o2Index = 0;

            for (int index = 0; index < cItem.getItems().size(); index++) {
                if (InstanceOf.Double(cItem.getItem(index).toString())) {
                    o1Doubles[o1Index++] = Double.valueOf(cItem.getItem(index).toString());
                }
                if (InstanceOf.Double(item.getItem(index).toString())) {
                    o2Doubles[o2Index++] = Double.valueOf(item.getItem(index).toString());
                }
            }

            Double d = distance.getDistance(o1Doubles, o2Doubles);
            nearestItems.add(new NearestItem<>(cItem, d));
        }

        return getKNearest(nearestItems);
    }

    /**
     * Returns the winner class of the K selected data points.
     * @param item
     * @return 
     */
    @Override
    protected T1 getWinner(KnowledgeBaseItem<T> item) {
        HashMap<T1, Integer> results = new HashMap();
        for (KnowledgeBaseItem<T> cItem : processNearestNeighbor(item)) {
            if (results.containsKey((T1) cItem.getClassName())) {
                results.put((T1) cItem.getClassName(), results.get((T1) cItem.getClassName()) + 1);
            } else {
                results.put((T1) cItem.getClassName(), 1);
            }
        }

        T1 winningClass = null;
        Integer winningPoints = 0;
        for (Map.Entry<T1, Integer> result : results.entrySet()) {
            if (result.getValue() > winningPoints) {
                winningClass = result.getKey();
                winningPoints = result.getValue();
            }
        }

        return winningClass;
    }

}
