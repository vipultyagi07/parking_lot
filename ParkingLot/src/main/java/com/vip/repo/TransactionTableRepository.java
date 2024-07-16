package com.vip.repo;

import com.vip.entity.TransactionTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTableRepository extends JpaRepository<TransactionTable, Long> {
    TransactionTable findByParkingTicketIdAndUserId(Long parkingTicketId, Long userId);
}
