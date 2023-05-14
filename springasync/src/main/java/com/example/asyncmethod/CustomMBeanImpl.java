package com.example.asyncmethod;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.management.DynamicMBean;

@Component
@ManagedResource(objectName = "com.datadoghq.custom:name=Springasync,application=jmx-async,module=AsyncResourceStats,type=Statistics")
public class CustomMBeanImpl {
    private int attribute;

    @ManagedAttribute
    @ManagedOperation (description = "Get Attribute")
    public int getAttribute() {
        return attribute;
    }

    @ManagedOperation(description = "Set Attribute")
    public void setAttribute(int value) {
        attribute = value;
    }
}