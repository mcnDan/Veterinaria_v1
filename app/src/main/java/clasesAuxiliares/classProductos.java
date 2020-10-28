package clasesAuxiliares;

import android.graphics.Bitmap;

import java.sql.Blob;

public class classProductos {
    public String codProducto, nomProducto;
    public double costoProduco, precioProducto;
    public int stockProducto;
    public String descripcion;
    public String imgProducto;



    public Bitmap imgProdutctoBitmap;

    public classProductos(String codProducto, String nomProducto, double costoProduco, double precioProducto, int stockProducto, String descripcion, String imgProducto) {
        this.codProducto = codProducto;
        this.nomProducto = nomProducto;
        this.costoProduco = costoProduco;
        this.precioProducto = precioProducto;
        this.stockProducto = stockProducto;
        this.descripcion = descripcion;
        this.imgProducto = imgProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }




    public classProductos() {
    }
    public Bitmap getImgProdutctoBitmap() {
        return imgProdutctoBitmap;
    }

    public void setImgProdutctoBitmap(Bitmap imgProdutctoBitmap) {
        this.imgProdutctoBitmap = imgProdutctoBitmap;
    }
    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public double getCostoProduco() {
        return costoProduco;
    }

    public void setCostoProduco(double costoProduco) {
        this.costoProduco = costoProduco;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }

    public String getImgProducto() {
        return imgProducto;
    }

    public void setImgProducto(String imgProducto) {
        this.imgProducto = imgProducto;
    }

    @Override
    public String toString() {
        return "classProductos{" +
                "codProducto='" + codProducto + '\'' +
                ", nomProducto='" + nomProducto + '\'' +
                ", costoProduco=" + costoProduco +
                ", precioProducto=" + precioProducto +
                ", stockProducto=" + stockProducto +
                ", imgProducto=" + imgProducto +
                '}';
    }
}
