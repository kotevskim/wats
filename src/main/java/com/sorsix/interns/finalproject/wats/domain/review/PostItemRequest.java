package com.sorsix.interns.finalproject.wats.domain.review;

public class PostItemRequest {
    private String description;
    private Long parentId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
