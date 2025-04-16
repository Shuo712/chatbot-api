package chatbot.api.domain.github.model.req;

/**
 * 请求回答接口信息
 */
public class CommentReq {

    private ReqData reqData;

    public ReqData getReqData() {
        return reqData;
    }

    public CommentReq(ReqData reqData) {
        this.reqData = reqData;
    }

    public CommentReq() {
    }

    public void setReqData(ReqData reqData) {
        this.reqData = reqData;
    }
}
