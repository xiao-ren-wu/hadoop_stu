package org.xrw.topn;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/30 17:46
 */


public class PageCount implements Comparable<PageCount>{
    private String page;
    private Integer count;

    public PageCount() {
    }

    public PageCount(String page, Integer count) {
        this.page = page;
        this.count = count;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int compareTo(PageCount o) {
        return o.count-this.count==0?o.compareTo(this):o.count-this.count;
    }
}
