package RayTracer;

import java.lang.reflect.Type;

public class Material {
    private static final Material NM = new Material("NM", 0.0f, 0.0f,false,false,0.0f,false);
    private String name;
    private float specular;
    private float shininess;
    private boolean reflective;
    private boolean refractive;
    private float colorIntensity;
    private boolean transparent;

    public Material(String name, float specular, float shininess, boolean reflective, boolean refractive, float colorIntensity, boolean transparent){
        this.name = name;
        this.specular = specular;
        this.shininess = shininess;
        this.reflective = reflective;
        this.refractive = refractive;
        this.colorIntensity = colorIntensity;
        this.transparent = transparent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSpecular() {
        return specular;
    }

    public void setSpecular(float specular) {
        this.specular = specular;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public boolean isReflective() {
        return reflective;
    }

    public void setReflective(boolean reflective) {
        this.reflective = reflective;
    }

    public boolean isRefractive() {
        return refractive;
    }

    public void setRefractive(boolean refractive) {
        this.refractive = refractive;
    }

    public float getColorIntensity() {
        return colorIntensity;
    }

    public void setColorIntensity(float colorIntensity) {
        this.colorIntensity = colorIntensity;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public Material clone(){
        return new Material(getName(),getSpecular(),getShininess(),isReflective(),isRefractive(),getColorIntensity(),isTransparent());
    }

    public static Material NM(){
        return NM.clone();
    }
}
