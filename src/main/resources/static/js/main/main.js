// 3초마다 배너 자동 슬라이드 실행
setInterval(bannerSlide, 3000);

/**
 * bannerSlide()
 * - 배너 슬라이드를 왼쪽으로 한 칸 이동
 * - 애니메이션이 끝나면 첫 번째 배너를 맨 뒤로 보내고 위치 초기화하여
 *   사용자는 끊김 없이 무한히 돌아가는 것처럼 보이게 처리
 */
function bannerSlide() {
  // 배너 가로 너비 구하기
  const bannerWidth = $("#SECTION-BANNER-CONTAINER").width();

  $('#UL-BANNER-WRAP').animate(
    { 'margin-left': `-${bannerWidth}px` }, 1200, function() {
      // 첫 번째 li를 마지막 뒤로 이동
      $('#UL-BANNER-WRAP').append($('#UL-BANNER-WRAP .li-slide-banner:first-child'));
      // 다음 이동을 위해 margin 초기화
      $('#UL-BANNER-WRAP').css('margin-left', '0');
    }
  );
}

// ---------------------------------------------------------------

// 유저 페이지 이동 - basic.js
$(document).ready(function () {
  $(document).on("click", ".div-go-user-page", goUserPage);
});

// ---------------------------------------------------------------

// 팔로우 버튼 클릭 - 해당 유저가 자기 자신일 경우 페이지 이동만 막기
$(document).on("click", ".limit-follow", function(e){
  e.stopPropagation();
  return false;
});

// -----------------------------------

// 팔로우 버튼 눌렀을 때
// 팔로우 하기
function goFollowMain(event, element, toUserId) {
  event.stopPropagation();

  if (userId > 0 && userId != null) {
	fetch(`/follow/follow-save/${toUserId}`, {
	  method: 'POST'
	})
	.then(response => {
	  if (!response.ok) throw new Error("팔로우 실패");
      element.innerText = "팔로잉";
      element.setAttribute("onclick", `noFollow(event, this, ${toUserId})`);
      element.setAttribute("class", "button-style basic-button");
	  // 현재 팔로우 수 + 1
	  const $count = $(element).closest(".div-rank-follower-wrap").find(".div-follow");
	  const current = parseInt($count.text());
	  $count.text(current + 1);
	})
	.catch(() => {
	  openModal(errMsg);
	});
  } else if (userId == null) {
    openModal("로그인이 필요해요.<br>팔로우는 로그인 후 이용할 수 있어요!");
  }
}

// -----------------------------------

// 팔로우 해제
function noFollowMain(event, element, toUserId) {
  event.stopPropagation();

  fetch(`/follow/follow-cancel/${toUserId}`, {
  	method: 'DELETE'
  })
  .then(response => {
	if (!response.ok) throw new Error("팔로우 취소 실패");
    element.innerText = "팔로우";
    element.setAttribute("onclick", `goFollow(event, this, ${toUserId})`);
    element.setAttribute("class", "button-style follow-btn");
	// 현재 팔로우 수 - 1
	const $count = $(element).closest(".div-rank-follower-wrap").find(".div-follow");
	const current = parseInt($count.text());
	$count.text(current - 1);
  })
  .catch(() => {
	openModal(errMsg);
  })
}

// ---------------------------------------------------------------
