package RayTracer;

public class Ray {
    private Vector origin;
    private Vector direction;
    private Vector finalPosition;
    private double minDistance;
    private double maxDistance;
    private int depth;

    public Ray(Vector origin){
        this.origin = origin;
        this.depth=0;
        this.minDistance = 0;
        this.maxDistance = 1000;
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

    public Vector getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(Vector finalPosition) {
        this.finalPosition = finalPosition;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
