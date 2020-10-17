package veterinaria_v1.basedatosConeccion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import clasesAuxiliares.classBusquedaApellidos;
import clasesAuxiliares.classEspecieMascota;
import clasesAuxiliares.classMascota;

public class accesoBasedatos {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private accesoBasedatos accesoBd;
    private Cursor c = null;
    private static accesoBasedatos instance;

    private accesoBasedatos(Context context) {
        this.openHelper = new bdConeccion(context);
    }

    public static accesoBasedatos getInstance(Context context) {
        if (instance == null)
            instance = new accesoBasedatos(context);
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null)
            this.database.close();
    }

    //codigo de version anterior al 08/10/2020
    public ArrayList<classBusquedaApellidos> buscarPorApellido(String ape) {
        c = database.rawQuery("select p.apellidos , p.nombres, c.cod_cliente_dni " +
                "from persona p join cliente c on p.cod_persona=c.cod_persona " +
                "where apellidos LIKE '%" + ape + "%' order by apellidos", null);

        ArrayList<classBusquedaApellidos> aux = new ArrayList<classBusquedaApellidos>();
        classBusquedaApellidos busApe;
        while (c.moveToNext()) {
            busApe = new classBusquedaApellidos();
            busApe.apellido = c.getString(c.getColumnIndex("apellidos"));
            busApe.nombre = c.getString(c.getColumnIndex("nombres"));
            String dni = c.getString(c.getColumnIndex("cod_cliente_dni"));
            busApe.dni = Integer.parseInt(dni);
            aux.add(busApe);
        }
        return aux;
    }
    public String getCliente(int dni){
        c=database.rawQuery("select p.apellidos, p.nombres from persona p join cliente c " +
                "on p.cod_persona=c.cod_persona where c.cod_cliente_dni="+dni+"",null);
        String aux="";
        if(c!=null){
            while(c.moveToNext()){
                aux = c.getString(00);
                aux = aux + " " + c.getString(1);
            }
        }
        return aux;
    }

    public ArrayList<classMascota> getMascota(int num){
        c = database.rawQuery("select m.cod_mascota, m.nombre , e.nombre as tipo , m.raza , m.sexo , m.edad " +
                "from mascota m join cliente c on m.cod_cliente = c.cod_cliente_dni join especie e " +
                "on m.cod_especie = e.cod_especie where c.cod_cliente_dni = "+num+" order by m.nombre",null);
        ArrayList<classMascota> aux = new ArrayList<>();
        classMascota cmascota;
        while(c.moveToNext()){
            cmascota = new classMascota();
            cmascota.codigo = c.getString(c.getColumnIndex("cod_mascota"));
            cmascota.setNombre(c.getString(c.getColumnIndex("nombre")));
            cmascota.descripcion = c.getString((c.getColumnIndex("tipo")));
            cmascota.raza = c.getString((c.getColumnIndex("raza")));
            cmascota.sexo=c.getString(c.getColumnIndex("sexo"));
            String aux_dni = c.getString(c.getColumnIndex("edad"));
            cmascota.edad= Integer.parseInt(aux_dni);
            aux.add(cmascota);
        }
        return aux;
    }
    // METODOS AUXILIARES
    public String getFechahoy(){
        Date ahora = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(ahora);
    }
    //generando codigos personalizados de clase PERSONA, MASCOTA , CONSULTA , TRATAMIENTO, VACUNAS Y SERVICIOS
        public String generarCodPersona(){
        c= database.rawQuery("select cod_persona" +
                " from persona",null);
        int contador = c.getCount()+1;
        String aux = "PER000"+String.valueOf(contador);
        return aux;
    }
    public String generarCodMascota(){
        c= database.rawQuery("select cod_mascota" +
                " from mascota",null);
        int contador = c.getCount()+1;
        String aux = "MSC000"+String.valueOf(contador);
        return aux;
    }
    public String generarCodConsulta(){
        c= database.rawQuery("select cod_consulta" +
                " from consulta",null);
        int contador = c.getCount()+1;
        String aux = "CON000"+String.valueOf(contador);
        return aux;
    }
    public String generarCodTratamiento() {
        c = database.rawQuery("select cod_tratamiento" +
                " from tratamiento", null);
        int contador = c.getCount() + 1;
        String aux = "TRT0001" + String.valueOf(contador);
        return aux;
    }

    // Insertar
    public void insertarPersona(String codigo , String apellidos , String nombres, String telefono,
                                String correo , String direccion) {
        String sqlAux = "insert into persona values " +
                "('" + codigo + "','" + nombres + "', '"+ apellidos+"','" + telefono + "','" + correo + "','" + direccion + "')";
        database.execSQL(sqlAux);
    }
    public void insertarCliente ( String dni , String cod_persona){
        String sqlAux = "insert into cliente values ('" + dni + "','" + cod_persona + "')";
        database.execSQL(sqlAux);
    }
    public void insertarMascota(String cm , String ce , int cc,String nom, String raz, String sex , double edad){
        String sqlAux ="insert into mascota values " +
                "('"+cm+"','"+ce+"', "+cc+",'"+nom+"','"+raz+"','"+sex+"',"+edad+")";
        database.execSQL(sqlAux);
    }
    public void insertarConsulta(String codConsulta,String codMascota, String fecha,String triaje,
                                 String descripcion, String hallazgosC, String pruebasA,String diagnostico,
                                 String tratamientoRealizar,boolean reqTratamiento){
        String sqlAux= "insert into consulta values('"+codConsulta+"','"+codMascota+"','"+fecha+"','"+triaje+"',\n" +
                "                '"+descripcion+" ',' "+hallazgosC+"','"+pruebasA+"',\n" +
                "                '"+diagnostico+" ','"+tratamientoRealizar+" ','"+reqTratamiento+"')";
        database.execSQL(sqlAux);
    }
    // metodos auxiliares (comprobacion, llenado ,numero de Consultas tratamientos etc
    public boolean verificarDNI(int dni) {
        boolean resultado = false;
        String sqlAux = "select cod_cliente_dni from cliente where cod_cliente_dni=" + dni + "";
        c = database.rawQuery(sqlAux, null);
        if (c.getCount() == 1)
            return resultado = true;
        else
            return resultado = false;
    }
    public String contarConsultas(String codigo){
        c = database.rawQuery("select cod_consulta from consulta where cod_mascota = '"+codigo+"'",null);
        int codigoInt = c.getCount();
        return String.valueOf(codigoInt);
    }
    public String contarTratamiento(String codigo){
        c = database.rawQuery("select cod_tratamiento from tratamiento where cod_mascota = '"+codigo+"'",null);
        int codigoInt = c.getCount();
        return String.valueOf(codigoInt);
    }
    public String contarVacunas(String codigo){
        c = database.rawQuery("select cod_vacuna from vacuna where cod_mascota = '"+codigo+"'",null);
        int codigoInt = c.getCount();
        return String.valueOf(codigoInt);
    }
    public String contarLavado(String codigo){
        c = database.rawQuery("select cod_serviciolc from servicioLyC where cod_mascota = '"+codigo+"'",null);
        int codigoInt = c.getCount();
        return String.valueOf(codigoInt);
    }

    public ArrayList<classEspecieMascota> llenarSpinnerEspecieM(){
        c = database.rawQuery("select e.cod_especie, e.nombre from especie e",null);
        ArrayList<classEspecieMascota> aux = new ArrayList<>();
        classEspecieMascota especie;
        while(c.moveToNext()){
            especie = new classEspecieMascota();
            especie.codEspecie = c.getString(c.getColumnIndex("cod_especie"));
            especie.nomEspecie = c.getString(c.getColumnIndex("nombre"));
            aux.add(especie);
        }
        return aux;
    }/*
    public int contarMascotas(){
        c = database.rawQuery("select cod_mascota, cod_especie, cod_cliente, nombre, raza, sexo, edad from mascota",null);
        int contador = c.getCount();
        return contador;
    }
    public void insertarMascota(String cm , String ce , int cc,String nom, String raz, String sex , int edad){
        String sqlAux ="insert into mascota values " +
                "('"+cm+"','"+ce+"', "+cc+",'"+nom+"','"+raz+"','"+sex+"',"+edad+")";
        database.execSQL(sqlAux);
    }*/

}
