package com.project.database.repo;

import com.project.database.Entities.Name;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameRepository extends JpaRepository<Name, Long> {
}
