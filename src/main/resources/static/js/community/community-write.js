// 글 등록 버튼 클릭 시
const $postForm= $("#postForm");
const writePostMsg = "게시글을 업로드하시겠습니까?<br>다른 사용자들에게 곧바로 보여집니다.";

$(document).on("click", "#WRITE-BTN", function() {
  openModal(writePostMsg, 2).then((result) => {
    if (result) {
	  submitHashtag();
	  tinymce.triggerSave();
	  $postForm.submit();
    }
  });
});

// --------------------------------------------------------------- 

// 값이 입력된 상태일 때 뒤로가기나 페이지 이동 클릭 시 모달 띄우는 메세지 - 동작은 community/basic-write.js
const moveMsg = "작성 중인 내용이 저장되지 않습니다.<br>정말 이동하시겠습니까?";

// --------------------------------------------------------------- 
