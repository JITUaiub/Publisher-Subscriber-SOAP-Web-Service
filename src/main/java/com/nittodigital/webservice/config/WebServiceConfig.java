package com.nittodigital.webservice.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/production-counter-service/*");
    }

    @Bean(name = "productions")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema empSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProductionPort");
        wsdl11Definition.setLocationUri("/production-counter-service");
        wsdl11Definition.setTargetNamespace("http://nittodigital.com/webservice/models/soap/production");
        wsdl11Definition.setSchema(empSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema empSchema() {
        return new SimpleXsdSchema(new ClassPathResource("production-counter.xsd"));
    }
}
