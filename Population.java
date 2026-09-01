package application;


import java.util.List;
import java.util.ArrayList;
import java.util.Random;

//Chromosomes
public class Population {
    private List<Chromosome> chromosomes;
    private Random random;

    public Population() {
        chromosomes = new ArrayList<>();
        random = new Random();
    }
    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }
//index-->Chromosome
    public Chromosome getChromosome(int index) {
        return chromosomes.get(index);
    }

    public int size() {
        return chromosomes.size();
    }

    public void setChromosome(int index, Chromosome chromosome) {
        chromosomes.set(index, chromosome);
    }
    //every time we called this method-->random sech
    private Chromosome RandomChromosome(List<String> courses, List<String> slots) {
        Chromosome chromosome = new Chromosome();
        
        for (String course : courses) {
        	//random Slot 
            String randomSlot = slots.get(random.nextInt(slots.size()));
            chromosome.addGene(new Gene(course, randomSlot));
        }
        return chromosome;
    }
    //size-->num of chromosome
    public void Population(int size, List<String> courses, List<String> slots) {
        for (int i = 0; i < size; i++) {
            chromosomes.add(RandomChromosome(courses, slots));
        }
    }
    
}