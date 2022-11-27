package id.ac.umn.uas_43802.model;

public class Carousel_Page_Model {
    private int featured_image;
    private String the_caption_Title;

    public Carousel_Page_Model(int hero, String title) {
        this.featured_image = hero;
        this.the_caption_Title = title;
    }

    public int getFeatured_image() {
        return featured_image;
    }

    public String getThe_caption_Title() {
        return the_caption_Title;
    }

    public void setFeatured_image(int featured_image) {
        this.featured_image = featured_image;
    }

    public void setThe_caption_Title(String the_caption_Title) {
        this.the_caption_Title = the_caption_Title;
    }
}
