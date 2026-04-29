package kkuStudentManagement2;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationSystem {

    private ArrayList<Student> students;

    public RegistrationSystem() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (findStudent(student.getId()) != null) {
            System.out.println("Error: Student with ID " + student.getId() + " already exists.");
            return;
        }
        this.students.add(student);
        System.out.println("Student added successfully: " + student.getName());
    }

    public Student findStudent(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public void displayAllStudentDetails() {
        if (students.isEmpty()) {
            System.out.println("No students in the system yet.");
            return;
        }
        
        System.out.println("\n--- All Student Details ---");
        for (Student s : students) {
            System.out.println("Name: " + s.getName());
            System.out.println("ID: " + s.getId());
            if (s instanceof UndergradStudent) {
                System.out.println("Type: Undergraduate Student");
            } else if (s instanceof GradStudent) {
                System.out.println("Type: Graduate Student");
            }
            s.displayMarksInfo();
        }
    }

    public void saveStudentsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Student s : students) {
                String type = (s instanceof UndergradStudent) ? "Undergrad" : "Grad";
                
                StringBuilder marksString = new StringBuilder();
                ArrayList<Integer> marks = s.getMarks();
                if (marks != null && !marks.isEmpty()) {
                    for (int i = 0; i < marks.size(); i++) {
                        marksString.append(marks.get(i));
                        if (i < marks.size() - 1) {
                            marksString.append(","); 
                        }
                    }
                }
                
                
                writer.println(s.getId() + ";" + s.getName() + ";" + type + ";" + marksString.toString());
            }
            System.out.println("Student data saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void loadStudentsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 3) continue; 

                String id = parts[0];
                String name = parts[1];
                String type = parts[2];

                Student student;
                if (type.equals("Undergrad")) {
                    student = new UndergradStudent(id, name);
                } else {
                    student = new GradStudent(id, name);
                }

                if (parts.length == 4 && !parts[3].isEmpty()) {
                    ArrayList<Integer> marks = new ArrayList<>();
                    String[] markStrings = parts[3].split(","); 
                    for (String markStr : markStrings) {
                        try {
                            marks.add(Integer.parseInt(markStr.trim()));
                        } catch (NumberFormatException e) {
                            System.out.println("Skipping invalid mark: " + markStr);
                        }
                    }
                    student.setMarks(marks);
                }
                this.students.add(student); 
            }
            System.out.println("Students loaded successfully from " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("No existing data file found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing file data: " + e.getMessage());
        }
    }
}


