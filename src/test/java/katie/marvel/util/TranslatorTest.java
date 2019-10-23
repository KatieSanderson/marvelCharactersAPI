package katie.marvel.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslatorTest {

    @Autowired
    private Translator translator;

    @Test
    public void translate_hiWithEn_returnsHi() {
        assertEquals("hi", translator.translate("hi", "en"));
    }

    @Test
    public void translate_hiWithEs_returnsHola() {
        assertEquals("hola", translator.translate("hi", "es"));
    }

    @Test
    public void translate_blank_returnsBlank() {
        assertEquals("", translator.translate("", "es"));
    }

    @Test
    public void translate_specialCharWithEs_returnsSpecialChar() {
        assertEquals("!", translator.translate("!", "es"));
    }

    @Test
    public void translate_specialCharWithHiWithEs_returnsSpecialCharWithHola() {
        assertEquals("hola!", translator.translate("hi!", "es"));
    }

    @Test
    public void translate_unsupportedLanguage_throwsException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> translator.translate("hi", ""));
        assertEquals("Language provided not supported.", exception.getMessage());
    }
}