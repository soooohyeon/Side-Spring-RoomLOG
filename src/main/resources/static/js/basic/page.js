const url = new URL(window.location.href);
const searchParams = new URLSearchParams(window.location.search);

/* 페이징 - 이거 왜 안돼 */
function goPage(page) {
  url.searchParams.set("page", page);
  location.href = url.toString();
}

/* 검색 */
if (searchParams.has('keyword')) {
	let category = searchParams.get("category");
	const keyword = searchParams.get("keyword");
	
	category = category == null ? 'all' : category;
	$("#INPUT-SEARCH-TEXT").val(keyword);
	$("#SELECT-SEARCH-CATEGORY").val(category);
}

/* 정렬 */
$("#SELECT-SORT").on("change", function() {
	url.searchParams.set("sort", $(this).val());
	location.href = url.toString();
});