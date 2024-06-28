package com.vip.entity;

import com.vip.entity.baseEntity.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class MessageTemplate extends BaseEntity {

    private String templateName;
    private String templateContent;
    private String templateType;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }
}
