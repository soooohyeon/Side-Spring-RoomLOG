// 해시태그 DB 값 불러오기
const communityId = new URLSearchParams(window.location.search).get("communityId");

fetch(`/hashtag/selectAll/${communityId}`)
.then(res => res.json())
.then(tagList => {
	tagify.addTags(tagList);
});

// --------------------------------------------------------------- 

// 삭제한 이미지의 번호를 담은 배열을 input 태그에 담아 전송
function updateDeletedImage() {
	const $deleteImgInput = $('<input type="text" name="deleteImgId" hidden>');
	$deleteImgInput.val(deletedImageIds.join(","));
	$(".img-regist-btn").append($deleteImgInput);
}

// --------------------------------------------------------------- 

// 글 수정 버튼 클릭 시
const $postForm= $("#postForm");
const editPostMsg = "게시글을 수정하시겠습니까?<br>수정된 내용은 곧바로 반영됩니다.";

$(document).on("click", "#EDIT-BTN", function() {
  openModal(editPostMsg, 2).then((result) => {
    if (result) {
	  if (deletedImageIds.length > 0) {
		updateDeletedImage();
	  }
	  submitHashtag();
	  tinymce.triggerSave();
	  $postForm.submit();
    }
  });
});

// --------------------------------------------------------------- 

// 값이 수정된 상태일 때 뒤로가기나 페이지 이동 클릭 시 모달 띄우는 메세지 - 동작은 community/basic-write.js
const moveMsg = "수정 중인 내용이 저장되지 않습니다.<br>정말 이동하시겠습니까?";

// --------------------------------------------------------------- 
