package POJOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public Category() {
    }

    /**
     *
     * @param name
     * @param id
     */
    public Category(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

}
