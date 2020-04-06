package com.aiit.byh.service.common.entity.base;

import java.io.Serializable;

/**
 * 菜单信息
 *
 * @author jianchen3
 * @date 2019-9-5 10:37:18
 */
public class Menu implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2476406311182302329L;
    /**
     * 菜单名称
     */
    private String nm;
    /**
     * 栏目编号
     */
    private String colid;
    /**
     * 栏目类型
     */
    private String coltp;


    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getColid() {
        return colid;
    }

    public void setColid(String colid) {
        this.colid = colid;
    }

    public String getColtp() {
        return coltp;
    }

    public void setColtp(String coltp) {
        this.coltp = coltp;
    }

    public Menu() {
        super();
    }

    public Menu(String nm, String colid, String coltp) {
        super();
        this.nm = nm;
        this.colid = colid;
        this.coltp = coltp;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Menu-{");
        sb.append("\"nm\":\"")
                .append(nm).append('\"');
        sb.append(",\"colid\":\"")
                .append(colid).append('\"');
        sb.append(",\"coltp\":\"")
                .append(coltp).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
