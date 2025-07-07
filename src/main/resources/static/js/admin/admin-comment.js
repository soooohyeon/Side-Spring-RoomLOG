// 댓글 내용 전체 보기/숨기기
$(document).on("click", ".comment-content", function() {
  const commentWrap = $(this).closest(".go-page");

  commentWrap.hasClass("comment-active")
    ? hideComment($(this))
    : showComment($(this));
});

// 댓글 내용 전체 보여주기
function showComment(element) {
  const commentWrap = element.closest(".go-page");
  const commentContent = element.children(".comment-text").html();
  const $arrow = element.find("img");
  const $commentAllFrame = $(`
    <tr class="comment-all">
    <td colspan="6"><div class="comment-slide-wrap">${commentContent}</div></td>
    </tr>
  `);
  
  $arrow.addClass("rotate");
  commentWrap.addClass("comment-active");
  commentWrap.after($commentAllFrame);
  $commentAllFrame.find(".comment-slide-wrap").slideDown(200);
}

// 댓글 내용 숨기기
function hideComment(element) {
  const commentWrap = element.closest(".go-page");
  const $arrow = element.find("img");
  const $nextRow = commentWrap.next(".comment-all");
  const $slideWrap = $nextRow.find(".comment-slide-wrap");

  $arrow.removeClass("rotate");
  $slideWrap.slideUp(200, function() {
    $nextRow.remove();
  });
  commentWrap.removeClass("comment-active");
}

// ---------------------------------------------------------------

// 게시글 상세 페이지 이동
$(document).on("click", ".board-number", function() {
  const boardNumber = $(this).data("board");
  location.href = "../../html/community/community-view.html";
});

// ---------------------------------------------------------------

// 댓글 삭제
function deleteComment(commentNumber, parentCommentNumber) {
  const deleteCommentMsg = parentCommentNumber === null
    ? "해당 댓글을 삭제하시겠습니까?<br>삭제된 댓글은 복구할 수 없습니다."
    : "해당 댓글에 대댓글이 달려 있어 즉시 삭제할 수 없습니다.<br>댓글은 ‘삭제된 댓글입니다’로 표시되며, 모든 대댓글이 삭제된 이후 자동으로 삭제됩니다.<br><br>정말 삭제하시겠습니까?";
  const deleteOkCommentMsg = parentCommentNumber === null
    ? "댓글이 삭제 되었습니다."
    : "삭제가 신청되었습니다.<br>댓글은 ‘삭제된 댓글입니다’로 표시되며, 순차적으로 삭제됩니다.";
  
  $("#MODAL-ALERT-ONE-A").removeClass("modal-comment");
  openModal(deleteCommentMsg, 2).then(result => {
    if (result) {
      setTimeout(() => {
        openModal(deleteOkCommentMsg);
        // location.href = "탈퇴경로";
      }, 50);

    }
  });
}

$(document).on("click", ".delete-btn", function() {
  const commentNumber = $(this).data("comment");
  const parentCommentNumber = $(this).data("parent");
  deleteComment(commentNumber, parentCommentNumber);
});