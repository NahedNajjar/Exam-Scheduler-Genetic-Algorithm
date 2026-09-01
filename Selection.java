package application;


import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Selection {
    private Random random;

    public Selection() {
        random = new Random();
    }
    
   
    public Chromosome select(List<Chromosome> chromosomes) {
        double totalFitness = 0;
        for (Chromosome c : chromosomes) {
            totalFitness += c.getFitness();
        }
        double rand = random.nextDouble() * totalFitness;
        double Sum = 0;
        //Sum of fitness-->min Number of conflict
        for (Chromosome c : chromosomes) {
            Sum += c.getFitness();
            if (Sum >= rand) {
                return c;
            }
        }
        return chromosomes.get(chromosomes.size() - 1);
    }
}