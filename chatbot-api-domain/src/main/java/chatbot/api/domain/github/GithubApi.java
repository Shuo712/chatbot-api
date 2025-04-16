package chatbot.api.domain.github;

import chatbot.api.domain.github.model.aggregates.IssuesAggregates;

import java.util.List;

/**
 * Github API 接口
 */
public interface GithubApi {

    IssuesAggregates queryIssuesNumber(String owner, String repo, String token) throws Exception;

    boolean addComment(String owner, String repo, String issueNumber, String token, String comment) throws Exception;
}
