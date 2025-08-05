// 해시태그
let input = $("input[name='hashtag']");
let firstFocusDone = false;
const MAX_TAGS = 5;

let tagify = new Tagify(input[0], {
  focusable: false,
  placeholder: '관련 키워드를 태그로 남겨보세요.',
  keepPlaceholder: true,
  maxTags: MAX_TAGS,
  dropdown: {
    position: "input",
    enabled: 0,
	selectOnClick: true
  }
});

async function showHashtagList(keyword = "") {
  const res = await fetch(`/hashtag?keyword=${keyword}`);
  return res.json();
}

// 해시태그 입력칸 입력 시 해시태그 목록 보이도록 설정
tagify.on("input", async (e) => {
  const keyword = e.detail.value;
  const data = await showHashtagList(keyword);
  tagify.settings.whitelist = data;
  tagify.dropdown.show.call(tagify, keyword);
});

// 해시태그 입력칸 클릭 시 해시태그 목록 보이도록 설정
tagify.DOM.input.addEventListener("focus", () => {
  if (tagify.state.dropdown.visible) return;

  setTimeout(async () => {
    const data = await showHashtagList();
    tagify.settings.whitelist = data;

	const triggerValue = firstFocusDone ? '' : ' ';
	tagify.input.value = triggerValue;
	tagify.input.set();
	tagify.dropdown.show.call(tagify, triggerValue);

	firstFocusDone = true;
  }, 100);
});

// 등록한 태그 삭제시 해시태그 목록 안보이도록 설정
tagify.DOM.scope.addEventListener("click", (e) => {
  if (e.target.matches('.tagify__tag__removeBtn')) {
    tagify.dropdown.hide();
  }
});

// placeholder 보이도록
function updatePlaceholderState() {
  const len = tagify.value.length;
  // 태그 입력창에 값 존재 여부 확인
  const hasText = tagify.DOM.input.textContent.trim() !== "";

  const placeholderText = len >= MAX_TAGS
    ? "최대 5개까지 입력 가능합니다."
    : "관련 키워드를 태그로 남겨보세요.";

  // 핵심 placeholder 문구 갱신
  tagify.DOM.input.setAttribute("data-placeholder", placeholderText);

  // placeholder 보이게 할지 말지 (css로 강제로 보이게 해뒀기 때문에 설정 필요)
  const showPlaceholder = !hasText;
  tagify.DOM.input.style.setProperty("--placeholder-visible", showPlaceholder ? "1" : "0");

  // contenteditable 유지
  tagify.DOM.input.setAttribute("contenteditable", true);
  tagify.DOM.input.style.pointerEvents = len >= MAX_TAGS ? "none" : "auto";

  // 태그 5개 이상일때도 placeholder` 보이도록 처리
  if (!hasText && len >= MAX_TAGS) {
    tagify.DOM.input.innerHTML = "";
  }
}

updatePlaceholderState();
tagify.on('add', updatePlaceholderState);
tagify.on('remove', updatePlaceholderState);

// 태그 클릭 시 placeholder 복구
tagify.DOM.scope.addEventListener("click", () => {
  setTimeout(updatePlaceholderState, 0);
});

// 외부 클릭 시 placeholder 복구
document.addEventListener("click", () => {
  setTimeout(updatePlaceholderState, 0);
});

// 외부 클릭 + blur 시에도 placeholder 보이도록
tagify.DOM.input.addEventListener("blur", () => {
  setTimeout(updatePlaceholderState, 0);
});

// Backspace로 태그 삭제 막기
tagify.DOM.input.addEventListener("keydown", function (e) {
  if (e.key === "Backspace" && tagify.DOM.input.textContent.trim() === "") {
    e.preventDefault();
    e.stopImmediatePropagation(); // Tagify 내부 로직까지 차단
  }
}, true); // 캡쳐 단계에서 감지시켜 Tagify 로직보다 먼저 작동하도록 설정

// --------------------------------------------------------------- 

// TinyMCE Editor API 설정
let content = "";
tinymce.init ({
	selector: "#TEXTAREA-CONTENT",
	plugins: "paste emoticons preview table lists checklist fullscreen",
	height: 400,
  resize: false,
  menubar: false,
  content_style: 'body { cursor: text; !important }',
  toolbar_mode: 'floating',
	toolbar:  [
    { name: 'history', items: [ 'undo', 'redo' ] },
    { name: 'styles', items: [ 'styles' ] },
    { name: 'colors', items: [ 'forecolor', 'backcolor' ] },
    { name: 'formatting', items: [ 'bold', 'italic', 'underline', 'strikethrough' ] },
    { name: 'insert', items: [ 'emoticons', 'table' ] },
    { name: 'lists', items: [ 'bullist ', 'numlist', 'checklist' ] },
    { name: 'view', items: [ 'preview', 'fullscreen' ] }
  ],
  paste_data_images: false,
  paste_remove_styles: true,
  paste_as_text: true,
  invalid_elements: 'img',
  setup: function (editor) {
    editor.on('input', function () {
      content = editor.getContent();

      updateCharCount(); 
      updateCharCount(); 
      writeButton();
      isFormFilled()
      updateBackBlock();
    });

	// 게시글 수정 시 글자 수 카운트 반영
	editor.on('init', function () {
	  content = editor.getContent();
	  updateCharCount(); 
	});
	
    // TinyMCE Editor 입력 부분 어디든 포커스 되게 설정
    editor.on('PostRender', function () {
      const container = editor.getContainer();
      const editArea = container.querySelector('.tox-edit-area');
      
      if (!container) return;
      
      if (editArea) {
        editArea.addEventListener('click', () => {
          editor.focus();
        });
      }
    });

  }
});

