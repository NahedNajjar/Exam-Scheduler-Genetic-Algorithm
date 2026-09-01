package application;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrossMutat {
    private Random random;

    public CrossMutat() {
        random = new Random();
    }
//one-point
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        Chromosome child = new Chromosome();
        int crossoverPoint = random.nextInt(parent1.size());

        for (int i = 0; i < parent1.size(); i++) {
            if (i < crossoverPoint) {
                child.addGene(new Gene(
                    parent1.getGenes().get(i).getCourse_Code(),
                    parent1.getGenes().get(i).getSlot_ID()
                ));
            } else {
                child.addGene(new Gene(
                    parent2.getGenes().get(i).getCourse_Code(),
                    parent2.getGenes().get(i).getSlot_ID()
                ));
            }
        }
        return child;
    }
    public void mutate(Chromosome chromosome, List<String> slots, double mutationRate) {
        for (int i = 0; i < chromosome.size(); i++) {
            if (random.nextDouble() < mutationRate) {
                String newSlot = slots.get(random.nextInt(slots.size()));
                chromosome.getGenes().get(i).setSlot_ID(newSlot);
            }
        }
    }
}