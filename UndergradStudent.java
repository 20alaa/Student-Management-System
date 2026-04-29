package kkuStudentManagement2;


public class UndergradStudent extends Student {

    public UndergradStudent(String id, String name) {
        super(id, name);
    }

   
    @Override
    public int getPassingPercentage() {
        return 60;
    }
}