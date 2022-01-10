package com.bedir.mywords.repository;

import com.bedir.mywords.domain.Deste;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Deste entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesteRepository extends JpaRepository<Deste, Long> {

    @Query("select deste from Deste deste where deste.internalUser.login = ?#{principal.username}")
    List<Deste> findByInternalUserIsCurrentUser();

    List<Deste> findAllByInternalUser_Id(long id);
}
