package RayTracer;

import RayTracer.Lights.DirectionalLight;
import RayTracer.Lights.Lights;
import RayTracer.Lights.PointLight;
import RayTracer.Objects.Model3D;
import RayTracer.Objects.Objects;
import RayTracer.Objects.Sphere;
import RayTracer.Objects.Triangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class RayTracer {
    public static Color RayCast(Ray ray, List<Lights> lights, List<Objects> objects, Color defaultColor, HashMap<String, Material> materials){
        Color color = defaultColor;
        Intersection intersections = new Intersection();
        color = intersections.nearestIntersection(ray, lights, objects, color, materials);
        return color;
    }

    public static void main(String[] args) {
        LocalDateTime startTime = LocalDateTime.now();
        System.out.println(startTime);
        int width = 1000;
        int height = 750;
        int resolution = 2;
        float xInit = -width/2f;
        float yInit = height/2f;
        float zInit = 100;
        char fixedCoord = 'z';
        if (fixedCoord == 'x'){
            float aux = xInit;
            xInit = zInit;
            zInit = aux;
        }else if (fixedCoord == 'y'){
            float aux = yInit;
            yInit = zInit;
            zInit = aux;
        }
        Scene scene01 = new Scene(new Vector(xInit,yInit,zInit),width,height);
        BufferedImage image = new BufferedImage(width*resolution,height*resolution,TYPE_INT_ARGB);
        List<Objects> objects = new ArrayList<>();
        List<Lights> lights = new ArrayList<>();
        //
        /*
        Sphere sphere01 = new Sphere(new Vector(0,0,100), 20, Color.RED);
        Sphere sphere02 = new Sphere(new Vector(100,0,200), 20, Color.BLUE);
        Triangle triangle01 = new Triangle(new Vector(0,70,100), new Vector(-20,30,100), new Vector(20,30,100), Color.YELLOW);
        Triangle triangle02 = new Triangle(new Vector(0,70,100), new Vector(20,30,100), new Vector(50,70,150), Color.GREEN);
        */
        //
        HashMap<String, Material> materials = new HashMap<>();
        materials.put("mat1",new Material("mat1",0.9f,1,false,false,0.2f,false));
        materials.put("mat2",new Material("mat2",80,100,false,false,0,false));
        materials.put("mat3",new Material("mat3",1,5,true,false,0,false));
        materials.put("mat4",new Material("mat4",0.2f,1,true,false,0.f,false));
        materials.put("mat5",new Material("mat5",5,20,true,false,0,false));
        Sphere sphere01 = new Sphere(new Vector(0,4,3), 3, Color.blue,new Material("transparent",0.2f,1,false,false,0.05f,false));
        //objects.add(sphere01);
        Sphere sphere02 = new Sphere(new Vector(-5,4,3), 1, Color.green,new Material("transparent",0.9f,10,false,false,0.1f,false));
        //objects.add(sphere02);
        Sphere sphere03 = new Sphere(new Vector(0,-9,8), 5, Color.orange,new Material("transparent",0.6f,100,false,false,0.1f,false));
        //objects.add(sphere03);
        Sphere sphere04 = new Sphere(new Vector(5,-5,2), 1f, Color.red);
        //objects.add(sphere04);
        Sphere sphere05 = new Sphere(new Vector(5,3,2), 1f, Color.red);
        //objects.add(sphere05);
        Sphere sphere06 = new Sphere(new Vector(0,7,8), 5f, Color.orange);
        //objects.add(sphere06);
        String model01Path = "models/catff.obj";
        Color model01Color = Color.orange;
        Vector model01Center = new Vector(0,0.1f,4);
        Model3D model01 = new Model3D(model01Path, model01Color, model01Center,materials.get("mat1"));
        String model02Path = "models/SmallTeapot.obj";
        Color model02Color = Color.blue;
        Vector model02Center = new Vector(0,0,4);
        Model3D model02 = new Model3D(model02Path, model02Color, model02Center,materials.get("mat2"));
        String model03Path = "models/crown.obj";
        Color model03Color = Color.cyan;
        Vector model03Center = new Vector(-5,5,6);
        Model3D model03 = new Model3D(model03Path, model03Color, model03Center,new Material("transparent",990,1000,false,false,0,false));
        String model04Path = "models/crown.obj";
        Color model04Color = Color.cyan;
        Vector model04Center = new Vector(0,5,6);
        Model3D model04 = new Model3D(model04Path, model04Color, model04Center,new Material("transparent",0.f,0,false,false,0,false));
        String model05Path = "models/crown.obj";
        Color model05Color = Color.yellow;
        Vector model05Center = new Vector(0,0,5);
        Model3D model05 = new Model3D(model05Path, model05Color, model05Center,new Material("transparent",12,1000,false,false,0,false));
        //
        for (Triangle triangle : model01.getTriangles()){
            objects.add(triangle);
        }
        for (Triangle triangle : model02.getTriangles()){
            //objects.add(triangle);
        }
        for (Triangle triangle : model03.getTriangles()){
            //objects.add(triangle);
        }
        for (Triangle triangle : model04.getTriangles()){
            //objects.add(triangle);
        }
        for (Triangle triangle : model05.getTriangles()){
            //objects.add(triangle);
        }
        //
        Color wallColor = Color.red;
        materials.put("wall",new Material("espejo",1,1,true,false,0,false));
        //Left
        Triangle wall1 = new Triangle(new Vector(-3,20,2),null,new Vector(-3,0,6),null,new Vector(-3,0,2),null,wallColor);
        Triangle wall2 = new Triangle(new Vector(-3,20,2),null,new Vector(-3,20,6),null,new Vector(-3,0,6),null,wallColor);
        //Front
        Triangle wall3 = new Triangle(new Vector(-3,20,6),null,new Vector(23,0,6),null,new Vector(-3,0,6),null,wallColor);
        Triangle wall4 = new Triangle(new Vector(-3,20,6),null,new Vector(23,20,6),null,new Vector(23,0,6),null,wallColor);
        //Right
        Triangle wall5 = new Triangle(new Vector(3,20,6),null,new Vector(3,0,2),null,new Vector(3,0,6),null,wallColor);
        Triangle wall6 = new Triangle(new Vector(3,20,6),null,new Vector(3,20,2),null,new Vector(3,0,2),null,wallColor);
        //Back
        Triangle wall7 = new Triangle(new Vector(-3,20,2),null,new Vector(-3,0,2),null,new Vector(3,0,2),null,wallColor);
        Triangle wall8 = new Triangle(new Vector(-3,20,2),null,new Vector(3,0,2),null,new Vector(3,20,2),null,wallColor);
        //Top
        Triangle wall9 = new Triangle(new Vector(-3,5,6),null,new Vector(-3,5,2),null,new Vector(3,5,2),null,wallColor);
        Triangle wall10 = new Triangle(new Vector(-3,5,6),null,new Vector(3,5,2),null,new Vector(3,5,6),null,wallColor);
        //Bottom
        Triangle wall11 = new Triangle(new Vector(-3,0.1f,6),null,new Vector(3,0.1f,2),null,new Vector(-3,0.1f,2),null,wallColor);
        Triangle wall12 = new Triangle(new Vector(-3,0.1f,6),null,new Vector(3,0.1f,6),null,new Vector(3,0.1f,2),null,wallColor);


        wall1.setMaterial(materials.get("wall"));
        wall2.setMaterial(materials.get("wall"));
        wall3.setMaterial(materials.get("wall"));
        wall4.setMaterial(materials.get("wall"));
        wall5.setMaterial(materials.get("wall"));
        wall6.setMaterial(materials.get("wall"));
        wall7.setMaterial(materials.get("wall"));
        wall8.setMaterial(materials.get("wall"));
        wall9.setMaterial(materials.get("wall"));
        wall10.setMaterial(materials.get("wall"));
        wall11.setMaterial(materials.get("wall"));
        wall12.setMaterial(materials.get("wall"));

        objects.add(wall1);
        objects.add(wall2);
        objects.add(wall3);
        objects.add(wall4);
        objects.add(wall5);
        objects.add(wall6);
        objects.add(wall7);
        objects.add(wall8);
        objects.add(wall9);
        objects.add(wall10);
        objects.add(wall11);
        objects.add(wall12);

        //
        Color planeColor = Color.green;
        Triangle plane1 = new Triangle(new Vector(-500,0,500),null,new Vector(500,0,-500),null,new Vector(-500,0,-500),null, planeColor);
        Triangle plane2 = new Triangle(new Vector(-500,0,500),null,new Vector(500,0,500),null,new Vector(500,0,-500),null, planeColor);
        plane1.setMaterial(materials.get("mat4"));
        plane2.setMaterial(materials.get("mat4"));
        objects.add(plane1);
        objects.add(plane2);
        Vector light01Origin = new Vector(0,4.5f,4);
        PointLight light01 = new PointLight(light01Origin, Color.white, 0.5f);
        lights.add(light01);
        Vector light02Origin = new Vector(0,2.5f,2.5f);
        PointLight light02 = new PointLight(light02Origin, Color.white, 0.3f);
        lights.add(light02);
        Vector light03Direction = new Vector(0,10,0);
        DirectionalLight light03 = new DirectionalLight(light03Direction, Color.magenta, 0.3f);
        //lights.add(light03);
        Vector light04Origin = new Vector(0,5,1);
        PointLight light04 = new PointLight(light04Origin, Color.yellow, 1.f);
        //lights.add(light04);
        Vector light05Origin = new Vector(0,10,3.5);
        PointLight light05 = new PointLight(light04Origin, Color.white, 5);
        //lights.add(light05);
        Vector camera01Origin = new Vector(0,1,2);
        //Camera camera01 = new Camera(camera01Origin);
        Ray camera01Ray = new Ray(camera01Origin);
        double minDistance = 0;
        double maxDistance = 1000;
        camera01Ray.setMinDistance(minDistance);
        camera01Ray.setMaxDistance(maxDistance);
        int scene01PositionX = (int) scene01.getPosition().getI();
        int scene01PositionY = (int) scene01.getPosition().getJ();
        double z = scene01.getPosition().getK();
        if (fixedCoord == 'x'){
            double aux = scene01PositionX;
            scene01PositionX = (int) z;
            z = (int) aux;
        }else if (fixedCoord == 'y'){
            double aux = scene01PositionY;
            scene01PositionY = (int) z;
            z = aux;
        }
        Color sceneColor;
        for (double x = scene01PositionX; x< scene01PositionX+scene01.getWidth(); x+=1f/resolution){
            for (double y= scene01PositionY; y> scene01PositionY-scene01.getHeight(); y-=1f/resolution){
                if (fixedCoord == 'x'){
                    double aux = x;
                    x = z;
                    z = (int) aux;
                }else if (fixedCoord == 'y'){
                    double aux = y;
                    y = z;
                    z = aux;
                }
                Vector direction = new Vector(x,y,z);
                direction = direction.normalizeVector();
                camera01Ray.setDirection(direction);
                sceneColor = Color.white;
                sceneColor = RayCast(camera01Ray, lights, objects, sceneColor, materials);
                if (fixedCoord == 'x'){
                    double aux = x;
                    x = z;
                    z = (int) aux;
                }else if (fixedCoord == 'y'){
                    double aux = y;
                    y = z;
                    z = aux;
                }
                image.setRGB((int) (Math.abs(x-scene01PositionX)*resolution), (int) (Math.abs(y-scene01PositionY)*resolution), sceneColor.getRGB());
            }
        }
        try {
            ImageIO.write(image, "png", new File("test01.png"));
            System.out.println(startTime);
            System.out.println(LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
