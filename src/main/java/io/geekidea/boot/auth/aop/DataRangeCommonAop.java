package io.geekidea.boot.auth.aop;

import io.geekidea.boot.common.constant.AopConstant;
import io.geekidea.boot.framework.query.DataRangeQuery;
import io.geekidea.boot.util.DataRangeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author geekidea
 * @date 2022/4/20
 **/
@Slf4j
@Aspect
@Component
public class DataRangeCommonAop {

    @Around(AopConstant.COMMON_POINTCUT)
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (ArrayUtils.isEmpty(args)) {
            return joinPoint.proceed();
        }
        for (Object arg : args) {
            if (arg instanceof DataRangeQuery) {
                DataRangeQuery dataRangeQuery = (DataRangeQuery) arg;
                DataRangeUtil.handleCommonLogin(dataRangeQuery);
                break;
            }
        }
        return joinPoint.proceed();
    }


}
