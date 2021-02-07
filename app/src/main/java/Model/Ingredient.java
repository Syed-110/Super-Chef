package Model;

public class Ingredient {
    private String ingredient_name;
    private int id;

    public Ingredient() {
    }

    public Ingredient(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }
}