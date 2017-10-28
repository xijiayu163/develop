/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.juli.logging;

import java.io.FileNotFoundException;

import org.apache.commons.logging.LogFactory;
import org.springframework.util.Log4jConfigurer;

/** 
 * Hardcoded java.util.logging commons-logging implementation.
 * 
 * In addition, it curr 
 * 
 */
class DirectJDKLog implements Log {
    // no reason to hide this - but good reasons to not hide
    public org.apache.commons.logging.Log logger;
    
    static {
        try {
			Log4jConfigurer.initLogging("classpath:log4j.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    public DirectJDKLog(String name ) {
        logger=LogFactory.getLog(name);//Logger.getLogger(name);        
    }
    
    @Override
    public final boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }
    
    @Override
    public final boolean isWarnEnabled() {
    	return logger.isWarnEnabled();
    }
    
    @Override
    public final boolean isInfoEnabled() {
    	return logger.isInfoEnabled();
    }
    
    @Override
    public final boolean isDebugEnabled() {
    	return logger.isDebugEnabled();
    }
    
    @Override
    public final boolean isFatalEnabled() {
    	return logger.isFatalEnabled();
    }
    
    @Override
    public final boolean isTraceEnabled() {
    	return logger.isTraceEnabled();
    }
    
    @Override
    public final void debug(Object message) {
    	logger.debug(String.valueOf(message));
    }
    
    @Override
    public final void debug(Object message, Throwable t) {
    	logger.debug(String.valueOf(message),t);
    }
    
    @Override
    public final void trace(Object message) {
    	logger.trace(String.valueOf(message));
    }
    
    @Override
    public final void trace(Object message, Throwable t) {
    	logger.trace(String.valueOf(message),t);
    }
    
    @Override
    public final void info(Object message) {
    	logger.info(String.valueOf(message));
    }
    
    @Override
    public final void info(Object message, Throwable t) {        
    	logger.info(String.valueOf(message),t);
    }
    
    @Override
    public final void warn(Object message) {
    	logger.warn(String.valueOf(message));
    }
    
    @Override
    public final void warn(Object message, Throwable t) {
    	logger.warn(String.valueOf(message),t);
    }
    
    @Override
    public final void error(Object message) {
    	logger.error(String.valueOf(message));
    }
    
    @Override
    public final void error(Object message, Throwable t) {
    	logger.error(String.valueOf(message),t);
    }
    
    @Override
    public final void fatal(Object message) {
    	logger.fatal(String.valueOf(message));
    }
    
    @Override
    public final void fatal(Object message, Throwable t) {
    	logger.fatal(String.valueOf(message),t);
    }    

    // from commons logging. This would be my number one reason why java.util.logging
    // is bad - design by committee can be really bad ! The impact on performance of 
    // using java.util.logging - and the ugliness if you need to wrap it - is far
    // worse than the unfriendly and uncommon default format for logs. 
    
//    private void log(Level level, String msg, Throwable ex) {
//        if (logger.isLoggable(level)) {
//            // Hack (?) to get the stack trace.
//            Throwable dummyException=new Throwable();
//            StackTraceElement locations[]=dummyException.getStackTrace();
//            // Caller will be the third element
//            String cname = "unknown";
//            String method = "unknown";
//            if (locations != null && locations.length >2) {
//                StackTraceElement caller = locations[2];
//                cname = caller.getClassName();
//                method = caller.getMethodName();
//            }
//            if (ex==null) {
//                logger.logp(level, cname, method, msg);
//            } else {
//                logger.logp(level, cname, method, msg, ex);
//            }
//        }
//    }        

    // for LogFactory
    static void release() {
        
    }
    
    static Log getInstance(String name) {
        return new DirectJDKLog( name );
    }
}


