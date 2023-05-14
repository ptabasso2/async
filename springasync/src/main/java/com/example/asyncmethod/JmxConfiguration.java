package com.example.asyncmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.jmx.support.RegistrationPolicy;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JmxConfiguration {

    @Autowired
    private CoreMediaMBean coreMediaMBean;

    @Autowired
    private CustomMBeanImpl customMBeanImpl;


    @Bean
    public AnnotationMBeanExporter mbeanExporter() {
        AnnotationMBeanExporter exporter = new AnnotationMBeanExporter();
        Map<String, Object> beans = new HashMap<>();
        beans.put("com.coremedia:application=replication-live-server,module=ResourceCacheStatistics,pool=ResourceCache,type=Statistics", coreMediaMBean);
        beans.put("com.datadoghq.custom:name=Springasync,application=jmx-async,module=AsyncResourceStats,type=Statistics", customMBeanImpl);
        exporter.setRegistrationPolicy(RegistrationPolicy.REPLACE_EXISTING);
        exporter.setBeans(beans);
        return exporter;
    }
}