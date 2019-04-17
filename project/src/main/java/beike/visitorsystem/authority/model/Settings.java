package beike.visitorsystem.authority.model;

import java.math.BigInteger;

public class Settings {
	private BigInteger id;
	private String settingName;
	private String settingCode;
	private String value;
	private String updateTime;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getSettingName() {
		return settingName;
	}
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}
	public String getSettingCode() {
		return settingCode;
	}
	public void setSettingCode(String settingCode) {
		this.settingCode = settingCode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
