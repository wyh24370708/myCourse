package com.course.server.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Accessors(chain = true)
@Data
public class User {
    private String id;

    private String loginName;

    private String name;

    private String password;

    private Date createdAt;

    private Date updatedAt;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginName=").append(loginName);
        sb.append(", name=").append(name);
        sb.append(", password=").append(password);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}