package com.psf.core.aspect;

import com.psf.core.persistence.dao.CronLogDao;
import com.psf.core.persistence.entity.CronLogEntity;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CronLogAspect {

    private final CronLogDao cronLogDao;

    @Pointcut("@annotation(com.psf.core.aspect.CronLog)")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object aroundHandler(ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        String jobName  = methodSignature.getName();
        boolean isSuccess = true;
        String reason = null;
        Object result = null;
        long startTime = System.currentTimeMillis();
        try {
            result = jp.proceed();
        } catch (Throwable e) {
            reason = e.getMessage();
            isSuccess = false;
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        CronLogEntity entity = new CronLogEntity();
        entity.setJobName(jobName);
        entity.setIsSuccess(isSuccess);
        entity.setDetail(reason);
        entity.setDuration((int) totalTime / 1000);
        cronLogDao.save(entity);
        return result;
    }
}
