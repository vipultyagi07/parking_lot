package com.vip.repo;

import com.vip.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TestRepository extends JpaRepository<Test,Long> {

    @Query(value = "CALL GetLogsByType(:logIdParam, :logTypeParam)", nativeQuery = true)
    List<Map<String, Object>> getLogsByType(@Param("logIdParam") int logIdParam, @Param("logTypeParam") String logTypeParam);
}
