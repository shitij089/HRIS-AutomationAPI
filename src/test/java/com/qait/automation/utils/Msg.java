/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.utils;

import org.testng.Reporter;

/**
 *
 * @author Prashant Shukla <prashantshukla@qainfotech.com>
 */
public class Msg {

    protected static final String fail = "[ASSERT FAIL]: ";
    protected static final String info = "[INFO]: ";
    protected static final String pass = "[ASSERT PASS]: ";
    protected static final String scripterror = "[SCRIPTING ERROR]: ";
    protected static final String actions = "[ACTION]: ";

    public  String failForAssert(String message) {
        return reportMsgForAssert(fail, message, true);
    }
    
    public String fail(String message) {
        return reportMsg(fail, message);
    }

    public String pass(String message) {
        return reportMsg(pass, message);
    }

    public  String scripterror(String message) {
        return reportMsg(scripterror, message);
    }
    
    public String log(String message){
    	return reportMsg(info, message);
    }
    
    public String actions(String message){
    	return reportMsg(actions, message);
    }

    private String reportMsg(String prefix, String message) {
    	Reporter.log(prefix + message, true);
        return prefix + message;
    }
    
    private String reportMsgForAssert(String prefix, String message, boolean flag) {
        return prefix + message;
    }
    
}
