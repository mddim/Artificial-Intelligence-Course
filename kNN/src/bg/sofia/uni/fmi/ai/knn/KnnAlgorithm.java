package bg.sofia.uni.fmi.ai.knn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KnnAlgorithm {

    private Map<Integer, String[]> data;
    private int countOfAttributes;
    private int k;

    public KnnAlgorithm(String newElement, String filename) {
        data = getDataFromFile(filename);
        this.countOfAttributes = data.get(0).length - 1;
        setK();
        System.out.println(returnClass(newElement));
    }

    public boolean isConvertibleToDouble(String str) {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    public Map<Integer, String[]> getDataFromFile(String fileName) {
        Map<Integer, String[]> map = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line = null;
            String[] lineData;
            boolean startOfData = false;
            int id = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (startOfData) {
                    lineData = line.split(",");
                    if (isConvertibleToDouble(lineData[0])) {
                        map.put(id, lineData);
                    }
                    id++;
                }
                if (line.equalsIgnoreCase("@data")) {
                    startOfData = true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void setK() {
        double tmp = 0.0;
        tmp = Math.sqrt(countOfAttributes);
        k = (int) Math.round(tmp);
        if (k % 2 == 0) {
            k++;
        }
    }

    public Map<Integer, Double> calculateDistances(String newElement) {
        Map<Integer, Double> map = new TreeMap<>();
        String[] newElementAttributes = newElement.split(",");
        int id = 0;
        for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
            String[] curr = entry.getValue();
            double euclideanDistance = 0.0;
            for (int i = 0; i < countOfAttributes; i++) {
                euclideanDistance += Math.pow((Double.parseDouble(curr[i]) -
                        Double.parseDouble(newElementAttributes[i])), 2);
            }
            euclideanDistance = Math.sqrt(euclideanDistance);
            map.put(id, euclideanDistance);
            id++;
        }

        Map<Integer, Double> sortedMap = new TreeMap<>(new ValueComparator(map));
        sortedMap.putAll(map);
        return sortedMap;
    }

    public String returnClass(String newElement) {
        Map<Integer, Double> sortedDistances = calculateDistances(newElement);
        Integer[] arrayOfKeys = sortedDistances.keySet().toArray(new Integer[k]);
        int classIndex = countOfAttributes;
        Map<String, Integer> classes = new TreeMap<>();
        for (int i = 0; i < k; i++) {
            String currentKey = data.get(arrayOfKeys[i])[classIndex];
            if (classes.containsKey(currentKey)) {
                classes.put(currentKey, classes.get(currentKey) + 1);
            } else {
                classes.put(currentKey, 1);
            }
        }

        Map<String, Integer> sortedClasses = new TreeMap<>(new ValueComparator2(classes));
        sortedClasses.putAll(classes);
        return (String) sortedClasses.keySet().toArray()[0];
    }

    public static void main(String[] args) {
        KnnAlgorithm alg = new KnnAlgorithm("2.3,3.5,4.0,5.2", "./iris.arff");
    }
}
