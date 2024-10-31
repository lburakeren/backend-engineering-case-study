package com.dreamgames.backendengineeringcasestudy.repository;

import com.dreamgames.backendengineeringcasestudy.model.GroupParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupParticipantRepository extends JpaRepository<GroupParticipant,Long> {

    Optional<GroupParticipant> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

}
