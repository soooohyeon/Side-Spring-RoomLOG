package com.example.roomlog.service.community;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.domain.community.Community;
import com.example.roomlog.domain.community.image.CommunityImg;
import com.example.roomlog.dto.community.image.CommunityImgDTO;
import com.example.roomlog.repository.community.image.CommunityImgRepository;
import com.example.roomlog.util.ImageUploadUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Service
@RequiredArgsConstructor
public class CommunityImgService {
	
	private final CommunityImgRepository communityImgRepository;
	private final ImageUploadUtils imageUploadUtils;
	
	// application.properties(또는 application.yml)에 저장해둔 file.dir 프로퍼티 값을 가져와서 아래의 필드에 넣어줌
	@Value("${file.dir}")
	private String fileDir;

	// 커뮤니티에서 이미지 등록 또는 수정 전 형식변환하여 CommunityImg 객체에 담아 반환
	public CommunityImg formatCommunityImg(Community community, MultipartFile image) throws IOException {
		Map<String, Object> img = imageUploadUtils.formatImage("community", image);
		
		// 썸네일 이미지 별도 저장
        Thumbnails.of((File) img.get("uploadImg"))
        .size(350, 280)
        .toFile(new File((File) img.get("uploadPath"), "th_" + img.get("imgUuid")));

        CommunityImg communityImg = CommunityImg.builder()
			.community(community)
			.communityImgOriginal((String)img.get("originalImgName"))
			.communityImgUuid((String) img.get("imgUuid"))
			.communityImgPath((String) img.get("setfileDir"))
			.build();
		
		return communityImg;
	}
	
	// 폴더에 저장된 이미지 삭제 후 DB에 저장된 이미지 삭제
	public void deleteCommunityImg(long imageId) {
		CommunityImgDTO communityImgDTO = communityImgRepository.findCommunityImgByImageId(imageId);
		deleteCommunityImgFile(communityImgDTO);
		communityImgRepository.deleteByCommunityId(imageId);
	}
	
	// 폴더에 저장된 이미지 삭제
	public void deleteCommunityImgFile(CommunityImgDTO img) {
		File image = new File(fileDir, img.getCommunityImgPath() + "/" + img.getCommunityImgUuid());
		File thumbnail = new File(fileDir, img.getCommunityImgPath() + "/th_" + img.getCommunityImgUuid());
		
		if (image.exists()) {
			image.delete();
		}
		if (thumbnail.exists()) {
			thumbnail.delete();
		}
	}
}
