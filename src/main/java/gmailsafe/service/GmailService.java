package gmailsafe.service;

import java.util.Collection;

import gmailsafe.bean.Backup;

public interface GmailService {

	public String backup();
	public Collection<Backup> getLisBackups();	
	public void exportBackup();
	public void exportPartBackup();	
}
