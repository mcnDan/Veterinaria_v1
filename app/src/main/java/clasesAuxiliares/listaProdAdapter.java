package clasesAuxiliares;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.veterinaria_v1.R;

import java.util.List;

import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class listaProdAdapter extends ArrayAdapter<classProductos> {
private TextView codProd_lp_vet, nomProd_lp_vet,costoProd_lp_vet,precioProd_lp_vet,cantProd_lp_vet,descriProd_lp_vet;
private ImageView imgProd_lp_vet;
private accesoBasedatos basedatos;

    public listaProdAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View result = convertView;
        if(result ==null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.lista_productos_vet, null);
        }

           /* //inicializnado con viewHolder
           ViewHolder viewHolder = new ViewHolder();
            viewHolder.codProd_lp_vet = result.findViewById(R.id.codProLvp);
            viewHolder.nomProd_lp_vet = result.findViewById(R.id.tv_nomProLvp);
            viewHolder.precioProd_lp_vet = result.findViewById(R.id.tv_precioProLvp);
            viewHolder.cantProd_lp_vet = result.findViewById(R.id.tv_stockProLpv);
            viewHolder.costoProd_lp_vet = result.findViewById(R.id.tv_costoProLvp);
            viewHolder.imgProd_lp_vet = result.findViewById(R.id.iv_imagenProLvp);
            result.setTag(viewHolder);*/

            //Inicializando normalmente
            codProd_lp_vet = result.findViewById(R.id.codProLvp);
            nomProd_lp_vet = result.findViewById(R.id.tv_nomProLvp);
            precioProd_lp_vet = result.findViewById(R.id.tv_precioProLvp);
            cantProd_lp_vet = result.findViewById(R.id.tv_stockProLpv);
            costoProd_lp_vet = result.findViewById(R.id.tv_costoProLvp);
            imgProd_lp_vet = result.findViewById(R.id.iv_imagenProLvp);

            classProductos productos = getItem(position);
//RECUPERAR Y ACTUALIZAR DATOS
            //            ((ViewHolder)result.getTag()).codProd_lp_vet
 /*           TextView cheeseName =
                    ((ViewHolder)convertView.getTag()).cheeseName;
            TextView cheeseDescription =
                    ((ViewHolder)convertView.getTag()).cheeseDescription;

            cheeseName.setText(currentCheese.name);
            cheeseDescription.setText(currentCheese.description);*/


            codProd_lp_vet.setText(productos.codProducto);
            nomProd_lp_vet.setText(productos.nomProducto);
            precioProd_lp_vet.setText(String.valueOf(productos.precioProducto));
            cantProd_lp_vet.setText(String.valueOf(productos.stockProducto));
            costoProd_lp_vet.setText(String.valueOf(productos.costoProduco));
            imgProd_lp_vet.setImageBitmap(productos.imgProdutctoBitmap);

        return result;
    }
}
/*
final class ViewHolder{
    public TextView codProd_lp_vet, nomProd_lp_vet,costoProd_lp_vet,
            precioProd_lp_vet,cantProd_lp_vet,descriProd_lp_vet;
    public ImageView imgProd_lp_vet;
}*/
