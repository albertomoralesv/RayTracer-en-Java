package RayTracer;

public class Vector {
    private double i;
    private double j;
    private double k;

    public Vector(double i, double j, double k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getJ() {
        return j;
    }

    public void setJ(double j) {
        this.j = j;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getMagnitude(){
        double i = getI();
        double j = getJ();
        double k = getK();
        double magnitude = Math.sqrt(i*i + j*j + k*k);
        return magnitude;
    }

    public Vector normalizeVector(){
        double i = getI();
        double j = getJ();
        double k = getK();
        double magnitude = Math.sqrt(i*i + j*j + k*k);
        return new Vector(i/magnitude, j/magnitude, k/magnitude);
    }

    public Vector scalarMultiplication(double scalar){
        return(new Vector(scalar*getI(), scalar*getJ(), scalar*getK()));
    }

    public double dotProduct(Vector vector02){
        double iResult = getI() * vector02.getI();
        double jResult = getJ() * vector02.getJ();
        double kResult = getK() * vector02.getK();
        return(iResult + jResult + kResult);
    }

    public Vector crossProduct(Vector vector02){
        double i = getJ()* vector02.getK() - getK()* vector02.getJ();
        double j = getI()* vector02.getK() - getK()* vector02.getI();
        double k = getI()* vector02.getJ() - getJ()* vector02.getI();
        return new Vector(i, -j, k);
    }

    public Vector vectorSubstraction(Vector vector02){
        double i = getI() - vector02.getI();
        double j = getJ() - vector02.getJ();
        double k = getK() - vector02.getK();
        return(new Vector(i, j, k));
    }

    public Vector vectorAddition(Vector vector02){
        double i = getI() + vector02.getI();
        double j = getJ() + vector02.getJ();
        double k = getK() + vector02.getK();
        return(new Vector(i, j, k));
    }

    public double vectorsDistance(Vector vector02){
        double x = getI()-vector02.getI();
        double y = getJ()-vector02.getJ();
        double z = getK()-vector02.getK();
        return Math.sqrt(x*x + y*y + z*z);
    }

    public boolean sameVectors(Vector vector02){
        boolean equal = true;
        if (getI() != vector02.getI()){
            equal=false;
        }else if (getJ() != vector02.getJ()){
            equal=false;
        }else if (getK() != vector02.getK()){
            equal=false;
        }
        return equal;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "i=" + i +
                ", j=" + j +
                ", k=" + k +
                '}';
    }
}
