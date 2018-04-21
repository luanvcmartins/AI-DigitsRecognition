package AIToolkit.Distances;

/**
 * Returns the Manhattan distance between two items.
 *
 * @author luan
 */
public class ManhattanDistance extends Distance {

    @Override
    public double getDistance(Double[] dist1, Double[] dist2) {
        double distance = 0;
        for (int index = 0; index < dist1.length; index++) {
            double difference = dist1[index] - dist2[index];
            if (difference != 0) {
                distance += difference >= 0 ? difference : difference * -1;
            }
        }
        return distance;
    }

}
