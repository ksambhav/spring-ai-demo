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
import org.springframework.ai.embedding.EmbeddingModel;
import org.vaadin.firitin.components.messagelist.MarkdownMessage;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.UUID;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@Slf4j
@PageTitle("LLM Tutor")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.FILE)
public class MyLLMTutor extends VerticalLayout {

    private final String CHAT_CONVERSATION_ID = UUID.randomUUID().toString();

    public MyLLMTutor(ChatClient.Builder builder, ChatMemory memory, EmbeddingModel embeddingModel) {
        log.debug("EmbeddingModel = {}", embeddingModel);
        setSizeFull();
        var chatClient = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(memory))
                .defaultSystem(PromptConstants.LLM_TUTOR_SYS_PROMPT)
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
