function toSpringData(data) {
    let d = {
        draw: data.draw,
        start: data.start,
        length: data.length,
        'search.value': data.search.value,
        'search.regex': data.search.regex
    };

    for (let i = 0; i < data.order.length; i++) {
        d['order[' + i + '].column'] = data.order[i].column;
        d['order[' + i + '].dir'] = data.order[i].dir;
    }

    for (let i = 0; i < data.columns.length; i++) {
        d['columns[' + i + '].data'] = data.columns[i].data;
        d['columns[' + i + '].name'] = data.columns[i].name;
        d['columns[' + i + '].searchable'] = data.columns[i].searchable;
        d['columns[' + i + '].orderable'] = data.columns[i].orderable;
    }

    return d;
}

function renderList(data, mapFunc) {
    return data.map(mapFunc).join(', ');
}

function renderDate(date) {
    if (date) {
        return date.slice(0, date.indexOf('T'));
    }

    return '';
}

function createDataTable(id, url, columns) {
    $(`#${id}`).DataTable({
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
            url: url,
            data: toSpringData
        },
        columns: columns
    });
}
