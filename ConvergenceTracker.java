package application;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class ConvergenceTracker {

    public static void saveToFile(List<Double> values, String filePath) throws Exception {
        PrintWriter pw = new PrintWriter(new FileWriter(filePath));
        pw.println("Generation,Fitness");

        for (int i = 0; i < values.size(); i++) {
            pw.println(i + "," + values.get(i));
        }

        pw.close();
    }
}