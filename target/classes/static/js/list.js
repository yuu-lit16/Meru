var totalPageNum = 0;
var flag = false;
var counter = 1;

/** ItemList表示機能 - 初めてアクセスされた際にitemを表示する - */
$(function () {

	$.ajax({
		type: "get",
		url: "/getJsonOfIndexItemList",
		dataType: "json"
	}).then(function (data) {
		console.log(data);

		for (var value of data) {
			if (value.brand == null) {
				$(".appendClass").append(

					"<tr>"
					+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
					+ value.name
					+ "</a></td>"
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
					+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
					+ value.name
					+ "</a></td>"
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



/** ページネーション機能  - 初めてアクセスされた際に総ページ数を算出 - */
$(function () {

	var totalDataNum = 0;

	//　商品の総数を取得
	$.ajax({
		type: "get",
		url: "/getTotalItem",
		dataType: "json"
	}).then(function (data) {

		totalDataNum = data;
		totalPageNum = Math.ceil(totalDataNum / 30);
		$(".input-group-addon").html(
			"/" + totalPageNum
		)

	}, function () {
	});

	var selectVal = $("#select_test").val();
});


/** ページネーション機能  - next or prev が押された時に発生 - */
$(function () {

	$(select_page_form).val(counter);

	// next が押されたら +1
	$(".nextA").click(function () {

		// ページ総数処理 -------------------
		if (flag) {
			$(".input-group-addon").html(
				"/" + totalPageNum
			)
			flag = false;
		}
		//----------------------------------

		if (counter >= totalPageNum) {
			counter = totalPageNum;
			$(select_page_form).val(counter);
		} else {

			counter += 1;
			console.log(counter);
			$(select_page_form).val(counter);

			$.ajax({
				type: "get",
				url: "/paging",
				dataType: "json",
				data: { "counter": counter },
			}).then(function (data) {

				$(".appendClass").empty();

				for (var value of data) {

					if (value.brand == null) {
						$(".appendClass").append(

							"<tr>"
							+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
							+ value.name
							+ "</a></td>"
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
							+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
							+ value.name
							+ "</a></td>"
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
		}
	});


	// prev が押されたら -1
	$(".prevA").click(function () {

		// ページ総数処理 -------------------
		if (flag) {
			$(".input-group-addon").html(
				"/" + totalPageNum
			)
			flag = false;
		}
		//----------------------------------


		if (counter < 1 || counter == 1) {
			counter = 1;
			$(select_page_form).val(counter);
		} else {
			counter -= 1;
			console.log(counter);
			$(select_page_form).val(counter);

			$.ajax({
				type: "get",
				url: "/paging",
				dataType: "json",
				data: { "counter": counter },
			}).then(function (data) {

				$(".appendClass").empty();

				for (var value of data) {

					if (value.brand == null) {
						$(".appendClass").append(

							"<tr>"
							+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
							+ value.name
							+ "</a></td>"
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
							+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
							+ value.name
							+ "</a></td>"
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

		}

	});

});


/** ページ選択遷移機能 */
$("#go_btn").click(function () {


	// ページ総数処理 -------------------
	if (flag) {
		$(".input-group-addon").html(
			"/" + totalPageNum
		)
		flag = false;
	}
	//----------------------------------

	var select_page = $("#select_page_form").val();

	// 数値チェック
	if (isNaN(select_page)) {
		alert("数値を入力してください");
		return;
	}


	alert($("#select_page_form").val() + "ページ目へ移動します");

	$.ajax({
		type: "get",
		url: "/selectPaging",
		dataType: "json",
		data: { "selectPageNum": $("#select_page_form").val() },
	}).then(function (data) {

		counter = parseInt($("#select_page_form").val());
		console.log(counter);

		$(".appendClass").empty();

		for (var value of data) {


			if (value.brand == null) {
				$(".appendClass").append(

					"<tr>"
					+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
					+ value.name
					+ "</a></td>"
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
					+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
					+ value.name
					+ "</a></td>"
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

	return false;
});


/** 検索機能 - 初めてアクセスされた際に親Categoryを取得してセレクトボックスに表示する - */
$(function () {

	$.ajax({
		type: "get",
		url: "/getJsonOfParentCategory",
		dataType: "json",
	}).then(function (data) {

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

	var childValue = $("#child_category").val();

	if (childValue == '0') {

		$("#grandchild_category").empty();
		$("#grandchild_category").append(
			"<option>childCategoryを選択してください</option>"
		);

	} else {

		$.ajax({
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

					"<option value='" + value + "'>" + value + "</option>"
				)
			}
		}, function () {
		});
	}
});


// 検索用フォームがクリックされたら発動
$(document).on('click', '#serch_button', function () {

	// ページ総数処理 -------------------
	$(".input-group-addon").html(
		"/" + 1
	)
	flag = true;
	//----------------------------------

	var selectNameValue = $("#name").val();
	var selectGrandChildValue = $("#grandchild_category").val();
	var selectBrandValue = $("#select_category").val();
	var selectParentValue = $("#parent_category").val();

	// name 有り
	if (selectNameValue != "") {

		// name only
		if (selectBrandValue == "Categoryを選択してください") {

			$.ajax({
				type: "get",
				url: "/getItemOfSerchedOnlyName",
				dataType: "json",
				data: {
					"selectNameValue": selectNameValue
				}
			}).then(function (data) {

				$(".appendClass").empty();

				for (var value of data) {

					if (value.brand == null) {
						$(".appendClass").append(

							"<tr>"
							+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
							+ value.name
							+ "</a></td>"
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
							+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
							+ value.name
							+ "</a></td>"
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

		} else {
			// name + category + brand

			$.ajax({
				type: "get",
				url: "/getItemOfSerchedExistName",
				dataType: "json",
				data: {
					"selectNameValue": selectNameValue,
					"selectGrandChildValue": selectGrandChildValue,
					"selectBrandValue": selectBrandValue
				}
			}).then(function (data) {

				$(".appendClass").empty();

				for (var value of data) {

					if (value.brand == null) {
						$(".appendClass").append(

							"<tr>"
							+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
							+ value.name
							+ "</a></td>"
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
							+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
							+ value.name
							+ "</a></td>"
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
		}


	} else {
		// category + brand 検索

		$.ajax({
			type: "get",
			url: "/getItemOfSerched",
			dataType: "json",
			data: {
				"selectGrandChildValue": selectGrandChildValue,
				"selectBrandValue": selectBrandValue
			}
		}).then(function (data) {

			$(".appendClass").empty();

			for (var value of data) {

				if (value.brand == null) {
					$(".appendClass").append(

						"<tr>"
						+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
						+ value.name
						+ "</a></td>"
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
						+ "<td class='item-name'><a href='detail?id=" + value.id + "'>"
						+ value.name
						+ "</a></td>"
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

		$(select_page_form).val(1);

	}
});
