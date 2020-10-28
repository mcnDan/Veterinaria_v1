package clasesAuxiliares;

public class classFichaConsulta {
    public String codConsulta, codMascota, motivo,fechaC, triajeC, descripcionC,
            hallazgosC, pruebasAux, diagnostico, trataC;
    private boolean requiereT,chkPago;

    public classFichaConsulta() {
    }

    @Override
    public String toString() {
        return "Codigo consulta: "+codConsulta+"\n"
                         + triajeC
                        + descripcionC
                         + hallazgosC
                        + pruebasAux
                         + diagnostico
                         + trataC;

    }
}
