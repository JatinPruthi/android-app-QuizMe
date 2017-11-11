package projects.jatin.quizme.model;

/**
 * Created by Jateen on 30-10-2017.
 */

public class Categories {

    private String Name;
    private String Image;

    public Categories(String name, String image) {
        Name = name;
        Image = image;
    }

    public Categories() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
