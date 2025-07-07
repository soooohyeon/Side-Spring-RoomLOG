// 일주일 간 유저 등록 현황 라인 그래프
const $lineChartElement = $("#DIV-WEEK-USER");

// 현재 날짜 기준으로 지난 7일 날짜 라벨 생성
const getLast7Days = () => {
  const today = new Date();
  const result = [];
  for (let i = 6; i >= 0; i--) {
    const d = new Date(today);
    d.setDate(today.getDate() - i);
    const formatted = d.toISOString().slice(5, 10).replace('-', '.');
    result.push(formatted);
  }
  return result;
};

// 라인 차트의 데이터 배결 색을 그라데이션으로 설정
const canvaContext = $lineChartElement.get(0).getContext('2d');
let gradient = canvaContext.createLinearGradient(0, 0, 0, 500); // 더 길게
gradient.addColorStop(0,   'rgba(77, 157, 224, 0.4)');
gradient.addColorStop(0.6, 'rgba(77, 157, 224, 0.2)');
gradient.addColorStop(1,   'rgba(77, 157, 224, 0.0)');

// 차트 데이터 & 스타일 & x, y축 설정
const lineData = {
  labels: getLast7Days(),
  datasets: [{
    label: '신규 가입자 수',
    data: [4, 1, 12, 7, 10, 15, 9],
    fill: true,
    borderColor: '#4D9DE0 ',
    backgroundColor: gradient,
    pointBackgroundColor: '#347DCB',
    pointBorderColor: '#ffffff',
    tension: 0.1
  }]
};

const lineOptions = {
  responsive: true,
  plugins: {
    legend: {
      display: false
    } },
  scales: {
    x: {
      ticks: {
        color: '#555',
        font: {
          size: 15,
          weight: '500'
        }
      }
    },
    y: {
      ticks: {
        precision: 0,
        stepSize: 3,
        color: '#555',
        font: {
          size: 16,
          weight: '500'
        },
        precision: 0
      },
      afterDataLimits(scale) {  // 최대치 보다 + 5
        const rawMax = scale.max;
        scale.max = Math.ceil((rawMax + 3) / 3) * 3;
      }
    }
  }
};

// config 설정
const config = {
  type: 'line',
  data: lineData,
  options: lineOptions
};

// 차트 렌더링
const myLineChart = new Chart($lineChartElement, config);

// ----------------------------------------------------------------

// 한달 간 건의사항 처리/미처리 상태 현황 원형 그래프
var $pieChartElement = $("#DIV-MONTH-FEEDBACK");
var myPieChart = new Chart($pieChartElement, {
  type: 'pie',
  data: {
    labels: ['처리 전', '진행 중', '완료', '보류'],
    datasets: [{
      label: 'Market Share',
      data: [30, 25, 20, 25],
      backgroundColor: [ '#F59A9A', '#A7D4F9', '#B6E2D3', '#D3D3D3' ]
    }]
  },
  options: {
    plugins: {
      legend: {
        position: 'top',
        labels: {
          font: {
            size: 16,         // ✅ 글씨 키우기
            weight: '500'
          },
        }
      }
    }
  }
});
  
// --------------------------------------------------------------- 

// 링크 연결 - 사용자 관리 상세, 게시글 상세, 건의사항 관리 상세, 공지 관리 상세
$(document).on("click", ".go-page", function() {
  const type = $(this).data("type");
  const typeId= $(this).data("id");
  let url = "";

  switch(type) {
    case "user" :
      url = "admin-user-view.html";
      break;
    case "board" :
      url = "../community/community-view.html";
      break;
    case "feedback" :
      url = "admin-feedback-view.html";
      break;
    case "notice" :
      url = "admin-notice-view.html";
      break;
    default :
      openModal("지원하지 않는 유형입니다.");
      return;
  }

  location.href=url;
});