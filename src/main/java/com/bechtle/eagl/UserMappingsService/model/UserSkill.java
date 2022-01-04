package com.bechtle.eagl.UserMappingsService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;


import java.util.Set;

@Data
public class UserSkill extends Skill {

    @Id
    @JsonIgnore
    Long id;

    @Column("user_id")
    String userId;

    Status status;

    public UserSkill(String userId, String label, Status status) {
        super.label = label;
        this.userId = userId;
        this.status = status;
    }
}
