package com.lifeos.repository;

import com.lifeos.model.Task;
import com.lifeos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUserOrderByScheduledStartAsc(User user);
}
