package RayTracer.Objects;

import RayTracer.Material;
import RayTracer.Vector;

import java.awt.*;

public class Sphere extends Objects {
    private Vector center;
    private double radius;

    public Sphere(Vector center, double radius, Color color, Material material) {
        super(color, Shape.Sphere, material);
        this.center = center;
        this.radius = radius;
    }

    public Sphere(Vector center, double radius, Color color) {
        super(color, Shape.Sphere, Material.NM());
        this.center = center;
        this.radius = radius;
    }

    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}
