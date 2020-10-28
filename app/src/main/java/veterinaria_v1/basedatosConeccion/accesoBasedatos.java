package veterinaria_v1.basedatosConeccion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;


import androidx.constraintlayout.solver.PriorityGoalRow;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import clasesAuxiliares.classBusquedaApellidos;
import clasesAuxiliares.classEspecieMascota;
import clasesAuxiliares.classFichaConsulta;
import clasesAuxiliares.classListaConsultas;
import clasesAuxiliares.classMascota;
import clasesAuxiliares.classProductos;

public class accesoBasedatos {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private accesoBasedatos accesoBd;
    private Cursor c = null;
    private static accesoBasedatos instance;
    private classProductos cp;

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
    // METODOS PARA MOSTRAR TABLAS
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

    public String getCliente(int dni) {
        c = database.rawQuery("select p.apellidos, p.nombres from persona p join cliente c " +
                "on p.cod_persona=c.cod_persona where c.cod_cliente_dni=" + dni + "", null);
        String aux = "";
        if (c != null) {
            while (c.moveToNext()) {
                aux = c.getString(00);
                aux = aux + " " + c.getString(1);
            }
        }
        return aux;
    }

    public ArrayList<classMascota> getMascota(int num) {
        c = database.rawQuery("select m.cod_mascota, m.nombre , e.nombre as tipo , m.raza , m.sexo , m.edad " +
                "from mascota m join cliente c on m.cod_cliente = c.cod_cliente_dni join especie e " +
                "on m.cod_especie = e.cod_especie where c.cod_cliente_dni = " + num + " order by m.nombre", null);
        ArrayList<classMascota> aux = new ArrayList<>();
        classMascota cmascota;
        while (c.moveToNext()) {
            cmascota = new classMascota();
            cmascota.codigo = c.getString(c.getColumnIndex("cod_mascota"));
            cmascota.setNombre(c.getString(c.getColumnIndex("nombre")));
            cmascota.descripcion = c.getString((c.getColumnIndex("tipo")));
            cmascota.raza = c.getString((c.getColumnIndex("raza")));
            cmascota.sexo = c.getString(c.getColumnIndex("sexo"));
            String aux_dni = c.getString(c.getColumnIndex("edad"));
            cmascota.edad = Integer.parseInt(aux_dni);
            aux.add(cmascota);
        }
        return aux;
    }

    public ArrayList<classListaConsultas> mostrarConsultasCH(String codMascota) {
        c = database.rawQuery("select cod_consulta, motivo,fecha from consulta " +
                "where cod_mascota = upper('" + codMascota + "') ", null);
        ArrayList<classListaConsultas> aux = new ArrayList<>();
        classListaConsultas consulta;
        while (c.moveToNext()) {
            consulta = new classListaConsultas();
            consulta.codConsulta = c.getString(c.getColumnIndex("cod_consulta"));
            consulta.motivo = c.getString(c.getColumnIndex("motivo"));
            consulta.fecha = c.getString(c.getColumnIndex("fecha"));

            aux.add(consulta);
        }
        return aux;
    }

    public ArrayList<classListaConsultas> mostrarConsultasCHxcodigo(String codigo) {
        c = database.rawQuery("select cod_consulta, motivo,fecha from consulta " +
                "where cod_consulta = upper('" + codigo + "') ", null);
        ArrayList<classListaConsultas> aux = new ArrayList<>();
        classListaConsultas consulta;
        while (c.moveToNext()) {
            consulta = new classListaConsultas();
            consulta.codConsulta = c.getString(c.getColumnIndex("cod_consulta"));
            consulta.motivo = c.getString(c.getColumnIndex("motivo"));
            consulta.fecha = c.getString(c.getColumnIndex("fecha"));

            aux.add(consulta);
        }
        return aux;
    }

    public ArrayList<classListaConsultas> mostrarConsultasCHxfecha(String codigo, String fechaC) {
        c = database.rawQuery("select cod_consulta, motivo,fecha from consulta " +
                "where cod_mascota = '" + codigo + "' and fecha like '%" + fechaC + "%'", null);
        ArrayList<classListaConsultas> aux = new ArrayList<>();
        classListaConsultas consulta;
        while (c.moveToNext()) {
            consulta = new classListaConsultas();
            consulta.codConsulta = c.getString(c.getColumnIndex("cod_consulta"));
            consulta.motivo = c.getString(c.getColumnIndex("motivo"));
            consulta.fecha = c.getString(c.getColumnIndex("fecha"));

            aux.add(consulta);
        }
        return aux;
    }

