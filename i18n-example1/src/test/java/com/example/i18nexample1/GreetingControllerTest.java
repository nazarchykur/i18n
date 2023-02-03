package com.example.i18nexample1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
class GreetingControllerTest {

    @Autowired
    private MessageSource messageSource;

    @Test
    void greeting_default_en() {
        //given
        Locale locale = Locale.US;

        //when
        String messageText = messageSource.getMessage("greeting.text", null, locale);

        //then
        assert messageText.equals("Hi Welcome to I18n");
    }

    @Test
    void greeting_fr() {
        //given
        Locale locale = Locale.FRANCE;

        //when
        String messageText = messageSource.getMessage("greeting.text", null, locale);

        //then
        assert messageText.equals("Salut Bienvenue sur i18n");
    }

    @Test
    void greeting_pl() {
        //given
        Locale locale = new Locale("pl", "PL");

        //when
        String messageText = messageSource.getMessage("greeting.text", null, locale);

        //then
        assert messageText.equals("Witamy w I18n");
    }
}