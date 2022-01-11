package com.bechtle.eagl.UserMappingsService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserSkill extends Skill {

    @Id
    @JsonIgnore
    Long id;

    String userId;

    Status status;

    public UserSkill(String userId, String label, Status status) {
        super.label = label;
        this.userId = userId;
        this.status = status;
    }
}
