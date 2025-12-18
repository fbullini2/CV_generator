package ai.agenticity.cv.generator.domain;

import java.util.List;

/**
 * Represents a category within a Technical Environment section.
 * Each category has a title (e.g., "Languages", "Cloud") and a list of technologies.
 */
public class TechnicalEnvironmentCategory {
    private String title;
    private List<String> technologies;

    public TechnicalEnvironmentCategory(String title, List<String> technologies) {
        this.title = title;
        this.technologies = technologies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }
}