// --------------------------------------------------------------- 

// 글자 수 카운트 (공백, 줄바꿈 포함)
function updateCharCount() {
  const editor = tinymce.get('TEXTAREA-CONTENT');
  const text = editor.getContent({ format: 'text' }) ?? '';
  // 공백을 포함한 문자만 카운팅
  const charCount = text.replace(/\n/g, '').length;
  // 줄 개수 카운팅
  let lineCount = (text.match(/\n/g) || []).length;
  let totalCount = 0;
  
  // 줄이 2개씩 카운팅 되므로 /2로 줄바꿈 1번당 1개로 계산
  lineCount = Math.floor(lineCount / 2);
  
  totalCount = charCount + lineCount;
  isCheckContent = totalCount === 0 ? false : true;
  
  $("#SPAN-COUNT").text(totalCount);
}

// --------------------------------------------------------------- 

// 제목, 본문 유효성 검사
// 회원가입 유효성 검사
let isCheckTitle = false;
let isCheckContent = false;

// 제목 값 확인
$("input[name='communityTitle']").on("input keyup", function() {
  isCheckTitle = $(this).val() == "" ? false : true;
  writeButton();
});

function writeButton() {
  const $btn = $("#WRITE-BTN");
  if (isCheckTitle && isCheckContent) {
    $btn
      .addClass("basic-button")
      .removeClass("disabled");
  } else {
    $btn
      .removeClass("basic-button")
      .addClass("disabled");
  } 
}

// --------------------------------------------------------------- 

// 글 등록 버튼 클릭 후 submit 전 hashtag 값 넘기기 위한 input 태그 생성
function submitHashtag() {
	const tags = tagify.value.map(tag => tag.value);
	
	tags.forEach(tag => {
	  const hiddenTagsInput = $('<input>').attr({
	    type: 'hidden',
	    name: 'tags',
	    value: tag
	  });
	  $postForm.append(hiddenTagsInput);
	});
}

// --------------------------------------------------------------- 

// 값이 입력된 상태일 때 뒤로가기나 페이지 이동 클릭 시 모달 띄우기
let isBlocking = false;   // 뒤로가기 막을지에 대한 여부
let hasPushed = false;    // 뒤로가기 눌렀는지에 대한 여부
let isModalOpen = false;  // 중복 모달 방지를 위한 flag 변수

// 입력된 값이 있는지 확인
function isFormFilled() {
  const file = $("input[name='images']")[0]?.files.length > 0;
  const tags = tagify?.value?.length > 0;
  
  return isCheckTitle || isCheckContent || file || tags;
}

// 뒤로가기 눌렀을 때
function updateBackBlock() {
  // TinyMCE Editor의 입력 값을 Textarea에 동기화
  tinymce.triggerSave();
  const filled = isFormFilled();
  // 값이 채워졌을 때 
  if (filled && !hasPushed) {
    history.pushState({ preventBack: true }, "", location.href);
    hasPushed = true;
    isBlocking = true;
  }

  // 값이 비워졌을 때
  if (!filled && hasPushed) {
    history.back();
    hasPushed = false;
    isBlocking = false;
  }
}

// 값을 입력 후 다시 지웠을 때 히스토리 복구하기 위함
$(document).ready(function () {
  $("input").on("input", updateBackBlock);
  $("input[name='images']").on("change", updateBackBlock);
  tagify.on("change", updateBackBlock);

  // 약간의 지연을 두고 TinyMCE 초기화 끝나길 기다렸다가 실행
  setTimeout(updateBackBlock, 100); // 초기에 한번 강제 실행
});

// 페이지 이동 시
$(document).on("click", "a, button, [data-navigate]", function (e) {
  const href = $(this).attr("href") || $(this).data("href");
  if (href && isFormFilled()) {
    e.preventDefault(); // 기본 이동 막음
    openModal(moveMsg, 2).then((result) => {
      if (result) {
        isBlocking = false;
        hasPushed = false;
        location.href = href;
      }
    });
  }
});

// 뒤로가기 눌렀을 때
window.addEventListener("popstate", function (e) {
  if (isBlocking && isFormFilled() && !isModalOpen) {
    isModalOpen = true;
    openModal(moveMsg, 2).then((result) => {
      if (result) {
        isBlocking = false;
        hasPushed = false;
        history.back();
      } else {
        history.pushState({ preventBack: true }, "", location.href);
      }
      isModalOpen = false;
    });
  }
});
