package bg.sofia.uni.fmi.ai.kmean;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KmeansClustering {
    private Map<Integer, String[]> data;
    private int countOfAttributes;
    private int k;
    private int numberOfObservations;
    private Map<Integer, String[]> meanPoints;
    private Random random;

    public KmeansClustering(String filename, int k) {
        this.numberOfObservations = 0;
        data = getDataFromFile(filename);
        this.countOfAttributes = data.get(0).length - 1;
        this.k = k;
        random = new Random();
        meanPoints = new HashMap<>();

        chooseRandomMeanPoints();
        Map<Integer, LinkedList<String[]>> currentTable = createTableOfClusters();
        Map<Integer, LinkedList<String[]>> newTable = new HashMap<>();
        while (!currentTable.equals(newTable)) {
            currentTable.putAll(newTable);
            chooseNewMeanPoints(currentTable);
            newTable = createTableOfClusters();
        }
        printAnswer(currentTable);

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
                if (line.trim().isEmpty() && startOfData) break;
                if (startOfData) {
                    lineData = line.split(",");
                    if (isConvertibleToDouble(lineData[0])) {
                        map.put(id, lineData);
                        numberOfObservations++;
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

    public void chooseRandomMeanPoints() {
        for (int i = 0; i < k; i++) {
            int r = 0;
            LinkedList<Integer> addedIndexes = new LinkedList<>();
            while (addedIndexes.contains(r = random.nextInt(numberOfObservations + 1))) {
            }
            meanPoints.put(i, data.get(r));
            addedIndexes.add(r);
        }
    }

    public double euclideanDistance(String[] pt1, String[] pt2) {
        double euclideanDistance = 0.0;
        for (int i = 0; i < countOfAttributes; i++) {
            euclideanDistance += Math.pow((Double.parseDouble(pt1[i]) -
                    Double.parseDouble(pt2[i])), 2);
        }
        euclideanDistance = Math.sqrt(euclideanDistance);
        return euclideanDistance;
    }

    public int closestToWhichMeanPoint(String[] pt) {
        int closestTo = -1;
        double minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            if (euclideanDistance(pt, meanPoints.get(i)) < minDistance) {
                minDistance = euclideanDistance(pt, meanPoints.get(i));
                closestTo = i;
            }
        }
        return closestTo;
    }

    public Map<Integer, LinkedList<String[]>> createTableOfClusters() {
        Map<Integer, LinkedList<String[]>> tableOfClusters = new HashMap<>();
        for (int i = 0; i < k; i++) {
            LinkedList<String[]> list = new LinkedList<>();
            for (int j = 0; j < numberOfObservations; j++) {
                if (closestToWhichMeanPoint(data.get(j)) == i) {
                    list.add(data.get(j));
                }
            }
            tableOfClusters.put(i, list);
        }
        return tableOfClusters;
    }

    public void chooseNewMeanPoints(Map<Integer, LinkedList<String[]>> table) {
        for (int i = 0; i < k; i++) {
            String[] point = new String[countOfAttributes];
            for (int j = 0; j < countOfAttributes; j++) {
                LinkedList<String[]> list = table.get(i);
                ListIterator<String[]> listIterator = list.listIterator();
                double allInColumn = 0.0;
                while (listIterator.hasNext()) {
                    String column = listIterator.next()[j];
                    allInColumn += Double.parseDouble(column);
                }
                point[j] = String.valueOf(allInColumn/list.size());
            }
            meanPoints.put(i, point);
        }
    }

    public void printAnswer(Map<Integer, LinkedList<String[]>> table) {
        for (int i = 0; i < k; i++) {
            System.out.println("Group: " + i + " ");
            LinkedList<String[]> list = table.get(i);
            ListIterator<String[]> listIterator = list.listIterator();
            double allInColumn = 0.0;
            while (listIterator.hasNext()) {
                System.out.print("(");
                String[] current = listIterator.next();
                for (int j = 0; j < countOfAttributes - 1; j++) {
                    System.out.print(current[j] + ", ");
                }
                System.out.print(current[countOfAttributes - 1] +  ")");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        KmeansClustering ka = new KmeansClustering("D:\\FMI\\AI\\6_K-meanAlgorithm\\src\\bg\\sofia\\uni\\fmi\\ai\\kmean\\iris.arff", 25);
    }

}
