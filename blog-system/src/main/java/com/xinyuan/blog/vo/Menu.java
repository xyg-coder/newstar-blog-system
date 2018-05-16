package com.xinyuan.blog.vo;

import java.io.Serializable;

/**
 * Menu object
 * @since 2018.2.14
 * @author guixinyuan
 */
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Menu(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
