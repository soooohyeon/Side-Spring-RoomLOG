
// 유저 페이지 이동
$(document).ready(function () {
  // 이미지 클릭 시
  $(".div-go-user-page img").on("click", goUserPage);

  // 닉네임 클릭 시
  $(".div-nickname").on("click", goUserPage);
});

// ---------------------------------------------------------------
// 카테고리
const $receiveMsgImage = $(".ca-img[data-category='receive-msg']");
const $sendMsgImage = $(".ca-img[data-category='send-msg']");
const imageSrc = {
    receiveMsg: {
        normal: "/image/message/msg_receive.png",
        selected: "/image/message/msg_receive_active.png",
        hover: "/image/message/msg_receive_hover.png"
    },
    sendMsg: {
        normal: "/image/message/msg_sent.png",
        selected: "/image/message/msg_sent_active.png",
        hover: "/image/message/msg_sent_hover.png"
    }
};

// ----------------------------------------

// 카테고리 클릭 시 - 받은 메세지, 보낸 메세지
$(".li-category-wrap").on("click", function () {
    const $categoryGroup = $(this).find(".div-category");
    const category = $categoryGroup.data("category");

    // 탭 상태 토글
    $(".div-category").removeClass("div-selected");
    $categoryGroup.addClass("div-selected");

    // 아이콘 변경 + 슬라이드 전환
    if (category === "receive-msg") {
        $receiveMsgImage.attr("src", imageSrc.receiveMsg.selected);
        $sendMsgImage.attr("src", imageSrc.sendMsg.normal);
        $(".list-slide-wrap").css("transform", "translateX(0%)");
        updateSlideHeight(".receive-msg-list-wrap");
    } else if (category === "send-msg") {
        $sendMsgImage.attr("src", imageSrc.sendMsg.selected);
        $receiveMsgImage.attr("src", imageSrc.receiveMsg.normal);
        $(".list-slide-wrap").css("transform", "translateX(-100%)");
        updateSlideHeight(".send-msg-list-wrap");
    }

    expressCategory($categoryGroup);
});


// ----------------------------------------

// 기본 값은 받은 메세지
// 선 표시
function expressCategory($selectedCategory) {
  const $wrap = $selectedCategory.closest(".li-category-wrap");
  const width = $wrap.innerWidth();
  const moveX = $wrap.position().left;

  $(".ca-selected").css({
    width: width,
    transform: `translateX(${moveX}px)`
  });
}

// 선택 탭의 위치와 너비에 맞춰 활성화 바 이동 및 리사이즈
function updateActiveBar() {
  const $target = $(".div-category.div-selected");
  expressCategory($target);
}

// $(document).ready(upSdateUnderline)와 같음
$(updateActiveBar);

// 가로 너비에 맞춰 활성화 탭 표시 리사이즈되도록 실행
$(window).on("resize", updateActiveBar);


// ----------------------------------------

// 현재 선택되지 않은 카테고리 호버 시 - 받은 메세지, 보낸 메세지
$(".li-category-wrap").hover(function () {
  const $categoryGroup = $(this).find(".div-category");
  const category = $categoryGroup.data("category");
    
    if ($categoryGroup.hasClass("div-selected")) return;
    
    if (category === "receive-msg") {
      $receiveMsgImage.attr("src", imageSrc.receiveMsg.hover);
    } else if (category === "send-msg") {
      $sendMsgImage.attr("src", imageSrc.sendMsg.hover);
    }
  }, function() {
    const $categoryGroup = $(this).find(".div-category");
    const category = $categoryGroup.data("category");
    
    if ($categoryGroup.hasClass("div-selected")) return;

    if (category === "receive-msg") {
        $receiveMsgImage.attr("src", imageSrc.receiveMsg.normal);
    } else if (category === "send-msg") {
        $sendMsgImage.attr("src", imageSrc.sendMsg.normal);
    }
});

// ---------------------------------------------------------------

// 화면에 보여지는 리스트의 높이로 계산하여 자동 설정
function updateSlideHeight(targetSelector) {
    const targetHeight = $(targetSelector).outerHeight(true);
    $(".list-slide-wrap").stop().animate({ height: targetHeight }, 300);    // 애니메이션 효과 넣어줌
}

// ----------------------------------------

// 기본 값은 받은 메세지 기준으로 계산
$(document).ready(function () {
    updateSlideHeight(".receive-msg-list-wrap");
});

// ---------------------------------------------------------------

// 메세지 상세보기 모달창
// 모달 열기
function openFollowModal(userId, modalId = "#MODAL-ALERT-ONE-A") {
  const $modal = $(modalId);
  const $alertWrap = $(".div-alert-wrap");
  const messageFrame = `
    <div class="div-alert-close-btn">
      <img src="../../image/layout/close_btn_grey.png" class="modal-close modal-close-img" alt="close">
    </div>
    <div class="div-alert-content">
      <div id="DIV-MESSAGE-CONTENT-CONTAINER">
        <div class="div-profile-btn-wrap">
          <div class="div-profile-wrap">
            <div class="div-go-user-page">
              <img src="../../image/example/profile_image/profile6.jpg">
              <div class="div-nickname div-nick">반쯤열린창 </div>
            </div>
            <div class="div-age">20대</div>
          </div>
          <div class="div-time-btn-wrap">
            <div class="div-delete">삭제</div>
            <div class="div-time time-ago" data-timestamp="2025-06-27T15:05:00"></div>
          </div>
        </div>
        <div id="DIV-MESSAGE-CONTENT-WRAP">
          잘 지내고 있으시죠~? 
        </div>
      </div>
    </div>
    <div class="div-alert-btn-wrap div-message-btn-wrap">
      <div class="button-style list-button" onclick="showMessage(3)">지난 대화</div>
      <div class="button-style blue-line-button" onclick="sendMessage(3)">답장하기</div>
    </div>
  `;

  $alertWrap.addClass("div-message-view");
  $alertWrap.html(messageFrame);
  // 시간 형식 포맷 함수 사용 (작성일짜 출력) (basic.js)
  updateTimeAgo();
  
  // 모달 표시
  $modal.addClass("alert-active");
  $modal.fadeIn(200);
}

// ----------------------------------------

// 메세지 클릭 시 상세보기
$(".div-one-post-wrap").on("click", function() {
  const userId = $(this).find(".div-go-user-page").data("user-id");
  openFollowModal(userId);
});

// ---------------------------------------------------------------

const deleteMsg = "이 메세지를 삭제할까요?<br>아직 읽지 않았다면 상대방에게도 삭제돼요.<br>이미 읽은 메세지는 상대방이 볼 수 있습니다.";

// 메세지 삭제 클릭 시
$(document).on("click", ".div-delete", function (e){
  e.stopPropagation();
  const messageNumber = $(this).data("messageNumber");
  openModal(deleteMsg, 2).then((result) => {
    if (result) {
      location.href="";
    }
  });
});

// ---------------------------------------------------------------

// 지난 대화 클릭 시
function showMessage(userNumber) {
  location.href = "/html/message/message-history.html";
}

// ---------------------------------------------------------------

// 답장하기 클릭 시
function sendMessage(userNumber) {
  location.href = "/html/message/message-write.html";
}