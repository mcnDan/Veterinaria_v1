package clasesAuxiliares;

public class classMascota {
    public String codigo;
    private String nombre;
    public String descripcion;
    public String raza;
    public String sexo;
    public double edad;
    public classMascota(){

    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public double getEdad() {
        return edad;
    }

    public String getRaza() {
        return raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setEdad(double edad) {
        this.edad = edad;
    }

    @Override
    public String toString(){
        return "Nombre: "+ nombre +"\n" + "Especie: "+descripcion+"\n"+ "Raza: "+ raza+"\n"+"Sexo: "+sexo+"\n"+ "Edad: "+edad;

    }
}
