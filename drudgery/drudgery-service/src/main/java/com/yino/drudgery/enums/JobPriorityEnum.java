package com.yino.drudgery.enums;

public enum JobPriorityEnum {
	Low(1),
	Normal(4),
	Hight(7);
	
	private int num;
	public int getNum() {
		return num;
	}
	
	private JobPriorityEnum(int value){
		this.num = value;
	}
	
	public static JobPriorityEnum valueOf(int val) {
		for (JobPriorityEnum jobPriorityEnum : JobPriorityEnum.values()) {
			if (jobPriorityEnum.num == val) {
				return jobPriorityEnum;
			}
		}
		
		return null;
	}
}
