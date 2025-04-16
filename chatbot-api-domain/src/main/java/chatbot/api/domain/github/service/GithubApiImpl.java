package chatbot.api.domain.github.service;

import chatbot.api.domain.github.GithubApi;
import chatbot.api.domain.github.model.aggregates.IssuesAggregates;
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

public class GithubApiImpl implements GithubApi {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(GithubApiImpl.class);

    @Override
    public IssuesAggregates queryIssuesNumber(String owner,String repo, String token) throws Exception {

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
            return JSON.parseObject(jsonStr, IssuesAggregates.class);
        } else {
            throw new RuntimeException("queryIssuesNumber Err code is " + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean addComment(String owner, String repo, String issueNumber, String token, String comment) throws Exception {
        // 请求https接口，忽略证书
        CloseableHttpClient httpClient = HttpsUtils.createSSLClientDefault();

        // URL
        String urlString = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/" + issueNumber + "/comments";
        HttpPost post = new HttpPost(urlString);

        // 请求头
        post.addHeader("Authorization", "token " + token);
        post.addHeader("Accept", "application/vnd.github+json");

        // 请求体
        String jsonBody = "{\"body\": \"Test comment2\"}";
        StringEntity stringEntity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
        return false;
    }
}
