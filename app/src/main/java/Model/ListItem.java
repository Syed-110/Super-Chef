package Model;
public class ListItem {
    private String title;
    private  String des;
    public ListItem(String title,String des) {
        this.title=title;
        this.des=des;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return des;
    }

    public void setDescription(String description) {
        this.des = description;
    }
}
