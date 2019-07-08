package com.code4ro.legalconsultation.common.i18n;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class I18nServiceTest {

    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private I18nService i18nService;

    @Test
    public void translate() {
        final List<Object> args = Collections.singletonList("i18nArg");
        i18nService.translate("i18nKey", args);

        verify(messageSource).getMessage("i18nKey", args.toArray(), Locale.US);
    }

    @Test
    public void translateMessageNotFound() {
        final List<Object> args = Collections.singletonList("i18nArg");
        when(messageSource.getMessage("i18nKey", args.toArray(), Locale.US))
                .thenThrow(new NoSuchMessageException("No message!"));

        final String translation = i18nService.translate("i18nKey", args);
        assertEquals(translation, "i18nKey");

    }
}