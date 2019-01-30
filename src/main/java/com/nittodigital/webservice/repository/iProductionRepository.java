package com.nittodigital.webservice.repository;

import com.nittodigital.webservice.models.soap.production.ProductionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iProductionRepository extends JpaRepository<ProductionModel, Integer> {

    //public Production
}
