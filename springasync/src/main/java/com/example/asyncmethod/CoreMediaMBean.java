package com.example.asyncmethod;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName = "com.coremedia:application=replication-live-server,module=ResourceCacheStatistics,pool=ResourceCache,type=Statistics")
public class CoreMediaMBean {

    private Long CacheFaults = 10L;
    private Long CacheMisses = 12L;
    private Long CacheHits = 11L;
    private Long CacheSize = 120L;
    @ManagedOperation(description = "Set CacheFaults")
    public void setCacheFaults(Long cacheFaults) {
        CacheFaults = cacheFaults;
    }
    @ManagedOperation(description = "Set CacheMisses")
    public void setCacheMisses(Long cacheMisses) {
        CacheMisses = cacheMisses;
    }
    @ManagedOperation(description = "Set CacheHits")
    public void setCacheHits(Long cacheHits) {
        CacheHits = cacheHits;
    }
    @ManagedOperation(description = "Set CacheSize")
    public void setCacheSize(Long cacheSize) {
        CacheSize = cacheSize;
    }

    @ManagedAttribute
    @ManagedOperation(description = "Get CacheFaults")
    public Long getCacheFaults() {
        return CacheFaults;
    }
    @ManagedAttribute
    @ManagedOperation(description = "Get CacheMisses")
    public Long getCacheMisses() {
        return CacheMisses;
    }
    @ManagedAttribute
    @ManagedOperation(description = "Get CacheHits")
    public Long getCacheHits() {
        return CacheHits;
    }
    @ManagedAttribute
    @ManagedOperation(description = "Get CacheSize")
    public Long getCacheSize() {
        return CacheSize;
    }

}