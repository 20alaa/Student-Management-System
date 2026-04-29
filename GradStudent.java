package kkuStudentManagement2;

public class GradStudent extends Student {
		
	    public GradStudent(String id, String name) {
	        super(id, name);
	    }

	    @Override
	    public int getPassingPercentage() {
	        return 80; 
	    }
	}


