package clasesAuxiliares;

public class classListaConsultas {
    public String codConsulta,motivo,fecha;
    public classListaConsultas(){
    }

    @Override
    public String toString() {
        return
                "Codigo: "+ codConsulta +
                "\nMotivo: " + motivo +
                "\nFecha:  "+ fecha;
    }
}
