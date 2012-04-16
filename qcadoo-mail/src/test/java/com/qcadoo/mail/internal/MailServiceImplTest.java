package com.qcadoo.mail.internal;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.mail.api.InvalidMailAddressException;
import com.qcadoo.mail.api.MailConfigurationException;

public class MailServiceImplTest {

    private static final String DEFAULT_SENDER = "test-sender@qcadoo.com";

    private static final String DEFAULT_RECIPIENT = "test-recipient@qcadoo.com";

    private static final String DEFAULT_SUBJECT = "test-subject";

    private static final String DEFAULT_BODY = "test-body";

    private MailServiceImpl mailServiceImpl;

    private MailSender mailSender;

    @Before
    public final void init() {
        mailServiceImpl = new MailServiceImpl();

        mailSender = mock(MailSender.class);
        ReflectionTestUtils.setField(mailServiceImpl, "mailSender", mailSender);
        ReflectionTestUtils.setField(mailServiceImpl, "defaultSender", DEFAULT_SENDER);
    }

    @Test
    public final void shouldSendPlainTextEmail() throws Exception {
        // given
        ArgumentCaptor<SimpleMailMessage> mailMessageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, DEFAULT_SUBJECT, DEFAULT_BODY);

        // then
        verify(mailSender, Mockito.times(1)).send(mailMessageCaptor.capture());
        SimpleMailMessage mailMessage = mailMessageCaptor.getValue();
        assertEquals(DEFAULT_SENDER, mailMessage.getFrom());
        assertEquals(DEFAULT_RECIPIENT, mailMessage.getTo()[0]);
        assertEquals(DEFAULT_SUBJECT, mailMessage.getSubject());
        assertEquals(DEFAULT_BODY, mailMessage.getText());
    }

    @Test(expected = MailConfigurationException.class)
    public final void shouldThrowExceptionIfDefaultSenderIsNull() throws Exception {
        // given
        setDefaultSender(null);

        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = MailConfigurationException.class)
    public final void shouldThrowExceptionIfDefaultSenderIsEmpty() throws Exception {
        // given
        setDefaultSender("");

        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = MailConfigurationException.class)
    public final void shouldThrowExceptionIfDefaultSenderIsBlank() throws Exception {
        // given
        setDefaultSender("  ");

        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = MailConfigurationException.class)
    public final void shouldThrowExceptionIfDefaultSenderIsNotValid() throws Exception {
        // given
        setDefaultSender("invalid");

        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    private void setDefaultSender(final String defaultSender) {
        ReflectionTestUtils.setField(mailServiceImpl, "defaultSender", defaultSender);
    }

    @Test(expected = InvalidMailAddressException.class)
    public final void shouldThrowExceptionIfSenderIsNull() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail(null, DEFAULT_RECIPIENT, DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = InvalidMailAddressException.class)
    public final void shouldThrowExceptionIfSenderIsEmpty() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail("", DEFAULT_RECIPIENT, DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = InvalidMailAddressException.class)
    public final void shouldThrowExceptionIfSenderIsBlank() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail(" ", DEFAULT_RECIPIENT, DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = InvalidMailAddressException.class)
    public final void shouldThrowExceptionIfSenderIsNotValid() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail("invalid", DEFAULT_RECIPIENT, DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = InvalidMailAddressException.class)
    public final void shouldThrowExceptionIfRecipientIsNull() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail(null, DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = InvalidMailAddressException.class)
    public final void shouldThrowExceptionIfRecipientIsEmpty() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail("", DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = InvalidMailAddressException.class)
    public final void shouldThrowExceptionIfRecipientIsBlank() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail(" ", DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = InvalidMailAddressException.class)
    public final void shouldThrowExceptionIfRecipientIsNotValid() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail("invalid", DEFAULT_SUBJECT, DEFAULT_BODY);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void shouldThrowExceptionIfSubjectIsNull() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, null, DEFAULT_BODY);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void shouldThrowExceptionIfSubjectIsEmpty() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, "", DEFAULT_BODY);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void shouldThrowExceptionIfSubjectIsBlank() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, " ", DEFAULT_BODY);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void shouldThrowExceptionIfBodyIsNull() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, DEFAULT_SUBJECT, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void shouldThrowExceptionIfBodyIsEmpty() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, DEFAULT_SUBJECT, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void shouldThrowExceptionIfBodyIsBlank() throws Exception {
        // when
        mailServiceImpl.sendPlainTextEmail(DEFAULT_RECIPIENT, DEFAULT_SUBJECT, " ");
    }

}
