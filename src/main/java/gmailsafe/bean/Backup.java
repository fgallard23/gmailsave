package gmailsafe.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Backup {

	private String backupId;
	private Date date;
	private String status;

	public Backup() {

	}

	public Backup(String backupId, Date date, String status) {
		this.backupId = backupId;
		this.date = date;
		this.status = status;
	}

	// backup
	public String getBackupId() {
		return backupId;
	}

	public void setBackupId(String backupId) {
		this.backupId = backupId;
	}

	// local date
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// status
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
