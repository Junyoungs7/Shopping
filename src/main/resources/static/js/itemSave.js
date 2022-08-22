var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var data = {
            itemNm: $('#itemNm').val(),
            price: $('#price').val(),
            stockNumber: $('#stockNumber').val(),
            itemDetail: $('#itemDetail').val(),
            itemSellStatus: $('#itemSellStatus').val(),
            itemCategory: $('#itemCategory').val()
        };

        $.ajax({
            type: 'POST',
            url: '/admin/itemReg',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend : function(xhr){
                xhr.setRequestHeader(header, token);
            },
        }).done(function() {
            alert('상품이 등록되었습니다.');
            window.location.href = "/admin/itemSave";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/posts';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/posts';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();