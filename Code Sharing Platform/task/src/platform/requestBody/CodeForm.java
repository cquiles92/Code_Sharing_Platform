package platform.requestBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeForm {
    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int time;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int views;

}
