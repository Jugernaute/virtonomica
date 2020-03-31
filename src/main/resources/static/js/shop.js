$('.table tr').click(function () {
    let unitId = $(this).find("th:first-child").text();
    let unitTypeId = $(this).find("th:nth-child(2)").text();
    $.ajax({
        url: '/company/units/shop/',
        data: {unitId: unitId, unitTypeId:unitTypeId},
        type: 'get',
        success: function (result) {
            console.log("ok");
        }
    });
    console.log(unitId);
    console.log(unitTypeId);
});

$('ul').click(function () {
    let text = $(this).find('li> a> span').text();
    console.log(text);
});