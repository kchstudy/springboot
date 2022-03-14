function cm_pager() {};

cm_pager.prototype.init = function( id, fn_search ) {
    $('#'+id).children().remove();
    $('#'+id).append(
         '    <div class="col-sm-10">'
        +'        <nav>'
        +'            <ul class="pagination" id="'+ id +'_pagination" >'
        +'                <li class="page-item"><a class="page-link" href="#">Previous</a></li>'
        +'                <li class="page-item active"><a class="page-link" href="#">1</a></li>'
        +'                <li class="page-item"><a class="page-link" href="#">Next</a></li>'
        +'            </ul>'
        +'        </nav>'
        +'    </div>'
        +'    <div class="col-sm-2">'
        +'        <select class="form-select" id="'+id+'_page_cnt">'
        +'            <option value="5">5</option>'
        +'            <option value="10">10</option>'
        +'            <option value="20">20</option>'
        +'            <option value="30">30</option>'
        +'            <option value="50">50</option>'
        +'            <option value="100">100</option>'
        +'        </select>'
        +'    </div>'
    );
    $('#'+id+'_page_cnt').unbind('change');
    $('#'+id+'_page_cnt').on('change', function(e) {
        fn_search(1);
    });
}

cm_pager.prototype.calcPage = function( totalItems, currentPage, pageSize, maxPages ) {
    if ( cm_util.isEmptyObj(totalItems) || cm_util.isEmptyObj(currentPage)
      || cm_util.isEmptyObj(pageSize) || cm_util.isEmptyObj(maxPages)
    ) {
        return false;
    }

    // calculate total pages
    var totalPages = Math.ceil( Number(totalItems) / Number(pageSize) );

    // ensure current page isn't out of range
    if (Number(currentPage) < 1) {
        currentPage = 1;
    } else if (Number(currentPage) > Number(totalPages)) {
        currentPage = Number(totalPages);
    }

    var startPage = 1;
    var endPage   = Number(maxPages);
    if ( Number(totalPages) <= Number(maxPages) ) {
        // total pages less than max so show all pages
        startPage = 1;
        endPage   = Number(totalPages);
    } else {
        // total pages more than max so calculate start and end pages
        var maxPagesBeforeCurrentPage = Math.floor(Number(maxPages) / 2);
        var maxPagesAfterCurrentPage  = Math.ceil(Number(maxPages) / 2) - 1;
        if ( Number(currentPage) <= Number(maxPagesBeforeCurrentPage) ) {
            // current page near the start
            startPage = 1;
            endPage   = Number(maxPages);
        } else if ( Number(currentPage) + Number(maxPagesAfterCurrentPage) >= Number(totalPages) ) {
            // current page near the end
            startPage = Number(totalPages) - Number(maxPages) + 1;
            endPage   = Number(totalPages);
        } else {
            // current page somewhere in the middle
            startPage = Number(currentPage) - Number(maxPagesBeforeCurrentPage);
            endPage   = Number(currentPage) + Number(maxPagesAfterCurrentPage);
        }
    }

    // calculate start and end item indexes
    var startIndex = (Number(currentPage) - 1) * Number(pageSize);
    var endIndex   = Math.min(Number(startIndex) + Number(pageSize) - 1, Number(totalItems) - 1);

    // create an array of pages to ng-repeat in the pager control
    var pages = Array.from(Array((Number(endPage) + 1) - Number(startPage)).keys()).map(i => Number(startPage) + i);

    // return object with all pager properties required by the view
    return {
         totalItems  : Number(totalItems)
        ,currentPage : Number(currentPage)
        ,pageSize    : Number(pageSize)
        ,totalPages  : Number(totalPages)
        ,startPage   : Number(startPage)
        ,endPage     : Number(endPage)
        ,startIndex  : Number(startIndex)
        ,endIndex    : Number(endIndex)
        ,pages       : pages
    };

}

cm_pager.prototype.paginate = function( id, fn_search, tot_cnt, current_page, page_per_cnt, max_page ) {
    if ( cm_util.isEmptyObj(id) || cm_util.isEmptyObj(fn_search) || cm_util.isEmptyObj(current_page) || cm_util.isEmptyObj(page_per_cnt) ) {
        return false;
    }

    var pageObj = cm_pager.calcPage(Number(tot_cnt), Number(current_page), Number(page_per_cnt), Number(cm_util.nvl(max_page,'10')));
    var preHtml  = '<li class="page-item"><a class="page-link" id="'+id+'_page_pre" href="javascript:void(0);">Previous</a></li>';
    var nextHtml = '<li class="page-item"><a class="page-link" id="'+id+'_page_next" href="javascript:void(0);">Next</a></li>';

    $('#'+id+'_pagination').children('.page-item').children('.page-link').unbind('click');
    $('#'+id+'_pagination').children().remove();
    var pagination = '';
    pageObj.pages.forEach(function(page) {
        pagination += '<li class="page-item';
        if ( page == pageObj.currentPage ) {
            pagination += ' active';
        }
        pagination += '"><a class="page-link" href="javascript:void(0);" id="'+id+'_page_'+page+'" >'+page+'</a></li>';
    });
    $('#'+id+'_pagination').append(preHtml+pagination+nextHtml);
    pageObj.pages.forEach(function(page) {
        $('#'+id+'_page_'+page).on('click', function(e) {
            fn_search(page);
        });
    });
    if ( pageObj.currentPage > 1 ) {
        $('#'+id+'_page_pre').on('click', function(e) {
            fn_search(pageObj.currentPage-1);
        });
    }
    if ( pageObj.currentPage != pageObj.totalPages ) {
        $('#'+id+'_page_next').on('click', function(e) {
            fn_search(pageObj.currentPage+1);
        });
    }
}

var cm_pager = new cm_pager();