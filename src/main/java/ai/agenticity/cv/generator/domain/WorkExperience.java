package ai.agenticity.cv.generator.domain;

/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */
import java.util.List;

/**
 * POJO for an object that corresponds with a record in the table
 * film_movietitle.
 */
public class WorkExperience implements Comparable<WorkExperience> {

    protected String companyBusinessSector;
    protected String companyName;
    protected String companyContext;
    protected String role;
    protected List<Achievement> ach_list;
    /**
     * The year the movie was released.
     */
    protected int start_year;
    protected int end_year;
    protected String start_month;
    protected String end_month;
    protected String location;
    /**
     * The duration of the movie in minutes.
     */
    protected int duration;
    protected List<Duty> duty_list;
    protected String language;
    protected TechnicalEnvironment technicalEnvironment;
    protected String contractType;
    protected boolean isOneLineAchievementEnabled;
    protected String oneLineAchievement;


    public WorkExperience(String companyName, String start_month, int start_year, String end_month, int end_year, String role, String location, int duration, List<Achievement> ach_list, List<Duty> duty_list, String companyBusinessSector, String contractType, boolean isOneLineAchievementEnabled, String oneLineAchievement) {
        this.companyName = companyName;
        this.companyBusinessSector= companyBusinessSector;
        this.duration = duration;
        this.role = role;
        this.start_year = start_year;
        this.end_year = end_year;
        this.start_month = start_month;
        this.end_month = end_month;
        this.location = location;
        this.ach_list = ach_list;
        this.duty_list = duty_list;
        this.contractType = contractType;
        this.oneLineAchievement=oneLineAchievement;
        this.isOneLineAchievementEnabled = isOneLineAchievementEnabled;
    }

    /**
     * Returns the title in the correct form.
     *
     * @return a title
     */
    public String getCompanyName() {
        return companyName;
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
    public int getStartYear() {
        return start_year;
    }

    public int getEndYear() {
        return end_year;
    }

    public String getStartMonth() {
        return start_month;
    }

    public String getEndMonth() {
        return end_month;
    }

    public String getLocation() {
        return location;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the directors
     */
    public int compareTo(WorkExperience that) {
        return ((Integer) this.start_year).compareTo((Integer) that.getStartYear());
    }

     public List<Duty> getDuties() {
        return this.duty_list;
    }

    public List<Achievement> getAchievements() {
        return this.ach_list;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCompanyContext() {
        return companyContext;
    }

    public void setCompanyContext(String companyContext) {
        this.companyContext = companyContext;
    }

    public TechnicalEnvironment getTechnicalEnvironment() {
        return technicalEnvironment;
    }

    public void setTechnicalEnvironment(TechnicalEnvironment technicalEnvironment) {
        this.technicalEnvironment = technicalEnvironment;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getCompanyBusinessSector() {
        return companyBusinessSector;
    }

}
