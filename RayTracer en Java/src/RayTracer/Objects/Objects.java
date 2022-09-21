package RayTracer.Objects;

import RayTracer.Material;
import RayTracer.Vector;

import java.awt.*;

public class Objects {
    public enum Shape{
        Sphere, Triangle
    }
    private double id;
    private Color color;
    private Shape shape;
    private Vector normal;
    private Material material;

    private static double idCont=0;


    public Objects(Color color, Shape shape, Material material) {
        this.id = idCont;
        idCont++;
        this.color = color;
        this.shape = shape;
        this.material = material;
    }

    public Objects(Color color, Shape shape) {
        this.id = idCont;
        idCont++;
        this.color = color;
        this.shape = shape;
        this.material = Material.NM();
    }

    public double getId(){
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Vector getNormal() {
        return normal;
    }

    public void setNormal(Vector normal) {
        this.normal = normal;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
