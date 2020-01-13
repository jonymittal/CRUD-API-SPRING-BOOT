package com.demo.ServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.Service.IMultipartFileService;

@Service
public class MultipartFileService implements IMultipartFileService {

	@Override
	public String saveUserImage(MultipartFile file, Long id) {
		try {
			String dir = "Demo/resources/images/user";
			String path = "images/user";

			if (!new File(dir).exists()) {
				new File(dir).mkdirs();
			}

			String fileName = file.getOriginalFilename();

			String ext = FilenameUtils.getExtension(fileName);
			fileName = "user_" + id + "." + ext;

			InputStream fileContent = file.getInputStream();
			OutputStream outputStream = new FileOutputStream(new File(dir + "/" + fileName));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = fileContent.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			String n = path + "/" + fileName;
			outputStream.close();
			fileContent.close();
			return n;
		} catch (Exception e) {
			return null;
		}
	}

}
