const url = new URL(window.location.href);
const searchParams = new URLSearchParams(window.location.search);

/* 페이징 */
function goPage(page) {
  url.searchParams.set("page", page);
  location.href = url.toString();
}

// ---------------------------------------------------------------

/* 검색 */
if (searchParams.has("keyword")) {
	let category = searchParams.get("category");
	const keyword = searchParams.get("keyword");
	
	category = category == null ? 'all' : category;
	$("#INPUT-SEARCH-TEXT").val(keyword);
	$("#SELECT-SEARCH-CATEGORY").val(category);
}

if (searchParams.has("sort")) {
  const sort = searchParams.get("sort");
  $("#SELECT-SORT").val(sort);
}

// ---------------------------------------------------------------

/* 정렬 */
// 선택한 정렬에 따라 select 여백 조정
let $selectValue = $("#SELECT-SORT");
const sort = searchParams.get("sort")

if (sort == "comment") {
  $selectValue.css("padding", "0 15px");
} else if (sort == "scrap") {
  $selectValue.css("padding", "0 32px");
}

// 파라미터 추가
$("#SELECT-SORT").on("change", function() {
  if (searchParams.has("page")) {
    url.searchParams.delete("page");
  }
  url.searchParams.set("sort", $(this).val());
  location.href = url.toString();
});