package com.yu.digester;

import java.io.File;
import java.io.IOException;

import org.apache.commons.digester3.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

public class ConfigTest {
	
	
	public static void main(String[] args) {
		
		
		final String fileName = "src/main/java/com/yu/digester/webconfig.xml";
		WebConfig config = new WebConfig();
		Digester digester = new Digester();
		ConfigRuleSet rule = new ConfigRuleSet();
		rule.addRuleInstances(digester);
		try {
			config = (WebConfig) digester.parse(new File(fileName));
			System.out.println("roots size :" + config.getSize());
			Root[] roots = config.getRoots();
			for (int i = 0; i < roots.length; i++) {
				System.out.println("root name:" + roots[i].getName());
				Bar[] bars = roots[i].getBars();
				for (int j = 0; j < bars.length; j++) {
					if (j > 0)
						System.out.println("/t------------------");
					System.out.println("/tbar id:" + bars[j].getId());
					System.out.println("/tbar title:" + bars[j].getTitle());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
}