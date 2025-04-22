package chatbot.api.application.job;

import chatbot.api.domain.chatbot.service.DeepSeek;
import chatbot.api.domain.github.model.res.IssuesRes;
import chatbot.api.domain.github.model.vo.Issue;
import chatbot.api.domain.github.service.GithubApi;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * 定时问答任务
 * 查询Issues,调用AI回答,回复Comment
 * @author Shuo
 */
@EnableScheduling
@Configuration
public class ChatbotSchedule {

    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.owner}")
    private String owner; // 仓库所有人
    @Value("${chatbot-api.repo}")
    private String repo; // 仓库名
    String token = System.getenv("GITHUB_TOKEN");

    @Autowired
    private GithubApi githubApi;
    @Autowired
    private DeepSeek deepSeek;

    // 每十秒执行一次定时任务
    @Scheduled(cron = "0/10 * * * * ?")
    public void run() throws Exception {
        try {
            // 设置打烊
            if (new Random().nextBoolean()) {
                logger.info("随机打烊中...");
                return;
            }
            LocalDateTime time = LocalDateTime.now();
            int hour = time.getHour();
            if (hour > 22 || hour < 7) {
                logger.info("打烊时间不工作...");
                return;
            }

            // 1. 检索问题
            IssuesRes issuesRes = githubApi.queryIssues(owner, repo, token);
            logger.info("query issues: {}", JSON.toJSONString(issuesRes));
            List<Issue> issues = issuesRes.getIssues();
            if (null == issues || issues.isEmpty()) {
                logger.info("No issues");
                return;
            }

            // 2. AI回答
            Issue issue = issues.get(0);
            String answer = deepSeek.doDeepSeek(issue.getTitle() + ":" + issue.getBody());

            // 3. 问题回复
            boolean status = githubApi.addComment(owner, repo, issue.getNumber(), token, "AI回答：" + answer);
            logger.info("Issue{} -> title:{}  body:{}  answer:{}  {}", issue.getNumber(), issue.getTitle(), issue.getBody(), answer, status ? "success!" : "false~");

        } catch (Exception e) {
            logger.error("ChatbotSchedule run error", e);
        }
    }
}
