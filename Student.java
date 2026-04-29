package kkuStudentManagement2;

import java.util.ArrayList;

public abstract class Student implements StudentBehavior {

    private String name;
    private String id;
    private ArrayList<Integer> marks;

    public Student(String id, String name) {
        if (!isValidKkuId(id)) {
            throw new IllegalArgumentException("Invalid KKU ID. Must be 9 digits and start with '4'.");
        }
        this.id = id;
        this.name = toTitleCase(name.trim());
        this.marks = new ArrayList<>(); 
    }

    @Override
    public abstract int getPassingPercentage();

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void displayMarksInfo() {
        if (marks == null || marks.isEmpty()) {
            System.out.println("No marks to display.");
            return;
        }

        int total = 0;
        int min = marks.get(0);
        int max = marks.get(0);

        for (int mark : marks) {
            total += mark;
            if (mark < min) min = mark;
            if (mark > max) max = mark;
        }

        double average = (double) total / marks.size();
        
        
        int passingMark = getPassingPercentage(); 

        System.out.println("\n--- Marks Analysis ---");
        System.out.printf("Average: %.1f\n", average);
        System.out.println("======================================");
        System.out.printf("%-10s %-7s %-7s %-7s\n", "Course", "Mark", "P/F", "Min/Max");
        System.out.println("--------------------------------------");

        for (int i = 0; i < marks.size(); i++) {
            int mark = marks.get(i);
            String status = (mark >= passingMark) ? "Pass" : "Fail";
            String minMaxLabel = "";
            if (mark == max) minMaxLabel += "Max";
            if (mark == min) minMaxLabel += (minMaxLabel.isEmpty() ? "Min" : "/Min");
            if (minMaxLabel.isEmpty()) minMaxLabel = "--";

            System.out.printf("%-10d %-7d %-7s %-7s\n", (i + 1), mark, status, minMaxLabel);
        }
        System.out.println();
    }
    

    public ArrayList<Integer> getMarks() {
        return marks;
    }

    
    public void setMarks(ArrayList<Integer> marks) {
        for (int mark : marks) {
            if (mark < 0 || mark > 100) {
                System.out.println("Error: Mark " + mark + " is invalid. Skipping.");
                return; 
            }
        }
        this.marks = marks;
    }

 
    public static String toTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        String[] words = text.split("\\s+");
        StringBuilder titleCaseText = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                titleCaseText.append(Character.toUpperCase(word.charAt(0)))
                             .append(word.substring(1).toLowerCase())
                             .append(" ");
            }
        }
        return titleCaseText.toString().trim();
    }

    
    public static boolean isValidKkuId(String id) {
        return id != null && id.matches("^4\\d{8}$");
    }
}