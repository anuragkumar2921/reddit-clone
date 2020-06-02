package com.anurag.redditclone.service;

import com.anurag.redditclone.exception.RedditCloneException;
import com.anurag.redditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * Created by anurag on 3/6/20.
 */
@AllArgsConstructor
@Service
@Slf4j
public class MailService {
    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender javaMailSender;

    public void sendEmail(NotificationEmail notificationEmail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("anuragk@gmail.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilder.buildEmail(notificationEmail.getBody()));
        };
        try {
            javaMailSender.send(mimeMessagePreparator);
            log.info("Activation Email has been sent");
        } catch (MailException ex) {
            log.error("Activation Email failed to send... Please try again later..!!", ex);
            throw new RedditCloneException("Activation Email send failed to : " + notificationEmail.getRecipient());
        }
    }
}
