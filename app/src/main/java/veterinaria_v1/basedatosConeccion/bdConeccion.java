package veterinaria_v1.basedatosConeccion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class bdConeccion extends SQLiteAssetHelper {
    private final static String DATABASE="veterinaria_vf5.db";
    private final static int VERSION=1;

    public bdConeccion(Context context) {
        super(context, DATABASE,null  , VERSION);
    }
}