    public ArrayList<classListaConsultas> mostrarConsultasCHxMotivo(String codMascota, String buscar) {
        c = database.rawQuery("select cod_consulta, motivo,fecha from consulta " +
                "where cod_mascota = '" + codMascota + "' and motivo like '%" + buscar + "%' order by cod_mascota ", null);
        ArrayList<classListaConsultas> aux = new ArrayList<>();
        classListaConsultas consulta;
        while (c.moveToNext()) {
            consulta = new classListaConsultas();
            consulta.codConsulta = c.getString(c.getColumnIndex("cod_consulta"));
            consulta.motivo = c.getString(c.getColumnIndex("motivo"));
            consulta.fecha = c.getString(c.getColumnIndex("fecha"));

            aux.add(consulta);
        }
        return aux;
    }

    public ArrayList<classFichaConsulta> mostrarConsultasCHCompleto(String codigo) {
        c = database.rawQuery("select cod_consulta, triaje, descripcion, hallazgosClinicos," +
                "pruebasAuxiliares,diagnostico,tratamientoRealizar from consulta " +
                "where cod_consulta = upper('" + codigo + "') ", null);
        ArrayList<classFichaConsulta> aux = new ArrayList<>();
        classFichaConsulta consulta;
        while (c.moveToNext()) {
            consulta = new classFichaConsulta();
            consulta.codConsulta = c.getString(c.getColumnIndex("cod_consulta"));
            consulta.triajeC = c.getString(c.getColumnIndex("triaje"));
            consulta.descripcionC = c.getString(c.getColumnIndex("descripcion"));
            consulta.hallazgosC = c.getString(c.getColumnIndex("hallazgosClinicos"));
            consulta.pruebasAux = c.getString(c.getColumnIndex("pruebasAuxiliares"));
            consulta.diagnostico = c.getString(c.getColumnIndex("diagnostico"));
            consulta.trataC = c.getString(c.getColumnIndex("tratamientoRealizar"));
            aux.add(consulta);
        }
        return aux;
    }

    public classProductos mostrarProdxCod(String codigo) {
        c = database.rawQuery("select * from producto " +
                "where cod_producto = '" + codigo + "'", null);
//cod_producto, nombre, costo, precio, inventario, imagen
        classProductos productos = null;
        //Bitmap bitmap = null;
        if (c.moveToFirst()) {
            productos = new classProductos();
            productos.codProducto = c.getString(0);
            productos.nomProducto = c.getString(c.getColumnIndex("nombre"));
            productos.costoProduco = Double.parseDouble(c.getString(c.getColumnIndex("costo")));
            productos.precioProducto = Double.parseDouble(c.getString(c.getColumnIndex("precio")));
            productos.stockProducto = Integer.parseInt(c.getString(c.getColumnIndex("inventario")));
            productos.imgProducto = c.getString(c.getColumnIndex("imagen"));
            //opcion 1
            /*byte[] bitmapdata = c.getBlob(c.getColumnIndex("imagen"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);*/
            //opcion  2
            /*byte[] blob = c.getBlob(c.getColumnIndex("imagen"));
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);*/
            //opcion 3
        }
        return productos;
    }

    public ArrayList<classProductos> mostrarProductos() {
        c = database.rawQuery("select * from producto ", null);
        ArrayList<classProductos> aux = new ArrayList<>();
        classProductos productos = null;
        while (c.moveToNext()) {
            productos = new classProductos();
            productos.codProducto = c.getString(c.getColumnIndex("cod_producto"));
            productos.nomProducto = c.getString(c.getColumnIndex("nombre"));
            productos.costoProduco = Double.parseDouble(c.getString(c.getColumnIndex("costo")));
            productos.precioProducto = Double.parseDouble(c.getString(c.getColumnIndex("precio")));
            productos.stockProducto = Integer.parseInt(c.getString(c.getColumnIndex("inventario")));
            productos.descripcion = c.getString(c.getColumnIndex("descripcion"));
            productos.imgProducto = c.getString(c.getColumnIndex("imagen"));
            //DECODIFICANDO
            String base64String = productos.imgProducto;
            byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            int ancho = 700;
            int alto = 800;
            productos.imgProdutctoBitmap = Bitmap.createScaledBitmap(decodedByte, ancho, alto, true);
            aux.add(productos);
        }
        return aux;
    }

   /* public ArrayList<classProductos> mostrarProductos(String codigo){

        ArrayList<classProductos> aux = new ArrayList<>();;

        try {
            c = database.rawQuery("select cod_producto, nombre, costo, precio, inventario, imagen from producto where cod_producto ='"+codigo+"'",null);

            cp = new classProductos();
           // cp.codProducto = c.getString(c.getColumnIndex("cod_producto"));
            //cp.setCodProducto(c.getString(c.getColumnIndex("cod_producto")));
            cp.setNomProducto(c.getString(c.getColumnIndex("nombre")));
            cp.setCostoProduco(Double.parseDouble(c.getString(c.getColumnIndex("costo"))));
            cp.setPrecioProducto(Double.parseDouble(c.getString(c.getColumnIndex("precio"))));
            cp.setStockProducto(Integer.parseInt(c.getString(c.getColumnIndex("inventario"))));
            byte[] bitmapdata = c.getBlob(c.getColumnIndex("imagen"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
            cp.setImgProducto(bitmap);
        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
        aux.add(cp);
        return aux;
    }*/


