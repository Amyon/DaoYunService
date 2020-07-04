package lu.springboot.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class CacheUtil {

    static public Cache<String, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();

}
