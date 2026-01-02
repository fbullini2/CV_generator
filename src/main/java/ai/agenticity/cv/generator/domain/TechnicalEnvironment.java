package ai.agenticity.cv.generator.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Technical Environment section for a work experience.
 * Contains multiple categories (e.g., Languages, Cloud, Databases) with their associated technologies.
 */
public class TechnicalEnvironment {

    private List<TechnicalEnvironmentCategory> categories;
    private boolean oneLine = false;
    private String oneLineText = "";

    public TechnicalEnvironment() {
        this.categories = new ArrayList<>();
        this.oneLine = false;
    }

    public TechnicalEnvironment(String oneLineText) {
        this.oneLineText = oneLineText;
        this.oneLine = true;
        this.categories = new ArrayList<>(); // Initialize to avoid null pointers
    }

    public void addCategory(TechnicalEnvironmentCategory category) {
        this.categories.add(category);
    }

    public List<TechnicalEnvironmentCategory> getCategories() {
        return categories;
    }

    public boolean isOneLine() {
        return oneLine;
    }

    public String getOneLineText() {
        return oneLineText;
    }

    public boolean isEmpty() {
        return categories.isEmpty() && oneLineText.isEmpty();
    }
}
