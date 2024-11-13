package com.example.lab6.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@LogExecutionTime
public class LogExecutionTimeInterceptor {

    @AroundInvoke
    public Object log(InvocationContext ctx) throws Exception {
        String className = ctx.getTarget().getClass().getName();
        String methodName = ctx.getMethod().getName();
        String target = className + "." + methodName + "()";

        long t1 = System.currentTimeMillis();

        try {
            return ctx.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            long t2 = System.currentTimeMillis();
            System.out.println(target + " took " + (t2 - t1) + "ms to execute");
        }
    }

}
