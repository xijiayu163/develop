package com.yino.drudgery.enums;

public enum TriggerTypeEnum {

	DataQuery(0),
	CronTime(1),
	denpendency(2);
	
	private int num;
	public int getNum() {
		return num;
	}
	
	private TriggerTypeEnum(int value){
		this.num = value;
	}
	
	public static TriggerTypeEnum valueOf(int val) {
		for (TriggerTypeEnum triggerTypeEnum : TriggerTypeEnum.values()) {
			if (triggerTypeEnum.num == val) {
				return triggerTypeEnum;
			}
		}
		
		return null;
	}
}
