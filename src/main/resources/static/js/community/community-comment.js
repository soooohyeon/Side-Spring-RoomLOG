// ---------------------------------------------------------------
// 댓글

// 답글 버튼 호버시
const $reCommentBtn = $(".div-re-comment-btn");

$reCommentBtn.hover(function() {
  const $img = $(this).children("img");
  const src = $img.attr("src");
  
  // hover 이미지로 바꾸기
  if (src.includes("re_comment_btn.png")) {
    $img.attr("src", "/image/community/re_comment_btn_hover.png");
  }
}, function () {
  const $img = $(this).children("img");
  const src = $img.attr("src");

  // hover에서 벗어날 때 다시 원래 이미지로 복귀
  if (src.includes("re_comment_btn_hover.png")) {
    $img.attr("src", "/image/community/re_comment_btn.png");
  }
});

// ---------------------------------------------------------------

// 댓글 입력시 줄 수에 따라 입력칸 높이 자동 조절
function setResizeTextArea(textarea) {
  if (!textarea) return;

  textarea.style.height = 'auto';
  textarea.style.height = textarea.scrollHeight + 'px';
}

// 값 입력시 자동 조절 함수 호출
['keyup', 'input'].forEach(evt => {
  document.addEventListener(evt, (e) => {
    if (e.target.tagName === 'TEXTAREA') {
      setResizeTextArea(e.target);
    }
  });
});

// ---------------------------------------------------------------

$(document).ready(function() {
  // 새 댓글 입력 칸
  const $comment = $("#TEXTAREA-COMMENT-TXT");
  
  // 로그아웃 상태면 textarea 비활성화
  if (userNumber == null) {
    $comment.on("focus", function (e) {
      openModal("로그인 후 이용해 주세요.");
    });
  }
  // 실시간 글자 수 표시, 등록 버튼 활성화
  $comment.on("change input", countComent);

  // 댓글 수정 취소 함수 호출
  updateCancel();
});

// --------------------------------

// 새 댓글 작성 시 - 실시간 글자 수 표시, 등록 버튼 활성화
const $saveBtn = $(".btn-comment-save");
let checkRegist = false;

function countComent() {
  const $commentCount = $("#SPAN-COMMENT-COUNT");

  // 이모지 포함 정확한 길이 계산
  let content = $(this).val();
  let trimmed = Array.from(content).slice(0, 200).join("");

  // 200자까지만 입력
  if (content !== trimmed) {
    $(this).val(trimmed);
    content = trimmed;
  }

  const nowLength = Array.from(content).length;
  // 글자수 표시
  $commentCount.text(nowLength);
  
  // 1자 이상 입력 시 클래스 부여
  if (nowLength > 0) {
    $saveBtn.addClass("btn-active");
    $saveBtn.attr("type", "submit");
	checkRegist = true;
  } else {
    $saveBtn.removeClass("btn-active");
	checkRegist = false;
  }
}

$saveBtn.on("click", function() {
	if (checkRegist) {
		openModal(commentRegistMsg, 2).then((result) =>	{
			if (result) {
				const commentContent = $(".txt-comment-content").val();
				const communityId = $("input[name='communityId'").val();
					
				saveComment(commentContent, communityId);
			}
		});
	}
});

// ---------------------------------------------------------------

// 댓글 등록
const commentRegistMsg = "댓글을 등록하시겠습니까?";
function saveComment(commentContent, communityId, parentCommentId=null) {
	fetch ("/comment/comment-registOk", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			communityId: communityId,
			commentContent: commentContent,
			parentCommentId: parentCommentId
		})
	})
	.then(response => {
		if (!response.ok) throw new Error("등록 실패");
		return response.json();
	})
	.then((result) => {
		openModal(result.msg);
		// 예: 댓글 목록 동적 추가 또는 reload
		location.reload();
	})
	.catch(() => {
		openModal(errMsg);
	});
}

// ---------------------------------------------------------------

// 200자 제한, 내용 존재 여부 검사
function isValidComment($textarea, maxLength = 200) {
  let comment = $textarea.val();
  const trimmed = Array.from(comment).slice(0, maxLength).join("");
  
  if (comment !== trimmed) {
    $textarea.val(trimmed);
    comment = trimmed;
  }
  
  return Array.from(comment).length > 0;
}

// 대댓글 작성 버튼(답글 버튼) 클릭 시 
$(document).on("click", "#RE-COMMENT-WRITE-BTN", function() {
  const $reComment = $(this).closest("form").find("#TEXTAREA-RE-COMMENT-TXT");
  const result = isValidComment($reComment);

  if (result) {
    console.log("댓글 저장");
  } else {
    openModal("댓글 내용을 입력해 주세요.");
  }
});

