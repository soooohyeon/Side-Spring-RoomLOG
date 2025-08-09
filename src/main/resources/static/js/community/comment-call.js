// ---------------------------------------------------------------

// 댓글 기능 모듈 가져오기
import * as comment from "../module/comment.js";

const userId = Number(sessionStorage.getItem("userId"));
const commentRegistMsg = "댓글을 등록하시겠습니까?";
// 댓글 목록 기본 페이지
let page = 1;

// ---------------------------------------------------------------

// 댓글 목록 보여주는 callback 함수
function hadleCommentList(data) {
	let $pageWrap = $("#DIV-PAGENATION-WRAP");
	// 댓글 보여주기
	showParentCommentList($pageWrap, data);
	// 페이징 처리
	showPage($pageWrap, data.page);
}

// 처음 부모 댓글 보여주기
comment.getParentList(1, hadleCommentList);

// --------------------------------

// 부모 댓글 보여주기
function showParentCommentList($pageWrap, data) {
	let $commentWrap = $("#DIV-COMMENT-CONTAINER");
	let $comments = '';

	// 댓글 수
	let $countWrap = $("#DIV-COMMENT-COUNT-WRAP");
	let $commentCount = `댓글 수 <span>${data.totalCount}</span>`;
	$countWrap.html($commentCount);
	
	// 댓글이 없을 경우
	if (data.totalCount === 0 || data.parents.length === 0) {
		$comments = `<div class="div-post-none">아직 작성된 댓글이 없습니다.<br>첫 댓글을 남겨주세요!</div>`;
		$commentWrap.html($comments);
		$pageWrap.remove();
		return;
	}
	
	// 댓글이 있을 경우
	(data.parents).forEach(pl => {
		$comments += `
		  <div class="div-comment-wrap div-parent-comment" data-comment-id=${pl.commentId} data-parent-id=${pl.commentId}>
		    <div class="div-comment-user-time-wrap">
		      <div class="div-comment-user-profile-wrap div-go-user-page" data-user-id="${pl.userId}">
		        <div class="div-comment-profile-wrap">
				${pl.profileImgPath == null
				  ? `<img src="/image/layout/profile_img_basic.png" alt="profile">`
			  	  : `<img src="/upload/${pl.profileImgPath}/th_${pl.profileImgUuid}" alt="profile">`}
		        </div>
		        <div class="div-nick">${pl.userNickname}</div>
		        <div class="div-age">${pl.userAge}</div>
		      </div>
		      <div class="div-comment-date time-ago" data-timestamp="${pl.createDate}"></div>
		    </div>
		    <div class="div-comment-content-wrap parent-comment-wrap">
		      <div class="div-re-comment-btn-wrap">
		        <div class="div-comment-content">
				  ${pl.isDeleted == 0 ? pl.commentContent.replaceAll('\n', '<br>') : `삭제된 댓글입니다.`}
				</div>
		        <div class="div-re-comment-btn">
		          <img src="/image/community/re_comment_btn.png">답글
		        </div>
		      </div>
			  ${userId !== null && userId === pl.userId && pl.isDeleted == 0
		      ? `<div class="div-comment-btn-wrap">
		        <div class="div-comment-btn div-menu-line"><span class="comment-update-btn">수정</span></div>
		        <div class="div-comment-btn"><span class="comment-delete-btn">삭제</span></div>
		      </div>`
			  : ``}
		    </div>
		  </div>
		`;
	
	  // 부모 댓글 보여주기
	  $commentWrap.html($comments);

	  // 자식 댓글 보여주기
	  comment.getChildList(pl.commentId, 0, (data) => {
		let $lastChild = $(`.div-parent-comment[data-parent-id="${pl.commentId}"]`);
		
		data.childs.content.forEach(cl => {
			const $recomment = showChildCommentList(cl);
			$lastChild.after($recomment);
			// 마지막 자식 댓글 위치로 갱신
			$lastChild = $recomment;
	    });

		if (!data.childs.last) {
			const moreCommentCount = data.totalCount - data.childs.size;
			const $moreBtn = $(`
				<div class="div-more-btn-wrap" data-parent-id="${pl.commentId}">
				  더보기 (<span class="span-reComment-count">${moreCommentCount}</span>)
				  <span class="span-plus">&plus;</span>
				</div>`);
			$lastChild.after($moreBtn);
		}
		
		// basic.js의 함수 호출 - 부모/자식 댓글 시간 형식 포맷
		updateTimeAgo();
	  });
	});
}

