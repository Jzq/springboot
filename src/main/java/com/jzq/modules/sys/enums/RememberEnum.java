package com.jzq.modules.sys.enums;

public enum RememberEnum {

    RememberMe("RememberMe","true");

    private String code;
    private String value;

    RememberEnum(String code, String value){
        this.code = code;
        this.value = value;
    }

    public static RememberEnum getValueByCode(String code){

        if(null != code){
            for (RememberEnum srmReportStatusEnum : RememberEnum.values()) {
                if(srmReportStatusEnum.getCode().equals(code)){
                    return srmReportStatusEnum;
                }
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
		this.value = value;
	}
	
}
