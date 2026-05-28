package com.bank;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// ===================================================
// AuditAspect - Logs all operations automatically (AOP)
//
// Without AOP: add System.out.println in every method
// With AOP:    write it once here and it runs for all
//
// @Aspect    - from AspectJ library
// @Component - so Spring knows about it
// ===================================================

@Aspect
@Component
public class AuditAspect {

    // -----------------------------------------------
    // Pointcut - which methods do we watch?
    // All methods in package com.bank.service
    // -----------------------------------------------
    @Pointcut("execution(* com.bank.service.*.*(..))")
    public void bankServiceMethods() {}

    // -----------------------------------------------
    // Around Advice - before and after every operation
    // -----------------------------------------------
    @Around("bankServiceMethods()")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();

        // Before the operation
        System.out.println("AOP: started -> " + methodName);
        long startTime = System.currentTimeMillis();

        try {
            // Execute the original method
            Object result = joinPoint.proceed();

            // After the operation succeeds
            long time = System.currentTimeMillis() - startTime;
            System.out.println("AOP: finished -> " + methodName + " (" + time + "ms)");

            return result;

        } catch (Exception e) {
            // If an error occurs
            System.out.println("AOP: failed -> " + methodName + " | reason: " + e.getMessage());
            throw e;
        }
    }
}