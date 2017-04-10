package com.yu.service.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration({
    "classpath:spring-context.xml"
})
public class BaseSpringTestCase extends AbstractJUnit4SpringContextTests{
	public static final Log log = LogFactory.getLog(MyServiceTest.class);
	
}
