package ai.agenticity.cv.generator.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Technical Environment section for a work experience.
 * Contains multiple categories (e.g., Languages, Cloud, Databases) with their associated technologies.
 */
public class TechnicalEnvironment {
    private List<TechnicalEnvironmentCategory> categories;

    public TechnicalEnvironment() {
        this.categories = new ArrayList<>();
    }

    public TechnicalEnvironment(List<TechnicalEnvironmentCategory> categories) {
        this.categories = categories;
    }

    public List<TechnicalEnvironmentCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<TechnicalEnvironmentCategory> categories) {
        this.categories = categories;
    }

    public void addCategory(TechnicalEnvironmentCategory category) {
        this.categories.add(category);
    }

    public boolean isEmpty() {
        return categories == null || categories.isEmpty();
    }
}
