package com.lifeos.repository;

import com.lifeos.model.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, UUID> {
    
    List<Routine> findByUserId(UUID userId);

    @Query("SELECT r FROM Routine r WHERE r.user.id = :userId AND r.daysOfWeek LIKE %:dayOfWeek%")
    List<Routine> findActiveRoutines(@Param("userId") UUID userId, @Param("dayOfWeek") String dayOfWeek);
}