// 댓글 수정 버튼 클릭 시
$(document).on("click", "#COMMENT-UPDATE-BTN", function() {
  const $commentEdit = $(this).closest(".div-comment-update-wrap").find("#TEXTAREA-RE-COMMENT-TXT");
  const result = isValidComment($commentEdit);

  if (result) {
    console.log("댓글 수정");
  } else {
    openModal("댓글 내용을 입력해 주세요.");
  }
});

// ---------------------------------------------------------------

// 현재 열린 댓글 폼이 있는지 확인
let reCommentCheck = false;
const cancelRecommentMsg = "작성 중인 댓글은 저장되지 않습니다.<br>정말 취소할까요?";
const changeRecommentMsg = "수정 중인 댓글은 저장되지 않습니다.<br>정말 취소할까요?";

// 답글 버튼 클릭 시 입력 폼 생성
$(document).on("click", ".div-re-comment-btn", function() {
  // 누른 답글 버튼의 댓글을 감싸는 마지막 div
  const $divWrap = $(this).closest(".div-parent-comment");
  // 대댓글 입력 폼
  const reCommentForm = `
    <div class="div-comment-wrap div-re-comment-write-wrap">
      <form action="">
        <div class="div-re-comment-write">
          <textarea name="commentContent" class="text-re-content-txt" placeholder="댓글에 답글을 남겨보세요."></textarea>
        </div>
        <div class="div-comment-btn-wrap textarea-btn-wrap">
            <div class="div-comment-btn div-menu-line"><span id="RE-COMMENT-WRITE-BTN">등록</span></div>
            <div class="div-comment-btn"><span id="RE-COMMENT-CANCEL-BTN">취소</span></div>
        </div>
      </form>
    </div>
  `;

  console.log($currentEditComment);
  if ($currentEditComment) {
    // 이미 수정 중인 댓글이 있다면
    openModal(changeRecommentMsg, 2).then((result) => {
      if (result) {
        const oriComment = $currentEditComment.data("original-text");
        const className = $currentEditComment.attr("class");
        
        // 클래스명으로 댓글, 대댓글 구분
        const type = className.includes("parent-comment-wrap") ? "comment" : "reComment";
        // 댓글 수정 취소
        renderOriginalComment($currentEditComment, oriComment, type);
        // 대댓글 입력 폼 생성
        $divWrap.after(reCommentForm);
        reCommentCheck = true;
      }
    });
  } else if (reCommentCheck) {
    // 다른 입력 폼이 있을 경우 삭제
    openModal(cancelRecommentMsg, 2).then((result) => {
      if (result) {
        $(".div-re-comment-write-wrap").remove();
        $divWrap.after(reCommentForm);
      }
    });
  } else {
    $divWrap.after(reCommentForm);
    reCommentCheck = true;
  }
});


// 취소 버튼 클릭시 입력 폼 삭제
$(document).on("click", "#RE-COMMENT-CANCEL-BTN", function() {
  const reCommentCount = $(this).closest("form").find("textarea").val().length;
  if (reCommentCount > 0) {
    openModal(cancelRecommentMsg, 2).then((result) => {
      if (result) {
        $(this).closest(".div-re-comment-write-wrap").remove();
        reCommentCheck = false;
      }
    });
    
  } else {
    $(this).closest(".div-re-comment-write-wrap").remove();
    reCommentCheck = false;
  }
});

// ---------------------------------------------------------------

// 현재 댓글 수정 폼이 있는지 확인
let $currentEditComment = null;
const currentEditCommentMsg = "이미 다른 댓글을 수정 중입니다.<br>현재 수정을 취소하고 이 댓글을 수정하시겠어요?";

