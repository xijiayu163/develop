package com.yu.digester;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.RuleSetBase;

public class ConfigRuleSet extends RuleSetBase { // ע�⣬��Ҫ�̳�RuleSetBase
	public void addRuleInstances(Digester digester) {
		digester.setValidating(false);
		//������<web>ʱ����һ��WebConfig���󣬲��������ջ��
		digester.addObjectCreate("web", WebConfig.class);
		//����<web>Ԫ�ص�����(attribute)���Ըմ�����WebConfig���������(property)��������
		digester.addSetProperties("web");

		//������<web>����Ԫ��<root>ʱ����һ��Root���󣬲��������ջ��
		digester.addObjectCreate("web/root", Root.class);
		//����<root>Ԫ�ص�����(attribute)���Ըմ�����Root���������(property)��������
		digester.addSetProperties("web/root");
		//���ٴ�����<web>����Ԫ��<root>ʱ����һ��Root���󣬲��������ջ����ͬʱ���õڶ�ջ��Ԫ��(WebConfig����)��addBar������
		digester.addSetNext("web/root", "addRoot");

		//������<root>����Ԫ��<bar>ʱ����һ��Bar���󣬲��������ջ��
		digester.addObjectCreate("web/root/bar", Bar.class);
		//����<bar>Ԫ�ص�����(attribute)���Ըմ�����Bar���������(property)��������
		digester.addSetProperties("web/root/bar");
		//���ٴ�����<root>����Ԫ��<bar>ʱ����һ��Bar���󣬲��������ջ����ͬʱ���õڶ�ջ��Ԫ��(Root����)��addBar������
		digester.addSetNext("web/root/bar", "addBar");
	}
}