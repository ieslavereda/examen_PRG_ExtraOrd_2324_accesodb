package es.ieslavereda.model;

public class NonITWorker extends Worker {
    private final Department departament;

    public NonITWorker(int id, String nombre, String apellidos, String DNI, int edad, String email, int experiencia, Department departament) {
        super(id, nombre, apellidos, DNI, edad, email, experiencia);
        this.departament = departament;
    }

    public Department getDepartament() {
        return departament;
    }

    @Override
    public String toString() {
        return super.toString() +
                " department=" + departament +
                '}'+"\n";
    }

    @Override
    public String getRole() {
        return getDepartament().getDepartmentAsString();
    }

    public enum Department{
        BOARD("Board"),
        RRHH("RRHH");

        private String department;
        Department(String department){
            this.department = department;
        }
        public String getDepartmentAsString() {
            return department;
        }

        public static Department getDepartmentFromString(String department) throws Exception{
            for(Department department1: Department.values()){
                if (department1.getDepartmentAsString().equals(department))
                    return department1;
            }
            throw new Exception("Error: non valid department");
        }

    }
}