// 댓글 수정 버튼 클릭 시
$(document).on("click", ".comment-update-btn", function() {
  $oriCommentWrap = $(this).closest(".div-comment-content-wrap");
  $oriComment = $oriCommentWrap.find(".div-comment-content");

  const oriCommentText = $oriComment.text().trim();
  const editFrame = `
            <div class="div-comment-update-wrap">
              <div class="div-comment-update">
                <textarea name="commentContent" class="text-re-content-txt">` + oriCommentText + `</textarea>
              </div>
              <div class="div-comment-btn-wrap textarea-btn-wrap">
                  <div class="div-comment-btn div-menu-line"><span id="COMMENT-UPDATE-BTN">등록</span></div>
                  <div class="div-comment-btn"><span id="COMMENT-UPDATE-CANCEL-BTN">취소</span></div>
              </div>
            </div>
  `;

  if (reCommentCheck) {
    openModal(cancelRecommentMsg, 2).then((result) => {
      if (result) {
        $(".div-re-comment-write-wrap").remove();
        reCommentCheck = false;
        
        // 속성에 원본 댓글 넣기
        $oriCommentWrap.data("original-text", oriCommentText);
        // 수정 폼으로 변경
        $oriCommentWrap.html(editFrame);
        // 최근 수정한 댓글 담아두기
        $currentEditComment = $oriCommentWrap;
      }
    });
  } else if ($currentEditComment && !$oriCommentWrap.is($currentEditComment)) {
    // 이미 수정 중인 댓글이 있다면
    // 최근 수정 중인 댓글에 담긴 값이 있거나 현재 누른 댓글과 일치하지 않는다면
    openModal(currentEditCommentMsg, 2).then((result) => {
      if (result) {
        const oriComment = $currentEditComment.data("original-text");
        const className = $currentEditComment.attr("class");
        
        // 클래스명으로 댓글, 대댓글 구분
        const type = className.includes("parent-comment-wrap") ? "comment" : "reComment";
        // 댓글 수정 취소
        renderOriginalComment($currentEditComment, oriComment, type);

        // 속성에 원본 댓글 넣기
        $oriCommentWrap.data("original-text", oriCommentText);
        // 수정 폼으로 변경
        $oriCommentWrap.html(editFrame);
        // 최근 수정한 댓글 담아두기
        $currentEditComment = $oriCommentWrap;
      }
    });
  } else {
    // 속성에 원본 댓글 넣기
    $oriCommentWrap.data("original-text", oriCommentText);
    // 수정 폼으로 변경
    $oriCommentWrap.html(editFrame);
    // 최근 수정한 댓글 담아두기
    $currentEditComment = $oriCommentWrap;
  }

});

// ---------------------------------------------------------------

// 댓글 수정 취소
function updateCancel() {
  $(document).on("click", "#COMMENT-UPDATE-CANCEL-BTN", function (){
    const $wrap = $(this).closest(".div-comment-content-wrap");
    const className = $wrap.attr("class");
    const oriComment = $wrap.data('original-text');
    
    // 클래스명으로 댓글, 대댓글 구분
    const type = className.includes("parent-comment-wrap") ? "comment" : "reComment";
    // 댓글 수정 취소
    renderOriginalComment($currentEditComment, oriComment, type);
  });
}

// 수정 취소한 댓글 화면에 다시 뿌리기
function renderOriginalComment(wrap, oriText, type) {
  // 공통 DOM 부분
  let oriSameStart = `
    <div class="div-re-comment-btn-wrap">
    <div class="div-comment-content">
      ${oriText}
    </div>
  `;
  let oriSameLast = `
    <div class="div-comment-btn-wrap">
      <div class="div-comment-btn div-menu-line"><span class="comment-update-btn">수정</span></div>
      <div class="div-comment-btn"><span>삭제</span></div>
    </div>
  `;
  // 댓글, 대댓글에 따라 다른 DOM 부분
  let oriOther = "";

  if (type === "comment") {
    oriOther = `
        <div class="div-re-comment-btn">
          <img src="/image/community/re_comment_btn.png">답글
        </div>
      </div>
    `;
  } else if (type === "reComment") {
    oriOther = `</div>`;
  }

  // 화면에 뿌리기
  wrap.html(`${oriSameStart}${oriOther}${oriSameLast}`);
  // 현재 댓글 수정 폼 변수 초기화
  $currentEditComment = null;
}

// ---------------------------------------------------------------

// 댓글 삭제 버튼 클릭 시
$(document).on("click", ".comment-delete-btn", function (){

  const $wrap = $(this).closest(".div-comment-content-wrap");
  const className = $wrap.attr("class");

  // 댓글에 대댓글 존재 여부에 따라 알람 문구 구별
  const hasReComment =  $wrap.closest(".div-parent-comment").next(".div-re-comment-wrap").length > 0;
  const deleteCommentMsg = hasReComment
    ? "이 댓글에는 답글이 달려 있어요.<br>삭제 시, 모든 답글도 함께 삭제됩니다.<br>정말 삭제하시겠어요?"
    : "이 댓글을 정말 삭제하시겠어요?<br>한 번 삭제하면 복구할 수 없어요.";
  const deleteReCommentMsg = "이 답글을 정말 지우시겠어요?<br>삭제하면 복구가 불가능해요.";
  
  // 클래스명으로 댓글, 대댓글 구분
  if (className.includes("parent-comment-wrap")) {
    // 댓글
    openModal(deleteCommentMsg, 2).then((result) => {
      if (result) {
        location.href = "삭제경로";
      }
    });
  } else if (className.includes("child-comment-wrap")) {
    // 대댓글
    openModal(deleteReCommentMsg, 2).then((result) => {
      if (result) {
        location.href = "삭제경로";
      }
    });
  }
});

// ---------------------------------------------------------------