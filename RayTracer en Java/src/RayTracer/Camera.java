package RayTracer;

public class Camera {
    private Vector position;

    public Camera(Vector position) {
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
}
