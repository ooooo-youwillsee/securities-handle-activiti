package com.ooooo.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class Delegate2 implements JavaDelegate {
	
	public void execute(DelegateExecution execution) {
		Variable v = (Variable) execution.getVariable("variable");
		boolean ok = (v.value != null && v.value.equals("delegate1"));
		v.value = "delegate2";
		execution.setVariable("variable", v);
		
		System.out.println("---delegate2---");
	}
	
}
