package com.bechtle.eagl.UserMappingsService.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Relation {

    Mapping mapping;
    List<UserSkill> skills;

    public Relation(Mapping mapping, List<UserSkill> skills) {
        this.mapping = mapping;
        this.skills = skills;
    }
}
