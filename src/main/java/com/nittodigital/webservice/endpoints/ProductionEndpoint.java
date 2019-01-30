package com.nittodigital.webservice.endpoints;

import com.nittodigital.webservice.models.soap.production.GetProductionRequest;
import com.nittodigital.webservice.models.soap.production.GetProductionResponse;
import com.nittodigital.webservice.repository.ProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductionEndpoint {

    private static final String NAMESPACE_URI = "http://nittodigital.com/webservice/models/soap/production";

    private ProductionRepository productionRepository;

    @Autowired
    public ProductionEndpoint(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductionRequest")
    @ResponsePayload
    public GetProductionResponse getEmployee(@RequestPayload GetProductionRequest request) {
        GetProductionResponse response = new GetProductionResponse();
        response.setProduction(productionRepository.findProduction(request.getId()));

        return response;
    }
}
