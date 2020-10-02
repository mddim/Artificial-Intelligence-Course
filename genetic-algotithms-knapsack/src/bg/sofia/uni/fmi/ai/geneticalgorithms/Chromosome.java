package bg.sofia.uni.fmi.ai.geneticalgorithms;

public class Chromosome {

    private boolean[] genes;
    private int fitness;

    public Chromosome(boolean[] genes, int fitness) {
        this.genes = genes;
        this.fitness = fitness;
    }

    public boolean[] getGenes() {
        return genes;
    }

    public int getFitness() {
        return fitness;
    }

    public void setGenes(boolean[] genes) {
        this.genes = genes;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}
