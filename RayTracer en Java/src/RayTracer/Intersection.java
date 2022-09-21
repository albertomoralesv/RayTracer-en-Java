package RayTracer;

import RayTracer.Lights.DirectionalLight;
import RayTracer.Lights.Lights;
import RayTracer.Lights.PointLight;
import RayTracer.Objects.Objects;
import RayTracer.Objects.Sphere;
import RayTracer.Objects.Triangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Intersection {
    public Color nearestIntersection(Ray ray, List<Lights> lights, List<Objects> objects, Color defaultColor, HashMap<String, Material> materials){
        Color color = defaultColor;
        double distance;
        double minDistance = ray.getMinDistance();
        double maxDistance = ray.getMaxDistance();
        List<Double> distances = new ArrayList<>();
        List<Objects> objectsList = new ArrayList<>();
        distance = 0;
        for (Objects object : objects) {
            if (object.getShape() == Objects.Shape.Sphere) {
                distance = raySphereIntersection(ray, (Sphere) object);
            } else if (object.getShape() == Objects.Shape.Triangle) {
                distance = rayTriangleIntersection(ray, (Triangle) object);
            }
            if (distance > 0 && distance > minDistance && distance < maxDistance) {
                distances.add(distance);
                objectsList.add(object);
            }
        }
        if (distances.size()>0){
            double min = Collections.min(distances);
            int index = distances.indexOf(min);
            Objects closestObject = objectsList.get(index);
            ray.setFinalPosition(ray.getOrigin().vectorAddition( ray.getDirection().scalarMultiplication(min) ));
            Color objectColor = closestObject.getColor();
            color = closestObject.getColor();
            Vector normalOnPoint = null;
            if (closestObject.getShape()==Objects.Shape.Sphere){
                Sphere closestSphere = (Sphere) closestObject;
                closestObject.setNormal(ray.getFinalPosition().vectorSubstraction(closestSphere.getCenter()).normalizeVector());
                normalOnPoint = closestObject.getNormal();
            }else if (closestObject.getShape()==Objects.Shape.Triangle){
                Triangle closestTriangle = (Triangle) closestObject;
                normalOnPoint = closestTriangle.getNormalOnPoint(ray.getFinalPosition());
            }
            normalOnPoint = normalOnPoint.normalizeVector();

            /*float[] fresnelValues = Fresnel(1,1.3f,ray.getDirection(),normalOnPoint);
            float reflectionValue = fresnelValues[0];
            float refractionValue = fresnelValues[1];
            if (!closestObject.getMaterial().isReflective()){
                reflectionValue=0;
                refractionValue = 1;
            }else{
                reflectionValue=1;
                refractionValue=0;
            }
            if (!closestObject.getMaterial().isRefractive()){
                refractionValue=0;
                reflectionValue=1;
            }else{
                refractionValue=1;
                reflectionValue=0;
            }*/

            float ambience = 0.4f;
            float specular = closestObject.getMaterial().getSpecular();
            float shininess = closestObject.getMaterial().getShininess();
            boolean reflective = closestObject.getMaterial().isReflective();
            boolean refractive = closestObject.getMaterial().isRefractive();
            boolean transparent = closestObject.getMaterial().isTransparent();
            float colorIntensity = closestObject.getMaterial().getColorIntensity();
            if (transparent){
                Ray transmittedRay = new Ray(ray.getFinalPosition().vectorAddition(ray.getDirection().scalarMultiplication(0.01)));
                transmittedRay.setDepth(ray.getDepth()+1);
                transmittedRay.setDirection(ray.getDirection());
                if (transmittedRay.getDepth()<4){
                    Color transparentColor = nearestIntersection(transmittedRay,lights,objects,defaultColor,materials);
                    color = transparentColor;
                }else{
                    color = defaultColor;
                }
                color = addColor(color, colorScalar(objectColor,colorIntensity) );
            }
            if (refractive && !transparent){
                Ray refractionRay = new Ray(ray.getFinalPosition().vectorAddition(ray.getDirection().scalarMultiplication(0.001)));
                Vector refractionRayDirection = refraction(1,1.3f,ray.getDirection(),normalOnPoint).normalizeVector();
                refractionRay.setDirection(refractionRayDirection);
                if (ray.getDepth()<4){
                    refractionRay.setDepth(ray.getDepth()+1);
                    Color refractedColor = nearestIntersection(refractionRay,lights,objects,defaultColor,materials);
                    color = refractedColor;
                }else{
                    color = defaultColor;
                }
                color = addColor(color, colorScalar(objectColor,colorIntensity) );
            }
            if (reflective && !refractive && !transparent){
                Ray reflectRay = new Ray(ray.getFinalPosition());
                Vector I = ray.getDirection();
                Vector reflectRayDirection = I.vectorSubstraction(normalOnPoint.scalarMultiplication(2*normalOnPoint.dotProduct(I))).normalizeVector();
                reflectRay.setOrigin(reflectRay.getOrigin().vectorAddition(reflectRayDirection.scalarMultiplication(0.01)));
                reflectRay.setDirection(reflectRayDirection);
                if (ray.getDepth()<4){
                    reflectRay.setDepth(ray.getDepth()+1);
                    Color reflectionColor = nearestIntersection(reflectRay,lights,objects,defaultColor,materials);
                    objectColor = addColor(colorScalar(objectColor,1-clamp(specular,0,1)),colorScalar(reflectionColor,clamp(specular,0,1)));
                }else{
                    objectColor = colorScalar(defaultColor,1-clamp(specular,0,1));
                }
            }
            if (!transparent && !refractive){
                Color ambientColor = colorScalar(objectColor,ambience);
                Color pixelColor = ambientColor;
                for (Lights light : lights) {
                    Vector lightDirection = null;
                    double distanceFromLight = 0;
                    if (light.getType() == Lights.typeOfLight.PointLight) {
                        PointLight currentLight = (PointLight) light;
                        currentLight.setDirection(currentLight.getOrigin().vectorSubstraction(ray.getFinalPosition()).normalizeVector());
                        lightDirection = currentLight.getDirection();
                        distanceFromLight = ray.getFinalPosition().vectorsDistance(currentLight.getOrigin());
                    }else if (light.getType() == Lights.typeOfLight.DirectionalLight){
                        DirectionalLight currentLight = (DirectionalLight) light;
                        lightDirection = currentLight.getDirection().normalizeVector();
                        distanceFromLight = 1;
                    }
                    double nDotL = normalOnPoint.dotProduct(lightDirection);
                    double intensity = light.getIntensity() / (distanceFromLight);
                    Color lightColor = light.getColor();

                    Color diffuseLight = colorScalar( lightColor, (float) (intensity*nDotL));
                    Color diffuseColor = multiplyColor(objectColor, diffuseLight);
                    Vector H = lightDirection.vectorAddition(ray.getDirection().scalarMultiplication(0.01)).normalizeVector();
                    Color specularLight = colorScalar(lightColor, (float) intensity);
                    double specularShininess = Math.pow(H.dotProduct(normalOnPoint),shininess)*specular;
                    Color specularColor = multiplyColor(colorScalar(objectColor, (float) specularShininess), specularLight);
                    pixelColor = addColor(pixelColor, diffuseColor);
                    pixelColor = addColor(pixelColor, specularColor);
                    if (shadowOnPoint(ray.getFinalPosition().vectorAddition(normalOnPoint.scalarMultiplication(0.01)),closestObject,light,objects)){
                        if (reflective){
                            pixelColor = colorScalar(pixelColor,ambience*(1-clamp(specular,0,1)));
                        }else{
                            pixelColor = colorScalar(pixelColor,ambience);

                        }
                    }
                }
                color = pixelColor;
            }

            if (true){
                return color;
            }

        }

        return color;
    }

    public static Vector refraction(float n1, float n2, Vector I, Vector N){
        float n = n1/n2;
        float c1 = (float) N.dotProduct(I);
        float theta = (float) Math.acos(c1);
        float c2 = (float) Math.sqrt(1-n*n*Math.sin(theta)*Math.sin(theta));
        Vector T = I.scalarMultiplication(n).vectorAddition(N.scalarMultiplication(n*c1-c2));
        return T;
    }

    public static float[] Fresnel(float ior1, float ior2, Vector Intersection, Vector Normal){
        double cos1 = clamp((float)Normal.dotProduct(Intersection),-1,1);
        float[] fresnelValues = new float[2];
        if (cos1>0){
            float aux = ior1;
            ior1 = ior2;
            ior2 = aux;
        }
        double sin2 = ior1/ior2*Math.sqrt(1-Math.pow(cos1,2));
        if (sin2>=1){
            fresnelValues[0] = 1;
        }else{
            double cos2 = Math.sqrt(1-Math.pow(sin2,2));
            cos1 = Math.abs(cos1);
            float Fr1 = (float) Math.pow((( ior2*cos1 - ior1*cos2 ) / ( ior2*cos1 + ior1*cos2 )),2);
            float Fr2 = (float) Math.pow((( ior1*cos2 - ior2*cos1 ) / ( ior1*cos2 + ior2*cos1 )),2);
            float Fr = (Fr1+Fr2)/2;
            fresnelValues[0] = Fr;
        }
        float Ft = 1-fresnelValues[0];
        fresnelValues[1] = Ft;
        return fresnelValues;
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static Color addColor(Color original, Color otherColor) {
        float red = clamp((original.getRed() / 255f) + (otherColor.getRed() / 255f), 0, 1);
        float green = clamp((original.getGreen() / 255f) + (otherColor.getGreen() / 255f), 0, 1);
        float blue = clamp((original.getBlue() / 255f) + (otherColor.getBlue() / 255f), 0, 1);
        return new Color(red, green, blue);
    }
    public static Color multiplyColor(Color original, Color otherColor) {
        float red = clamp((original.getRed() / 255f) * (otherColor.getRed() / 255f), 0, 1);
        float green = clamp((original.getGreen() / 255f) * (otherColor.getGreen() / 255f), 0, 1);
        float blue = clamp((original.getBlue() / 255f) * (otherColor.getBlue() / 255f), 0, 1);
        return new Color(red, green, blue);
    }
    public static Color colorScalar(Color original, float scalar) {
        //scalar = clamp(scalar,0,1);
        float red = clamp((original.getRed() / 255f) * (scalar), 0, 1);
        float green = clamp((original.getGreen() / 255f) * (scalar), 0, 1);
        float blue = clamp((original.getBlue() / 255f) * (scalar), 0, 1);
        return new Color(red, green, blue);
    }

    public double raySphereIntersection(Ray ray, Sphere sphere){
        double[] intersections = new double[2];
        double distance = -1;
        Vector direction = new Vector(ray.getDirection().getI(), ray.getDirection().getJ(), ray.getDirection().getK());
        Vector origin = new Vector(ray.getOrigin().getI(), ray.getOrigin().getJ(), ray.getOrigin().getK());
        Vector sphereCenter = new Vector(sphere.getCenter().getI(), sphere.getCenter().getJ(), sphere.getCenter().getK());
        Vector L = sphereCenter.vectorSubstraction(origin);
        double tca = L.dotProduct(direction);
        if (tca>=0){
            double d = Math.sqrt( L.dotProduct(L) - tca*tca );
            if (d>=0) {
                double radius = sphere.getRadius();
                if ( (radius*radius - d*d) >= 0 ) {
                    double thc = Math.sqrt(radius*radius - d*d);
                    double t0 = tca - thc;
                    double t1 = tca + thc;
                    intersections[0] = t0;
                    intersections[1] = t1;
                    if (intersections[0]>0){
                        distance = intersections[0];
                    }else{
                        if (intersections[1]>0){
                            distance = intersections[1];
                        }
                    }
                }
            }
        }

        return distance;
    }

    public double rayTriangleIntersection(Ray ray, Triangle triangle){
        double t = -1;
        Vector direction = ray.getDirection();
        Vector v2v0 = triangle.getV2().vectorSubstraction(triangle.getV0());
        Vector v1v0 = triangle.getV1().vectorSubstraction(triangle.getV0());
        Vector P = direction.crossProduct(v1v0);
        double determinant = v2v0.dotProduct(P);
        double invDet = 1.0/determinant;
        Vector T = ray.getOrigin().vectorSubstraction(triangle.getV0());
        double u = invDet * ( T.dotProduct(P) );
        if (u<0 || u>1){
        }else{
            Vector Q = T.crossProduct(v2v0);
            double v = invDet * direction.dotProduct(Q);
            double epsilon = 0.0001;
            if (v<0 || ( (u+v)>(1.0+epsilon) )){
            }else{
                t = invDet * Q.dotProduct(v1v0);
            }
        }
        return t;
    }

    public boolean shadowOnPoint(Vector point, Objects objectIntersected, Lights light, List<Objects> objects){
        Ray pointToLight = new Ray(point);
        double distance;
        if (light.getType()==Lights.typeOfLight.PointLight){
            PointLight currentLight = (PointLight) light;
            pointToLight.setFinalPosition(currentLight.getOrigin());
            pointToLight.setDirection(pointToLight.getFinalPosition().vectorSubstraction(point).scalarMultiplication(1/pointToLight.getFinalPosition().vectorsDistance(point)));
            for (Objects object : objects){
                if (!object.getMaterial().isRefractive() && !object.getMaterial().isTransparent()){
                    if (object.getShape()==Objects.Shape.Triangle){
                        Triangle triangle = (Triangle) object;
                        distance = rayTriangleIntersection(pointToLight,triangle);
                        if (distance>0 && distance<pointToLight.getFinalPosition().vectorsDistance(point)){
                            return true;
                        }
                    }else if (object.getShape()==Objects.Shape.Sphere){
                        Sphere sphere = (Sphere) object;
                        distance = raySphereIntersection(pointToLight,sphere);
                        if (distance>0 && distance<pointToLight.getFinalPosition().vectorsDistance(point)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
