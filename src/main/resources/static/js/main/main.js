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
