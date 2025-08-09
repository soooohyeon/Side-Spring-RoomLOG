/**
 * 댓글 기능 모듈화
 */

const communityId = new URLSearchParams(window.location.search).get("communityId");
// errMsg는 basic.js

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
	.catch(() => { openModal(errMsg); });
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
	.catch(() => { openModal(errMsg); });
}

// 댓글 등록
export function registComment(commentContent, parentCommentId, callback) {
	fetch ("/comment/comment-regist", {
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
	.catch(() => { openModal(errMsg); });
}

// 댓글 수정
export function editComment(commentId, commentContent, callback) {
	fetch (`/comment/comment-edit/${commentId}`, {
		method: "PATCH",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({
			commentContent: commentContent
		})
	})
	.then(response => {
		if (!response.ok) throw new Error("수정 실패");
	})
	.then(() => { callback(); })
	.catch(() => { openModal(errMsg); });
}

// 댓글 삭제
export function deleteComment(commentId, callback) {
	fetch (`/comment/comment-delete/${commentId}`, {
		method: "DELETE"
	})
	.then(response => {
		if (!response.ok) throw new Error("삭제 실패");
	})
	.then(() => { callback(); })
	.catch(() => { openModal(errMsg); });
}
