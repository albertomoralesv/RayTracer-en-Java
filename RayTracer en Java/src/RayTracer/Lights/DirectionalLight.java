package RayTracer.Lights;

import RayTracer.Vector;

import java.awt.*;

public class DirectionalLight extends Lights{
    private Vector direction;

    public DirectionalLight(Vector direction, Color color, double intensity) {
        super(intensity, color, typeOfLight.DirectionalLight);
        this.direction = direction;
    }
    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }
}
