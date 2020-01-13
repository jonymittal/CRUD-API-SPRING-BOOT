package com.demo.Service;

import org.springframework.web.multipart.MultipartFile;

public interface IMultipartFileService {
	
	String saveUserImage(MultipartFile file,Long id);

}
