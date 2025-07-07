// 사용자 관리 목록 페이지

// 정렬 카테고리 - 선택한 정렬에 따라 select 여백 조정
$("#SELECT-SORT").click(function() {
  let selectValue = $("#SELECT-SORT");
  
  if (selectValue.val() == "kakao") {
    selectValue.css("padding", "0 25px");
  } else {
    selectValue.removeAttr("style");
  }
});

// --------------------------------------------------------------- 

// 상세 페이지 이동
$(document).on("click", ".go-detail", function(e) {
  e.stopPropagation();
  const userNumber = $(this).data("user");
  location.href = "admin-user-view.html";
});

// --------------------------------------------------------------- 

// 사용자 상세 페이지

// 모달 또는 링크 연결 -  게시글 상세, 댓글 상세, 건의사항 상세
$(document).on("click", ".go-type-page", function() {
  const type = $(this).data("type");
  const typeId= $(this).data("id");
  let url = "";

  switch(type) {
    case "board" :
      url = "../community/community-view.html";
      break;
    case "comment" :
      const boardNumber = $(this).find(".span-board").text();
      console.log(boardNumber);
      openCommentModal(typeId, boardNumber);
      return;
    case "feedback" :
      url = "admin-feedback-view.html";
      break;
    default :
      openModal("지원하지 않는 유형입니다.");
      return;
  }

  location.href=url;
});

// 상세 페이지 이동
$(document).on("click", ".span-board-number", function() {
  const boardNumber = $(this).children(".span-board").text();
  location.href = "../community/community-view.html";
});

// 댓글 상세 보기
function openCommentModal(commentNumber, boardNumber, modalId = "#MODAL-ALERT-ONE-A") {
  const $modal = $(modalId);
  const $alertWrap = $(".div-alert-wrap");
  const commentFrame = `
    <div id="DIV-MODAL-HEADER">
      <div class="div-modal-title">[게시글 131] 에 달린 댓글</div>
      <img src="../../image/layout/close_btn_grey.png" class="modal-close modal-close-img" alt="close">
    </div>
    <div class="div-alert-content">
      <div class="div-comment">
        사진 진짜 좋네요. 뭔가 말 안 해도 오늘 하루가 어땠을지 느껴져서 괜히 같이 조용해졌어요.<br>
        저도 가끔 그런 날 있거든요. 말없이 조명만 켜놓고, 그냥 멍하게 있는 거요.<br>괜히 위로 받고 가요. 고마워요.
      </div>
    </div>
    <div class="div-alert-btn-wrap">
      <div class="button-style btn-full-blue delete-comment" data-comment="2" data-parent="3">댓글 삭제</div>
    </div>
  `;

  $alertWrap.empty();
  $alertWrap.html(commentFrame);
  $modal.addClass("alert-active modal-comment").fadeIn(200);
}

$(document).on("click", ".delete-comment", function() {
  // 댓글 삭제 버튼 클릭 시 (admin-comment.js)
  const commentNumber = $(this).data("comment");
  const parentCommentNumber = $(this).data("parent");
  deleteComment(commentNumber, parentCommentNumber);
});

// --------------------------------------

// 건의사항 상태 별 스타일 및 문구 구별
function updateStatus() {
  $(".td-status").each(function () {
    const status = $(this).data("status");
    let statusValue = "";
    let statusColor = "";

    if (status === 0) {
      statusValue = "처리 전"
      statusColor = "#999999"
    } else if (status === 1) {
      statusValue = "진행 중"
      statusColor = "#E96B8A "
    } else if (status === 2) {
      statusValue = "완료"
      statusColor = "#3B7A57 "
    } else if (status === 3) {
      statusValue = "보류"
      statusColor = "#6366F1  "
    }

    $(this).text(statusValue);
    $(this).css("color", statusColor);
  });
}

// 건의사항 상태 출력
$(document).ready(function () {
  updateStatus();
})

// --------------------------------------------------------------- 

// 탈퇴 클릭 시
$(document).on("click", ".delete-btn", function(e) {
  const deleteUserMsg = "해당 사용자를 탈퇴 처리하면, 작성한 게시글, 댓글, 스크랩 등 모든 활동 기록이 삭제됩니다.<br>작성자의 닉네임 및 프로필 정보 역시 복구할 수 없으며,<br>댓글 중 대댓글이 달린 항목은 순차적으로 삭제됩니다.<br><br>정말 탈퇴를 진행하시겠습니까?";
  const deleteOkUserMsg = "탈퇴 처리되었습니다.";
  e.stopPropagation();
  const userNumber = $(this).closest(".userNumber").data("user");

  openModal(deleteUserMsg, 2).then((result) => {
    if (result) {
      setTimeout(() => {
        openModal(deleteOkUserMsg);
        // location.href = "탈퇴경로";
      }, 50);
    }
  });
});