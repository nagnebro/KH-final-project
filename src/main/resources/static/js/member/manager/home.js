// 일별 가입자 수 현황
const ctx = document.getElementById('myChart');
$.ajax({

    method: 'POST',
    data: "",
    url: '/test/getUserChart',
    success: function (data) {


        var daycountList = data.countList;
        var dayList = getdayList(daycountList.length);
        var ageMap = data.ageMap;// circle chart에 보여질값.


        //
        showChart(daycountList, dayList);
        showCircleChart(ageMap);

    }, error() {

        console.log("bye")
    }
})

function getdayList(daylength) { // 오늘부터 이전 10일까지의 날짜값은 직접 포매팅해야한다.

    var dayList = [];
    const date = new Date();

    date.setDate(date.getDate() - daylength);

    for (var i = 0; i < daylength; i++) {

        date.setDate(date.getDate() + 1);

        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const formattedDate = `${month}-${day}`; // "2023-08-31"

        dayList.push(formattedDate);

    }
    return dayList;
}

function showChart(countList, dayList) {

    console.log(countList);
    new Chart(ctx, {

        type: 'line',
        data: {
            labels: dayList,
            datasets: [{
                label: '일별 가입자 수 현황',
                data: countList,
                borderWidth: 2
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: false
                }
            }
        }
    });

}


function showCircleChart(ageMap) {


    anychart.onDocumentReady(function () {
        // create pie chart with passed data
        var chart = anychart.pie([
            ['10대', ageMap.ten],
            ['20대', ageMap.two],
            ['30대', ageMap.three],
            ['40대', ageMap.four],
            ['50대', ageMap.five],
            ['60대', ageMap.six],
            ['70대', ageMap.seven]
        ]);


        // set chart labels position to outside
        // set legend title settings
        chart.title('');
        // set chart labels position to outside
        chart.labels().position('outside');
        // set legend title settings
        chart
            .legend()
            .title()
            .enabled(true)
            .text('회원 연령대 비율')
            .padding([0, 0, 10, 0]);

        // set legend position and items layout
        chart
            .legend()
            .position('center-bottom')
            .itemsLayout('horizontal')
            .align('center');
        chart.innerRadius('30%');

        // set container id for the chart
        chart.container('container');
        // initiate chart drawing
        chart.draw();
    });


}