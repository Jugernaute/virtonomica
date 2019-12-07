// ==UserScript==
// @name           Virtonomica: калькулятор производства
// @namespace      virtonomica
// @version 	   3.28
// @description    Добавляет калькулятор производства в справочные данные игры и в юнит/производство. За основу был взят Открытый помощник для Виртономики (https://virtonomica.ru/olga/forum/forum_new/15/topic/122999/view).
// @include        http*://*virtonomic*.*/*/main/industry/unit_type/info/*
// @include        http*://*virtonomic*.*/*/main/product/info/*
// @include        http*://*virtonomic*.*/*/main/unit/view/*/manufacture
// @include        http*://*virtonomic*.*/*/main/globalreport/marketing/by_products/*
// ==/UserScript==


let calcFunc = function calcProd(editor, productID, productIdx, productionSpec, unitType, ing_id_array) {
    console.log('productionSpec = ' + productionSpec);
    editor.size = ( editor.value.length > 4 ) ? editor.value.length : 3;
    //console.log(editor.id + ' = ' + editor.value);
    setVal(productID+'_'+editor.id, editor.value)
    //резделитель разрядов
    function commaSeparateNumber(val){
        while (/(\d+)(\d{3})/.test(val.toString())){
            val = val.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
        }
        return val;
    }
    function setVal(spName, pValue){
        window.localStorage.setItem(spName, JSON.stringify(pValue));
    }
    function getLocale() {
        return (document.location.hostname === 'virtonomica.ru') ? 'ru' : 'en';
    }
    function getRealm(){
        let svHref = window.location.href;
        let matches = svHref.match(/\/(\w+)\/main\//);
        return matches[1];
    }
    function calcByRecipe(recipe){
        let tech = $('#tech_prod_'+productIdx).val();
        if(tech == null || tech == '' || tech <= 0) return;
        let ingQual = [],
            ingPrice = [],
            ingBaseQty = [],
            ingTotalPrice = [],
            IngTotalCost = 0,
            prodbase_quan = [],
            resultQty     = [],
            Prod_Quantity = [];

        let eff = 1;
        for(let i = 0, len = recipe.rp.length; i < len; ++i){
            //количество товаров производимых 1 человеком
            prodbase_quan[i] = recipe.rp[i].pbq;
            console.log('prodbase_quan'+ i +' = ' + prodbase_quan[i]);
            //итоговое количество товара за единицу производства
            resultQty[i] = recipe.rp[i].rq;
            console.log('resultQty'+ i +' = ' + resultQty[i]);
        }

        recipe.ip.forEach(function(ingredient) {
            ingBaseQty.push(parseFloat(ingredient.q,10));
            console.log('ingBaseQty = ' + parseFloat(ingredient.q,10) );
        });
        let ingCnt = ingBaseQty.length;
        for(let ingIdx = 0; ingIdx < ingCnt; ++ingIdx){
            ingQual.push(parseFloat($('#quality_prod_'+productIdx+'_ing_'+ingIdx).val().replace(',', '.'),10) || 0);
            ingPrice.push(parseFloat($('#price_prod_'+productIdx+'_ing_'+ingIdx).val().replace(',', '.'),10) || 0);
        }

        let unit_quant	= parseFloat($('#unit_qty_prod_'+productIdx).val(),10) || 1;
        let work_quant	= parseFloat($('#worker_qty_prod_'+productIdx).val(),10) * unit_quant;
        let work_salary	= $('#worker_salary_prod_'+productIdx).val().replace(',', '.');
        //квалификация работников
        let PersonalQual = Math.pow(tech, 0.8) ;
        $('#worker_quality_prod_'+productIdx).text(PersonalQual.toFixed(2));

        //качество станков
        let EquipQuan = Math.pow(tech, 1.2) ;
        $('#equip_quality_prod_'+productIdx).text(EquipQuan.toFixed(2));

        let ingQuantity = [];
        let animalQuanPerWorker = recipe.epw;
        console.log('equipPerWorker = ' + animalQuanPerWorker);
        console.log('unitType = ' + unitType);

        //количество ингридиентов
        for (let ingIdx = 0; ingIdx < ingCnt; ingIdx++) {
            //ферма
            if ( unitType === 'farm' ) {
                ingQuantity[ingIdx] = ingBaseQty[ingIdx] * animalQuanPerWorker * work_quant;
            } else {
                ingQuantity[ingIdx] = ingBaseQty[ingIdx] / resultQty[0] * prodbase_quan[0] * work_quant * Math.pow(1.05, tech-1 ) * eff;
            }
            $('#qty_prod_'+productIdx+'_ing_'+ingIdx).text(commaSeparateNumber( Math.ceil( ingQuantity[ingIdx].toFixed(2)) ) + " ед.");
        }

        //цена ингридиентов
        for (let ingIdx = 0; ingIdx < ingCnt; ingIdx++) {
            if (ingPrice[ingIdx] > 0) {
                ingTotalPrice[ingIdx] = ingQuantity[ingIdx] * ingPrice[ingIdx];
            } else {
                ingTotalPrice[ingIdx] = 0;
            }
            $('#total_price_prod_'+productIdx+'_ing_'+ingIdx).text("$" + commaSeparateNumber(ingTotalPrice[ingIdx].toFixed(2)));
        }

        //общая цена ингридиентов
        for (let ingIdx = 0; ingIdx < ingCnt; ingIdx++) {
            IngTotalCost += ingTotalPrice[ingIdx];
        }
        //$("#IngTotalPrice", this).text("$" + commaSeparateNumber(IngTotalCost.toFixed(2)));

        //объем выпускаемой продукции
        for(let i = 0, len = recipe.rp.length; i < len; ++i){
            Prod_Quantity[i] = work_quant * prodbase_quan[i] * Math.pow(1.05, tech-1) *  eff;
            $('#qty_prod_'+productIdx+'_result_' + i).text( commaSeparateNumber( Math.round (Prod_Quantity[i]) ) + " ед." );
        }

        //итоговое качество ингридиентов
        let IngTotalQual = 0;
        let IngTotalQty = 0;
        for (let ingIdx = 0; ingIdx < ingCnt; ingIdx++) {
            IngTotalQual+= ingQual[ingIdx] * ingBaseQty[ingIdx];
            IngTotalQty += ingBaseQty[ingIdx];
        }
        IngTotalQual = IngTotalQual / IngTotalQty * eff;
        //ферма
        if ( unitType === 'farm' ) {
            let animal_Qual = parseFloat($('#animal_qual_prod_'+productIdx).val(),10) || 1;
            IngTotalQual = ( ingQual[0] * 0.3 + animal_Qual * 0.7 ) * eff;
        }

        //качество товара
        let ProdQual = Math.pow(IngTotalQual, 0.5) * Math.pow(tech, 0.65);
        let bonus = parseFloat($('#bonus_prod_'+productIdx+'_result').val().replace('%', ''),10) || 0;
        ProdQual = ProdQual * ( 1 + bonus / 100 );

        //ограничение качества (по технологии)
        if (ProdQual > Math.pow(tech, 1.3) ) {ProdQual = Math.pow(tech, 1.3)}
        if ( ProdQual < 1 ) { ProdQual = 1 }

        //бонус к качеству
        ProdQual = ProdQual * ( 1 + recipe.rp[0].qbp / 100 );
        $('#quality_prod_'+productIdx+'_result_0').text( ProdQual.toFixed(2) ) ;
        //если есть второй продукт производства
        for(let i = 1, len = recipe.rp.length; i < len; ++i){
            let ProdQual2 = Math.pow(IngTotalQual, 0.5) * Math.pow(tech, 0.65)  * ( 1 + recipe.rp[i].qbp / 100 );
            ProdQual2 = ProdQual2 * ( 1 + bonus / 100 );
            if (ProdQual2 > Math.pow(tech, 1.3) ) {ProdQual2 = Math.pow(tech, 1.3)}
            if ( ProdQual2 < 1 ) { ProdQual2 = 1 }
            $('#quality_prod_'+productIdx+'_result_' + i).text( ProdQual2.toFixed(2) ) ;
        }

        //себестоимость
        let zp = work_salary * work_quant;
        let exps = IngTotalCost + zp + zp * 0.1 ;

        if (recipe.rp.length == 3) {
            //Нефтеперегонка
            //Бензин Нормаль-80 - 35%
            $('#price_prod_'+productIdx+'_result_0').text( "$" + commaSeparateNumber((exps / Prod_Quantity[0] * 0.35 ).toFixed(2)) );
            //Дизельное топливо - 30%
            $('#price_prod_'+productIdx+'_result_1').text( "$" + commaSeparateNumber((exps / Prod_Quantity[1] * 0.30 ).toFixed(2)) );
            //Мазут             - 35%
            $('#price_prod_'+productIdx+'_result_2').text( "$" + commaSeparateNumber((exps / Prod_Quantity[2] * 0.35 ).toFixed(2)) );
        } else if (recipe.rp.length == 4) {
            //Ректификация нефти
            //Бензин Нормаль-80 - 35%
            $('#price_prod_'+productIdx+'_result_0').text( "$" + commaSeparateNumber((exps / Prod_Quantity[0] * 0.35 ).toFixed(2)) );
            //Бензин Регуляр-92 - 32%
            $('#price_prod_'+productIdx+'_result_1').text( "$" + commaSeparateNumber((exps / Prod_Quantity[1] * 0.32 ).toFixed(2)) );
            //Дизельное топливо - 23%
            $('#price_prod_'+productIdx+'_result_2').text( "$" + commaSeparateNumber((exps / Prod_Quantity[2] * 0.23 ).toFixed(2)) );
            //Мазут             - 10%
            $('#price_prod_'+productIdx+'_result_3').text( "$" + commaSeparateNumber((exps / Prod_Quantity[3] * 0.10 ).toFixed(2)) );
        } else if (recipe.rp.length == 5) {
            //Каталитический крекинг нефти
            //Бензин Нормаль-80 - 7%
            $('#price_prod_'+productIdx+'_result_0').text( "$" + commaSeparateNumber((exps / Prod_Quantity[0] * 0.07 ).toFixed(2)) );
            //Бензин Премиум-95 - 35%
            $('#price_prod_'+productIdx+'_result_1').text( "$" + commaSeparateNumber((exps / Prod_Quantity[1] * 0.35 ).toFixed(2)) );
            //Бензин Регуляр-92 - 51%
            $('#price_prod_'+productIdx+'_result_2').text( "$" + commaSeparateNumber((exps / Prod_Quantity[2] * 0.51 ).toFixed(2)) );
            //Дизельное топливо - 6%
            $('#price_prod_'+productIdx+'_result_3').text( "$" + commaSeparateNumber((exps / Prod_Quantity[3] * 0.06 ).toFixed(2)) );
            //Мазут             - 1%
            $('#price_prod_'+productIdx+'_result_4').text( "$" + commaSeparateNumber((exps / Prod_Quantity[4] * 0.01 ).toFixed(2)) );
        } else {
            for(let i = 0, len = recipe.rp.length; i < len; ++i){
                $('#price_prod_'+productIdx+'_result_' + i).text( "$" + commaSeparateNumber((exps / Prod_Quantity[i] / len ).toFixed(2)) );
            }
        }

        //прибыль
        let profit = -exps;
        for(let i = 0, len = recipe.rp.length; i < len; ++i){
            let sellPrice = parseFloat($('#sell_price_prod_'+productIdx+'_result_' + i).val(),10) || 0;
            profit +=  sellPrice * Prod_Quantity[i];
        }
        $('#profit_prod_'+productIdx).text( "$" + commaSeparateNumber(profit.toFixed(2)) );
    }
    let locale = getLocale();
    let realm = getRealm();
    let suffix = (locale === 'en') ? '_en' : '';
    let recipe_exist = 0;
    let svRecipeUrl = 'https://cobr123.github.io/industry/'+ realm +'/recipe_'+ productID + suffix +'.json';
    $.getJSON(svRecipeUrl, function (data) {
        console.log(svRecipeUrl);
        $.each(data, function (key, val) {
            if(recipe_exist === 0){
                if(productionSpec === val.s){
                    recipe_exist = 1;
                    calcByRecipe(val);
                } else if (ing_id_array != null && val.ip.length == ing_id_array.length) {
                    recipe_exist = 1;
                    $.each(val.ip, function (key2, val2) {
                        if(!$.inArray( val2.pi, ing_id_array )) {
                            recipe_exist = 0;
                        }
                    });
                    if (recipe_exist === 1) {
                        calcByRecipe(val);
                    }
                }
            }
        });
        if (recipe_exist === 0) {
            console.log('Не найден рецепт для специализации "'+productionSpec+'"');
        }
    })
        .fail(function() {
            console.log('Не найден рецепт для продукта с id "'+productID+'"');
        });
}

let run = function() {

    let win = (typeof(unsafeWindow) != 'undefined' ? unsafeWindow : top.window);
    $ = win.$;

    function getLocale() {
        return (document.location.hostname === 'virtonomica.ru') ? 'ru' : 'en';
    }
    function getRealm(){
        let svHref = window.location.href;
        let matches = svHref.match(/\/(\w+)\/main\//);
        return matches[1];
    }
    function getVal(spName){
        return JSON.parse(window.localStorage.getItem(spName));
    }
    function setVal(spName, pValue){
        window.localStorage.setItem(spName, JSON.stringify(pValue));
    }
    function trim(str) {
        return str.replace(/^\s+|\s+$/g,'');
    }
    function clearBaseQtyNumber(str) {
        str = trim(str.replace('ед.',''));
        let matches = str.match(/(\d+)\/?(\d+)?/);
        let qty = matches[1] / (matches[2]||1);
        return qty;
    }
    function getLast(str){
        let matches = str.match(/\/(\d+)$/);
        return matches[1];
    }
    function getUnitType(imgSrc) {
        switch (imgSrc) {
            case '/img/products/machine.gif':
                return 'factory';
                break;
            case '/img/products/cow.gif':
            case '/img/products/bee.gif':
            case '/img/products/pig.gif':
            case '/img/products/chicken.gif':
            case '/img/products/sheep.gif':
                return 'farm';
                break;
            case '/img/products/tractor.gif':
                return 'plant';
                break;
            default:
                return 'factory';
                break;
        }
    }
    function strToNumber(spSum){
        return parseFloat(spSum.replace('$','').replace(/\s+/g,''),10);
    }

    function shortenNumber(text){
        if ((''+parseFloat(text).toFixed(0)).length > 3) {
            let num = parseFloat(text);
            if (num < 1e+6) {
                num = (num / 1e+3).toFixed(0) + 'k';
            } else if (num < 1e+9) {
                num = (num / 1e+6).toFixed(0) + 'm';
            } else if (num < 1e+12) {
                num = (num / 1e+9).toFixed(0) + 'b';
            } else if (num < 1e+15) {
                num = (num / 1e+12).toFixed(0) + 't';
            } else if (num < 1e+18) {
                num = (num / 1e+15).toFixed(0) + 'q';
            }
            return num;
        } else {
            return text;
        }
    }
    function getCurrDate(){
        return new Date().getDate();
    }
    function updSelectPriceQual(productIdx, ingIdx, ingProdId){
        let svUrl = $('#select_price_qual_prod_'+productIdx+'_ing_'+ingIdx).attr('url');
        console.log(svUrl);
        $.get(svUrl.replace('/globalreport/marketing/by_products/','/common/util/setpaging/reportcompany/marketingProduct/').replace('/marketingProduct/'+ingProdId,'/marketingProduct/20000'), function (dummy) {
            $.get(svUrl, function (data) {
                let avNewData = [];
                let html = $(data);
                console.log('ingProdId = '+ingProdId+', row_cnt = '+$('table.grid > tbody > tr[class]', html).length);
                $('table.grid > tbody > tr[class]', html).each(function(){
                    let row = $(this);
                    //let volume = $('> td:nth-child(3)', row).text();
                    let quality = $('> td:nth-child(4)', row).text();
                    let price = $('> td:nth-child(5)', row).text();
                    //let unidId = $('> td:nth-child(5)', row);
                    avNewData.push({
                        p: strToNumber(price)
                        ,q: strToNumber(quality)
                        ,s: 0
                        //,v: strToNumber(volume)
                        //,ui: unidId
                    });
                });
                let svUrl2 = svUrl.replace('/globalreport/marketing/by_products/','/corporation/products/').replace('/products/'+ingProdId,'/products');
                $.get(svUrl.replace('/globalreport/marketing/by_products/','/common/util/setpaging/alliance/offers/').replace('/offers/'+ingProdId,'/offers/20000'), function (dummy2) {
                    console.log(svUrl.replace('/globalreport/marketing/by_products/','/common/util/setpaging/alliance/offers/').replace('/offers/'+ingProdId,'/offers/20000'));
                    $.post(svUrl.replace('/globalreport/marketing/by_products/','/common/util/setfiltering/alliance/offers/qty=1/id='), function (dummy3) {
                        console.log(svUrl.replace('/globalreport/marketing/by_products/','/common/util/setfiltering/alliance/offers/qty=1/id='));
                        $.get(svUrl2, function (data2) {
                            console.log(svUrl2);
                            let html2 = $(data2);
                            console.log('ingProdId = '+ingProdId+', row_cnt2 = '+ $('table[class^="unit-list-"] > tbody > tr[class]', html2).length);
                            $('table[class^="unit-list-"] > tbody > tr[class]', html2).each(function(){
                                let row = $(this);
                                //let volume = $('> td:nth-child(4)', row).text();
                                let quality = $('> td:nth-child(5)', row).text();
                                let price = $('> td:nth-child(6)', row).text();
                                //let unidId = $('> td:nth-child(5)', row);
                                avNewData.push({
                                    p: strToNumber(price)
                                    ,q: strToNumber(quality)
                                    ,s: 1
                                    //,v: strToNumber(volume)
                                    //,ui: unidId
                                });
                            });
                            setVal('aSelectPriceQual_'+ingProdId, avNewData);
                            setVal('aSelectPriceQual_'+ingProdId+ '_date', getCurrDate());
                            $('select[ingProdId="'+ ingProdId +'"]').each(function(){
                                let sel = $(this);
                                fillSelectPriceQual(sel.attr('productIdx'), sel.attr('ingIdx'), ingProdId);
                            });
                        })
                            .fail(function() {
                                console.log('fail');
                            });
                    })
                        .fail(function() {
                            console.log('fail');
                        });
                })
                    .fail(function() {
                        console.log('fail');
                    });
            })
                .fail(function() {
                    console.log('fail');
                });
        })
            .fail(function() {
                console.log('fail');
            });
    }
    let loading = [];
    function fillSelectPriceQual(productIdx, ingIdx, ingProdId){
        let arr = getVal('aSelectPriceQual_'+ingProdId);
        let arr_date = getVal('aSelectPriceQual_'+ingProdId + '_date');
        if((arr === null || arr_date !== getCurrDate()) && loading[ingProdId] !== 1){
            loading[ingProdId] = 1;
            updSelectPriceQual(productIdx, ingIdx, ingProdId);
        } else if (arr !== null) {
            arr.sort(function(a, b){
                if(a.q - b.q == 0){
                    return a.p - b.p;
                } else {
                    return a.q - b.q;
                }
            });
            let sel = $('#select_price_qual_prod_'+productIdx+'_ing_'+ingIdx);
            let defValQual = $('#'+ sel.attr('id').replace('select_price_qual_prod_','quality_prod_') ).val();
            let defValPrc = $('#'+ sel.attr('id').replace('select_price_qual_prod_','price_prod_') ).val();
            sel.html('<option price="'+ defValPrc +'" quality="'+ defValQual +'" value="">--</option>');
            let showOnlySelfAndCorp = $('#show_only_self_and_corp_prod_'+productIdx+'_ing_'+ingIdx +':checked').length;
            console.log('showOnlySelfAndCorp = ' + showOnlySelfAndCorp);
            for(let i = 0, len = arr.length; i < len; ++i){
                if(showOnlySelfAndCorp === 0 || (showOnlySelfAndCorp === 1 && arr[i]['s'] === 1)){
                    sel.append('<option price="'+ arr[i]['p'] +'" quality="'+ arr[i]['q'] +'" value="0">'+ arr[i]['q'] +' $'+ shortenNumber(arr[i]['p']) +'</option>');
                }
            }
        }
    }
    function addCalcFormToUnitInfo() {
        //table[3]/tbody/tr[2]
        let productIdx = 0;
        $('table[class="grid"]:nth-child(4) > tbody > tr').each(function(){
            let row = $(this);
            let svHref = window.location.href;
            let productNameCell = $('td:nth-child(1) > b', row);
            if(productNameCell != null && productNameCell.text() != '') {
                let productionSpec = productNameCell.text();
                let unitType = getUnitType($(' > td:nth-child(2) > a:nth-child(1) > img', row).attr('src'));
                let productID = getLast($('> td:nth-child(5) > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(1) > td > a:nth-child(1) > img', row).parent().attr('href'));
                let calcFuncCallStr = 'calcProd(this, '+productID+', '+productIdx+', \''+productionSpec+'\', \''+unitType+'\')';

                $('>td:nth-child(2) > a > img', row).each(function(){
                    let machineImg = $(this);
                    //https://virtonomica.ru/olga/main/industry/unit_type/info/422160
                    //https://virtonomica.ru/olga/main/globalreport/technology/422160/13/target_market_summary/03-05-2015/bid
                    let svDate = new Date().toISOString().slice(0, 10);
                    let svBidHref = svHref.replace('/industry/unit_type/info/','/globalreport/technology/') + '/2/target_market_summary/'+svDate+'/bid';
                    let defValTech = getVal(productID+'_tech_prod_'+productIdx) || 1;
                    let inputTech = '<br><a href="'+svBidHref+'" onclick="return doWindow(this, 1000, 700);">Техна</a> <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="tech_prod_'+productIdx+'" value="'+defValTech+'">';

                    let equipMarketLink = machineImg.parent().attr('href').replace('/product/info/','/globalreport/marketing/by_products/');
                    let equipMarketLinkOpt = ' href="'+equipMarketLink+'" onclick="return doWindow(this, 1000, 800);"';
                    let inputAnimalQual = '';
                    let labelEquipQual = '';
                    if (unitType === 'farm') {
                        let defValAnimalQual = getVal(productID+'_animal_qual_prod_'+productIdx) || 1;
                        inputAnimalQual = '<br><a'+equipMarketLinkOpt+'>Кач.</a> <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="animal_qual_prod_'+productIdx+'" value="'+defValAnimalQual+'">';
                    } else {
                        labelEquipQual = '<br><a'+equipMarketLinkOpt+'>Станки</a> <b id="equip_quality_prod_'+productIdx+'">0</b>';
                    }
                    machineImg.parent().after(inputTech + inputAnimalQual + labelEquipQual);
                });
                let ingIdx = 0;
                $('>td:nth-child(3) > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td', row).each(function(){
                    let qtyCell = $(this);
                    let minQuality = $('> div.resultmessageerror > nobr > b', qtyCell);
                    let minQualityStr = '';
                    if(minQuality.length > 0){
                        minQualityStr = '<font color="red">Мин. кач. <b>' + minQuality.text() + '</b></font><br>';
                    }

                    let labelTotalPrice = '<tr><td align="center" id="total_price_prod_'+productIdx+'_ing_'+ingIdx+'">0.00</td></tr>';
                    //https://virtonomica.ru/olga/main/product/info/422132
                    //https://virtonomica.ru/olga/main/globalreport/marketing/by_products/422714/
                    let tmp = qtyCell.parent().parent().children().first().children().first().children().first();
                    //console.log(tmp.attr('href'));
                    let ingProdId = getLast(tmp.attr('href'));
                    let productMarketLink = tmp.attr('href').replace('/product/info/','/globalreport/marketing/by_products/');
                    let productMarketLinkOpt = ' href="'+productMarketLink+'" onclick="return doWindow(this, 1000, 800);"';
                    //https://virtonomica.ru/olga/main/globalreport/product_history/1462
                    let productHistoryLink = tmp.attr('href').replace('/product/info/','/globalreport/product_history/');
                    let productHistoryLinkOpt = ' href="'+productHistoryLink+'" onclick="return doWindow(this, 1000, 800);"';

                    let defValQual = getVal(productID+'_quality_prod_'+productIdx+'_ing_'+ingIdx) || 1;
                    let inputQualityRow = '<tr><td align="left">'+ minQualityStr +'<a'+productHistoryLinkOpt+'>Кач.</a> <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="quality_prod_'+productIdx+'_ing_'+ingIdx+'" value="'+defValQual+'"></td></tr>';

                    let defValPrc = getVal(productID+'_price_prod_'+productIdx+'_ing_'+ingIdx) || 1;
                    let inputPriceRow = '<tr><td align="left"><a'+productMarketLinkOpt+'>Цена</a><input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="price_prod_'+productIdx+'_ing_'+ingIdx+'" value="'+defValPrc+'"></td></tr>';

                    let showOnlySelfAndCorp = '<input type="checkbox" title="Только свои и корпоративные" id="show_only_self_and_corp_prod_'+productIdx+'_ing_'+ingIdx +'" productIdx="'+ productIdx +'" ingIdx="'+ ingIdx +'" ingProdId="'+ ingProdId +'">';

                    let selectPriceQual = '<tr><td><select id="select_price_qual_prod_'+productIdx+'_ing_'+ingIdx +'" productIdx="'+ productIdx +'" ingIdx="'+ ingIdx +'" ingProdId="'+ ingProdId +'" url="'+productMarketLink+'"></select>&nbsp;<label for="show_only_self_and_corp_prod_'+productIdx+'_ing_'+ingIdx +'">корп.</label>'+showOnlySelfAndCorp+'</td></tr>';
                    qtyCell.attr('id','qty_prod_'+productIdx+'_ing_'+ingIdx);
                    qtyCell.parent().after(labelTotalPrice + inputQualityRow + inputPriceRow + selectPriceQual);

                    $('#select_price_qual_prod_'+productIdx+'_ing_'+ingIdx).hover( function(){
                        let sel2 = $(this);
                        if($('> option', sel2).length === 0) {
                            $('select[ingProdId="'+ sel2.attr('ingProdId') +'"]').each(function(){
                                let sel = $(this);
                                if($('> option', sel).length === 0) {
                                    console.log('productIdx = '+ sel.attr('productIdx') +', ingIdx = '+ sel.attr('ingIdx') +', ingProdId = '+ sel.attr('ingProdId') );
                                    sel.html('<option>loading</option>');
                                    fillSelectPriceQual(sel.attr('productIdx'), sel.attr('ingIdx'), sel.attr('ingProdId'));
                                }
                            });
                        }
                    });
                    $('#select_price_qual_prod_'+productIdx+'_ing_'+ingIdx).change( function(){
                        let sel = $(this);
                        let opt = $('> option:selected', sel);
                        //console.log('id = '+ sel.attr('id') +', ingProdId = '+ sel.attr('ingProdId') +', price = '+ opt.attr('price') +', quality = '+ opt.attr('quality') +', quantity = '+ opt.attr('quantity') );
                        $('#'+ sel.attr('id').replace('select_price_qual_prod_','price_prod_') ).val(opt.attr('price'));
                        $('#'+ sel.attr('id').replace('select_price_qual_prod_','quality_prod_')).val(opt.attr('quality'));
                        //
                        $('#'+ sel.attr('id').replace('select_price_qual_prod_','price_prod_')).keyup();
                        $('#'+ sel.attr('id').replace('select_price_qual_prod_','quality_prod_')).keyup();
                    });
                    $('#show_only_self_and_corp_prod_'+productIdx+'_ing_'+ingIdx).click( function(){
                        let btn = $(this);
                        fillSelectPriceQual(btn.attr('productIdx'), btn.attr('ingIdx'), btn.attr('ingProdId'));
                    });
                    ++ingIdx;
                });
                let resultIdx = 0;
                let resultQtyCell = null;
                $('>td:nth-child(5) > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td', row).each(function(){
                    resultQtyCell = $(this);
                    let tmp = resultQtyCell.parent().parent().children().first().children().first().children().first();
                    //console.log(tmp.attr('href'));
                    let productMarketLink = tmp.attr('href').replace('/product/info/','/globalreport/marketing/by_products/');
                    let productMarketLinkOpt = ' href="'+productMarketLink+'" onclick="return doWindow(this, 1000, 800);"';
                    //https://virtonomica.ru/olga/main/globalreport/product_history/1462
                    let productHistoryLink = tmp.attr('href').replace('/product/info/','/globalreport/product_history/');
                    let productHistoryLinkOpt = ' href="'+productHistoryLink+'" onclick="return doWindow(this, 1000, 800);"';
                    //let productBaseQty = clearQtyNumber(resultQtyCell.text());
                    //console.log(productBaseQty);
                    let resultQualityRow = '<tr><td align="left"><a'+productHistoryLinkOpt+'>Кач.:</a> <b id="quality_prod_'+productIdx+'_result_'+resultIdx+'">1</b></td></tr>';
                    let resultPriceRow = '<tr><td align="left">C/c: <b id="price_prod_'+productIdx+'_result_'+resultIdx+'">0</b></td></tr>';
                    //let resultQtyRow = '<tr><td align="left">Кол-во: <b id="qty_prod_'+productIdx+'_result_'+resultIdx+'">0</b></td></tr>';
                    //
                    let defValSellPrc = getVal(productID+'_sell_price_prod_'+productIdx+'_result_'+resultIdx) || 0;
                    let resultSellPriceRow = '<tr><td align="left"><a'+productMarketLinkOpt+'>Цена</a> <input onKeyUp="'+calcFuncCallStr+'" type="text" size="4" id="sell_price_prod_'+productIdx+'_result_'+resultIdx+'" value="'+defValSellPrc+'"></td></tr>';
                    //alert(resultQtyRow);
                    resultQtyCell.attr('id','qty_prod_'+productIdx+'_result_'+resultIdx);
                    resultQtyCell.parent().after(resultQualityRow + /*resultQtyRow +*/ resultPriceRow + resultSellPriceRow);
                    ++resultIdx;
                });
                let defBonus = getVal(productID+'_bonus_prod_'+productIdx+'_result') || 0;
                let inputBonus = '<tr><td align="left" colspan="'+resultIdx+'">Бонус к качеству <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="bonus_prod_'+productIdx+'_result" value="'+defBonus+'"></td></tr>';

                let resultProfitRow = '<tr><td align="left" colspan="'+resultIdx+'">Прибыль: <b id="profit_prod_'+productIdx+'">0</b></td></tr>';
                resultQtyCell.parent().parent().parent().parent().parent().after(inputBonus).after(resultProfitRow);

                let defValUnitQty = getVal(productID+'_unit_qty_prod_'+productIdx) || 1;
                let inputUnitQty = '<br>Кол-во юнитов <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="unit_qty_prod_'+productIdx+'" value="'+defValUnitQty+'">';

                let defValWorkerQty = getVal(productID+'_worker_qty_prod_'+productIdx) || 50;
                let inputWorkerQty = '<br>Кол-во рабочих <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="worker_qty_prod_'+productIdx+'" value="'+defValWorkerQty+'">';

                let defValWorkerSal = getVal(productID+'_worker_salary_prod_'+productIdx) || 300;
                let inputWorkerSalary = '<br>Зп. <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="worker_salary_prod_'+productIdx+'" value="'+defValWorkerSal+'">';

                let workerQuality = '<br>Квалификация <b id="worker_quality_prod_'+productIdx+'">0</b>';
                let inputIngQty = '<input type="hidden" value="'+ingIdx+'" id="ing_qty_prod_'+productIdx+'">';
                let inputResultQty = '<input type="hidden" value="'+resultIdx+'" id="result_qty_prod_'+productIdx+'">';
                let resultQualBonus = $('>td:nth-child(6)', row).text().replace(/[\s+\-%]+/g,'') || 0;
                //console.log(resultQualBonus);
                productNameCell.after(inputUnitQty + inputWorkerQty + inputWorkerSalary + workerQuality + inputIngQty + inputResultQty);
                $('input#worker_qty_prod_'+productIdx).keyup();

                ++productIdx;
            }
        });
    }

    let sagMaterialImg = null;
    function loadProductImgs(callback) {
        let realm = getRealm();
        let locale = getLocale();
        let suffix = (locale === 'en') ? '_en' : '';
        let svUrl = 'https://cobr123.github.io/industry/'+ realm +'/materials'+ suffix +'.json';
        $.getJSON(svUrl, function (data) {
            sagMaterialImg = [];
            $.each(data, function (key, val) {
                sagMaterialImg[val.i] = val.s;
            });
            if(typeof(callback) === 'function') callback();
        });
        return false;
    }
    let productIdx = 0;
    function addFormByRecipe(productID, productLink, opRecipe) {
        let calc_table_content = '';
        let calc_table_row = '';
        let productionSpec = opRecipe.s;
        let unitType = 'factory';//getUnitType($(' > td:nth-child(2) > a:nth-child(1) > img', row).attr('src'));
        let calcFuncCallStr = 'calcProd(this, '+productID+', '+productIdx+', \''+productionSpec+'\', \''+unitType+'\', '+ '[]'+')';

        let defValUnitQty = getVal(productID+'_unit_qty_prod_'+productIdx) || 1;
        let inputUnitQty = '<br>Кол-во юнитов <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="unit_qty_prod_'+productIdx+'" value="'+defValUnitQty+'">';

        let defValWorkerQty = getVal(productID+'_worker_qty_prod_'+productIdx) || 50;
        let inputWorkerQty = '<br>Кол-во рабочих <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="worker_qty_prod_'+productIdx+'" value="'+defValWorkerQty+'">';

        let defValWorkerSal = getVal(productID+'_worker_salary_prod_'+productIdx) || 300;
        let inputWorkerSalary = '<br>Зп. <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="worker_salary_prod_'+productIdx+'" value="'+defValWorkerSal+'">';

        let workerQuality = '<br>Квалификация <b id="worker_quality_prod_'+productIdx+'">0</b>';

        let defValTech = getVal(productID+'_tech_prod_'+productIdx) || 1;
        let inputTech = '<br>Техна <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="tech_prod_'+productIdx+'" value="'+defValTech+'">';

        //https://virtonomica.ru/olga/main/globalreport/marketing/by_products/301320
        //https://virtonomica.ru/olga/main/product/info/301320
        let productInfoLink = productLink.attr('href').replace('/globalreport/marketing/by_products/','/product/info/');
        let labelEquipQual = '<br>Станки <b id="equip_quality_prod_'+productIdx+'">0</b>';
        calc_table_row += '<td><a target="blank" href="'+productInfoLink+'"><b>'+ productionSpec+'</b></a>' +inputUnitQty + inputWorkerQty + inputWorkerSalary + workerQuality+ '</td>';
        calc_table_row += '<td>'+ inputTech + labelEquipQual + '</td>';

        let ingIdx = 0;
        let ingCell = '';
        opRecipe.ip.forEach(function(ingredient) {
            let defValQual = getVal(productID+'_quality_prod_'+productIdx+'_ing_'+ingIdx) || 1;
            let inputQualityRow = '<tr><td align="left">Кач. <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="quality_prod_'+productIdx+'_ing_'+ingIdx+'" value="'+defValQual+'"></td></tr>';

            let defValPrc = getVal(productID+'_price_prod_'+productIdx+'_ing_'+ingIdx) || 1;
            let inputPriceRow = '<tr><td align="left">Цена <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="price_prod_'+productIdx+'_ing_'+ingIdx+'" value="'+defValPrc+'"></td></tr>';

            let imgSrc = sagMaterialImg[ingredient.pi].replace('/img/products/','/img/products/16/');
            let ingImg = '<tr><td align="center"><img src="'+ imgSrc +'" align="middle"></td></tr>'
            let labelTotalQty = '<tr><td align="center" id="qty_prod_'+productIdx+'_ing_'+ingIdx+'">0</td></tr>';
            let labelTotalPrice = '<tr><td align="center" id="total_price_prod_'+productIdx+'_ing_'+ingIdx+'">0.00</td></tr>';

            ingCell += '<td><table>' + ingImg + labelTotalQty + inputQualityRow + inputPriceRow + labelTotalPrice +'</table></td>';
            ++ingIdx;
        });
        calc_table_row += '<td><table><tr>'+ ingCell + '</tr></table></td>';
        let resultIdx = 0;
        let resultQtyCell = null;
        let resultCell = '';

        let resultQtyRow = '<tr><td align="left">Кол-во: <b id="qty_prod_'+productIdx+'_result_'+resultIdx+'">0</b></td></tr>';
        let resultQualityRow = '<tr><td align="left">Кач.: <b id="quality_prod_'+productIdx+'_result_'+resultIdx+'">1</b></td></tr>';
        let resultPriceRow = '<tr><td align="left">C/c: <b id="price_prod_'+productIdx+'_result_'+resultIdx+'">0</b></td></tr>';
        //
        let defValSellPrc = getVal(productID+'_sell_price_prod_'+productIdx+'_result_'+resultIdx) || 0;
        let resultSellPriceRow = '<tr><td align="left">Цена <input onKeyUp="'+calcFuncCallStr+'" type="text" size="4" id="sell_price_prod_'+productIdx+'_result_'+resultIdx+'" value="'+defValSellPrc+'"></td></tr>';

        resultCell = '<table>' + resultQtyRow + resultQualityRow + resultPriceRow + resultSellPriceRow +'</table>';
        ++resultIdx;

        let defBonus = getVal(productID+'_bonus_prod_'+productIdx+'_result') || 0;
        let inputBonus = '<br>Бонус к качеству <input onKeyUp="'+calcFuncCallStr+'" type="text" size="3" id="bonus_prod_'+productIdx+'_result" value="'+defBonus+'">';
        let resultProfitRow = '<br>Прибыль: <b id="profit_prod_'+productIdx+'">0</b>';
        //
        calc_table_row += '<td>'+ resultCell + resultProfitRow + inputBonus + '</td>';
        //
        let svRowClass = ((productIdx+1) % 2) ? 'class="even"' : 'class="odd"';
        calc_table_content += '<tr '+svRowClass+'>'+ calc_table_row + '</tr>';

        $('table#calc_panel > tbody > tr').last().after(calc_table_content);
        $('input[id=worker_qty_prod_'+ productIdx +']').keyup();
        ++productIdx;
    }

    function addCalcFormToOldUnitInfo() {
        //table[3]/tbody/tr[2]
        let productIdx = 0;
        let ing_id_array = [];
        $('form > table.list > tbody > tr > td:nth-child(2) > input[type="checkbox"]').each(function(){
            let matches = $(this).attr('name').match(/\[(\d+)\]$/);
            ing_id_array.push(matches[1]);
        });
        let locale = getLocale();
        let realm = getRealm();
        let suffix = (locale === 'en') ? '_en' : '';

        let calc_table = '<table width="100%" class="list" id="calc_panel"><tbody><tr><th>Специализация</th><th>Оборудование</th><th>Сырьё</th><th>Продукция</th></tr></tbody></table>';
        $('table.buttonset').after(calc_table);
        //
        $('table.grid > tbody > tr > td[title] > a:has(img)').each(function(){
            let productLink = $(this);
            let row = productLink.parent();
            let productNameCell = productLink;
            if(productNameCell != null && productNameCell.text() != '') {
                let productID = getLast(productLink.attr('href'));
                //
                let svRecipeUrl = 'https://cobr123.github.io/industry/'+ realm +'/recipe_'+ productID + suffix +'.json';
                $.getJSON(svRecipeUrl, function (data) {
                    console.log(svRecipeUrl);
                    $.each(data, function (key, val) {
                        addFormByRecipe(productID, productLink, val);
                    });
                })
                    .fail(function() {
                        console.log('Не найден рецепт для продукта с id "'+productID+'"');
                    });
            }
        });
    }
    //если страница информации о заводе
    //https://virtonomica.ru/olga/main/industry/unit_type/info/423170
    if (/\w*virtonomic\w+.\w+\/\w+\/main\/industry\/unit_type\/info\/\d+/.test(window.location)) {
        addCalcFormToUnitInfo();
    }
    //если страница информации о производстве продукта и того что из него производят
    //https://virtonomica.ru/olga/main/product/info/423151
    if (/\w*virtonomic\w+.\w+\/\w+\/main\/product\/info\/\d+/.test(window.location)) {
    }
    //добавляем калькулятор на закладке "производство" в подразделении
    //https://virtonomica.ru/olga/main/unit/view/5452988/manufacture
    if (/\w*virtonomic\w+.\w+\/\w+\/main\/unit\/view\/\d+\/manufacture/.test(window.location)) {
        if($('table.list').length >= 2) {
            //старый интерфейс
            loadProductImgs(addCalcFormToOldUnitInfo);
        } else {
            //новый интерфейс
            //addCalcFormToNewUnitInfo();
        }
    }
}

if(window.top == window) {
    let script = document.createElement("script");
    script.textContent = calcFunc.toString() + '(' + run.toString() + ')();';
    document.documentElement.appendChild(script);
}