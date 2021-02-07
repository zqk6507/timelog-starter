package org.coolcoding.boot;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@Configuration
@EnableAspectJAutoProxy
@ConditionalOnProperty(prefix = "time.log", name = "enable", havingValue = "true", matchIfMissing = true)
public class TimelogAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TimelogAutoConfiguration.class);


    @Around("@annotation(org.coolcoding.boot.Timelog)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String name = pjp.getSignature().toLongString().split(" ")[2];
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis();
        log.info("方法:{}耗时:{}ms", name, endTime - startTime);
        return result;
    }


}
