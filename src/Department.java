import java.util.Queue;

public class Department {

    private String name;
    private Queue<Person> executivesQueue;

    public Department(String nameInput, Queue<Person> executivesInput) {
        name = nameInput;
        executivesQueue = executivesInput;

    }

    //Getters

    public String getName() {
        return name;
    }

    public int getSize(){
        return executivesQueue.size();
    }

    public Queue<Person> getExecutivesQueue() {
        return executivesQueue;
    }

    //Misc

    public void addExecutive(Person input) {
        executivesQueue.add(input);
    }

}
