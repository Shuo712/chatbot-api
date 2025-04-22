package chatbot.api.domain.github.service;

import chatbot.api.domain.github.IGithubApi;
import chatbot.api.domain.github.model.req.CommentReq;
import chatbot.api.domain.github.model.res.IssuesRes;
import chatbot.api.domain.github.model.vo.Issue;
import chatbot.api.infrastructure.utils.HttpsUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Github API 实现类
 * @author Shuo
 * @date 2025/4/19
 */
@Service
public class GithubApi implements IGithubApi {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(GithubApi.class);

    @Override
    public IssuesRes queryIssues(String owner, String repo, String token) throws Exception {

        // 请求https接口，忽略证书
        CloseableHttpClient httpClient = HttpsUtils.createSSLClientDefault();

        // URL
        String urlString = "https://api.github.com/repos/" + owner + "/" + repo + "/issues";
        HttpGet get = new HttpGet(urlString);

        // 请求头
        get.addHeader("Accept", "application/vnd.github+json");
        // Github token(防止访问频繁被限制)
        get.addHeader("Authorization", token);

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("query issues data -> owner:{}  repo:{}  jsonStr:{}", owner, repo, jsonStr);
            IssuesRes issuesRes = new IssuesRes();
            issuesRes.setIssues(JSON.parseArray(jsonStr, Issue.class));
            return issuesRes;
        } else {
            throw new RuntimeException("queryIssuesNumber Err code is " + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean addComment(String owner, String repo, int issueNumber, String token, String comment) throws Exception {
        // 请求https接口，忽略证书
        CloseableHttpClient httpClient = HttpsUtils.createSSLClientDefault();

        // URL
        String urlString = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/" + issueNumber + "/comments";
        HttpPost post = new HttpPost(urlString);

        // 请求头
        post.addHeader("Authorization", "token " + token);
        post.addHeader("Accept", "application/vnd.github+json");
        post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36 Edg/135.0.0.0");

        // 请求体
        CommentReq commentReq = new CommentReq(comment);
        String jsonBody = JSON.toJSONString(commentReq);

        StringEntity stringEntity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("add a comment result -> owner:{}  repo:{}  issueNumber:{}  comment:{}", owner, repo, issueNumber, comment);
            return true;
        } else {
            throw new RuntimeException("addComment Err code is " + response.getStatusLine().getStatusCode());
        }
    }
}
