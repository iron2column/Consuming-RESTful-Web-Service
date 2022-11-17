package top.lessmore.demo;

/**
 * @author ChenMingYang
 * @date 2022-11-17 10:44
 */
public class Quote {
    private Long id;
    private String content;

    public Quote() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
