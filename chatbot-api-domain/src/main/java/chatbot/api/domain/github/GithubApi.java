package chatbot.api.domain.github;

import java.util.List;

public interface GithubApi {

    void queryIssuesNumber(String owner, String repo, List<Integer> issuesNumber);
}
