package com.msapi2.authservice.dao;

import com.msapi2.authservice.model.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

    Profile findByCode(String code);
    Profile findByGroupeAD(String groupeAD);
}
