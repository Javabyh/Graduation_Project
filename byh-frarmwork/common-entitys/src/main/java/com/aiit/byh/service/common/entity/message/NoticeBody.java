package com.aiit.byh.service.common.entity.message;

import java.io.Serializable;

/**
 * Created by jhyuan on 2017/11/18.
 */
public class NoticeBody implements Serializable {
    private String text;
    private String uid;
    private String id;
    private String pic;
    private String pushtitle;
    private String title;
    private String u;
    private String detail;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPushtitle() {
        return pushtitle;
    }

    public void setPushtitle(String pushtitle) {
        this.pushtitle = pushtitle;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NoticeBody{");
        sb.append("text='").append(text).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", pic='").append(pic).append('\'');
        sb.append(", pushtitle='").append(pushtitle).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", u='").append(u).append('\'');
        sb.append(", detail='").append(detail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
