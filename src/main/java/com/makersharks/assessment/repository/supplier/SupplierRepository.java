package com.makersharks.assessment.repository.supplier;

import com.makersharks.assessment.entity.Location;
import com.makersharks.assessment.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierRepository extends JpaRepository<SupplierEntity,Long> {


    List<SupplierEntity> findByLocation(Location location);

    List<SupplierEntity> findByBusinessNature(String businessNature);

    List<SupplierEntity> findByManufacturingProcess(String manufacturingProcesses);


    @Query("select s from SupplierEntity s where s.location.cityName = :city " +
            "AND s.businessNature = :business AND" +
            " s.manufacturingProcess = :process")
    Page<SupplierEntity> queriedList(String city, String business, String process, Pageable pageable);

    @Query("select s from SupplierEntity s where s.location.cityName = :city " +
            "AND s.businessNature = :business AND" +
            " s.manufacturingProcess = :process")
    List<SupplierEntity> queriedListTest(String city, String business, String process);
}
