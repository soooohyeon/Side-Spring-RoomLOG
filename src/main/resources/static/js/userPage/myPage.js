// 카테고리
const $caBoardImage = $(".ca-img[data-category='my-board']");
const $caScrapImage = $(".ca-img[data-category='my-scrap']");
const imageSrc = {
    board: {
        normal: "../../image/layout/board_count.png",
        selected: "../../image/userPage/board_menu.png",
        hover: "../../image/userPage/board_hover.png"
    },
    scrap: {
        normal: "../../image/layout/scrap_no.png",
        selected: "../../image/userPage/scrap_menu.png",
        hover: "../../image/userPage/scrap_line_hover.png"
    }
};

// ----------------------------------------

// 카테고리 클릭 시 - 작성 글, 스크랩
$(".li-category-wrap").on("click", function () {
    const $categoryGroup = $(this).find(".div-category");
    const category = $categoryGroup.data("category");
    const categoryWidth = $(this).innerWidth();
    // 스크랩 클릭 시 이동을 위한 작성글 카테고리 가로 너비 계산
    const moveWidth = $categoryGroup.closest("li").position().left;

    // 탭 상태 토글
    $(".div-category").removeClass("div-selected");
    $categoryGroup.addClass("div-selected");
    
    if (category === "my-board") {
        $caBoardImage.attr("src",imageSrc.board.selected);
        $caScrapImage.attr("src", imageSrc.scrap.normal);
        $(".list-slide-wrap").css("transform", "translateX(0%)");
        $clickElement.css("transform", "translateX(0)");
        updateSlideHeight(".write-list-wrap");
    } else if (category === "my-scrap") {
        $caScrapImage.attr("src", imageSrc.scrap.selected);
        $caBoardImage.attr("src", imageSrc.board.normal);
        $(".list-slide-wrap").css("transform", "translateX(-100%)");
        $clickElement.css("transform", `translateX(${moveWidth}px)`);
        updateSlideHeight(".scrap-list-wrap");
    }
    expressCategory($clickElement, categoryWidth);
});

// ----------------------------------------

// 카테고리 선택 시 라인 가로 너비 계산
// 기본 값은 작성 글
let $clickElement = $(".ca-selected");
let width = $(".li-category-wrap").eq(0).innerWidth();
function expressCategory(element, width) {
    element.width(width);
}
expressCategory($clickElement, width);

// ----------------------------------------

// 현재 선택되지 않은 카테고리 호버 시 - 작성 글, 스크랩
$(".li-category-wrap").hover(function () {
    const $categoryGroup = $(this).find(".div-category");
    const category = $categoryGroup.data("category");
    
    if ($categoryGroup.hasClass("div-selected")) return;
    
    if (category === "my-board") {
        $caBoardImage.attr("src", imageSrc.board.hover);
    } else if (category === "my-scrap") {
        $caScrapImage.attr("src", imageSrc.scrap.hover);
    }
}, function() {
    const $categoryGroup = $(this).find(".div-category");
    const category = $categoryGroup.data("category");
    
    if ($categoryGroup.hasClass("div-selected")) return;

    if (category === "my-board") {
        $caBoardImage.attr("src", imageSrc.board.normal);
    } else if (category === "my-scrap") {
        $caScrapImage.attr("src", imageSrc.scrap.normal);
    }
});

// ---------------------------------------------------------------

// 화면에 보여지는 리스트의 높이로 계산하여 자동 설정
function updateSlideHeight(targetSelector) {
    const targetHeight = $(targetSelector).outerHeight(true);
    $(".list-slide-wrap").stop().animate({ height: targetHeight }, 300);    // 애니메이션 효과 넣어줌
}

// ----------------------------------------

// 기본 값은 작성 글 기준으로 계산
$(document).ready(function () {
    updateSlideHeight(".write-list-wrap");
});