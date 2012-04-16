package com.qcadoo.mail.internal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.qcadoo.mail.api.InvalidMailAddressException;
import com.qcadoo.mail.api.MailConfigurationException;
import com.qcadoo.mail.api.MailService;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    protected MailSender mailSender;

    @Value("${mail.address}")
    private String defaultSender;

    @Override
    public void sendPlainTextEmail(final String recipient, final String subject, final String body) {
        sendPlainTextEmail(getDefaultSender(), recipient, subject, body);
    }

    protected void sendPlainTextEmail(final String sender, final String recipient, final String subject, final String body) {
        validateEmail(sender);
        validateEmail(recipient);
        Preconditions.checkArgument(StringUtils.isNotBlank(subject), "e-mail subject should not be blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(body), "e-mail body should not be blank");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailSender.send(mailMessage);
    }

    protected String getDefaultSender() {
        if (isValidEmail(defaultSender)) {
            return defaultSender;
        }
        throw new MailConfigurationException('\'' + defaultSender + "' is not valid e-mail address. Check your mail.properties");
    }

    private void validateEmail(final String email) {
        if (!isValidEmail(email)) {
            throw new InvalidMailAddressException('\'' + email + "' is not valid e-mail address");
        }
    }

    public static boolean isValidEmail(final String email) {
        return StringUtils.isNotBlank(email) && EmailValidator.getInstance().isValid(email);
    }

}
