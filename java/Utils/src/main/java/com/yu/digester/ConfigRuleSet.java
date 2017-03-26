package com.yu.digester;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.RuleSetBase;

public class ConfigRuleSet extends RuleSetBase { // 注意，需要继承RuleSetBase
	public void addRuleInstances(Digester digester) {
		digester.setValidating(false);
		//当遇到<web>时创建一个WebConfig对象，并将其放在栈顶
		digester.addObjectCreate("web", WebConfig.class);
		//根据<web>元素的属性(attribute)，对刚创建的WebConfig对象的属性(property)进行设置
		digester.addSetProperties("web");

		//当遇到<web>的子元素<root>时创建一个Root对象，并将其放在栈顶
		digester.addObjectCreate("web/root", Root.class);
		//根据<root>元素的属性(attribute)，对刚创建的Root对象的属性(property)进行设置
		digester.addSetProperties("web/root");
		//当再次遇到<web>的子元素<root>时创建一个Root对象，并将其放在栈顶，同时调用第二栈顶元素(WebConfig对象)的addBar方法。
		digester.addSetNext("web/root", "addRoot");

		//当遇到<root>的子元素<bar>时创建一个Bar对象，并将其放在栈顶
		digester.addObjectCreate("web/root/bar", Bar.class);
		//根据<bar>元素的属性(attribute)，对刚创建的Bar对象的属性(property)进行设置
		digester.addSetProperties("web/root/bar");
		//当再次遇到<root>的子元素<bar>时创建一个Bar对象，并将其放在栈顶，同时调用第二栈顶元素(Root对象)的addBar方法。
		digester.addSetNext("web/root/bar", "addBar");
	}
}