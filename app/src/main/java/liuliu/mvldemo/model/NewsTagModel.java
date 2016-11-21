package liuliu.mvldemo.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2016/11/22 00:07
 * QQ群481606175
 */

public class NewsTagModel {

    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"channelList":[{"channelId":"5572a108b3cdc86cf39001cd","name":"国内焦点"},{"channelId":"5572a108b3cdc86cf39001ce","name":"国际焦点"},{"channelId":"5572a108b3cdc86cf39001cf","name":"军事焦点"},{"channelId":"5572a108b3cdc86cf39001d0","name":"财经焦点"},{"channelId":"5572a108b3cdc86cf39001d1","name":"互联网焦点"},{"channelId":"5572a108b3cdc86cf39001d2","name":"房产焦点"},{"channelId":"5572a108b3cdc86cf39001d3","name":"汽车焦点"},{"channelId":"5572a108b3cdc86cf39001d4","name":"体育焦点"},{"channelId":"5572a108b3cdc86cf39001d5","name":"娱乐焦点"},{"channelId":"5572a108b3cdc86cf39001d6","name":"游戏焦点"},{"channelId":"5572a108b3cdc86cf39001d7","name":"教育焦点"},{"channelId":"5572a108b3cdc86cf39001d8","name":"女人焦点"},{"channelId":"5572a108b3cdc86cf39001d9","name":"科技焦点"},{"channelId":"5572a109b3cdc86cf39001da","name":"社会焦点"},{"channelId":"5572a109b3cdc86cf39001db","name":"国内最新"},{"channelId":"5572a109b3cdc86cf39001dc","name":"台湾最新"},{"channelId":"5572a109b3cdc86cf39001dd","name":"港澳最新"},{"channelId":"5572a109b3cdc86cf39001de","name":"国际最新"},{"channelId":"5572a109b3cdc86cf39001df","name":"军事最新"},{"channelId":"5572a109b3cdc86cf39001e0","name":"财经最新"},{"channelId":"5572a109b3cdc86cf39001e1","name":"理财最新"},{"channelId":"5572a109b3cdc86cf39001e2","name":"宏观经济最新"},{"channelId":"5572a109b3cdc86cf39001e3","name":"互联网最新"},{"channelId":"5572a109b3cdc86cf39001e4","name":"房产最新"},{"channelId":"5572a109b3cdc86cf39001e5","name":"汽车最新"},{"channelId":"5572a109b3cdc86cf39001e6","name":"体育最新"},{"channelId":"5572a10ab3cdc86cf39001e7","name":"国际足球最新"},{"channelId":"5572a10ab3cdc86cf39001e8","name":"国内足球最新"},{"channelId":"5572a10ab3cdc86cf39001e9","name":"CBA最新"},{"channelId":"5572a10ab3cdc86cf39001ea","name":"综合体育最新"},{"channelId":"5572a10ab3cdc86cf39001eb","name":"娱乐最新"},{"channelId":"5572a10ab3cdc86cf39001ec","name":"电影最新"},{"channelId":"5572a10ab3cdc86cf39001ed","name":"电视最新"},{"channelId":"5572a10ab3cdc86cf39001ee","name":"游戏最新"},{"channelId":"5572a10ab3cdc86cf39001ef","name":"教育最新"},{"channelId":"5572a10ab3cdc86cf39001f0","name":"女人最新"},{"channelId":"5572a10ab3cdc86cf39001f1","name":"美容护肤最新"},{"channelId":"5572a10ab3cdc86cf39001f2","name":"情感两性最新"},{"channelId":"5572a10ab3cdc86cf39001f3","name":"健康养生最新"},{"channelId":"5572a10ab3cdc86cf39001f4","name":"科技最新"},{"channelId":"5572a10bb3cdc86cf39001f5","name":"数码最新"},{"channelId":"5572a10bb3cdc86cf39001f6","name":"电脑最新"},{"channelId":"5572a10bb3cdc86cf39001f7","name":"科普最新"},{"channelId":"5572a10bb3cdc86cf39001f8","name":"社会最新"}],"ret_code":0,"totalNum":44}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    /**
     * channelList : [{"channelId":"5572a108b3cdc86cf39001cd","name":"国内焦点"},{"channelId":"5572a108b3cdc86cf39001ce","name":"国际焦点"},{"channelId":"5572a108b3cdc86cf39001cf","name":"军事焦点"},{"channelId":"5572a108b3cdc86cf39001d0","name":"财经焦点"},{"channelId":"5572a108b3cdc86cf39001d1","name":"互联网焦点"},{"channelId":"5572a108b3cdc86cf39001d2","name":"房产焦点"},{"channelId":"5572a108b3cdc86cf39001d3","name":"汽车焦点"},{"channelId":"5572a108b3cdc86cf39001d4","name":"体育焦点"},{"channelId":"5572a108b3cdc86cf39001d5","name":"娱乐焦点"},{"channelId":"5572a108b3cdc86cf39001d6","name":"游戏焦点"},{"channelId":"5572a108b3cdc86cf39001d7","name":"教育焦点"},{"channelId":"5572a108b3cdc86cf39001d8","name":"女人焦点"},{"channelId":"5572a108b3cdc86cf39001d9","name":"科技焦点"},{"channelId":"5572a109b3cdc86cf39001da","name":"社会焦点"},{"channelId":"5572a109b3cdc86cf39001db","name":"国内最新"},{"channelId":"5572a109b3cdc86cf39001dc","name":"台湾最新"},{"channelId":"5572a109b3cdc86cf39001dd","name":"港澳最新"},{"channelId":"5572a109b3cdc86cf39001de","name":"国际最新"},{"channelId":"5572a109b3cdc86cf39001df","name":"军事最新"},{"channelId":"5572a109b3cdc86cf39001e0","name":"财经最新"},{"channelId":"5572a109b3cdc86cf39001e1","name":"理财最新"},{"channelId":"5572a109b3cdc86cf39001e2","name":"宏观经济最新"},{"channelId":"5572a109b3cdc86cf39001e3","name":"互联网最新"},{"channelId":"5572a109b3cdc86cf39001e4","name":"房产最新"},{"channelId":"5572a109b3cdc86cf39001e5","name":"汽车最新"},{"channelId":"5572a109b3cdc86cf39001e6","name":"体育最新"},{"channelId":"5572a10ab3cdc86cf39001e7","name":"国际足球最新"},{"channelId":"5572a10ab3cdc86cf39001e8","name":"国内足球最新"},{"channelId":"5572a10ab3cdc86cf39001e9","name":"CBA最新"},{"channelId":"5572a10ab3cdc86cf39001ea","name":"综合体育最新"},{"channelId":"5572a10ab3cdc86cf39001eb","name":"娱乐最新"},{"channelId":"5572a10ab3cdc86cf39001ec","name":"电影最新"},{"channelId":"5572a10ab3cdc86cf39001ed","name":"电视最新"},{"channelId":"5572a10ab3cdc86cf39001ee","name":"游戏最新"},{"channelId":"5572a10ab3cdc86cf39001ef","name":"教育最新"},{"channelId":"5572a10ab3cdc86cf39001f0","name":"女人最新"},{"channelId":"5572a10ab3cdc86cf39001f1","name":"美容护肤最新"},{"channelId":"5572a10ab3cdc86cf39001f2","name":"情感两性最新"},{"channelId":"5572a10ab3cdc86cf39001f3","name":"健康养生最新"},{"channelId":"5572a10ab3cdc86cf39001f4","name":"科技最新"},{"channelId":"5572a10bb3cdc86cf39001f5","name":"数码最新"},{"channelId":"5572a10bb3cdc86cf39001f6","name":"电脑最新"},{"channelId":"5572a10bb3cdc86cf39001f7","name":"科普最新"},{"channelId":"5572a10bb3cdc86cf39001f8","name":"社会最新"}]
     * ret_code : 0
     * totalNum : 44
     */

    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        private int ret_code;
        private int totalNum;
        /**
         * channelId : 5572a108b3cdc86cf39001cd
         * name : 国内焦点
         */

        private List<ChannelListBean> channelList;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public List<ChannelListBean> getChannelList() {
            return channelList;
        }

        public void setChannelList(List<ChannelListBean> channelList) {
            this.channelList = channelList;
        }

        public static class ChannelListBean {
            private String channelId;
            private String name;

            public String getChannelId() {
                return channelId;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
