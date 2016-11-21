package liuliu.mvldemo.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */

public class MeiNvModel {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-03-06 14:11","title":"刘雨欣美女桌面","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/06/m.xxxiao.com_2411c2dfab27e4411a27c16f4f87dd22-760x500.jpg","url":"http://m.xxxiao.com/1811"},{"ctime":"2016-03-06 14:11","title":"咖啡馆邂逅忧伤的姑娘","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/04/m.xxxiao.com_6bb61e3b7bce0931da574d19d1d82c88-690x500.jpg","url":"http://m.xxxiao.com/123"},{"ctime":"2016-03-06 14:11","title":"中国乳神峰起樊玲","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/07/m.xxxiao.com_ed8dedecf4d4a62f60528676f6649b85-760x500.jpg","url":"http://m.xxxiao.com/2187"},{"ctime":"2016-03-06 14:11","title":"性感美女真空上阵秀美胸艺术照","description":"美女图片","picUrl":"http://t1.27270.com/uploads/150725/8-150H515520N54.jpg","url":"http://www.27270.com/ent/meinvtupian/2015/51906.html"},{"ctime":"2016-03-06 14:11","title":"长发美女齐贝贝大秀性感火辣身姿","description":"美女图片","picUrl":"http://t1.27270.com/uploads/tu/201507/375/slt.jpg","url":"http://www.27270.com/ent/meinvtupian/2015/122489.html"},{"ctime":"2016-03-06 14:11","title":"性感的大胸美女秘书诱惑靓照","description":"美女图片","picUrl":"http://t1.27270.com/uploads/150727/8-150HF914044U.jpg","url":"http://www.27270.com/ent/meinvtupian/2015/51122.html"},{"ctime":"2016-03-06 14:11","title":"韩国\u201c逆天颜值\u201d萝莉美少女 Yurisa","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/07/m.xxxiao.com_d425b67b2744d9f62be091b2bb3c9d93-760x500.jpg","url":"http://m.xxxiao.com/2200"},{"ctime":"2016-03-06 14:11","title":"嫩模刘嘉琦白色衬衫丁字裤展千娇百媚魅力","description":"美女图片","picUrl":"http://t1.27270.com/uploads/tu/201507/391/slt.jpg","url":"http://www.27270.com/ent/meinvtupian/2015/122766.html"},{"ctime":"2016-03-06 14:11","title":"极品御姐沙发撩人姿态魅惑写真集","description":"美女图片","picUrl":"http://t1.27270.com/uploads/tu/201507/380/slt.jpg","url":"http://www.27270.com/ent/meinvtupian/2015/122765.html"},{"ctime":"2016-03-06 14:11","title":"白皙丰满美女人体艺术照","description":"美女图片","picUrl":"http://t1.27270.com/uploads/150729/8-150H9102942291.jpg","url":"http://www.27270.com/ent/meinvtupian/2015/49857.html"}]
     */

    private int code;
    private String msg;
    /**
     * ctime : 2016-03-06 14:11
     * title : 刘雨欣美女桌面
     * description : 美女图片
     * picUrl : http://m.xxxiao.com/wp-content/uploads/sites/3/2015/06/m.xxxiao.com_2411c2dfab27e4411a27c16f4f87dd22-760x500.jpg
     * url : http://m.xxxiao.com/1811
     */

    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
