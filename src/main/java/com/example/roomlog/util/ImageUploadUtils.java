package com.example.roomlog.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploadUtils {
	
	// application.properties(또는 application.yml)에 저장해둔 file.dir 프로퍼티 값을 가져와서 아래의 필드에 넣어줌
	@Value("${file.dir}")
	private String fileDir;
	
	// 지정한 폴더에 이미지 저장 및 이미지명, 경로 등 반환
	public Map<String, Object> formatImage(String type, MultipartFile image) throws IllegalStateException, IOException {
		String originalImgName = image.getOriginalFilename();
		UUID uuid = UUID.randomUUID();
		String imgUuid = uuid.toString() + "_" + originalImgName;
		
		String setfileDir = type + "/" + getUploadDate();
		File uploadPath = new File(fileDir, setfileDir);
		
		// 경로가 존재하지 않는다면 필요한 모든 폴더 생성
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		File uploadImg = new File(uploadPath, imgUuid);
		
		// 매개변수로 받은 Multipart 객체에 담긴 이미지를 우리가 만든 경로와 이름으로 저장
		image.transferTo(uploadImg);
		
		Map<String, Object> result = new HashMap<>();
		result.put("uploadImg", uploadImg);
		result.put("uploadPath", uploadPath);
		result.put("imgUuid", imgUuid);
		result.put("originalImgName", originalImgName);
		result.put("setfileDir", setfileDir);
		
		return result;
	}

	// 이미지 업로드시 업로드 날짜별 이미지 저장
    private static String getUploadDate(){
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

}
