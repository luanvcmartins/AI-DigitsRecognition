package AIToolkit.Distances;

/**
 * Returns the euclidean distance between two items.
 *
 * @author luan
 */
public class EuclideanDistance extends Distance {

    @Override
    public double getDistance(Double[] dist1, Double[] dist2) {
        double distance = 0;
        for (int index = 0; index < dist1.length; index++) {
            double difference = dist1[index] - dist2[index];
            //No need to continue if the difference is 0:
            if (difference != 0) {
                distance += Math.pow(difference, 2);
            }
        }
        return Math.sqrt(distance);
    }

}
