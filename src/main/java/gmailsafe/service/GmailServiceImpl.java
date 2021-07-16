package gmailsafe.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import gmailsafe.bean.Backup;

@Service 
public class GmailServiceImpl implements GmailService {

	@Autowired
	Backup backup;
	
	@Async
	@Override
	public String backup() {
		backup.setBackupId("backupId01");
		return backup.getBackupId();
	}

	@Override
	public Collection<Backup> getLisBackups() {
		Collection<Backup> backups = Arrays.asList(
				new Backup("backupId01", new Date(), "In Progress"),
				new Backup("backupId02", new Date(), "OK"), 
				new Backup("backupId03", new Date(), "Failed"));

		return backups;
	}

	@Override
	public void exportBackup() {
		
	}

	@Override
	public void exportPartBackup() {
		
	}	
}
