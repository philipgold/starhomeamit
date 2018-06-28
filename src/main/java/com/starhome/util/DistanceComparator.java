package com.starhome.util;

import com.starhome.model.Cluster;
import scala.Tuple2;
import scala.Tuple3;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Evgeny Borisov
 */
public class DistanceComparator implements Comparator<Tuple3<Cluster,Cluster,Double>> ,Serializable {


    @Override
    public int compare(Tuple3<Cluster, Cluster, Double> t1, Tuple3<Cluster, Cluster, Double> t2) {
        return Double.compare(t1._3(),t2._3());
    }
}
