package es.ieslavereda;

import es.ieslavereda.model.*;
import es.ieslavereda.repository.ITWorkerRepository;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {


        // cargar datos desde csv
        List<Worker> workers = cargarDatos("documento.csv");
        ITWorkerRepository repository = new ITWorkerRepository();
        ITWorker itWorker = new ITWorker(1,"Nuevo","Añadido","9888X",64,"conexperiencia@informatica.es",40, ITWorker.Categoria.FULLSTACK);
        repository.addITWorker(itWorker);
        workers.addAll(repository.getAllITWorkers());
        imprimirEmpleados(getWorkersSorted(workers));
//        repository.deleteITWorkerById(itWorker.getId());
        itWorker.setCategoria(ITWorker.Categoria.FRONTEND);
        repository.updateITWorker(itWorker);
        // guardar personas como objeto
        saveAsObject(new HashSet<>(workers));
        // cargar personas
        Set<Worker> workerSet = loadObjectFile();
        System.out.println(getNonITWorkersSortedByAge(workerSet));
        System.out.println(getCategoryOfITWorkers(workerSet));

    }

    private static List<Worker> cargarDatos(String file) {

        List<Worker> workers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String l;
            br.readLine(); // Eliminamos la primera linea
            while ((l = br.readLine()) != null) {
                String[] fields = l.split(";");
                int id = Integer.parseInt(fields[0]);
                String nombre = fields[1];
                String apellidos = fields[2];
                String DNI = fields[3];
                int edad = Integer.parseInt(fields[4]);
                String mail = fields[5];
                int experiencia = Integer.parseInt(fields[6]);

                try{
                    if (fields[7].equals("")) {
                        String departmentString = fields[8];
                        NonITWorker.Department department = NonITWorker.Department.getDepartmentFromString(departmentString);
                        workers.add(new NonITWorker(id, nombre,apellidos,DNI,edad,mail,experiencia,department));
                    } else {

                        String categoriaString = fields[7];
                        ITWorker.Categoria categoria = ITWorker.Categoria.getCategoriaFromString(categoriaString);
                        workers.add(new ITWorker(id,nombre,apellidos,DNI,edad,mail,experiencia,categoria));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return workers;
    }

    private static Collection<Payable> getWorkersSorted(List<Worker> workers) {
        return workers.stream().sorted().collect(Collectors.toList());
    }

    private static void imprimirEmpleados(Collection<Payable> payables){
        for(Payable i : payables){
            System.out.println("-----------------------");
            System.out.println(i.getFullName()+" "+i.getRole() +" "+i.getYearsExperience());
        }
    }


    private static Set<Worker> loadObjectFile() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("trabajadores.dat"))){
            return (Set<Worker>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveAsObject(Set<Worker> Workers) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("trabajadores.dat"))) {
            oos.writeObject(Workers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Payable> getNonITWorkersSortedByAge(Set<Worker> Workers){
        return Workers.stream()
                .filter(p -> p instanceof NonITWorker)
                .map(p-> (NonITWorker)p)
                .sorted(Comparator.comparingInt(Worker::getEdad))
                .collect(Collectors.toList());
    }

    private static Map<ITWorker.Categoria, List<ITWorker>> getCategoryOfITWorkers(Set<Worker> Workers) {

        Map<ITWorker.Categoria, List<ITWorker>> categorias = new HashMap<>();

        Workers.stream()
                .filter( p -> p instanceof ITWorker)
                .map(Worker -> (ITWorker) Worker)
                .forEach( itWorker -> {
                    if(categorias.containsKey(itWorker.getCategoria()))
                        categorias.get(itWorker.getCategoria()).add(itWorker);
                    else
                        categorias.put(itWorker.getCategoria(),new ArrayList<>(List.of(itWorker)));
                });

        return categorias;
    }

}