// --------------------------------

// 자식 댓글 보여주기
function showChildCommentList(cl) {
	const $recomment = $(`
		<div class="div-comment-wrap div-re-comment-wrap" data-comment-id=${cl.commentId}>
		  <div class="div-comment-user-time-wrap">
		    <div class="div-comment-user-profile-wrap div-go-user-page" data-user-id="${cl.userId}">
		      <div class="div-comment-profile-wrap">
			  	${cl.profileImgUuid == null
			  	  ? `<img src="/image/layout/profile_img_basic.png" alt="profile">`
			      : `<img src="/upload/${cl.profileImgPath}/th_${cl.profileImgUuid}" alt="profile">` }
		      </div>
		      <div class="div-nick">${cl.userNickname}</div>
		      <div class="div-age">${cl.userAge}</div>
		    </div>
		    <div class="div-comment-date time-ago" data-timestamp="${cl.createDate}"></div>
		  </div>
		  <div class="div-comment-content-wrap child-comment-wrap">
		    <div class="div-re-comment-btn-wrap">
		      <div class="div-comment-content">${cl.commentContent}</div>
		    </div>
			${userId !== null && userId === cl.userId 
			  ? `<div class="div-comment-btn-wrap">
			    <div class="div-comment-btn div-menu-line"><span class="comment-update-btn">수정</span></div>
			    <div class="div-comment-btn"><span class="comment-delete-btn">삭제</span></div>
			  </div>`
			  : ``}
		  </div>
		</div>`);
	
	return $recomment;
}

// ---------------------------------------------------------------

// 부모 댓글 목록 페이징 처리
function showPage($pageWrap, page) {
	let $prevWrap = $pageWrap.find(".previous").parent();
	let $nextWrap = $pageWrap.find(".next").parent();
	let $ul = $pageWrap.find("ul");
	
	// 이전 ◁
	if (page.prev) {
		$prevWrap.show();
		$prevWrap.find(".previous").data('page', (page.startPage - 1));
	} else {
		$prevWrap.hide();	
	}
	// 페이지 번호
	$ul.empty();
	for(let i = page.startPage; i <= page.endPage; i++) {
		const $li = $(`<li class="pagenation page" data-page="${i}">${i}</li>`);
		if (i === page.criteria.page) {
			$li.addClass("selected");
		}
		$ul.append($li);
	}
	// 다음 ▷
	if (page.next) {
		$nextWrap.show();
		$nextWrap.find(".next").data('page', (page.endPage + 1));
	} else {
		$nextWrap.hide();	
	}
}

// --------------------------------

// 페이지 이동 이벤트
$(document).off().on("click", ".pagenation", function() {
	const pageNum = $(this).data("page");
	
	if (reCommentCheck) {
	// 답글 입력 폼이 있는 경우
	  openModal(cancelRecommentMsg, 2).then((result) => {
	    if (result) {
		  reCommentCheck = false;
		  comment.getParentList(pageNum, hadleCommentList);
		}
	  });
	  return;
	} else if ($currentEditComment) {
  	// 이미 수정 중인 댓글이 있는 경우
  	  openModal(changeRecommentMsg, 2).then((result) => {
    	if (result) {
		  $currentEditComment = null;
		  comment.getParentList(pageNum, hadleCommentList);
		}
	  });
	  return;
	}
	
	// 아무 문제 없으면 바로 페이지 이동
	comment.getParentList(pageNum, hadleCommentList);
});

// --------------------------------

