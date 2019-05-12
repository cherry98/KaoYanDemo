package com.kaoyan.kaoyandemo.info;

public class CommunityInfo {

    /**
     * collectId :
     * postId : 2
     * title : 还在家正看着呢想你呗
     * content : 电话就行难兄难弟
     */

    private String collectId;
    private String postId;
    private String title;
    private String content;
    private boolean isCheck;
    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
