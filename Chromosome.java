package application;


import java.util.List;
import java.util.ArrayList;

public class Chromosome {
    private List<Gene> genes;
    
    private double fitness;

    public Chromosome() {
        genes = new ArrayList<>();
        fitness = 0;
    }
    public List<Gene> getGenes() {
        return genes;
    }
//To Chromosome
    public void addGene(Gene gene) {
        genes.add(gene);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double d) {
        this.fitness = d;
    }

    public int size() {
        return genes.size();
    }
}