package application;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
	
	
	
	 private List<String> courses = new ArrayList<>();
	    private List<String> slots = new ArrayList<>();
	    //StudentsID-->Courses
	    private Map<String, List<String>> student_Courses = new HashMap<>();
	    
	    public List<String> getCourses() {
	        return courses;
	    }

	    public List<String> getSlots() {
	        return slots;
	    }

	    public Map<String, List<String>> getStudent_Courses() {
	        return student_Courses;
	    }
	    
	    //Read Courses
	   //[COMP2110,COMP2340,....]
	    public void Courses(String filePath) throws Exception {
	        BufferedReader br = new BufferedReader(new FileReader(filePath));
	        String line;
	        br.readLine();
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            courses.add(parts[0].trim());
	        }
	        br.close();
	    }
	    //Read Slots
	    //[D1S1,D1S2,...]
	    public void Slots(String filePath) throws Exception {
	        BufferedReader br = new BufferedReader(new FileReader(filePath));
	        String line;
	        br.readLine(); 
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            slots.add(parts[0].trim());
	        }
	        br.close();
	    }
	    
	    //Read Enrollments
	    //1220704-->[COMP2110,...]
	    public void Enrollments(String filePath) throws Exception {
	        BufferedReader br = new BufferedReader(new FileReader(filePath));
	        String line;
	        br.readLine(); 
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split(",");
	            //StId
	            String studentId = parts[0].trim();
	            String course = parts[1].trim();
	            //if students is on list or not
	            if (!student_Courses.containsKey(studentId)) {
	                student_Courses.put(studentId, new ArrayList<>());
	            }
	            student_Courses.get(studentId).add(course);
	        }
	        br.close();
	    }
	    
	    
}
