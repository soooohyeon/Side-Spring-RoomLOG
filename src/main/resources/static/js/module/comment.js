/**
 * 댓글 기능 모듈화
 * 
 */

const communityId = new URLSearchParams(window.location.search).get("communityId");

// 댓글 등록
export function registComment(commentContent, parentCommentId=null, callback) {
	console.log("commentContent : " + commentContent);
	console.log("parentCommentId123 : " + parentCommentId);
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
	.then((result) => {
		callback(result);
	})
	.catch(() => {
		// errMsg는 basic.js
		openModal(errMsg);
	});
}