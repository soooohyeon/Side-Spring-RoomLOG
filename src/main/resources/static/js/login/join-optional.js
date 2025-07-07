$(document).ready(function () {

  // 한 줄 소개 30자 제한
  $("#profile-intro").on("propertychange change keyup paste input", function() {
    let $introValue = $(this);
    // basic.js
    limitUserIntroLength ($introValue);
  });
  
  // 프로필 이미지 미리보기
  $("#profile-image").on("change", function(event){
    const file = event.target.files[0];
    // basic.js
    const result = setOnePreview(file);
    
    isFileChange = result;
    if (result) {
      $("#DIV-PROFILE-IMG").attr("class", "has-img");
    }
  });
});

let isFileChange = false;
// 프로필 이미지 등록 버튼 호버
$('#DIV-PROFILE-IMG').hover(function() {
  if (!isFileChange) {  // 이미지 프리뷰가 없을 때 만 바뀌도록
    $imgBtn = $(this).find(".img-one-preview");
    $imgBtn.attr("src", "../../image/login/profile_img_add_hover.png");
  }
}, function() {
  if (!isFileChange) {  // 이미지 프리뷰가 없을 때 만 바뀌도록
    $imgBtn = $(this).find(".img-one-preview");
    $imgBtn.attr("src", "../../image/login/profile_img_add.png");
  }
});