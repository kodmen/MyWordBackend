package com.bedir.mywords.repository;

import com.bedir.mywords.domain.Kart;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Kart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KartRepository extends JpaRepository<Kart, Long> {
}
