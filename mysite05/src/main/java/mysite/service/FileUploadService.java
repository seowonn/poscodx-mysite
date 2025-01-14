package mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySource("classpath:mysite/config/web/fileupload.properties")
public class FileUploadService {

	@Autowired
	private Environment env;

	public String restore(MultipartFile file) throws RuntimeException {

		try {
			File uploadDirectory = new File(env.getProperty("fileupload.uploadLocation"));
			if (!uploadDirectory.exists() && !uploadDirectory.mkdir()) {
				return null;
			}

			if (file.isEmpty()) {
				return null;
			}

			String originalFilename = Optional.ofNullable(file.getOriginalFilename())
					.orElse("");
			String extName = originalFilename
					.substring(originalFilename.lastIndexOf('.') + 1);
			String savedFilename = generateSaveFilename(extName);
			long fileSize = file.getSize();

			System.out.println("######" + originalFilename);
			System.out.println("######" + savedFilename);
			System.out.println("######" + fileSize);

			byte[] data = file.getBytes();

			OutputStream os = new FileOutputStream(
					env.getProperty("fileupload.uploadLocation") + "/" + savedFilename);
			os.write(data);
			os.close();

			return env.getProperty("fileupload.resourceUrl") + "/" + savedFilename;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String generateSaveFilename(String extName) {
		Calendar calendar = Calendar.getInstance();

		return "" + calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH)
				+ calendar.get(Calendar.DATE) + calendar.get(Calendar.HOUR)
				+ calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND)
				+ calendar.get(Calendar.MILLISECOND);
	}
}
