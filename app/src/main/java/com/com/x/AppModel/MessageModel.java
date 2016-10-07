package com.com.x.AppModel;

import com.x.custom.DateUtils;

import java.io.Serializable;

/**
 * Created by X on 2016/10/7.
 */

public class MessageModel implements Serializable {


    /**
     * id : 735
     * title : 没有标题
     * content : 哈哈哈哈哈哈哈哈哈
     * create_time : 1475026275
     */

    private String id;
    private String title;
    private String content;
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreate_time() {

        if(!create_time.contains(" "))
        {
            create_time = DateUtils.unixToStr(create_time,"yyyy-MM-dd hh:mm:ss");
        }

        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
