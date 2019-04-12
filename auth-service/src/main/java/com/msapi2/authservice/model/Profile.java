package com.msapi2.authservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Profile Specific to a User
 */
@Entity
@Data
public class Profile {

    @Id
    @GeneratedValue
    private Long profileId;

    @Version
    private Long version;

    private String code;

    private String label;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Right> rights = new HashSet<>();

    public Profile() {
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Profile{");
        sb.append("profileId='").append(profileId).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", rights=");
        if (rights == null) {
            sb.append("[]");
        } else {
            sb.append("[");
            for (Right right: rights) {
                sb.append("code='").append(right.getCode()).append('\'');
                sb.append(", label='").append(right.getLabel()).append('\'');
            }
            sb.append("]");
        }

        return sb.toString();
    }
}
