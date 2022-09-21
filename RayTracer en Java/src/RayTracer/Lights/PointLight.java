package RayTracer.Lights;

import RayTracer.Vector;

import java.awt.*;

public class PointLight extends Lights{
    private Vector origin;
    private Vector direction;


    public PointLight(Vector origin, Color color, double intensity) {
        super(intensity, color, typeOfLight.PointLight);
        this.origin = origin;
    }

    public Vector getOrigin() {
        return origin;
    }

    public void setOrigin(Vector origin) {
        this.origin = origin;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }
}
