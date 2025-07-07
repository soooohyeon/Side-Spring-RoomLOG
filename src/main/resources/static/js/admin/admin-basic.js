// 페이지네이션에서 이전, 다음 기호에 마우스 오버시
// 이전
$('.pagenation').hover(function() {
  const className = $(this).attr("class");
  if (className.includes("previous")) {
    $(this).text("◀");
  } else if (className.includes("next")) {
    $(this).text("▶");
  }
}, function(){
  const className = $(this).attr("class");
  if (className.includes("previous")) {
    $(this).text("◁");
  } else if (className.includes("next")) {
    $(this).text("▷");
  }
});

// --------------------------------------------------------------- 

// 시간 형식 포맷
function getTimeAgo(timestamp) {
  const now = new Date();
  const past = new Date(timestamp);
  const diff = Math.floor((now - past) / 1000); // 초 단위 차이

  if (diff < 60) return "방금 전";
  if (diff < 3600) return Math.floor(diff / 60) + "분 전";
  if (diff < 86400) return Math.floor(diff / 3600) + "시간 전";
  if (diff < 172800) return "어제";
  return Math.floor(diff / 86400) + "일 전";
}

function updateTimeAgo() {
  $(".time-ago").each(function () {
    const ts = $(this).data("timestamp");
    $(this).text(getTimeAgo(ts));
  });
}

// 시간 형식 포맷 함수 사용 (작성일짜 출력) (basic.js)
$(document).ready(function () {
  updateTimeAgo();
});

// --------------------------------------------------------------- 

// 배너, 공지, 건의사항 작성시 textarea 글자 수 카운트
function countTextarea(element, maxCount) {
  const $commentCount = $("#SPAN-COUNT");

  // 이모지 포함 정확한 길이 계산
  let content = element.val();
  let trimmed = Array.from(content).slice(0, maxCount).join("");

  // 200자까지만 입력
  if (content !== trimmed) {
    element.val(trimmed);
    content = trimmed;
  }

  const nowLength = Array.from(content).length;
  // 글자수 표시
  $commentCount.text(nowLength);
}

// --------------------------------------------------------------- 

// 모달
// 모달 열기
function openModal(message, temp = 1, modalId = "#MODAL-ALERT-ONE-A") {
  return new Promise((resolve) => {
    const $modal = $(modalId);
    const $alertWrap = $modal.find(".div-alert-wrap");
    const basicFrame = `
      <div class="div-alert-content">${message}</div>
      <div class="div-alert-btn-wrap">
        <div class="div-alert-btn alert-ok modal-close">확인</div>
        <div class="div-alert-btn alert-no">취소</div>
      </div>
    `;

    $alertWrap.attr("class", "div-alert-wrap");
    $alertWrap.html(basicFrame);

    if (temp == 2) {
      $alertWrap.find(".alert-no").addClass("modal-coutinue");
    }

    // 확인 클릭 시 → true 반환
    $alertWrap.find(".alert-ok").one("click", function () {
      closeModal(modalId);
      resolve(true);
    });

    // 취소 클릭 시 → false 반환
    $alertWrap.find(".alert-no").one("click", function () {
      closeModal(modalId);
      resolve(false);
    });

    $modal.addClass("alert-active").fadeIn(200);
  });
}


// 모달 닫기
function closeModal(modalId = ".div-alert-container") {
  const $modal = $(modalId);
  $modal.removeClass("alert-active");
  $modal.fadeOut(200);
}

$(document).on("click", ".modal-close", function() {
  closeModal();
});

// 모달 닫기(x) 버튼 호버 시 - 마이페이지, 유저 페이지, 메세지함
$(document).on("mouseenter", ".modal-close-img", function() {
    $(this).attr("src", "../../image/layout/close_btn_black.png");
});

$(document).on("mouseleave", ".modal-close-img", function() {
    $(this).attr("src", "../../image/layout/close_btn_grey.png");
});