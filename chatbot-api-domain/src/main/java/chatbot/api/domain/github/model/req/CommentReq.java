package chatbot.api.domain.github.model.req;

/**
 * 请求Comment接口信息
 */
public class CommentReq {


    private String body;

    public CommentReq(String body) {
        this.body = body;
    }

    public CommentReq() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
