$('.test_tour_main').on('click', function(){
   var cityName = $(this).attr('id')
   console.log(cityName);
   citiesDetail(cityName);

});

function citiesDetail(cityName){
    location.href=`/store/tour/cities?cityName=${cityName}`;
}
$('.flight_rank_card').on('click', function(){
    var index = $(this).index();
    easyFlightSearch(index);
});
function easyFlightSearch(index) {
    var startAirport = "";
    var endAirport = "";

    var now = new Date();
    var startDate = new Date(now.setDate(now.getDate()+7))
    var year = startDate.getFullYear();
    var month = String(startDate.getMonth() + 1).padStart(2, "0");
    var date = String(startDate.getDate()).padStart(2, "0");

    startDate = year + "-" + month + "-" + date;

    switch (index) {
        case 0:
            startAirport = "NAARKSS";
            endAirport = "NAARKPC";
            break;
        case 1:
            startAirport = "NAARKSS";
            endAirport = "NAARKPK";
            break;
        case 2:
            startAirport = "NAARKSS";
            endAirport = "NAARKJY";
            break;
        case 3:
            startAirport = "NAARKPC";
            endAirport = "NAARKTU";
            break;
    }
    console.log(index);
    console.log(startAirport);
    console.log(endAirport);

    location.href="/store/flight/flights?startAirport=" + startAirport + "&endAirport=" + endAirport + "&startDate=" + startDate + "&pageNo=" + 1;
}