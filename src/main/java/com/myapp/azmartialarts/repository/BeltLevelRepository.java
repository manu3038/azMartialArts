package com.myapp.azmartialarts.repository;

import com.myapp.azmartialarts.domain.BeltLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BeltLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeltLevelRepository extends JpaRepository<BeltLevel, Long> {

}
