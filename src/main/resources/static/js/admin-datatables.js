$(document).ready(function() {
    $('#dataTable').DataTable({
        serverSide: true,
        ajax: '/admin/post/list'
    });
});
