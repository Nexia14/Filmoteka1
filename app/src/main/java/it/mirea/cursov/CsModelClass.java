package it.mirea.cursov;

public class CsModelClass {

    String n_name;
    String r_rate;
    String g_genre;
    String i_image;

    String d_description;


    public CsModelClass(String n_name, String r_rate, String g_genre, String i_image, String d_description) {
        this.n_name = n_name;
        this.r_rate = r_rate;
        this.g_genre = g_genre;
        this.i_image = i_image;

        this.d_description = d_description;
    }

    public CsModelClass() {
    }

    public String getN_name() {
        return n_name;
    }

    public void setN_name(String n_name) {
        this.n_name = n_name;
    }

    public String getR_rate() {
        return r_rate;
    }

    public void setR_rate(String r_rate) {
        this.r_rate = r_rate;
    }

    public String getG_genre() {
        return g_genre;
    }

    public void setG_genre(String g_genre) {
        this.g_genre = g_genre;
    }

    public String getI_image() {
        return i_image;
    }

    public void setI_image(String i_image) {
        this.i_image = i_image;
    }



    //try
    public String getD_description() {
        return d_description;
    }

    public void setD_description(String d_description) {
        this.d_description = d_description;
    }




}
