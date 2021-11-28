package models.facilities;

public class Bedroom {
    private String bed1;
    private String bed2;

    public Bedroom(String bed1, String bed2) {
        this.bed1 = bed1;
        this.bed2 = bed2;
    }

    public String getBed1() {
        return bed1;
    }

    public String getBed2() {
        return bed2;
    }
}
