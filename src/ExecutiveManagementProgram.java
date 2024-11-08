import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ExecutiveManagementProgram {

    int baseSalary = 40000;

    //Queues that hold the list of departments, and the unassigned employees
    Queue<Department> departments = new LinkedList<>();
    Queue<Person> unemployed = new LinkedList<>();

    Scanner userInput = new Scanner(System.in);

    //Runs program
    public void runProgram(){
        userInput();
    }

    //Creates a new department
    public void addDepartment() {
        System.out.print("Enter new department name: ");
        Queue<Person> departmentExecutives = new LinkedList<>();
        Department newDepartment = new Department(userInput.next(), departmentExecutives);
        departments.add(newDepartment);
    }

    //Creates a new executive
    public void newExecutive() {
        System.out.println("Enter new executive name: ");
        Person newExecutive = new Person(userInput.next(), 0, null);
        unemployed.add(newExecutive);
    }

    //Adds an unassigned executive to a department
    public void joinDepartment() {

        Person storage = doesExecutiveExist();

        if(storage == null) {
            inputNotExist();
            return;
        }

        enterDepartmentName();
        String departmentName = userInput.next();

        int y = departments.size();
        for (int i = 0; i < y; i++) {
            if (departments.peek().getName().equals(departmentName)) {
                storage.setSalary(baseSalary);
                storage.setDepartment(departments.peek());
                departments.peek().addExecutive(storage);
                storage = null;
                break;
            }
            departments.add(departments.remove());
        }
        if(storage != null) {
            inputNotExist();
        }
        if(storage == null) {
            departmentFixSalary();
        }
    }

    //Removes executive from department
    public void fireExecutive() {

        Person storage = doesExecutiveExist();

        if(storage == null) {
            inputNotExist();
            return;
        }
        if(storage.getDepartment() == null){
            System.out.println("Executive is not in a department.");
            return;
        }

        Department currentDepartment = storage.getDepartment();
        int y = currentDepartment.getSize();
        boolean wasExecutiveFired = false;
        for (int i = 0; i < y; i++) {
            if(currentDepartment.getExecutivesQueue().peek() == storage) {
                storage.setSalary(baseSalary);
                storage.setDepartment(null);
                currentDepartment.getExecutivesQueue().remove();
                wasExecutiveFired = true;
                break;
            }
            currentDepartment.getExecutivesQueue().add(currentDepartment.getExecutivesQueue().remove());
        }
        if(wasExecutiveFired) {
            departmentFixSalary();
        }
        else{
            inputNotExist();
        }
    }

    //Gets salary of specific executive and prints it
    public void getSalary(){

        Person storage = doesExecutiveExist();

        if(storage == null) {
            inputNotExist();
        }
        else {

            System.out.println(storage.getSalary());
        }
    }

    public void getPayroll(){
        for(int i = 0; i < departments.size(); i++) {
            if (departments.peek() == null) {
                inputNotExist();
            } else {
                System.out.println(departments.peek().getName());
                for (int j = 0; j < departments.peek().getSize(); j++) {
                    System.out.print(departments.peek().getExecutivesQueue().peek().getName() + ": ");
                    System.out.println(departments.peek().getExecutivesQueue().peek().getSalary());
                    departments.peek().getExecutivesQueue().add(departments.peek().getExecutivesQueue().remove());
                }
                System.out.println("---");
                System.out.println();
                departments.add(departments.remove());
            }
        }
    }

    //Moves an executive already within a department to another already existing department
    public void changeExecutive(){

        enterExecutiveName();
        String executiveName = userInput.next();

        Person storage = null;

        int x = unemployed.size();
        for (int i = 0; i < x; i++) {
            if (unemployed.peek().getName().equals(executiveName) && unemployed.peek().getDepartment() != null) {
                storage = unemployed.peek();
                break;
            }
            unemployed.add(unemployed.remove());
        }
        if (storage == null) {
            inputNotExist();
            return;
        }

        Department currentDepartment = storage.getDepartment();
        int y = currentDepartment.getSize();
        for (int i = 0; i < y; i++) {
            if(currentDepartment.getExecutivesQueue().peek() == storage) {
                storage.setDepartment(null);
                currentDepartment.getExecutivesQueue().remove();
                break;
            }
            currentDepartment.getExecutivesQueue().add(currentDepartment.getExecutivesQueue().remove());
        }

        enterDepartmentName();
        String departmentName = userInput.next();

        int z = departments.size();
        for (int i = 0; i < z; i++) {
            if (departments.peek().getName().equals(departmentName)) {
                storage.setDepartment(departments.peek());
                departments.peek().addExecutive(storage);
                storage = null;
                break;
            }
            departments.add(departments.remove());
        }
        if(storage != null) {
            inputNotExist();
        }
        if(storage == null) {
            departmentFixSalary();
        }
    }

    //Verifies whether the executive that the user entered exists or not, and returns the name if it does
    public Person doesExecutiveExist(){
        enterExecutiveName();
        String executiveName = userInput.next();

        Person storage = null;

        int x = unemployed.size();
        for (int i = 0; i < x; i++) {
            if (unemployed.peek().getName().equals(executiveName)) {
                storage = unemployed.peek();
                return storage;
            }
            unemployed.add(unemployed.remove());
        }
            return null;
    }

        public void departmentFixSalary(){
            for(int i = 0; i < departments.size(); i++){
                if(departments.peek().getSize() <= 1){}
                else {
                    for(int j = 0; j < departments.peek().getSize(); j++) {
                        departments.peek().getExecutivesQueue().peek().setSalary(baseSalary + (departments.peek().getSize()-j-1)*5000);
                        departments.peek().getExecutivesQueue().add(departments.peek().getExecutivesQueue().remove());
                    }

                }
                departments.add(departments.remove());
            }
        }

        //Reads the user input and matches it with the corresponding method
        public void userInput(){
        userCommands();
        String input = userInput.next();
            while(!input.equals("EXIT")){

                if(input.equals("HELP")){
                    userCommands();
                }
                else if(input.equals("ADD")){
                    addDepartment();
                }
                else if(input.equals("HIRE")){
                    newExecutive();
                }
                else if(input.equals("FIRE")){
                    fireExecutive();
                }
                else if(input.equals("JOIN")){
                    joinDepartment();
                }
                else if(input.equals("CHANGE")){
                    changeExecutive();
                }
                else if(input.equals("PAYROLL")){
                    getPayroll();
                }
                else if(input.equals("SALARY")){
                    getSalary();
                }
                else{
                    inputNotExist();
                }
                input = userInput.next();
            }
            System.out.println("Program ended.");
        }

        //Flavor Text
        public void userCommands(){
        System.out.println("COMMANDS: ");
        System.out.println("'HELP' = Show commands.");
        System.out.println("'ADD' = Create new department.");
        System.out.println("'HIRE' = Create new executive.");
        System.out.println("'JOIN' = Add executive to department.");
        System.out.println("'FIRE' = Fire executive from department.");
        System.out.println("'CHANGE' = Move executive from one department to another.");
        System.out.println("'PAYROLL' = Display all executive salaries.");
        System.out.println("'SALARY' = Display specific executive salary.");
        System.out.println("'EXIT' = Exit program.");
        }

        public void inputNotExist(){
        System.out.println("Input does not exist.");
        }

        public void enterExecutiveName(){
        System.out.println("Enter executive name: ");
        }

        public void enterDepartmentName(){
        System.out.println("Enter department name: ");
        }
    }


