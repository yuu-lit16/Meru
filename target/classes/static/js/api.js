$(function () {

    $.ajax({
        type: "get",
        url: "/getJsonOfIndexItemList",
        dataType: "json"
    }).then(function (data) {

        for (var value of data) {

            if (value.brand == null) {
                $(".appendClass").append(

                    "<tr>"
                    + "<td class='item-value'><button type='button' class='" + "view_botton" + "' value='" + value.id + "'>"
                    + "view detail"
                    + "</td>"
                    + "<td class='item-name'>"
                    + value.name
                    + "</td>"
                    + "<td class='item-price'>"
                    + value.price
                    + "</td>"
                    + "<td class='item-category'>"
                    + value.category
                    + "</td>"
                    + "<td class='item-brand'>"
                    + ""
                    + "</td>"
                    + "<td class='item-condition'>"
                    + value.condition
                    + "</td>"
                    + "</tr>"
                )

            } else {

                $(".appendClass").append(

                    "<tr>"
                    + "<td class='item-value'><button type='button' class='" + "view_botton" + "' value='" + value.id + "'>"
                    + "view detail"
                    + "</td>"
                    + "<td class='item-name'>"
                    + value.name
                    + "</td>"
                    + "<td class='item-price'>"
                    + value.price
                    + "</td>"
                    + "<td class='item-category'>"
                    + value.category
                    + "</td>"
                    + "<td class='item-brand'>"
                    + value.brand
                    + "</td>"
                    + "<td class='item-condition'>"
                    + value.condition
                    + "</td>"
                    + "</tr>"
                )
            }
        }
    }, function () {
    });
});

$(document).on('click', '.view_botton', function () {
    var url = "http://172.16.100.30:8080/merucariAPI/merucariAPI/detail?id=" + $(this).val();

    $.ajax({
        type: "get",
        url: url,
        dataType: "json"
    }).then(function (data) {
        console.log(data);

        $(".appendClass").html(
                "<tr>"
        	    + "<td class='item-value'><button type='button' class=view_botton>"
            + ""
            + "</td>"
            + "<td class='item-name'>"
            + data.name
            + "</td>"
            + "<td class='item-price'>"
            + data.price
            + "</td>"
            + "<td class='item-category'>"
            + data.category
            + "</td>"
            + "<td class='item-brand'>"
            + data.brand
            + "</td>"
            + "<td class='item-condition'>"
            + data.condition
            + "</td>"
            + "</tr>"

        )

    }, function () {
        });
});