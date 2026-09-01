package application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fitness {
    private Map<String, List<String>> studentCourses;

    public Fitness(Data data) {
        this.studentCourses = data.getStudent_Courses();
    }

    public double calculateFitness(Chromosome chromosome) {
        double penalty = 0;

        // create examSchedule-->Map(COMP2110-->D1S1,...)
        Map<String, String> examSchedule = new HashMap<String, String>();
        for (Gene gene : chromosome.getGenes()) {
            examSchedule.put(gene.getCourse_Code(), gene.getSlot_ID());
        }
//Num of exams per day
        Map<String, Integer> slotsUsedPerDay = new HashMap<String, Integer>();
        for (Gene gene : chromosome.getGenes()) {
            String day = getDay(gene.getSlot_ID());
            slotsUsedPerDay.put(day, slotsUsedPerDay.containsKey(day) ? slotsUsedPerDay.get(day) + 1 : 1);
        }

        // Preferred max used days-->5
        if (slotsUsedPerDay.size() > 5) {
            penalty += (slotsUsedPerDay.size() - 5) * 100;
        }

        // day has at most three exam slots.
        for (Map.Entry<String, Integer> entry : slotsUsedPerDay.entrySet()) {
            if (entry.getValue() > 3) {
                penalty += (entry.getValue() - 3) * 800;
            }
        }

        // List of courses for every student-->check Schedule
        for (List<String> courses : studentCourses.values()) {
            Map<String, Integer> examsPerDay = new HashMap<String, Integer>();

            for (int i = 0; i < courses.size(); i++) {
                String course1 = courses.get(i);
                if (!examSchedule.containsKey(course1)) continue;
                String slot1 = examSchedule.get(course1);
                String day1 = getDay(slot1);

                // Num of examsPerDay
                examsPerDay.put(day1, examsPerDay.containsKey(day1) ? examsPerDay.get(day1) + 1 : 1);

                for (int j = i + 1; j < courses.size(); j++) {
                    String course2 = courses.get(j);
                    if (!examSchedule.containsKey(course2)) continue;
                    String slot2 = examSchedule.get(course2);

                    // Same slot
                    if (slot1.equals(slot2)) {
                        penalty += 1000;
                    }

                    //  same day
                    if (getDay(slot1).equals(getDay(slot2)) && !slot1.equals(slot2)) {
                        penalty += 50;
                    }
                }
            }

            // no student has more than two exams in the same day
            for (String day : examsPerDay.keySet()) {
                int count = examsPerDay.get(day);
                if (count > 2) {
                    penalty += (count - 2) * 800;
                }
            }

            // student should not have four exams over two consecutive exam days.
            for (int day = 1; day <= 20; day++) {
                int current = examsPerDay.containsKey("D" + day) ? examsPerDay.get("D" + day) : 0;
                int next = examsPerDay.containsKey("D" + (day + 1)) ? examsPerDay.get("D" + (day + 1)) : 0;
                if (current + next >= 4) {
                    penalty += 500;
                }
            }
        }

        return 1.0 / (1 + penalty);
    }

    private String getDay(String slot) {
        int index = slot.indexOf('S');
        if (index == -1) return slot;
        return slot.substring(0, index);
    }
}