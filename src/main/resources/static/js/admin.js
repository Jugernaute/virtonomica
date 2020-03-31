

$('#company').click(function () {
    $.ajax({
        url: '/admin/company',
        type: 'get',
        success: function (result) {
            console.log(result);
        }
    })
});


$('#regions').click(function () {
    $.ajax({
        url: 'https://virtonomica.ru/api/lien/main/geo/city/browse',
        type: 'get',
    success: function (result) {
        let region = [];
        for (let el in result) {
            region.push(result[el]);
        }
        $.ajax({
            url: '/admin/regionsAndCountries',
            data: JSON.stringify(result),
            contentType: "application/json; charset=utf-8",
            type: 'post',
            error: function (err) {
                console.log(err);
            }
            });
        }
    })
});

$('#companyUnits').click(function () {
    $.ajax({
        url: '/admin/company/units',
        type: 'get',
        success: function (result) {
            console.log(result);
        }
    })
});

//main product
$('#mainProduct').click(function () {
    $.getJSON('https://virtonomica.ru/api/lien/main/product/browse',function (result) {
        $.ajax({
            url: '/admin/main_products',
            contentType: "application/json; charset=utf-8",
            type: 'post',
            data: JSON.stringify(result),
            error: function (result) {
            },
            success: function (result) {
                console.log(result);
            }
        })
    });
});

//retail product
$('#retailProduct').click(function () {
    $.getJSON('https://virtonomica.ru/api/lien/main/product/goods', function (result) {
        $.ajax({
            url: '/admin/retail_products',
            contentType: "application/json; charset=utf-8",
            type: 'post',
            data: JSON.stringify(result),
            error: function (result) {
            },
            success: function (result) {
                console.log(result);
            }
        })
    });
});

$('#industry').click(function () {
            $.getJSON('https://virtonomica.ru/api/lien/main/unittype/browse', function (result) {
                console.log(result);
                $.ajax({
                    url: '/admin/industry',
                    contentType: "application/json; charset=utf-8",
                    type: 'post',
                    data: JSON.stringify(result)
                });
            })
});

$('#downloadImage').click(function () {
    $.ajax({
        url:"/admin/image/download/"
    })
});

function unitsReport() {
    console.log("ok");
}

$('#test').click(function () {
    let url = "https://cobr123.github.io/industry/lien/recipe_312797.json";
    $.getJSON(url, function (data) {
        $.each(data,function (k, v) {
            console.log(k);
            console.log(v);
            console.log("====================");
        })
    });

})
