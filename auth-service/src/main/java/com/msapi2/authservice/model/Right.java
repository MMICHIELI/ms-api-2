package com.msapi2.authservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Related to one  or more Profile
 */
@Entity
@Data
@Table(name = "RIGHT")
public class Right {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    private String code;

    private String label;

    @ManyToMany(mappedBy = "rights", fetch = FetchType.EAGER)
    private Set<Profile> profiles = new HashSet<>();

    public Right() {
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Right{");
        sb.append("id='").append(id).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", profiles=");
        if (profiles == null) {
            sb.append("[]");
        } else {
            sb.append("[");
            for (Profile profile: profiles) {
                sb.append("code='").append(profile.getCode()).append('\'');
                sb.append(", label='").append(profile.getLabel()).append('\'');
            }
            sb.append("]");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Right right = (Right) o;
        return id == right.id &&
            version == right.version &&
            Objects.equals(code, right.code) &&
            Objects.equals(label, right.label) &&
            Objects.equals(profiles, right.profiles);
    }
}
