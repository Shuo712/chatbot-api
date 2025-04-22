package chatbot.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Shuo
 * @description 启动入口
 * @github <a href="https://github.com/Shuo712">
 */
@SpringBootApplication
@ComponentScan({
        "chatbot.api",           // 主包
        "chatbot.api.domain",    // 领域层包
        "chatbot.api.application", // 应用层包
        "chatbot.api.infrastructure" // 基础设施层包
})
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
