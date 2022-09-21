package RayTracer.Objects;

import RayTracer.Material;
import RayTracer.ObjReader;
import RayTracer.Vector;

import java.awt.*;

public class Model3D {
    private String pathName;
    private Color color;
    private Vector center;
    private Triangle[] triangles;
    private Material material;

    public Model3D(String pathName, Color color, Vector center, Material material) {
        this.pathName = pathName;
        this.color = color;
        this.center = center;
        this.material = material;
        setTriangles();
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
        this.center = center;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Triangle[] getTriangles() {
        return triangles;
    }

    private void setTriangles() {
        ObjReader model = new ObjReader(getPathName(), getColor());
        Triangle[] triangles = new Triangle[model.getFaces().size()];
        model.getFaces().toArray(triangles);
        for (Triangle triangle : triangles){
            triangle.setMaterial(getMaterial());
            Vector newV0 = triangle.getV0().vectorAddition(getCenter());
            Vector newV1 = triangle.getV1().vectorAddition(getCenter());
            Vector newV2 = triangle.getV2().vectorAddition(getCenter());
            triangle.setV0(newV0);
            triangle.setV1(newV1);
            triangle.setV2(newV2);
            //triangle.setNormal(newV0.vectorSubstraction(newV1).crossProduct(newV2.vectorSubstraction(newV1)).normalizeVector());
            triangle.setNormal(newV2.vectorSubstraction(newV1).crossProduct(newV0.vectorSubstraction(newV1)).normalizeVector());
            if (triangle.getV0Normal()==null){
                triangle.setV0Normal(triangle.getNormal());
                triangle.setV1Normal(triangle.getNormal());
                triangle.setV2Normal(triangle.getNormal());
            }
        }

        this.triangles = triangles;
    }
}
