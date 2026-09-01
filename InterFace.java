package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterFace {

    @FXML private TextField populationField;
    @FXML private TextField generationsField;
    @FXML private TextField mutationField;
    @FXML private TableView<Gene> scheduleTable;
    @FXML private TableColumn<Gene, String> courseCol;
    @FXML private TableColumn<Gene, String> slotCol;
    @FXML private LineChart<Number, Number> convergenceChart;
    @FXML private Label fitnessLabel;

    @FXML
    private void runGA() throws Exception {
        int populationSize = Integer.parseInt(populationField.getText().isEmpty() ? "500" : populationField.getText());
        int generations = Integer.parseInt(generationsField.getText().isEmpty() ? "2000" : generationsField.getText());
        double mutationRate = Double.parseDouble(mutationField.getText().isEmpty() ? "0.02" : mutationField.getText());

        Data loader = new Data();
        loader.Courses("C:\\Users\\hp\\Desktop\\Datatt\\Project4\\src\\application\\Course_catalog.csv");
        loader.Enrollments("C:\\Users\\hp\\Desktop\\Datatt\\Project4\\src\\application\\Enrollment_Pairs.csv");
        loader.Slots("C:\\Users\\hp\\Desktop\\Datatt\\Project4\\src\\application\\Exam_Slots.csv");
        GeneticAlgorithm ga = new GeneticAlgorithm(loader, populationSize, generations, mutationRate);
        Chromosome best = ga.run();
        
        testBadSchedule(loader);

        ObservableList<Gene> data = FXCollections.observableArrayList(best.getGenes());
        scheduleTable.setItems(data);
        courseCol.setCellValueFactory(cell ->
            new javafx.beans.property.SimpleStringProperty(cell.getValue().getCourse_Code()));
        slotCol.setCellValueFactory(cell ->
            new javafx.beans.property.SimpleStringProperty(cell.getValue().getSlot_ID()));

        fitnessLabel.setText("Best Fitness: " + String.format("%.6f", best.getFitness()));
       

        ConvergenceTracker.saveToFile(ga.getConvergence(), "convergence.csv");

        convergenceChart.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Fitness");
        List<Double> convergence = ga.getConvergence();
        for (int i = 0; i < convergence.size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, convergence.get(i)));
        }
        convergenceChart.getData().add(series);
    }
    public static void testBadSchedule(Data test) {
    	//We extracted this data from Sample Bad Schedule  sent by the instructor
         
        Chromosome badSchedule = new Chromosome();
        badSchedule.addGene(new Gene("COMP2110", "D1S2"));
        badSchedule.addGene(new Gene("COMP2340", "D1S3"));
        badSchedule.addGene(new Gene("COMP2380", "D2S3"));
        badSchedule.addGene(new Gene("COMP3130", "D1S3"));
        badSchedule.addGene(new Gene("COMP3320", "D3S1"));
        badSchedule.addGene(new Gene("COMP3330", "D3S1"));
        badSchedule.addGene(new Gene("COMP3340", "D3S2"));
        badSchedule.addGene(new Gene("COMP3390", "D3S3"));
        badSchedule.addGene(new Gene("COMP4110", "D4S1"));
        badSchedule.addGene(new Gene("COMP4120", "D4S2"));
        badSchedule.addGene(new Gene("COMP4310", "D4S3"));
        badSchedule.addGene(new Gene("COMP4330", "D5S1"));
        badSchedule.addGene(new Gene("COMP4350", "D5S2"));
        badSchedule.addGene(new Gene("COMP4360", "D5S3"));
        badSchedule.addGene(new Gene("ENEE2304", "D2S1"));
        badSchedule.addGene(new Gene("ENEE3307", "D2S2"));
        badSchedule.addGene(new Gene("ENEE4312", "D2S3"));
        badSchedule.addGene(new Gene("MATH1321", "D1S1"));
        badSchedule.addGene(new Gene("MATH2321", "D2S1"));
        badSchedule.addGene(new Gene("MATH2380", "D1S2"));
        badSchedule.addGene(new Gene("STAT2311", "D2S2"));
        badSchedule.addGene(new Gene("PHYS1411", "D1S1"));

        Fitness fitness = new Fitness(test);
        double score = fitness.calculateFitness(badSchedule);
        System.out.println("Bad Schedule Fitness: " +String.format("%.6f", score));
        System.out.println("Bad Schedule Penalty: " + (long)(1.0/score - 1));
    }
}