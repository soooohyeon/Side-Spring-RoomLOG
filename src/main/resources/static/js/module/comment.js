/**
 * 댓글 기능 모듈화
 * 
 */

const communityId = new URLSearchParams(window.location.search).get("communityId");
// errMsg는 basic.js

// 댓글 등록
export function registComment(commentContent, parentCommentId, callback) {
	fetch ("/comment/comment-registOk", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
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
	.then((result) => {	callback(result); })
	.catch((err) => { 
		console.log(err);
		openModal(errMsg); });
}

// 부모 댓글 목록
export function getParentList(parentPage, callback) {
	fetch(`/comment/comment-list/${communityId}?page=${parentPage}`, {
		method: "get"
	})
	.then(response => {
		if (!response.ok) throw new Error("부모 댓글 렌더링 실패");
		return response.json();
	})
	.then((parentlist) => { callback(parentlist); })
	.catch((err) => { 
		console.log(err);
		openModal(errMsg); });
}

// 자식 댓글 목록
export function getChildList(parentId, childPage, callback) {
	fetch(`/comment/comment-list/child/${parentId}?page=${childPage}`, {
		method: "get"
	})
	.then(response => {
		if (!response.ok) throw new Error("자식 댓글 렌더링 실패");
		return response.json();
	})
	.then((childlist) => { callback(childlist); })
	.catch((err) => {
		console.log(err);
		openModal(errMsg); });
}