// 자식 댓글 더보기 버튼 클릭 시
$(document).on("click", ".div-more-btn-wrap", function() {
	const parentId = $(this).data("parent-id");
	let nextPage = $(this).data("page") || 1;
	
	comment.getChildList(parentId, nextPage, (data) => {
		let $last = $(this);

		data.childs.content.forEach(cl => {
		  const $reComment = showChildCommentList(cl);
		  $last.before($reComment);
		  $last = $reComment;
		});

		if (data.childs.last) {
		  $(this).remove();
		} else {
		  const moreCommentCount = data.totalCount - (data.childs.number + 1) * data.childs.size;
		  $(this).find(".span-reComment-count").html(moreCommentCount);
		  $(this).data("page", nextPage + 1);
		}
		
		updateTimeAgo();
	});
});

// ---------------------------------------------------------------

// 댓글, 대댓글 수정 / 대댓글 작성 - 200자 제한, 내용 존재 여부 검사
function isValidComment($textarea, maxLength = 200) {
  let comment = $textarea.val();
  const trimmed = Array.from(comment).slice(0, maxLength).join("");
  
  if (comment !== trimmed) {
    $textarea.val(trimmed);
    comment = trimmed;
  }
  
  return Array.from(comment).length > 0;
}

// ---------------------------------------------------------------

// 새 댓글 입력 칸
const $comment = $("#TEXTAREA-COMMENT-TXT");
$(document).ready(function() {
  // 로그아웃 상태면 textarea 비활성화
  if (userId == null || userId <= 0) {
    $comment.prop("readonly", true);
    $comment.on("focus", function (e) {
      openModal("로그인 후 이용해 주세요.");
    });
  }
  // 실시간 글자 수 표시, 등록 버튼 활성화
  $comment.on("change input", countComent);

  // 댓글 수정 취소 함수 호출
  updateCancel();
});

// ---------------------------------------------------------------

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

// ---------------------------------------------------------------

// 부모 댓글 등록
$(".btn-comment-save").on("click", function() {
	if (checkRegist) {
		openModal(commentRegistMsg, 2).then((answer) =>	{
			if (answer) {
				const commentContent = $(".txt-comment-content").val();
				const parentId = null;
				// 댓글 등록 함수 호출
				comment.registComment(commentContent, parentId, function(result) {
					openModal(result.msg).then((ok) => {
						if (ok) {
							comment.getParentList(1, hadleCommentList);
							$comment.val("");
						}
					});
				});
			}
		});
	}
});

// ---------------------------------------------------------------

// 대댓글(답글 버튼) 등록
$(document).on("click", "#RE-COMMENT-WRITE-BTN", function() {
  const $reComment = $(this).closest(".div-re-comment-form").find(".text-re-content-txt");
  const result = isValidComment($reComment);

  if (result) {
	  openModal(commentRegistMsg, 2).then((answer) =>	{
	  	if (answer) {
			const recommnetContent = $reComment.val();
			const parentId = $(this).closest(".div-comment-wrap").prev().data("parent-id");
			// 댓글 등록 함수 호출
			comment.registComment(recommnetContent, parentId, function(result) {
				openModal(result.msg).then((ok) => {
					if (ok) {
						comment.getParentList(page, hadleCommentList);
						// 자식 댓글 등록 후 자식 댓글 작성 확인 플래그 변수 초기화
						reCommentCheck = false;
						reCommentCount = 0;
					}
				});
			});
	  	}
	  });
  } else {
    openModal("댓글 내용을 입력해 주세요.");
  }
});

// ---------------------------------------------------------------

// 댓글 수정
$(document).on("click", "#COMMENT-UPDATE-BTN", function() {
  const $commentEdit = $(this).closest(".div-comment-update-wrap").find(".text-re-content-txt");
  const result = isValidComment($commentEdit);

  if (result) {
    openModal("수정하시겠습니까?", 2).then((ok) => {
	  if (ok) {
		const commentId = $(this).closest(".div-comment-wrap").data("comment-id");
	    comment.editComment(commentId, $commentEdit.val(), function() {
	      openModal("수정되었습니다.").then((ok) => {
	        if (ok) {
			  $currentEditComment = null;
	 	      comment.getParentList(page, hadleCommentList);
	    	}
	      });
	    });
	  }
	});
  } else {
    openModal("댓글 내용을 입력해 주세요.");
  }
});

