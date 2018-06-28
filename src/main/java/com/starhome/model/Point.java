package com.starhome.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgeny Borisov
 */
public class Point implements Serializable {
    private long id;
    private List<Double> features = new ArrayList<>();

    public Point(long id, List<Double> features) {
        this.id = id;
        this.features = features;
    }

    public Point() {
    }

    public long getId() {
        return id;
    }

    public List<Double> getFeatures() {
        return features;
    }



    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", features=" + features +
                '}';
    }
}
