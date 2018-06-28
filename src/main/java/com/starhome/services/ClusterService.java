package com.starhome.services;

import com.starhome.model.Cluster;
import com.starhome.model.Point;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;

/**
 * @author Evgeny Borisov
 */
public interface ClusterService extends Serializable {
    JavaRDD<Cluster> hierarchicalClustering(JavaRDD<Point> points, int amountOfClusters);
}
