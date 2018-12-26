package com.starter.springboot.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Description(value = "Service for generating and validating OTP.")
@Service
public class OtpGenerator {

    private static final Integer EXPIRE_MIN = 10;
    private LoadingCache<String, Integer> otpCache;
    public OtpGenerator()
    {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    public Integer generateOTP(String key)
    {
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        otpCache.put(key, OTP);

        return OTP;
    }

    public Integer getOPTByKey(String key)
    {
        try {
            return otpCache.get(key);
        }
        catch (ExecutionException e) {
            return -1;
        }
    }
    
    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }
}
