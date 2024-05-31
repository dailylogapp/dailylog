package com.dailylog.dailylog.repository;

import com.dailylog.dailylog.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
