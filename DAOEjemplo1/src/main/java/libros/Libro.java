package libros;
public class Libro {
    private String titulo;
    private String autor;
    private String genero;
    private boolean disponible;

    public Libro(String titulo, String autor, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponible = true;
    }

    public String getTitulo (){
        return this.titulo;
    }

    public String getAutor (){
        return this.autor;
    }

    public String getGenero (){
        return this.genero;
    }

    public void setTitulo (String titulo){
        this.titulo = titulo;
    }

    public void setAutor (String autor){
        this.autor = autor;
    }

    public void setGenero (String genero){
        this.genero = genero;
    }

    public void setDisponible (boolean disponible) {
        this.disponible = disponible;
    }

    public boolean estaDisponible (){
        return this.disponible;
    }
}
