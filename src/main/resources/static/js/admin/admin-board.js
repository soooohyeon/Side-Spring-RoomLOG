// 커뮤니티 게시글 관리 목록 페이지

// 정렬 카테고리 - 선택한 정렬에 따라 select 여백 조정
$("#SELECT-SORT").click(function() {
  let selectValue = $("#SELECT-SORT");
  
  if (selectValue.val() == "comment") {
    selectValue.css("padding", "0 15px");
  } else if (selectValue.val() == "scrap") {
    selectValue.css("padding", "0 33px");
  } else {
    selectValue.removeAttr("style");
  }
});

// ---------------------------------------------------------------

// 상세 페이지 이동
$(document).on("click", ".go-detail", function() {
  const boardNumber = $(this).data("board");
  location.href = "../../html/community/community-view.html";
});

// --------------------------------------------------------------- 

// 삭제 클릭 시
$(document).on("click", ".delete-btn", function(e) {
  const deleteBoardMsg = "해당 게시글을 삭제하시겠습니까?<br>삭제 시 댓글을 포함한 모든 내용이 완전히 삭제되며, 복구할 수 없습니다.";
  const deleteOkBoardMsg = "게시글이 삭제되었습니다.";
  e.stopPropagation();
  const boardNumber = $(this).closest(".go-detail").data("board");

  openModal(deleteBoardMsg, 2).then((result) => {
    if (result) {
      setTimeout(() => {
        openModal(deleteOkBoardMsg);
        // location.href = "탈퇴경로";
      }, 50);
    }
  });
});