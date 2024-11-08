public class Person {

    private int salaray = 0;
    private String name;
    private Department department;

    public Person(String inputName, int inputSalary, Department inputDepartment) {
        name = inputName;
        salaray = inputSalary;
        department = inputDepartment;
    }

    //Getters
    public int getSalary() {
        return salaray;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment(){
        return department;
    }

    //Setter
    public void setSalary(int inputSalary) {
        salaray = inputSalary;
    }

    public void setDepartment(Department inputDepartment) {
        department = inputDepartment;
    }

}
