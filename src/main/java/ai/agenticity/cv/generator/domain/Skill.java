/*
 * To change this license extras.header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.agenticity.cv.generator.domain;

/**
 *
 * @author Francesco
 */
public class Skill {

    protected String tech_categ;
    protected String desc;
    protected String time;
    protected String type;//academical / professional

    protected String evaluationInWords;

    public Skill(String desc, String evaluationInWords) {
        this.desc = desc;
        this.evaluationInWords = evaluationInWords;
    }

    private Skill(String maven) {
        this.desc = desc;
    }

    /**
     * Returns the title in the correct form.
     *
     * @return a title
     */
    public String desc() {
        return desc;
    }

    /**
     * @return the originalTitle
     */
    public String getTime() {
        return time;
    }

    public String getEvaluationInWords() {
        return evaluationInWords;
    }

    public static void main(String[] args) {
        //----------
        Skill s1 = new Skill("Maven, Ant");
        Skill s2 = new Skill("Subversion");
        Skill s3 = new Skill("GIT");
        //--------
        Skill s4 = new Skill("Spring");
        Skill s5 = new Skill("JBoss");
        Skill s6 = new Skill("Tomcat");
        Skill s7 = new Skill("JavaSE");
    }
}
