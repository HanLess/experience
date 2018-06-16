package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCache {
    private static Logger logger= LoggerFactory.getLogger(TokenCache.class);

    public static final String PREFIX = "token_";

    public static LoadingCache<String ,String> loadingCache = CacheBuilder.newBuilder().
                                                                initialCapacity(1000).
                                                                maximumSize(10000).
                                                                expireAfterAccess(12,TimeUnit.HOURS).
                                                        // 默认的取值实现，当没有值的时候，就调用此方法
                                                                build(new CacheLoader<String, String>() {
                                                                    @Override
                                                                    public String load(String s) throws Exception {
                                                                        return null;
                                                                    }
                                                                });

    public static void setKey(String key,String value){
        loadingCache.put(key,value);
    }

    public static String getValue(String key){
        String value = null;
        try {
            value = loadingCache.get(key);
        }catch (Exception e){
            logger.error("local cache get error",e);
        }
        return value;
    }
}
