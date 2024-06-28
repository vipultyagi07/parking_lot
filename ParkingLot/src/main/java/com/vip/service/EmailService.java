package com.vip.service;

import com.vip.dto.EmailDetails;
import com.vip.dto.MessageTemplateRepository;
import com.vip.entity.MessageTemplate;
import com.vip.exception.ErrorCode;
import com.vip.exception.ParkingLotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${parkinglot.email.senderName}")
    private String senderName;

    @Autowired
    private MessageTemplateRepository messageTemplateRepository;

    public String sendSimpleMail(EmailDetails details, String templateName) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        MessageTemplate messageTemplate = messageTemplateRepository.findByTemplateName(templateName);
        if(Objects.isNull(messageTemplate)){

            throw new ParkingLotException("NO message template is present for templateName:"+templateName ,ErrorCode.TEMPLATE_NOT_AVAILABLE);
        }
        if(Objects.isNull(messageTemplate.getTemplateContent())){

            throw new ParkingLotException("NO content is present for templateName:"+templateName ,ErrorCode.CONTENT_IS_NOT_PRESENT);
        }

        String messageContent = messageTemplate.getTemplateContent().replace("{OTP}",details.getMsgBody());


        helper.setFrom(new InternetAddress(sender, senderName));
        helper.setTo(details.getRecipient());
        helper.setText(messageContent, true);
        helper.setSubject(details.getSubject());

        javaMailSender.send(message);
        return "Mail Sent Successfully...";
    }

    public String sendMailWithAttachment(EmailDetails details) throws Exception {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(new InternetAddress(sender, senderName));
        mimeMessageHelper.setTo(details.getRecipient());
        mimeMessageHelper.setText(details.getMsgBody(), true);
        mimeMessageHelper.setSubject(details.getSubject());

        // Adding the attachment
        FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
        mimeMessageHelper.addAttachment(file.getFilename(), file);

        // Sending the mail
        javaMailSender.send(mimeMessage);
        return "Mail sent Successfully";
    }
}
