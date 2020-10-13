package clasesAuxiliares;

public class classBusquedaApellidos {
    public String apellido;
    public String nombre;
    public int dni;
    public classBusquedaApellidos(String a , String n , int d){
        this.apellido = a;
        this.nombre = n;
        this.dni = d;
    }
    public classBusquedaApellidos(){

    }

    public int getDni() {
        return dni;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String toString()
    {
        return apellido+" "+nombre+ " - "+ dni;
    }
}


