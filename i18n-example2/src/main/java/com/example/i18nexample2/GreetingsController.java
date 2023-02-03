package com.example.i18nexample2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api")
public class GreetingsController {
    private final MessageSource messageSource;

    @Value("${placeholder.greetings}")
    private String greetings;


    public GreetingsController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/message")
    public String getLocaleMessage(
            @RequestHeader(name = "Accept-Language", required = false) final Locale locale,
            @RequestParam(name = "username", defaultValue = "Mr Incognito", required = false) final String username) {
        log.info("Returning greetings for locale = {}", locale);
        return messageSource.getMessage(greetings, new Object[]{username}, locale);
    }
}
