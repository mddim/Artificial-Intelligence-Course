package bg.sofia.uni.fmi.ai.geneticalgorithms;

import java.util.*;

public class GeneticAlgorithm {

    private int chromosomeLength;
    private int capacity;
    private int sizeOfPopulation;
    private List<Chromosome> population = new LinkedList<>();

    private Set<Item> items;

    private Random random = new Random(System.currentTimeMillis());

    public GeneticAlgorithm(int chromosomeLength, int capacity, int sizeOfPopulation, Set<Item> items, int maxGenerations) {
        this.chromosomeLength = chromosomeLength;
        this.capacity = capacity;
        this.sizeOfPopulation = sizeOfPopulation;
        this.items = items;

        createRandomPopulation();

        for (int i = 0; i < maxGenerations; i++) {
            selection();
            System.out.println("Iteration number: " + (i + 1) + "\n" + "Most fit chromosome: " + mostFitChromosome() +
                    "\n" + "Value: " + population.get(0).getFitness() + "\n");
            population.sort((a, b) -> Double.compare(b.getFitness(), a.getFitness()));
        }
    }

    public void createRandomPopulation() {
        boolean[] genes = new boolean[chromosomeLength];
        for (int i = 0; i < sizeOfPopulation; i++) {
            for (int j = 0; j < chromosomeLength; j++) {
                genes[j] = random.nextBoolean();
            }
            this.population.add(new Chromosome(genes, fitness(genes)));
        }
    }

    private Item getItemByID(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public int fitness(boolean[] genes) {
        int totalVolume = 0;
        int totalBenefit = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] && getItemByID(i+1) != null) {
                totalVolume += getItemByID(i+1).getPrice();
                totalBenefit += getItemByID(i+1).getValue();
            }
        }
        if (totalVolume > capacity) {
            return 0;
        } else {
            return totalBenefit;
        }
    }

    public void selection() {
        int end = sizeOfPopulation - 1;
        int begin = (int) (end - 0.2 * end);
        population.subList(begin, end).clear();
        int numberOfChromosomes = population.size();

        for (int i = 0; i < (end - begin) / 2; i++) {
            int firstParentIndex = random.nextInt(numberOfChromosomes);
            int secondParentIndex;
            while ((secondParentIndex = random.nextInt(numberOfChromosomes)) == firstParentIndex) { }
            crossover(firstParentIndex, secondParentIndex);
        }
    }

    public void crossover(int firstParentIndex, int secondParentIndex) {
        int crossOverPoint = random.nextInt(chromosomeLength);

        boolean[] genes1 = new boolean[chromosomeLength];
        boolean[] genes2 = new boolean[chromosomeLength];

        for (int i = 0; i < crossOverPoint; i++) {
            genes1[i] = population.get(firstParentIndex).getGenes()[i];
            genes2[i] = population.get(secondParentIndex).getGenes()[i];
        }

        for (int i = crossOverPoint; i < chromosomeLength; i++) {
            genes1[i] = population.get(firstParentIndex).getGenes()[i];
            genes2[i] = population.get(secondParentIndex).getGenes()[i];
        }

        genes1 = mutation(genes1);
        genes2 = mutation(genes2);

        Chromosome firstNewChromosome = new Chromosome(genes1, fitness(genes1));
        Chromosome secondNewChromosome = new Chromosome(genes2, fitness(genes2));

        population.add(firstNewChromosome);
        population.add(secondNewChromosome);
    }

    public boolean[] mutation(boolean[] genes) {
        for (int i = 0; i < genes.length; i++) {
            if (random.nextDouble() < 0.2) {
                changeGene(i, genes);
            }
        }
        return genes;
    }

    public void changeGene(int geneIndex, boolean[] genes) {
        for (int i = 0; i < genes.length; i++) {
            if (i == geneIndex) {
                genes[i] = !genes[i];
            }
        }
    }

    public String mostFitChromosome() {
        String printGenes = "";
        int chromosomeLength = population.get(0).getGenes().length;
        for (int i = 0; i < chromosomeLength; i++) {
            printGenes += population.get(0).getGenes()[i] ? 1 : 0;
        }
        return printGenes;
    }

}