package platform.responseBody;

import lombok.Data;

import java.util.UUID;
@Data
public class CodeID {
    private String id;
    public CodeID(UUID uuid) {
        this.id = uuid.toString();
    }
}
