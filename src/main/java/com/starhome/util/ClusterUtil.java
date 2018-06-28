package com.starhome.util;

import com.starhome.model.Cluster;
import com.starhome.model.Point;

/**
 * @author Evgeny Borisov
 */
public class ClusterUtil {
    public static double calcDistance(Cluster c1, Cluster c2) {
        double distance = 0;
        for (Point point1 : c1.getPoints()) {
            for (Point point2 : c2.getPoints()) {
                double similarity = cosineSimilarity(getFeaturesAsArray(point1), getFeaturesAsArray(point2));
                System.out.println( "comparing: "+c1.getId()+" with "+c2.getId()+" dist= "+similarity);
                if (similarity>distance){
                    distance = similarity;
                }
            }
        }
        return distance;
    }

    private static double[] getFeaturesAsArray(Point point1) {
        return point1.getFeatures().stream().mapToDouble(Double::doubleValue).toArray();
    }


    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
