package Model;

public class addtocart {
    private String ingredient_name;
    private String ing_type;

    public addtocart(String ingredient_name, String ing_type) {
        this.ingredient_name = ingredient_name;
        this.ing_type = ing_type;
    }

    public addtocart(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public String getIng_type() {
        return ing_type;
    }

}
