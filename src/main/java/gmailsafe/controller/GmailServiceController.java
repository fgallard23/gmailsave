package gmailsafe.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import gmailsafe.bean.Backup;
import gmailsafe.service.GmailService;

@RestController
public class GmailServiceController {

	@Autowired
	GmailService gmailService;

	/***
	 * Backup Method @Async
	 * 
	 * @return
	 */
	@PostMapping(value = "/backups")
	public ResponseEntity<String> backupPost() {
		// headers json
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// object json
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("backupId", gmailService.backup());
		return new ResponseEntity<String>(jsonObject.toString(), headers, HttpStatus.OK);
	}

	/***
	 * List backups in In progress OK Failed
	 * 
	 * @return
	 */
	@GetMapping(value = "/backups", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Backup>> backupGet() {
		Boolean flag = true;
		try {
			if (flag)
				return new ResponseEntity<Collection<Backup>>(gmailService.getLisBackups(), HttpStatus.OK);
			else
				return new ResponseEntity<Collection<Backup>>(HttpStatus.PROCESSING);

		} catch (Exception e) {
			return new ResponseEntity<Collection<Backup>>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	/***
	 * This API will return the content of a specified backup in a compressed archive.
	 * 
	 * @param backupid
	 * @param response
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@GetMapping(value = "/exports/{backupid}", produces = "application/zip")
	public void zipFiles(@PathVariable("backupid") String backupid, HttpServletResponse response) throws IOException, URISyntaxException {

		// setting headers
		response.setStatus(HttpServletResponse.SC_OK);
		response.addHeader("Content-Disposition", "attachment; filename=\"" + backupid + ".zip\"");
		
		// URI file
		URL resource = getClass().getClassLoader().getResource("Readme.txt");
		// pointer file 
	    File file = new File(resource.toURI());
		
	    // response output stream
		ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
		FileInputStream fileInputStream = new FileInputStream(file);
		zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
		// 
		IOUtils.copy(fileInputStream, zipOutputStream);
		//close objects
		fileInputStream.close();
		zipOutputStream.closeEntry();
		zipOutputStream.close();
	}

}
