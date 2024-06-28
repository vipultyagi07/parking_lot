package com.vip.dto;

import com.vip.entity.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate,Long> {
    MessageTemplate findByTemplateName(String templateName);
}
