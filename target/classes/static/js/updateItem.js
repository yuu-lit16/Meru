/** 初めてアクセスされた際に親Categoryを取得してセレクトボックスに表示する - */
$(function () {

    $("select option").attr("selected", false);

    $.ajax({
        type: "get",
        url: "/getJsonOfParentCategory",
        dataType: "json",
    }).then(function (data) {

        console.log(data);

        $("#parent_category").html("<option value='0'>- parentCategory -</option>");

        for (var value of data) {

            $("#parent_category").append(

                "<option value='" + value.id + "'>" + value.name + "</option>"
            )
        }

    }, function () {
    });
});


// *親カテゴリー* がデフォルト以外に変わったら発生
$(document).on('change', '#parent_category', function () {

    $("select option").attr("selected", false);
    

    var parentValue = $("#parent_category").val();

    if (parentValue == '0') {

        $("#child_category").empty();
        $("#child_category").append(
            "<option>parentCategoryを選択してください</option>"
        );

    } else {

        $.ajax({
        	type: "get",
            url: "/getJsonOfChildCategory",
            dataType: "json",
            data: { "parentId": parentValue }
        }).then(function (data) {

            $("#child_category").html("<option value='0'>- childCategory -</option>");

            for (var value of data) {

                $("#child_category").append(

                    "<option value='" + value.id + "'>" + value.name + "</option>"
                )
            }
        }, function () {
        });
    }
});


// *子カテゴリー* がデフォルト以外に変わったら発生
$(document).on('change', '#child_category', function () {

    $("select option").attr("selected", false);
    
    var childValue = $("#child_category").val();

    if (childValue == '0') {

        $("#grandchild_category").empty();
        $("#grandchild_category").append(
            "<option>childCategoryを選択してください</option>"
        );

    } else {

        $.ajax({
            // type: "POST",
        	type: "get",
            url: "/getJsonOfGrandChildCategory",
            dataType: "json",
            data: { "parentId": childValue }
        }).then(function (data) {

            $("#grandchild_category").html("<option value='0'>- grandChild -</option>");

            for (var value of data) {

                $("#grandchild_category").append(

                    "<option value='" + value.id + "'>" + value.name + "</option>"
                )
            }
        }, function () {
        });
    }
});


// *孫カテゴリー* がデフォルト以外に変わったら発生
$(document).on('change', '#grandchild_category', function () {

    $("select option").attr("selected", false);
    
    var grandChildValue = $("#grandchild_category").val();

    if (grandChildValue == '0') {

        $("#select_category").empty();
        $("#select_category").append(
            "<option>Categoryを選択してください</option>"
        );

    } else {

        $.ajax({
        	type: "get",
            url: "/getJsonOfBrand",
            dataType: "json",
            data: { "parentId": grandChildValue }
        }).then(function (data) {

            $("#select_category").html("<option>- brand -</option>");

            for (var value of data) {

                $("#select_category").append(

                    "<option value='" + value.brand + "'>" + value.brand + "</option>"
                )
            }
        }, function () {
        });
    }
});
