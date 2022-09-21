package RayTracer.Lights;

import java.awt.*;

public class Lights {
    public enum typeOfLight{
        PointLight, DirectionalLight
    }
    private double intensity;
    private Color color;
    private typeOfLight type;

    public Lights(double intensity, Color color, typeOfLight lightType) {
        this.intensity = intensity;
        this.color = color;
        this.type = lightType;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public typeOfLight getType() {
        return type;
    }

    public void setType(typeOfLight type) {
        this.type = type;
    }
}
