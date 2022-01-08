package com.bedir.mywords.repository;

import com.bedir.mywords.domain.Deste;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Deste entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesteRepository extends JpaRepository<Deste, Long> {
}
