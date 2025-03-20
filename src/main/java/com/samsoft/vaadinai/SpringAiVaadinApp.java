package com.samsoft.vaadinai;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Push
@SpringBootApplication
@Theme(value = "my-theme", variant = "dark")
public class SpringAiVaadinApp implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiVaadinApp.class, args);
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}
