package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSummaryBySellerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller " +
            "WHERE obj.date BETWEEN :min AND :max " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller " +
                    "WHERE obj.date BETWEEN :min AND :max " +
                    "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Sale> searchAll(@Param("min") LocalDate min,
                         @Param("max") LocalDate max,
                         @Param("name") String name,
                         Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryBySellerDTO(obj.seller.name, SUM(obj.amount)) " +
            "FROM Sale obj " +
            "WHERE (:min IS NULL OR obj.date >= :min) " +
            "AND (:max IS NULL OR obj.date <= :max) " +
            "GROUP BY obj.seller.name " +
            "ORDER BY obj.seller.name")
    List<SaleSummaryBySellerDTO> sumBySeller(@Param("min") LocalDate min,
                                             @Param("max") LocalDate max);
}

