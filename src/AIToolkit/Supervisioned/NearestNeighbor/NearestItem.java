package AIToolkit.Supervisioned.NearestNeighbor;

import AIToolkit.Supervisioned.KnowledgeBase.KnowledgeBaseItem;

/**
 * Special class used by the KNN to store the distance of the each item.
 * 
 * @author Luan
 */
public class NearestItem<T> {
    private final KnowledgeBaseItem<T> item;
    private final double distance;

    public NearestItem(KnowledgeBaseItem<T> item, double distance) {
        this.item = item;
        this.distance = distance;
    }

    public KnowledgeBaseItem<T> getItem() {
        return item;
    }

    public double getDistance() {
        return distance;
    }
    
    
}
