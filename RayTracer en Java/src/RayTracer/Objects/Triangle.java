package RayTracer.Objects;

import RayTracer.Material;
import RayTracer.Vector;

import java.awt.*;

public class Triangle extends Objects{
    private Vector v0;
    private Vector v1;
    private Vector v2;
    private Vector v0Normal;
    private Vector v1Normal;
    private Vector v2Normal;

    public Triangle(Vector v0, Vector v0Normal, Vector v1, Vector v1Normal, Vector v2, Vector v2Normal, Color color) {
        super(color, Shape.Triangle, Material.NM());
        this.v0 = v0;
        this.v0Normal = v0Normal;
        this.v1 = v1;
        this.v1Normal = v1Normal;
        this.v2 = v2;
        this.v2Normal = v2Normal;
        //setNormal(v0.vectorSubstraction(v1).crossProduct(v2.vectorSubstraction(v1)).normalizeVector());
        setNormal(v2.vectorSubstraction(v1).crossProduct(v0.vectorSubstraction(v1)).normalizeVector());
        if (getV0Normal()==null){
            setV0Normal(getNormal());
            setV1Normal(getNormal());
            setV2Normal(getNormal());
        }
    }

    public Vector getV0() {
        return v0;
    }

    public void setV0(Vector v0) {
        this.v0 = v0;
    }

    public Vector getV1() {
        return v1;
    }

    public void setV1(Vector v1) {
        this.v1 = v1;
    }

    public Vector getV2() {
        return v2;
    }

    public void setV2(Vector v2) {
        this.v2 = v2;
    }

    public Vector getV0Normal() {
        return v0Normal;
    }

    public void setV0Normal(Vector v0Normal) {
        this.v0Normal = v0Normal;
    }

    public Vector getV1Normal() {
        return v1Normal;
    }

    public void setV1Normal(Vector v1Normal) {
        this.v1Normal = v1Normal;
    }

    public Vector getV2Normal() {
        return v2Normal;
    }

    public void setV2Normal(Vector v2Normal) {
        this.v2Normal = v2Normal;
    }

    public Vector getNormalOnPoint(Vector point){
        Vector a = getV0();
        Vector b = getV1();
        Vector c = getV2();
        double u;
        double v;
        double w;
        Vector v0 = b.vectorSubstraction(a);
        Vector v1 = c.vectorSubstraction(a);
        Vector v2 = point.vectorSubstraction(a);
        double d00 = v0.dotProduct(v0);
        double d01 = v0.dotProduct(v1);
        double d11 = v1.dotProduct(v1);
        double d20 = v2.dotProduct(v0);
        double d21 = v2.dotProduct(v1);
        double denom = d00*d11 - d01*d01;
        v = (d11*d20 - d01*d21) / denom;
        w = (d00*d21 - d01*d20) / denom;
        u = 1.0f - v - w;
        Vector normalOnPoint = getV0Normal().scalarMultiplication(u).vectorAddition(getV1Normal().scalarMultiplication(v)).vectorAddition(getV2Normal().scalarMultiplication(w));
        return normalOnPoint;
    }

    public boolean pointInTriangle(Vector point){
        Vector d = getV1().vectorSubstraction(getV0());
        Vector e = getV2().vectorSubstraction(getV0());

        double w1 = (e.getI()*(getV0().getJ()-point.getJ())+e.getJ()*(point.getI()-getV0().getI())) / (d.getI()*e.getJ() - d.getJ()*e.getI());
        double w2 = (point.getJ()-getV0().getJ()-w1*d.getJ()) / e.getJ();

        if ((w1 >= 0.0) && (w2 >= 0.0) && ((w1 + w2) <= 1.0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
