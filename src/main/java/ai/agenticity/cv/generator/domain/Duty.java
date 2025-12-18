package ai.agenticity.cv.generator.domain;

/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

/**
 * POJO for an object that corresponds with a record in the table
 * film_movietitle.
 */
public class Duty implements Comparable<Duty> {

    /**
     * The title of the movie.
     */
    protected String ach_desc;
    protected String level;

    public Duty(String ach_desc) {
        this.ach_desc = ach_desc;

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
        return level;
    }

    @Override
    public String toString() {
        return ach_desc;
    }

    public int compareTo(Duty o) {
        return ach_desc.compareTo(o.ach_desc);
    }

}
