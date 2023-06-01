package todo;

import java.time.LocalDate;

public class Task {
	
	private String name;
	private String expireDate;
	private Status statusType;
	
	public Task(String name, String expireDate, Status statusType) {
		super();
		this.name = name;
		this.expireDate = expireDate;
		this.statusType = statusType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public Status getStatusType() {
		return statusType;
	}
	public void setStatusType(Status statusType) {
		this.statusType = statusType;
	}
	
	

}
