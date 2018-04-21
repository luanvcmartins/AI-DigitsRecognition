package AIToolkit.Supervisioned.NearestNeighbor;

import AIToolkit.Distances.Distance;
import AIToolkit.Supervisioned.KnowledgeBase.KnowledgeBase;
import AIToolkit.Supervisioned.KnowledgeBase.KnowledgeBaseItem;
import java.util.ArrayList;

/**
 * Abstract class thta represents the nearest neightbor family of algotithms.
 * You may not create a new instance of this class.
 * 
 * @author luan
 */
public abstract class NearestNeighbor<T, T1> {

    protected KnowledgeBase<T> knowledgeBase;
    protected Distance distance;
    protected int k;

    public NearestNeighbor() {
    }

    protected NearestNeighbor(int k, KnowledgeBase<T> knowledgedBase, Distance distance) {
        this.k = k;
        this.knowledgeBase = knowledgedBase;
        this.distance = distance;
    }

    protected abstract T1 getWinner(KnowledgeBaseItem<T> item);

    protected abstract ArrayList<KnowledgeBaseItem<T>> processNearestNeighbor(KnowledgeBaseItem<T> item);

    protected ArrayList<KnowledgeBaseItem<T>> getKNearest(ArrayList<NearestItem<T>> processedKnowledgedBase) {
        processedKnowledgedBase.sort((NearestItem<T> t, NearestItem<T> t1) -> {
            if (t.getDistance() > t1.getDistance()) {
                return 1;
            } else if (t.getDistance() < t1.getDistance()) {
                return -1;
            }
            return 0;
        });

        ArrayList<KnowledgeBaseItem<T>> kItems = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            kItems.add(processedKnowledgedBase.get(i).getItem());
        }
        return kItems;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setKnowledgeBase(KnowledgeBase<T> knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
