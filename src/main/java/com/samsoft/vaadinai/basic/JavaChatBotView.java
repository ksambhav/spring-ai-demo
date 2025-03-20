package com.samsoft.vaadinai.basic;

import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.vaadin.firitin.components.messagelist.MarkdownMessage;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.UUID;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@Slf4j
@PageTitle("Java Vaadin Chatbot")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.FILE)
public class JavaChatBotView extends VerticalLayout {

    private final String CHAT_CONVERSATION_ID = UUID.randomUUID().toString();

    public JavaChatBotView(ChatClient.Builder builder, ChatMemory memory) {
        setSizeFull();
        var chatClient = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(memory))
                .defaultSystem("""
                        Take a deep breath and work on this step by step. You are an Java, SQL, RDBMS, Machine Learning and LLM expert.
                        You have hands on experience of writing Java code using core library, collections and multi-threading.
                        You have in-depth knowledge of Machine Learning, LLM, embedding models, training models using PyTorch and vector databases like pgvector for building Retrieval Augmented Generation based applications.
                        Answer users question in simplified manner. Explain concepts using analogy wherever possible.
                        """)
                .build();
        var messageVerticalLayout = new VerticalLayout();
        var input = new MessageInput();
        input.setWidthFull();
        input.addSubmitListener(event -> {
            var message = event.getValue();
            var response = new MarkdownMessage("Bot");
            messageVerticalLayout.add(
                    new MarkdownMessage(message, "You"),
                    response
            );
            chatClient.prompt()
                    .advisors(c -> c.param(CHAT_MEMORY_CONVERSATION_ID_KEY, CHAT_CONVERSATION_ID))
                    .user(event.getValue())
                    .stream()
                    .content()
                    .subscribe(response::appendMarkdownAsync);
        });
        Scroller scroller = new Scroller(messageVerticalLayout);
        scroller.setSizeFull();
        addAndExpand(scroller);
        add(input);
    }
}
