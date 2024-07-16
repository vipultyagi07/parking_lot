package com.vip.repo.impl;

import com.vip.dto.ParkingTicketInfoDTO;
import com.vip.repo.CustomParkingTicketRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.persistence.Query;
import org.springframework.util.StringUtils;

import java.util.List;

public class CustomParkingTicketRepositoryImpl implements CustomParkingTicketRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ParkingTicketInfoDTO> findParkingTicketInfoByUserId(Long userId, Pageable pageable, Sort sort, String vehicleNumber) {
        String countQueryStr = "SELECT COUNT(pt.id) FROM ParkingTicket pt " +
                "JOIN pt.parkingSpot ps " +
                "JOIN pt.vehicle v " +
                "WHERE pt.userId = :userId";
        if (vehicleNumber != null && !vehicleNumber.isEmpty()) {
            countQueryStr += " AND v.vehicleNo LIKE :vehicleNumber";
        }
        Query countQuery = entityManager.createQuery(countQueryStr);
        countQuery.setParameter("userId", userId);
        if (vehicleNumber != null && !vehicleNumber.isEmpty()) {
            countQuery.setParameter("vehicleNumber", "%" + vehicleNumber + "%");
        }
        long total = (long) countQuery.getSingleResult();

        String queryStr = "SELECT new com.vip.dto.ParkingTicketInfoDTO(" +
                "pt.id, pt.entryTime, pt.exitTime, " +
                "v.id, v.vehicleNo, v.vehicleType, " +
                "ps.id, ps.isEmpty, ps.type, ps.price) " +
                "FROM ParkingTicket pt " +
                "JOIN pt.parkingSpot ps " +
                "JOIN pt.vehicle v " +
                "WHERE pt.userId = :userId";
        if (vehicleNumber != null && !vehicleNumber.isEmpty()) {
            queryStr += " AND v.vehicleNo LIKE :vehicleNumber";
        }
        // Check if sort is not null and apply sorting
        if (sort != null) {
            queryStr += " ORDER BY ";
            for (Sort.Order order : sort) {
                queryStr += "pt." + order.getProperty() + " " + order.getDirection().name() + ", ";
            }
            queryStr = queryStr.substring(0, queryStr.lastIndexOf(","));
        }

        Query query = entityManager.createQuery(queryStr);
        query.setParameter("userId", userId);
        if (vehicleNumber != null && !vehicleNumber.isEmpty()) {
            query.setParameter("vehicleNumber", "%" + vehicleNumber + "%");
        }
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());

        List<ParkingTicketInfoDTO> resultList = query.getResultList();
        return new PageImpl<>(resultList, pageable, total);
    }
}
