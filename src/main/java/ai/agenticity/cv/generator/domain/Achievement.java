package ai.agenticity.cv.generator.domain;

/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

/**
 * POJO for an object that corresponds with a record
 * in the table film_movietitle.
 */
public class Achievement implements Comparable<Achievement> {

    private final boolean isManagementRelated;
    /**
     * The title of the movie.
     */
    protected String ach_desc;
    protected String role;
    /**
     * The year the movie was released.
     */
    protected int year;
    /**
     * The duration of the movie in minutes.
     */
    protected int duration;

    public Achievement(String ach_desc, boolean isManagementRelated) {
        this.ach_desc = ach_desc;
        this.isManagementRelated=isManagementRelated;
    }

    /**
     * Returns the title in the correct form.
     *
     * @return a title
     */
    public String ach_desc() {
        return ach_desc;
    }

    /**
     * @return the originalTitle
     */
    public String getRole() {
        return role;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return ach_desc;
    }

    public int compareTo(Achievement o) {
        return ach_desc.compareTo(o.ach_desc);
    }

}
