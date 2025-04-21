package chatbot.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import chatbot.api.infrastructure.utils.HttpsUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Shuo
 * @description 单元测试
 * @github https://github.com/Shuo712
 */
@SpringBootTest
public class ApiTest {

    @Value("${chatbot-api.owner}")
    private String owner; // 仓库所有人
    @Value("${chatbot-api.repo}")
    private String repo; // 仓库名
    String token = System.getenv("GITHUB_TOKEN");

    /**
     * 测试查询issues
     */
    @Test
    public void queryIssues() throws Exception {
        // 原始方法: CloseableHttpClient httpClient = HttpClientBuilder.create().build();
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
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res); // 打印JSON返回内容
        } else {
            System.out.println(response.getStatusLine().getStatusCode()); // 打印错误码
        }
    }

    /**
     * 测试发布comment
     */
    @Test
    public void addComment() throws Exception {
        //原始方法: CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 请求https接口，忽略证书
        CloseableHttpClient httpClient = HttpsUtils.createSSLClientDefault();

        // URL
        int issueNumber = 1; // issue编号
        String urlString = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/" + issueNumber + "/comments";
        HttpPost post = new HttpPost(urlString);

        // 请求头
        post.addHeader("Authorization", "token " + token);
        post.addHeader("Accept", "application/vnd.github+json");
        post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36 Edg/135.0.0.0");

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
    }
}
