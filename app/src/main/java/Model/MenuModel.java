package Model;

public class MenuModel {
    private String itemName;
    private String cookTime;
    private String prepTime;
    private String totalTime;
    private String itemImage;
    private String itemIngredients;
    private String itemInstruction;


    public MenuModel(String itemName, String cookTime, String prepTime, String totalTime, String itemImage, String itemIngredients, String itemInstruction) {
        this.itemName = itemName;
        this.cookTime = cookTime;
        this.prepTime = prepTime;
        this.totalTime = totalTime;
        this.itemImage = itemImage;
        this.itemIngredients = itemIngredients;
        this.itemInstruction = itemInstruction;
    }
    public MenuModel(String itemName,String itemImage,String itemIngredients,String itemInstruction) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemIngredients=itemIngredients;
        this.itemInstruction = itemInstruction;
    }
    public MenuModel(){
    }

    public MenuModel(String itemName){
        this.itemName=itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemIngredients() {
        return itemIngredients;
    }

    public void setItemIngredients(String itemIngredients) {
        this.itemIngredients = itemIngredients;
    }

    public String getItemInstruction() {
        return itemInstruction;
    }

    public void setItemInstruction(String itemInstruction) {
        this.itemInstruction = itemInstruction;
    }
}
