package org.ld000.lessbatis;

import org.ld000.lessbatis.annotation.CamelHumpToUnderline;

import javax.persistence.Table;

/**
 * @author lidong9144@163.com 17-3-8.
 */
@Table(name = "jpa_test")
@CamelHumpToUnderline
public class Obj {

    private Long id;
    private String name;
    private String userName;

    @Override
    public String toString() {
        return "Obj{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