    // METODOS AUXILIARES
    public String getFechahoy() {
        Date ahora = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(ahora);
    }

    //generando codigos personalizados de clase PERSONA, MASCOTA , CONSULTA , TRATAMIENTO, VACUNAS Y SERVICIOS
    public String generarCodPersona() {
        c = database.rawQuery("select cod_persona" +
                " from persona", null);
        int contador = c.getCount() + 1;
        String aux = "PER000" + String.valueOf(contador);
        return aux;
    }

    public String generarCodMascota() {
        c = database.rawQuery("select cod_mascota" +
                " from mascota", null);
        int contador = c.getCount() + 1;
        String aux = "MSC000" + String.valueOf(contador);
        return aux;
    }

    public String generarCodConsulta() {
        c = database.rawQuery("select cod_consulta" +
                " from consulta", null);
        int contador = c.getCount() + 1;
        String aux = "CON000" + String.valueOf(contador);
        return aux;
    }

    public String generarCodTratamiento() {
        c = database.rawQuery("select cod_tratamiento" +
                " from tratamiento", null);
        int contador = c.getCount() + 1;
        String aux = "TRT000" + String.valueOf(contador);
        return aux;
    }

    public String generarCodProducto() {
        c = database.rawQuery("select cod_producto from producto", null);
        int contador = c.getCount() + 1;
        String aux = "PRO000" + String.valueOf(contador);
        return aux;
    }

    // Insertar
    public void insertarPersona(String codigo, String apellidos, String nombres, String telefono,
                                String correo, String direccion) {
        String sqlAux = "insert into persona values " +
                "('" + codigo + "','" + nombres + "', '" + apellidos + "','" + telefono + "','" + correo + "','" + direccion + "')";
        database.execSQL(sqlAux);
    }

    public void insertarCliente(String dni, String cod_persona) {
        String sqlAux = "insert into cliente values ('" + dni + "','" + cod_persona + "')";
        database.execSQL(sqlAux);
    }

    public void insertarMascota(String cm, String ce, int cc, String nom, String raz, String sex, double edad) {
        String sqlAux = "insert into mascota values " +
                "('" + cm + "','" + ce + "', " + cc + ",'" + nom + "','" + raz + "','" + sex + "'," + edad + ")";
        database.execSQL(sqlAux);
    }

    public void insertarConsulta(String codConsulta, String codMascota, String motivo, String fecha, String triaje,
                                 String descripcion, String hallazgosC, String pruebasA, String diagnostico,
                                 String tratamientoRealizar, boolean checkTratamiento, boolean checkPago) {
        String sqlAux = "insert into consulta values('" + codConsulta + "','" + codMascota + "','" + motivo + "','" + fecha + "','" + triaje + "'," +
                "                '" + descripcion + " ',' " + hallazgosC + "','" + pruebasA + "'," +
                "                '" + diagnostico + " ','" + tratamientoRealizar + " ','" + checkTratamiento + "','" + checkPago + "')";
        database.execSQL(sqlAux);
    }

    public void insertarProducto(String codProd, String nomProd, double costoProd,
                                 double precioProd, int inventarioProd, String descripcionProd, String imagenProd) {
        String sqlaux = "insert into producto values('" + codProd + "', '" + nomProd + "','" + costoProd + "'," +
                "'" + precioProd + "','" + inventarioProd + "','" + descripcionProd + "' ,'" + imagenProd + "')";
        database.execSQL(sqlaux);
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

    public String contarConsultas(String codigo) {
        c = database.rawQuery("select cod_consulta from consulta where cod_mascota = '" + codigo + "'", null);
        int codigoInt = c.getCount();
        return String.valueOf(codigoInt);
    }

    public String contarTratamiento(String codigo) {
        c = database.rawQuery("select cod_tratamiento from tratamiento where cod_mascota = '" + codigo + "'", null);
        int codigoInt = c.getCount();
        return String.valueOf(codigoInt);
    }

    public String contarVacunas(String codigo) {
        c = database.rawQuery("select cod_vacuna from vacuna where cod_mascota = '" + codigo + "'", null);
        int codigoInt = c.getCount();
        return String.valueOf(codigoInt);
    }

    public String contarLavado(String codigo) {
        c = database.rawQuery("select cod_serviciolc from servicioLyC where cod_mascota = '" + codigo + "'", null);
        int codigoInt = c.getCount();
        return String.valueOf(codigoInt);
    }

    public ArrayList<classEspecieMascota> llenarSpinnerEspecieM() {
        c = database.rawQuery("select e.cod_especie, e.nombre from especie e", null);
        ArrayList<classEspecieMascota> aux = new ArrayList<>();
        classEspecieMascota especie;
        while (c.moveToNext()) {
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
