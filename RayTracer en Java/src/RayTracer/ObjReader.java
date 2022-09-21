package RayTracer;

import RayTracer.Objects.Triangle;
import RayTracer.Vector;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ObjReader {
    private String pathName;
    private Color modelColor;
    private List<Vector> vertex;
    private List<Vector> vertexNormal;
    private List<Triangle> faces;

    public ObjReader(String pathName, Color modelColor){
        this.pathName = pathName;
        this.modelColor = modelColor;
        this.vertex = new ArrayList<>();
        this.vertexNormal = new ArrayList<>();
        this.faces = new ArrayList<>();
        ReadObj();
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public Color getModelColor() {
        return modelColor;
    }

    public void setModelColor(Color modelColor) {
        this.modelColor = modelColor;
    }

    public List<Vector> getVertex() {
        return vertex;
    }

    public void setVertex(Vector vertex) {
        this.vertex.add(vertex);
    }

    public List<Vector> getVertexNormal() {
        return vertexNormal;
    }

    public void setVertexNormal(Vector vertexNormal) {
        this.vertexNormal.add(vertexNormal);
    }

    public List<Triangle> getFaces() {
        return faces;
    }

    public void setFaces(Triangle face) {
        this.faces.add(face);
    }

    public void ReadObj(){
        String fileName = this.pathName;
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.length()>0){

                    switch (data.charAt(0)){
                        case 'v':
                            if (data.charAt(1) == ' '){
                                addVertex(data);
                            }
                            else if (data.charAt(1) == 'n'){
                                addVertexNormal(data);
                            }
                            break;
                        case 'f': addFace(data); break;
                        default: break;
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addVertex(String line){
        String[] lineParts = line.split("  ");
        double x;
        double y;
        double z;
        if (lineParts.length>1){
            String[] coordinates = lineParts[1].split(" ");
            x = Double.parseDouble(coordinates[0]);
            y = Double.parseDouble(coordinates[1]);
            z = Double.parseDouble(coordinates[2]);
        }else{
            String[] coordinates = line.split(" ");
            x = Double.parseDouble(coordinates[1]);
            y = Double.parseDouble(coordinates[2]);
            z = Double.parseDouble(coordinates[3]);
        }

        setVertex(new Vector(x,y,z));
    }

    private void addVertexNormal(String line){
        String[] lineParts = line.split("  ");
        double x;
        double y;
        double z;
        if (lineParts.length>1){
            String[] coordinates = lineParts[1].split(" ");
            x = Double.parseDouble(coordinates[0]);
            y = Double.parseDouble(coordinates[1]);
            z = Double.parseDouble(coordinates[2]);
        }else{
            String[] coordinates = line.split(" ");
            x = Double.parseDouble(coordinates[1]);
            y = Double.parseDouble(coordinates[2]);
            z = Double.parseDouble(coordinates[3]);
        }

        setVertexNormal(new Vector(x,y,z));
    }

    private void addFace(String line){
        String[] lineParts = line.split(" ");
        List<Integer> vertexIndex = new ArrayList<>();
        List<Integer> vertexTextureIndex = new ArrayList<>();
        List<Integer> vertexNormalIndex = new ArrayList<>();
        boolean slashCheck;
        for (int i=1; i< lineParts.length; i++){
            slashCheck = checkSlashes(lineParts[i]);
            if (slashCheck == false){
                String[] vertexType = lineParts[i].split("/");
                vertexIndex.add(Integer.valueOf(vertexType[0])-1);
                vertexTextureIndex.add(Integer.valueOf(vertexType[1])-1);
                vertexNormalIndex.add(Integer.valueOf(vertexType[2])-1);
            }else{
                String[] vertexType = lineParts[i].split("//");
                vertexIndex.add(Integer.valueOf(vertexType[0])-1);
                vertexNormalIndex.add(Integer.valueOf(vertexType[1])-1);
            }

        }
        createTriangles(vertexIndex, vertexNormalIndex);

    }

    private void createTriangles(List<Integer> vertexIndexes, List<Integer> vertexNormalIndex){
        Color color = getModelColor();
        //boolean orientation = true;
        Vector v0;
        Vector v1;
        Vector v2;
        Vector v0Normal;
        Vector v1Normal;
        Vector v2Normal;
        while(vertexIndexes.size()>=3){
            /* Strip Triangles
            if (orientation==true){
                v0 = getVertex().get(vertexIndexes.get(0));
                v1 = getVertex().get(vertexIndexes.get(1));
                v2 = getVertex().get(vertexIndexes.get(2));
            }else{
                v0 = getVertex().get(vertexIndexes.get(1));
                v1 = getVertex().get(vertexIndexes.get(0));
                v2 = getVertex().get(vertexIndexes.get(2));
            }
            vertexIndexes.remove(0);
            */
            v0 = getVertex().get(vertexIndexes.get(0));
            v1 = getVertex().get(vertexIndexes.get(1));
            v2 = getVertex().get(vertexIndexes.get(2));
            vertexIndexes.remove(1);
            if (getVertexNormal().size()==0){
                v0Normal = null;
                v1Normal = null;
                v2Normal = null;
            }else{
                v0Normal = getVertexNormal().get(vertexNormalIndex.get(0));
                v1Normal = getVertexNormal().get(vertexNormalIndex.get(1));
                v2Normal = getVertexNormal().get(vertexNormalIndex.get(2));
            }

            vertexNormalIndex.remove(1);
            setFaces(new Triangle(v0, v0Normal, v1, v1Normal, v2, v2Normal, color));
            /* Strip Triangles
            if (orientation==true){
                orientation=false;
            }else{
                orientation=true;
            }
            */
        }
    }

    private boolean checkSlashes(String string){
        String[] string2 = string.split("");
        boolean check = false;
        for (int i=0; i<string2.length; i++){
            if (i+1 < string2.length){
                if (string2[i].equals("/") && string2[i+1].equals("/")){
                    check = true;
                }
            }

        }
        return check;
    }


}
