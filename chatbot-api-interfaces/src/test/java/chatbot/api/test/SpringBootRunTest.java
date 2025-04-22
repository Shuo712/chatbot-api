package chatbot.api.test;

import chatbot.api.domain.chatbot.service.DeepSeek;
import chatbot.api.domain.github.model.res.IssuesRes;
import chatbot.api.domain.github.model.vo.Issue;
import chatbot.api.domain.github.service.GithubApi;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.owner}")
    private String owner; // 仓库所有人
    @Value("${chatbot-api.repo}")
    private String repo; // 仓库名
    String token = System.getenv("GITHUB_TOKEN");

    @Autowired
    private GithubApi githubApi;
    @Autowired
    private DeepSeek deepSeek;

    @Test
    public void test_githubApi() throws Exception {
        IssuesRes issuesRes = githubApi.queryIssues(owner, repo, token);
        logger.info("Test query issues: {}", JSON.toJSONString(issuesRes));

        List<Issue> issues = issuesRes.getIssues();
        for (Issue issue : issues) {
            int number = issue.getNumber();
            String title = issue.getTitle();
            String body = issue.getBody();
            logger.info("issue {} -> title:{}  body:{}", number, title, body);

            // add comment
            githubApi.addComment(owner, repo, number, token, "test comment");
        }
    }

    @Test
    public void test_deepSeek() throws Exception {
        String question = "你好";
        String response = deepSeek.doDeepSeek(question);
        logger.info("Test deepseek: {}", response);
    }

}
