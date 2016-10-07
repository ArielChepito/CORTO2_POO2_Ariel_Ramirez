$(document).ready(function() {
    $.fn.initBootTable = function() {
        $(this).bootstrapTable('destroy');
        $(this).bootstrapTable().
        unbind('check.bs.table').on('check.bs.table', function (e, row) {
            consGrupAlum([{name : 'codiUsuaRolePara', value : row.id.trim()}]);
            //$("[id='FormUsuaRole:codi']").val(row.id);
        });
        return false;
    };
    //Eliminar
    $.fn.funcElimUsuaRole = function() {
        $(this).confirmation(
        {
            popout: true,
            onConfirm: function() {
                elimGrupAlum();
                $('[data-toggle="confirmation-popout"]').confirmation('hide');
                return false;
            },
            onCancel: function()
            {
                $('[data-toggle="confirmation-popout"]').confirmation('hide');
                return false;
            }
        });
        return false;
    };
    
    INIT_OBJE_USUA_ROLE();
});

function INIT_OBJE_USUA_ROLE()
{
    $("#TablUsuaRole").initBootTable();
    $("#FormUsuaRole\\:btonElim").funcElimUsuaRole();
}