// --------------------------------------------------------------

// 답글 입력 폼의 글자 수 카운트
let reCommentCount = 0;
$(document).on("input change", ".text-re-content-txt", function() {
  if($(this).hasClass("regist-recomment")) {
	reCommentCount = $(this).val().trim().length;
  }
});

// ---------------------------------------------------------------

// 현재 열린 댓글 폼이 있는지 확인
let reCommentCheck = false;
const cancelRecommentMsg = "작성 중인 댓글은 저장되지 않습니다.<br>정말 취소할까요?";
const changeRecommentMsg = "수정 중인 댓글은 저장되지 않습니다.<br>정말 취소할까요?";

// 답글 버튼 클릭 시 입력 폼 생성
$(document).on("click", ".div-re-comment-btn", function() {
  if (userId === null || userId <= 0) {
	openModal("로그인 후 이용해 주세요.");
	return;
  }
  // 누른 답글 버튼의 댓글을 감싸는 마지막 div
  const $divWrap = $(this).closest(".div-parent-comment");
  // 대댓글 입력 폼
  const reCommentForm = `
    <div class="div-comment-wrap div-re-comment-write-wrap">
      <div class="div-re-comment-form">
        <div class="div-re-comment-write">
          <textarea name="commentContent" class="text-re-content-txt regist-recomment" placeholder="댓글에 답글을 남겨보세요."></textarea>
        </div>
        <div class="div-comment-btn-wrap textarea-btn-wrap">
            <div class="div-comment-btn div-menu-line"><span id="RE-COMMENT-WRITE-BTN">등록</span></div>
            <div class="div-comment-btn"><span id="RE-COMMENT-CANCEL-BTN">취소</span></div>
        </div>
      </div>
    </div>
  `;

  if ($currentEditComment) {
    // 이미 수정 중인 댓글이 있는데 답글 입력 버튼 클릭 시
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
  }	else if (reCommentCheck && reCommentCount > 0) {
	 // 이미 답글 입력 폼이 있는데 다른 답글 입력 버튼 클릭 시
	openModal(cancelRecommentMsg, 2).then((result) => {
	  if (result) {
	    $(".div-re-comment-write-wrap").remove();
	    $divWrap.after(reCommentForm);
		reCommentCount = 0;
	  }
	});
  } else if (reCommentCheck) {
    // 이미 답글 입력 폼(내용 x)이 있는데 다른 답글 입력 버튼 클릭 시
    $(".div-re-comment-write-wrap").remove();
    $divWrap.after(reCommentForm);
  } else {
    $divWrap.after(reCommentForm);
    reCommentCheck = true;
  }
});

// --------------------------------

// 답글 취소 버튼 클릭시 입력 폼 삭제
$(document).on("click", "#RE-COMMENT-CANCEL-BTN", function() {
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
  reCommentCount = 0;
});

// ---------------------------------------------------------------

// 현재 댓글 수정 폼이 있는지 확인
let $currentEditComment = null;
const currentEditCommentMsg = "이미 다른 댓글을 수정 중입니다.<br>현재 수정을 취소하고 이 댓글을 수정하시겠어요?";

