package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    //总条数
    private Integer totalRows;
    //总页数
    private Integer totalPage;
    //每页显示的条数
    private Integer rows = 10;
    //记录开始索引
    private Integer startRow;
    //当前页
    private Integer currentPage;
    //上一页
    private Integer prePage;
    //下一页
    private  Integer nextPage;
    //分页开始页
    private Integer startPage;
    //分页结束页
    private Integer endPage;
    //存储数据对象
    private List<T> list;


    public PageBean(Integer totalRows, Integer currentPage) {
        this.totalRows = totalRows;

        totalPage = totalRows%rows==0 ? totalRows/rows : totalRows/rows+1;

        if(currentPage <= 1){
            this.currentPage = 1;
        }else if(currentPage > totalPage){
            this.currentPage = totalPage;
        }else {
            this.currentPage = currentPage;
        }

        startPage = currentPage-5>1 ? currentPage-5:1;
        endPage = currentPage+4<totalPage ? currentPage+4:totalPage;
        if(totalPage>9){
            if((endPage-startPage) < 9){
                if(startPage == 1){
                    endPage = 10;
                }else {
                    startPage = endPage-9;
                }
            }

        }
        prePage = currentPage>1 ? currentPage-1 : 1;
        nextPage = currentPage<totalPage ? currentPage+1 : totalPage;
    }


    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getStartRow() {
        return (currentPage-1)*rows;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
