$(document).ready(function() {
    $('#dataTable').DataTable({
        processing: true,
        serverSide: true,
        ajax: '/admin/post/list',
        columns: [
            { data: "title" },
            { data: "author.userName" },
            { data: "updatedAt" }
        ]
    });
});
