package com.starhome.util;

import com.starhome.model.Cluster;
import com.starhome.model.Point;
import com.starhome.services.ClusterServiceImpl;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Evgeny Borisov
 */
public class ClusterUtilTest {

    @Test
    public void testSimilarityAlgorithm() {
        double similarity = ClusterUtil.cosineSimilarity(new double[]{3.0, 4.0, 5.0}, new double[]{1.0, 2.0, 3.0});
        double similarity2 = ClusterUtil.cosineSimilarity(new double[]{1.0, 2.0, 3.0}, new double[]{3.0, 4.0, 5.0});
        double similarity3 = ClusterUtil.cosineSimilarity(new double[]{1.0, 2.0, 3.0}, new double[]{30.0, 40.0, 5.0});
        double similarity4 = ClusterUtil.cosineSimilarity(new double[]{30.0, 40.0, 5.0}, new double[]{1.0,2.0,3.0});
        System.out.println("similarity = " + similarity);
        System.out.println("similarity2 = " + similarity2);
        System.out.println("similarity3 = " + similarity3);
        System.out.println("similarity4 = " + similarity4);

    }

    @Test
    public void calcDistance() {
        SparkConf sparkConf = new SparkConf().setAppName("amit-starhome").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        List<Point> pointList = asList(new Point(1, asList(1d, 2d, 3d)),
                new Point(2, asList(3d, 4d, 5d)),
                new Point(3, asList(30d, 40d, 5d)),
                new Point(4, asList(31d, 42d, 5d)),
                new Point(5, asList(34d, 44d, 15d)),
                new Point(6, asList(10d, 12d, 15d)));
        JavaRDD<Point> pointRdd = sc.parallelize(pointList);
        ClusterServiceImpl clusterService = new ClusterServiceImpl();
        JavaRDD<Cluster> rdd = clusterService.hierarchicalClustering(pointRdd, 1);
        rdd.collect().forEach(System.out::println);

//        JavaRDD<String> rdd1 = sc.parallelize(Arrays.asList("A", "B", "C", "D"));
//        rdd1.cartesian(rdd1).filter(v1 -> !v1._1.equals(v1._2)).collect().forEach(System.out::println);

    }

}