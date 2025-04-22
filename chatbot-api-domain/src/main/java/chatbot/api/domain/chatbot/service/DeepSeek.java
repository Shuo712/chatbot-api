package chatbot.api.domain.chatbot.service;

import chatbot.api.domain.chatbot.IDeepSeek;
import chatbot.api.domain.chatbot.model.aggregates.AIAnswer;
import chatbot.api.domain.chatbot.model.vo.Choices;
import chatbot.api.infrastructure.utils.HttpsUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeepSeek implements IDeepSeek {

    private Logger logger = LoggerFactory.getLogger(DeepSeek.class);

    @Value("${chatbot-api.deepseekApiKey}")
    private String deepseekApiKey;
    @Value("${chatbot-api.deepseekModel}")
    private String model;
    // 系统提示,设定AI行为或背景
    @Value("${chatbot-api.systemContent}")
    private String systemContent;

    @Override
    public String doDeepSeek(String question) throws Exception {

        // 请求https接口，忽略证书
        CloseableHttpClient httpClient = HttpsUtils.createSSLClientDefault();

        HttpPost post = new HttpPost("https://api.deepseek.com/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + deepseekApiKey);

        String jsonBody = "{\n" +
                "    \"model\": \"" + model + "\",\n" +
                "    \"messages\": [\n" +
                "      {\"role\": \"system\", \"content\": \"" + systemContent + "\"},\n" +
                "      {\"role\": \"user\", \"content\": \"" + question + "\"}\n" +
                "    ],\n" +
                "    \"stream\": false\n" +
                "  }";

        StringEntity stringEntity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getMessage().getContent());
            }
            return answers.toString();
        } else {
            throw new RuntimeException("api.deepseek.com Err code is " + response.getStatusLine().getStatusCode());
        }
    }
}
