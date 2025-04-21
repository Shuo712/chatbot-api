package chatbot.api.domain.chatbot;

public interface IOpenAI {

    String doChatGPT(String question) throws Exception;

}
