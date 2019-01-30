package com.nittodigital.webservice.models.soap.production;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "production_counter_data")
public class ProductionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "machine_no")
    private String machineNo;
    @Column(name = "card_no")
    private String cardNo;
    @Column(name = "timestamp")
    private Timestamp timestamp;

    public ProductionModel(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String matchineNo) {
        this.machineNo = matchineNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
