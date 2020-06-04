package com.course.server.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors(chain = true)
public class Profile {
    private String id;

    private String name;

    private String path;

    private Integer size;

    private String suffix;

    private String use;

    private Date createdAt;

    private Date updatedAt;

    private Integer shardIndex;

    private Integer shardSize;

    private Integer shardTotal;

    private String key;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", path=").append(path);
        sb.append(", size=").append(size);
        sb.append(", suffix=").append(suffix);
        sb.append(", use=").append(use);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", shardIndex=").append(shardIndex);
        sb.append(", shardSize=").append(shardSize);
        sb.append(", shardTotal=").append(shardTotal);
        sb.append(", key=").append(key);
        sb.append("]");
        return sb.toString();
    }
}