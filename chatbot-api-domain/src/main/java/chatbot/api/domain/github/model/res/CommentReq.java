package chatbot.api.domain.github.model.res;

/**
 * 请求问答接口结果
 */
public class CommentReq {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
