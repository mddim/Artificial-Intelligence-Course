package bg.sofia.uni.fmi.ai.geneticalgorithms;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main (String[] args) {
        Set<Item> itemSet = new HashSet<>();
        try {
            File file = new File("D:\\FMI\\AI\\3_GeneticAlgorithms_81536_GL\\items.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                Item item = Item.createItem(line);
                itemSet.add(item);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        GeneticAlgorithm GA = new GeneticAlgorithm(28, 5000, 100, itemSet, 500);
    }
}
