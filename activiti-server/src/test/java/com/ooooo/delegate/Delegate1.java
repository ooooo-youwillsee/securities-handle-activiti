package com.ooooo.delegate;

import java.util.concurrent.atomic.AtomicInteger;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class Delegate1 implements JavaDelegate {
    
    private static final AtomicInteger count = new AtomicInteger(0);
    
    public void execute(DelegateExecution execution) {
        Variable v = new Variable();
        v.value = "delegate1";
        execution.setVariable("variable", v);
        
        int cnt = count.incrementAndGet();
        System.out.println("---delegate1--- cnt: " + cnt);
        
        int exception_cnt = (int) execution.getVariable("exception_cnt");
        if (cnt <= exception_cnt) {
            throw new RuntimeException("service1");
        }
    }
    
}
