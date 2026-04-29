package kkuStudentManagement2;

import java.util.ArrayList;
import java.util.Scanner;

public class Main1 {

    private static final String DATA_FILE = "students_data.txt";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        RegistrationSystem system = new RegistrationSystem();
        int choice = 0;

        system.loadStudentsFromFile(DATA_FILE);

        do {
            displayMenu();
            System.out.print("Enter your choice:  ");
            
            while (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                System.out.print("Enter your choice:  ");
                input.next();
            }
            choice = input.nextInt();
            input.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter Student Name: ");
                    String nameInput = input.nextLine();
                    String studentName = Student.toTitleCase(nameInput.trim()); 

                    String studentId;
                    do {
                        System.out.print("Enter ID Number: ");
                        studentId = input.nextLine();
                        if (!Student.isValidKkuId(studentId)) { 
                            System.out.println("Invalid ID. Must be 9 digits and start with '4'. Please try again.");
                        }
                    } while (!Student.isValidKkuId(studentId));

                    int type = 0;
                    do {
                        System.out.print("Enter Student Type (1: Undergrad, 2: Grad): ");
                        while (!input.hasNextInt()) {
                            System.out.println("Invalid input, please enter 1 or 2.");
                            System.out.print("Enter Student Type (1: Undergrad, 2: Grad): ");
                            input.next();
                        }
                        type = input.nextInt();
                        if (type != 1 && type != 2) {
                            System.out.println("Invalid choice. Please enter 1 or 2.");
                        }
                    } while (type != 1 && type != 2);
                    
                    Student newStudent;
                    if (type == 1) {
                        newStudent = new UndergradStudent(studentId, studentName);
                    } else {
                        newStudent = new GradStudent(studentId, studentName);
                    }
                    
                    system.addStudent(newStudent);
                    break;

                case 2:
                    System.out.print("Enter Student ID to enter/modify marks: ");
                    String idToModify = input.nextLine();
                    Student studentToModify = system.findStudent(idToModify);

                    if (studentToModify == null) {
                        System.out.println("Student with ID " + idToModify + " not found.\n");
                        break;
                    }
                    
                    int numCourses;
                    do {
                        System.out.print("How many courses for " + studentToModify.getName() + "? (Enter a positive number): ");
                        while (!input.hasNextInt()) {
                            System.out.println("Invalid input, please enter a number.");
                            System.out.print("How many courses?: ");
                            input.next();
                        }
                        numCourses = input.nextInt();
                        if (numCourses <= 0) { 
                            System.out.println("Invalid number. Must be a positive number.");
                        }
                    } while (numCourses <= 0); 

                    ArrayList<Integer> newMarks = new ArrayList<>();
                    System.out.println("Enter Marks for " + numCourses + " subjects (each between 0-100):");
                    
                    for (int i = 0; i < numCourses; i++) {
                        System.out.print("Mark for course " + (i + 1) + ": ");
                        int mark;
                        while (!input.hasNextInt()) {
                            System.out.println("Invalid input, please enter a number.");
                            System.out.print("Mark for course " + (i + 1) + ": ");
                            input.next();
                        }
                        mark = input.nextInt();
                        while (mark < 0 || mark > 100) {
                            System.out.print("Invalid mark. Please enter 0-100 for course " + (i + 1) + ": ");
                            while (!input.hasNextInt()) {
                                System.out.println("Invalid input, please enter a number.");
                                System.out.print("Mark for course " + (i + 1) + ": ");
                                input.next();
                            }
                            mark = input.nextInt();
                        }
                        newMarks.add(mark);
                    }
                    input.nextLine(); 
                    
                    studentToModify.setMarks(newMarks);
                    System.out.println("\nMarks entered successfully for " + studentToModify.getName());
                    studentToModify.displayMarksInfo(); 
                    break;

                case 3:
                    System.out.print("Enter Student ID to display marks: ");
                    String idToDisplay = input.nextLine();
                    Student s = system.findStudent(idToDisplay);
                    
                    if (s == null) {
                        System.out.println("Student with ID " + idToDisplay + " not found.\n");
                    } else if (s.getMarks().isEmpty()) {
                        System.out.println("Please enter marks first for this student (Option 2).\n");
                    } else {
                        s.displayMarksInfo();
                    }
                    break;

                case 4:
                    system.displayAllStudentDetails();
                    break;

                case 5: 
                    system.saveStudentsToFile(DATA_FILE);
                    System.out.println("Exiting the program. Have a nice day!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.\n");
            }
        } while (choice != 5);

        input.close();
    }

    public static void displayMenu() {
        System.out.println("\n--- KKU Student Management System   ---");
        System.out.println("1. Enter Student Details");
        System.out.println("2. Enter/Modify Marks");
        System.out.println("3. Display Marks Info (for one student)");
        System.out.println("4. Display All Student Details");
        System.out.println("5. Exit");
    }
}