$(document).ready(function() {
    $('#dataTable').DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            url: '/admin/post/list',
            data: function (data) {
                var d = {
                    draw: data.draw,
                    start: data.start,
                    length: data.length,
                    'search.value': data.search.value,
                    'search.regex': data.search.regex
                };

                for (var i = 0; i < data.order.length; i++) {
                    d['order[' + i + '].column'] = data.order[i].column;
                    d['order[' + i + '].dir'] = data.order[i].dir;
                }

                for (var i = 0; i < data.columns.length; i++) {
                    d['columns[' + i + '].data'] = data.columns[i].data;
                    d['columns[' + i + '].name'] = data.columns[i].name;
                    d['columns[' + i + '].searchable'] = data.columns[i].searchable;
                    d['columns[' + i + '].orderable'] = data.columns[i].orderable;
                }

                return d;
            }
        },
        columns: [
            {
                data: "title",
                orderable: true
            }, {
                data: "author.userName",
                orderable: false
            }, {
                data: "categories",
                orderable: false,
                render: function(data) {
                    var str = '';
                    for (var i = 0; i < data.length; i++) {
                        str += data[i].name;
                        if (i !== data.length - 1) {
                            str += ', '
                        }
                    }
                    return str;
                }
            }, {
                data: "tags",
                orderable: false,
                render: function(data) {
                    var str = '';
                    for (var i = 0; i < data.length; i++) {
                        str += data[i].name;
                        if (i !== data.length - 1) {
                            str += ', '
                        }
                    }
                    return str;
                }
            }, {
                data: "publishedAt",
                orderable: true
            }
        ]
    });
});
