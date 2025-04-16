package chatbot.api.domain.github.model.aggregates;

import chatbot.api.domain.github.model.res.ResData;

/**
 * Issue的聚合信息
 */
public class IssuesAggregates {

    private int total;
    private ResData resData;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ResData getResData() {
        return resData;
    }

    public void setResData(ResData resData) {
        this.resData = resData;
    }
}
