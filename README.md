# Exam Scheduler - Genetic Algorithm

This is our project for COMP338 (Artificial Intelligence) at Birzeit University, 2026. It's a JavaFX app that builds an exam timetable using a Genetic Algorithm, trying to avoid exam clashes for students as much as possible.

## What this project is about

Every semester the university has to schedule exams for a bunch of students taking different courses, and it's easy to end up with students having two exams at once or way too many exams packed into one day. Since there are too many possible schedules to just try them all, we used a Genetic Algorithm instead - basically we generate a bunch of random schedules, then keep "evolving" them (picking the better ones, mixing them together, adding some random changes) until we get a schedule with as few conflicts as possible.

We're working with 95 students across 22 courses, and the exams are spread over 6 days with 3 slots per day.

## How it works

- **Initialization**: we start by making a bunch of totally random schedules (each course gets assigned to a random slot).
- **Fitness**: each schedule gets scored based on how many rules it breaks. We used `fitness = 1.0 / (1 + penalty)`, so fewer violations = higher fitness. The things we penalize:
  - a student having two exams in the same slot
  - a student having two exams on the same day
  - more than 2 exams for one student in a single day
  - 4 or more exams for a student across two days in a row
  - using more than 5 exam days total
  - putting more than 3 exams in one day
- **Selection**: parents are picked using roulette-wheel selection, so schedules with higher fitness have a better chance of getting picked.
- **Crossover**: we take one-point crossover - the child gets the first part of its genes from parent 1 and the rest from parent 2.
- **Mutation**: with a small probability, we randomly change a course's assigned slot, just so the algorithm doesn't get stuck.
- **Elitism/Termination**: we always keep the best schedule from each generation so we never lose progress, and the algorithm stops either when it hits the max number of generations or reaches a perfect fitness of 1.0.

## Project Structure

```
├── src/application/
│   ├── Main.java                  # JavaFX application entry point
│   ├── InterFace.java             # UI controller (FXML bindings, run logic)
│   ├── Data.java                  # Loads courses, slots, and enrollment data from CSV
│   ├── Gene.java                  # Represents a single (course, slot) assignment
│   ├── Chromosome.java            # A full candidate exam schedule (list of genes)
│   ├── Population.java            # Manages the population of chromosomes
│   ├── Fitness.java               # Penalty-based fitness function
│   ├── Selection.java             # Roulette-wheel parent selection
│   ├── CrossMutat.java            # Crossover and mutation operators
│   ├── GeneticAlgorithm.java      # Main GA loop (evolution across generations)
│   ├── ConvergenceTracker.java    # Logs best fitness per generation to CSV
│   ├── IinterFace.fxml            # JavaFX UI layout
│   └── application.css            # UI styling
├── Course_catalog.csv             # Course codes, credits, enrollment counts
├── Enrollment_Pairs.csv           # Student ID → enrolled course pairs
├── Exam_Slots.csv                 # Available exam day/slot definitions and GA parameters

```

## What you need to run it

- Java JDK 17 or newer
- JavaFX SDK (17+), in case it's not already bundled with your JDK
- Eclipse or any IDE with JavaFX support - we built and tested it in Eclipse

## Running the project

1. Clone the repo and open it as a Java project in your IDE.
2. Make sure JavaFX is added to the build path.
3. Important: in `InterFace.java` the CSV files are currently loaded using our own local Windows paths (something like `C:\Users\hp\Desktop\...`). You'll need to change those three paths to wherever you put `Course_catalog.csv`, `Enrollment_Pairs.csv`, and `Exam_Slots.csv` on your own machine, otherwise it won't find the files.
4. Run `Main.java`.
5. From the UI you can change the parameters if you want (or just leave the defaults):
   - Population Size (default 500)
   - Generations (default 2000)
   - Mutation Rate (default 0.02)
6. Hit Run. It'll show the generated schedule, the best fitness value it reached, and a chart of how fitness improved across generations. It also writes the convergence values to `convergence.csv`.

## Results

We tried a few different parameter combinations (population size, mutation rate, number of generations) and in pretty much all of them the algorithm converged early, somewhere around generation 20-50, and didn't really improve much after that. The final schedules satisfied all the hard constraints - no student ends up with two exams at once, no more than 2 exams a day, and no 4 exams across two back-to-back days.

We also tested our fitness function against a bad schedule the instructor gave us as an example, just to make sure it actually catches violations - it scored a fitness of 0.000003 with a penalty of 303,950, which confirmed the function was working correctly.

More detail on the parameter tuning and the reasoning behind everything is in the report: [`GeneticAlgorithmProjectReport.pdf`](./GeneticAlgorithmProjectReport.pdf).

## Group Members

- Nahed Najjar - 1220704
- Sylia Darabuzedan - 1230838

Instructor: Radi Jarrar
Course: COMP338 - Artificial Intelligence, Faculty of Engineering and Technology, Department of Computer Science, Birzeit University, 2026.

## References

2. JavaFX documentation
3. Russell, S. & Norvig, P., Artificial Intelligence: A Modern Approach, 3rd edition, Pearson, 2010.
4. GeeksforGeeks - Genetic Algorithms.
5. JavaFX Scene Builder documentation
