package application;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
    private Data data;
    private Fitness fitnessFunction;
    private Population population;
    private Selection selection;
    private CrossMutat operators;

    private int populationSize;
    private int generations;
    private double mutationRate;
    private List<Double> convergence;

    public GeneticAlgorithm(Data data, int populationSize, int generations, double mutationRate) {
        this.data = data;
        this.populationSize = populationSize;
        this.generations = generations;
        this.mutationRate = mutationRate;

        this.fitnessFunction = new Fitness(data);
        this.population = new Population();
        this.selection = new Selection();
        this.operators = new CrossMutat();
        this.convergence = new ArrayList<>();

        population.Population(populationSize, data.getCourses(), data.getSlots());
        initializePopulation();
    }

    private void initializePopulation() {
        for (Chromosome c : population.getChromosomes()) {
        	
            c.setFitness(fitnessFunction.calculateFitness(c));
        }
    }

    public Chromosome run() {
        Chromosome best = getBestChromosome();

        for (int gen = 0; gen < generations; gen++) {
            List<Chromosome> newGeneration = new ArrayList<>();
            newGeneration.add(best);

            while (newGeneration.size() < populationSize) {
                Chromosome parent1 = selection.select(population.getChromosomes());
                Chromosome parent2 = selection.select(population.getChromosomes());

                Chromosome child = operators.crossover(parent1, parent2);
                operators.mutate(child, data.getSlots(), mutationRate);
                child.setFitness(fitnessFunction.calculateFitness(child));

                newGeneration.add(child);
            }

            population.getChromosomes().clear();
            population.getChromosomes().addAll(newGeneration);

            best = getBestChromosome();
            convergence.add(best.getFitness());
            
//Stop
            if (best.getFitness() >= 1.0) {
                break;
            }
        }

        return best;
    }

    private Chromosome getBestChromosome() {
        Chromosome best = population.getChromosome(0);
        for (Chromosome c : population.getChromosomes()) {
            if (c.getFitness() > best.getFitness()) {
                best = c;
            }
        }
        return best;
    }

    public List<Double> getConvergence() {
        return convergence;
    }
}