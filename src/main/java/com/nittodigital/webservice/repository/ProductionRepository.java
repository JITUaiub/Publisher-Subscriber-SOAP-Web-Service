package com.nittodigital.webservice.repository;

import com.nittodigital.webservice.businesslogic.MQTTProductionCounter;
import com.nittodigital.webservice.models.soap.production.Production;
import com.nittodigital.webservice.models.soap.production.ProductionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductionRepository {

    public static final Map<Integer, Production> production = new HashMap<>();

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private iProductionRepository productionRepository;

    @PostConstruct
    public void init(){
        initData();
    }

    public void initData(){
        production.clear();
        List<ProductionModel> productionModelList = getAllData();

        for(ProductionModel tmp : productionModelList){

            Production tmpProduction = new Production();
            tmpProduction.setId(tmp.getId());
            tmpProduction.setMatchineNo(tmp.getMachineNo());
            tmpProduction.setCardNo(tmp.getCardNo());
            tmpProduction.setTimestamp(tmp.getTimestamp().toString());

            production.put(tmpProduction.getId(), tmpProduction);
        }
    }

    private List<ProductionModel> getAllData() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductionModel> criteriaQuery = criteriaBuilder.createQuery(ProductionModel.class);
        Root<ProductionModel> rootEntry = criteriaQuery.from(ProductionModel.class);
        CriteriaQuery<ProductionModel> all = criteriaQuery.select(rootEntry);
        TypedQuery<ProductionModel> allQuery = entityManager.createQuery(all);

        return allQuery.getResultList();
    }

    public Production findProduction(int id){
        return production.get(id);
    }
    public void insertRecord(ProductionModel model){
        productionRepository.save(model);
        initData();
    }
}
