package com.anurag.redditclone.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Created by anurag on 3/6/20.
 */
@AllArgsConstructor
@Service
public class MailContentBuilder {
    private final TemplateEngine templateEngine;

    public String buildEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}
