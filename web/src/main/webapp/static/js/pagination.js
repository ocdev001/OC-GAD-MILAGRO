class Pagination {
    constructor(tableSelector, rowsShown) {
        this.tableSelector = tableSelector;
        this.rowsShown = rowsShown;
        this.rows = $(tableSelector + ' tbody tr');
        this.rowsTotal = this.rows.length;
        this.numPages = Math.ceil(this.rowsTotal / this.rowsShown);
        this.currentPage = 0;
    }

    generatePagination() {
        $('#pagination').empty();
        // Botpara la primera p
        $('#pagination').append('<li><a href="#" class="first-page">&laquo;</a></li>');
        // Botpara la panterior
        $('#pagination').append('<li><a href="#" class="prev-page">&lsaquo;</a></li>');
        // Nde p
        for (var i = 0; i < this.numPages; i++) {
            var pageNum = i + 1;
            $('#pagination').append('<li><a href="#" rel="' + i + '">' + pageNum + '</a></li>');
        }
        // Botpara la psiguiente
        $('#pagination').append('<li><a href="#" class="next-page">&rsaquo;</a></li>');
        // Botpara la p
        $('#pagination').append('<li><a href="#" class="last-page">&raquo;</a></li>');
    }

    handlePaginationClicks() {
        var self = this;
        $(this.tableSelector + ' tbody tr').hide();
        $(this.tableSelector + ' tbody tr').slice(0, this.rowsShown).show();

        $('#pagination').on('click', 'a', function(e) {
            e.preventDefault();
            var $this = $(this);
            if ($this.hasClass('prev-page')) {
                self.currentPage = Math.max(0, self.currentPage - 1);
            } else if ($this.hasClass('next-page')) {
                self.currentPage = Math.min(self.numPages - 1, self.currentPage + 1);
            } else if ($this.hasClass('first-page')) {
                self.currentPage = 0;
            } else if ($this.hasClass('last-page')) {
                self.currentPage = self.numPages - 1;
            } else {
                self.currentPage = parseInt($this.attr('rel'), 10);
            }
            self.showCurrentPage();
            $('#pagination li').removeClass('active');
            $('#pagination li').eq(self.currentPage + 2).addClass('active');
        });
    }

    showCurrentPage() {
        var startItem = this.currentPage * this.rowsShown;
        var endItem = startItem + this.rowsShown;
        this.rows.hide().slice(startItem, endItem).show();
    }
}