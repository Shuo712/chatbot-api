package chatbot.api.domain.github;

import chatbot.api.domain.github.model.res.IssuesRes;

/**
 * Github API 接口
 * @author Shuo
 * @date 2025/4/19
 */
public interface IGithubApi {

    /**
     * GET 查询Issues
     * @param owner
     * @param repo
     * @param token
     * @return ResData结果数据
     */
    IssuesRes queryIssues(String owner, String repo, String token) throws Exception;

    /**
     * POST 添加Comment
     * @param owner
     * @param repo
     * @param issueNumber
     * @param token
     * @param comment
     * @return 成功：true，失败：false
     */
    boolean addComment(String owner, String repo, int issueNumber, String token, String comment) throws Exception;
}

