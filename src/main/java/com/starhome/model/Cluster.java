package com.starhome.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgeny Borisov
 */
public class Cluster implements Serializable {
    private long id;
    private List<Point> points = new ArrayList<>();

    public Cluster(long id, List<Point> points) {
        this.id = id;
        this.points = points;
    }

    public Cluster() {
    }

    public Cluster(long id, Point point) {
        this.id=id;
        points.add(point);
    }

    public long getId() {
        return id;
    }

    public List<Point> getPoints() {
        return points;
    }


    public void addPoints(List<Point> points) {
        this.points.addAll(points);
    }



    @Override
    public String toString() {
        return "Cluster{" +
                "id=" + id +
                ", points=" + points +
                '}';
    }
}
