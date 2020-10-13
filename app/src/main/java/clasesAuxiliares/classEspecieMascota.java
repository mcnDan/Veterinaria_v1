package clasesAuxiliares;

public class classEspecieMascota {
    public String codEspecie;
    public String nomEspecie;

    public String getCodEspecie() {
        return codEspecie;
    }

    public String getNomEspecie() {
        return nomEspecie;
    }

    @Override
    public String toString() {
        return
                "codEspecie='" + codEspecie + '\'' +
                ", nomEspecie='" + nomEspecie + '\'' +
                '}';
    }
}
