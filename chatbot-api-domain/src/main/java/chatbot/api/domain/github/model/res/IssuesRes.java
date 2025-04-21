package chatbot.api.domain.github.model.res;

import chatbot.api.domain.github.model.vo.Issue;

import java.util.List;

/**
 * Issues结果数据
 */
public class IssuesRes {

    private List<Issue> issues;

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
