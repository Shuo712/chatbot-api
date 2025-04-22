package chatbot.api.domain.chatbot.model.vo;

public class Usage {

    private String prompt_tokens;
    private String completion_tokens;
    private String total_tokens;
    private PromptTokensDetails prompt_tokens_details;
    private String prompt_cache_hit_tokens;
    private String prompt_cache_miss_tokens;

    public String getPrompt_tokens() {
        return prompt_tokens;
    }

    public void setPrompt_tokens(String prompt_tokens) {
        this.prompt_tokens = prompt_tokens;
    }

    public String getPrompt_cache_hit_tokens() {
        return prompt_cache_hit_tokens;
    }

    public void setPrompt_cache_hit_tokens(String prompt_cache_hit_tokens) {
        this.prompt_cache_hit_tokens = prompt_cache_hit_tokens;
    }

    public String getPrompt_cache_miss_tokens() {
        return prompt_cache_miss_tokens;
    }

    public void setPrompt_cache_miss_tokens(String prompt_cache_miss_tokens) {
        this.prompt_cache_miss_tokens = prompt_cache_miss_tokens;
    }

    public String getCompletion_tokens() {
        return completion_tokens;
    }

    public void setCompletion_tokens(String completion_tokens) {
        this.completion_tokens = completion_tokens;
    }

    public String getTotal_tokens() {
        return total_tokens;
    }

    public void setTotal_tokens(String total_tokens) {
        this.total_tokens = total_tokens;
    }

    public PromptTokensDetails getPrompt_tokens_details() {
        return prompt_tokens_details;
    }

    public void setPrompt_tokens_details(PromptTokensDetails prompt_tokens_details) {
        this.prompt_tokens_details = prompt_tokens_details;
    }
}
