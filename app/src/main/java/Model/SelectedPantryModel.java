package Model;

import android.graphics.drawable.Drawable;

public class SelectedPantryModel {
    private String title;
    private String[] btn;
    private Drawable icon;
    public SelectedPantryModel(String title, String[] btn, Drawable icon) {
        this.title=title;
        this.btn=btn;
        this.icon = icon;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBtn(int i) {
        return btn[i];
    }

    public void setBtn(String[] btn) {
        this.btn = btn;
    }

    public int getbtn_size(){
        return btn.length;
    }
}