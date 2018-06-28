package com.starhome.services;

import com.starhome.model.Cluster;
import com.starhome.model.Point;
import com.starhome.util.ClusterUtil;
import com.starhome.util.DistanceComparator;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;
import scala.Tuple3;

/**
 * @author Evgeny Borisov
 */
public class ClusterServiceImpl implements ClusterService {
    @Override
    public JavaRDD<Cluster> hierarchicalClustering(JavaRDD<Point> points, int amountOfClusters) {
        JavaRDD<Cluster> clusters = points.map(point -> new Cluster(point.getId(), point));

        JavaRDD<Cluster> mergedClusterRdd = null;
        long initialClusterAmount = clusters.count();
        for (int i = amountOfClusters; i<initialClusterAmount; i++) {
            printCurrentClusters(clusters);
            JavaPairRDD<Cluster, Cluster> cartesianRdd = clusters.cartesian(clusters);
            cartesianRdd = removeDuplicates(cartesianRdd);
            //System.out.println("cartesianRdd.count() = " + cartesianRdd.count());
            JavaRDD<Tuple3<Cluster, Cluster, Double>> cartesianWithDistance = cartesianRdd.map(v -> {

                double distance = ClusterUtil.calcDistance(v._1(), v._2());
                //System.out.println("setting cluster #" + v._1().getId() + "-" + v._2() + " dist=" + distance);

                return new Tuple3<>(v._1(), v._2(), distance);
            });
            //cartesianWithDistance.collect().forEach(System.out::println);
            Tuple3<Cluster, Cluster, Double> twoClustersWithDistance = cartesianWithDistance.min(new DistanceComparator());
            Cluster clusterWhichShouldGrow = twoClustersWithDistance._1();
            Cluster clusterWhichShouldBeRemoved = twoClustersWithDistance._2();
            mergedClusterRdd = clusters.map(v1 -> {
                if (v1.getId() == clusterWhichShouldGrow.getId()) {
                    v1.addPoints(clusterWhichShouldBeRemoved.getPoints());
                }
                return v1;
            }).filter(v1 -> v1.getId() != clusterWhichShouldBeRemoved.getId());
            clusters = mergedClusterRdd;
        }


        return mergedClusterRdd;
    }

    private void printCurrentClusters(JavaRDD<Cluster> clusters) {
        System.out.println("*******************************************************************");
        System.out.println("*********************** CURRENT CLUSTERS: *************************");
        clusters.collect().forEach(System.out::println);
        System.out.println("*******************************************************************");
    }

    private JavaPairRDD<Cluster, Cluster> removeDuplicates(JavaPairRDD<Cluster, Cluster> cartesianRdd) {
        return cartesianRdd.filter(v1 -> v1._1().getId() != v1._2().getId());
    }
}