// 댓글 수정 버튼 클릭 시
$(document).on("click", ".comment-update-btn", function() {
  const $oriCommentWrap = $(this).closest(".div-comment-content-wrap");
  const $oriComment = $oriCommentWrap.find(".div-comment-content");

  const oriCommentText = $oriComment.text().trim();
  const editFrame = `
            <div class="div-comment-update-wrap">
              <div class="div-comment-update">
                <textarea name="commentContent" class="text-re-content-txt edit-comment">` + oriCommentText + `</textarea>
              </div>
              <div class="div-comment-btn-wrap textarea-btn-wrap">
                  <div class="div-comment-btn div-menu-line"><span id="COMMENT-UPDATE-BTN">저장</span></div>
                  <div class="div-comment-btn"><span id="COMMENT-UPDATE-CANCEL-BTN">취소</span></div>
              </div>
            </div>
  `;

  if (reCommentCheck && reCommentCount === 0) {
	// 답글의 내용이 없는 상태에서 댓글 수정 클릭 시
    $(".div-re-comment-write-wrap").remove();
    reCommentCheck = false;

	// 속성에 원본 댓글 넣어두고 새로운 수정 폼으로 변경
	changeEditForm($oriCommentWrap, oriCommentText, editFrame);
  } else if (reCommentCheck && reCommentCount > 0) {
	// 답글의 내용을 입력한 상태에서 댓글 수정 클릭 시
    openModal(cancelRecommentMsg, 2).then((result) => {
      if (result) {
        $(".div-re-comment-write-wrap").remove();
        reCommentCheck = false;
		reCommentCount = 0;

		// 속성에 원본 댓글 넣어두고 새로운 수정 폼으로 변경
		changeEditForm($oriCommentWrap, oriCommentText, editFrame);
      }
    });
  } else if ($currentEditComment && !$oriCommentWrap.is($currentEditComment)) {
    // 최근 수정 중인 댓글과 다른 댓글의 수정 버튼을 누르면
    openModal(currentEditCommentMsg, 2).then((result) => {
      if (result) {
        const oriComment = $currentEditComment.data("original-text");
        const className = $currentEditComment.attr("class");
        
        // 클래스명으로 댓글, 대댓글 구분
        const type = className.includes("parent-comment-wrap") ? "comment" : "reComment";
        // 댓글 수정 취소
        renderOriginalComment($currentEditComment, oriComment, type);

		// 속성에 원본 댓글 넣어두고 새로운 수정 폼으로 변경
		changeEditForm($oriCommentWrap, oriCommentText, editFrame);
      }
    });
  } else {
	// 속성에 원본 댓글 넣어두고 새로운 수정 폼으로 변경
	changeEditForm($oriCommentWrap, oriCommentText, editFrame);
  }
});

// --------------------------------

// 속성에 원본 댓글 넣어두고 새로운 수정 폼으로 변경
function changeEditForm($oriCommentWrap, oriCommentText, editFrame) {
	// 속성에 원본 댓글 넣기
	$oriCommentWrap.data("original-text", oriCommentText);
	// 수정 폼으로 변경
	$oriCommentWrap.html(editFrame);
	// 최근 수정한 댓글 담아두기
	$currentEditComment = $oriCommentWrap;
}

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

// --------------------------------

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

// 댓글 삭제
$(document).on("click", ".comment-delete-btn", function (){
  const $wrap = $(this).closest(".div-comment-content-wrap");
  const className = $wrap.attr("class");
  const commentId = $(this).closest(".div-comment-wrap").data("comment-id");

  // 댓글에 대댓글 존재 여부에 따라 알람 문구 구별
  const hasReComment =  $wrap.closest(".div-parent-comment").next(".div-re-comment-wrap").length > 0;
  const deleteCommentMsg = hasReComment
    ? "이 댓글은 삭제 후 '삭제된 메시지입니다'로 표시됩니다.<br>답글은 삭제되지 않으며 계속 표시됩니다.<br>삭제하시겠어요?"
    : "이 댓글을 정말 삭제하시겠어요?<br>한 번 삭제하면 복구할 수 없어요.";
  const deleteReCommentMsg = "이 답글을 정말 지우시겠어요?<br>삭제하면 복구가 불가능해요.";
  
  // 클래스명으로 댓글, 대댓글 구분
  if (className.includes("parent-comment-wrap")) {
    // 댓글
	deleteComment(deleteCommentMsg, commentId)
  } else if (className.includes("child-comment-wrap")) {
    // 대댓글
	deleteComment(deleteReCommentMsg, commentId)
  }
});

// --------------------------------

// 댓글, 대댓글 삭제
function deleteComment(deleteMsg, commentId) {
  openModal(deleteMsg, 2).then((result) => {
    if (result) {
        comment.deleteComment(commentId, function(){
  	    openModal("삭제되었습니다.").then((ok) => {
  	      if (ok) {
  	       comment.getParentList(page, hadleCommentList);
  	      }
  	    });
  	  });
    }
  });
}

// ---------------------------------------------------------------