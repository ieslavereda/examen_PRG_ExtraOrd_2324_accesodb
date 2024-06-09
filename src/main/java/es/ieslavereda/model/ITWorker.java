package es.ieslavereda.model;

public class ITWorker extends Worker {
    private Categoria categoria;

    public ITWorker(int id,String nombre, String apellidos, String DNI, int edad, String email, int experiencia, Categoria categoria) {
        super(id,nombre, apellidos, DNI, edad, email, experiencia);
        this.categoria = categoria;
    }

    @Override
    public String getRole() {
        return categoria.getCategoria();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return super.toString() +
                " categoria=" + categoria +
                '}'+"\n";
    }

    public enum Categoria{
        FULLSTACK("FullStack"),
        BACKEND("BackEnd"),
        FRONTEND("FrontEnd");

        private String categoria;
        Categoria(String categoria){
            this.categoria = categoria;
        }
        public String getCategoria() {
            return categoria;
        }

        public static Categoria getCategoriaFromString(String categoria) throws Exception{
            for(Categoria categoria1:Categoria.values()){
                if (categoria1.getCategoria().equals(categoria))
                        return categoria1;
            }
            throw new Exception("Error: non valid category");
        }

    }

}
