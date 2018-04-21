package AIToolkit.Distances;

/**
 * Non-optimized Data Time Warping distance.
 *
 * @author Luan
 */
public class DTWDistance extends Distance {

    @Override
    public double getDistance(Double[] dist1, Double[] dist2) {
        Double[][] timewarping = new Double[dist1.length][dist2.length];

        for (int i1 = 0; i1 < dist1.length; i1++) {
            for (int i2 = 0; i2 < dist2.length; i2++) {
                timewarping[i1][i2] = processDifference(dist1[i1], dist2[i2]);
                if (i1 == 0 && i2 == 0) {
                    continue;
                }
                timewarping[i1][i2] += processGlobalDistance(timewarping, i1, i2);
            }
        }

        return timewarping[dist1.length - 1][dist2.length - 1];
    }

    private double processDifference(Double dist1, Double dist2) {
        double difference = (dist1 - dist2);
        if (difference == 0) {
            return 1;
        }
        return Math.pow(difference, 2) + 1;
    }

    private double processGlobalDistance(Double[][] timewarping, int i1, int i2) {
        double minDistance = Double.MAX_VALUE;

        if (withinBounds(timewarping, i1 - 1, i2) && timewarping[i1 - 1][i2] < minDistance) {
            minDistance = timewarping[i1 - 1][i2];
        }
        if (withinBounds(timewarping, i1, i2 - 1) && timewarping[i1][i2 - 1] < minDistance) {
            minDistance = timewarping[i1][i2 - 1];
        }
        if (withinBounds(timewarping, i1 - 1, i2 - 1) && timewarping[i1 - 1][i2 - 1] < minDistance) {
            minDistance = timewarping[i1 - 1][i2 - 1];
        }

        return minDistance;
    }

    private boolean withinBounds(Double[][] timewarping, int i1, int i2) {
        return i1 >= 0 && i1 < timewarping.length && i2 >= 0 && i2 < timewarping[0].length;
    }

}
