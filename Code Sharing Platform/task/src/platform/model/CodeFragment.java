package platform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import platform.requestBody.CodeForm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class CodeFragment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID uuid;

    private String code;

    private LocalDateTime dateCreated;

    private LocalDateTime expirationDate;

    private int viewLimit;

    private boolean isRestricted;

    public CodeFragment(CodeForm codeForm) {
        LocalDateTime currentTime = LocalDateTime.now();
        this.code = codeForm.getCode();
        this.dateCreated = currentTime;
        this.viewLimit = codeForm.getViews();

        if (codeForm.getTime() == 0) {
            this.expirationDate = null;
        } else {
            this.expirationDate = currentTime.plus(codeForm.getTime(), ChronoUnit.SECONDS).truncatedTo(ChronoUnit.SECONDS);
        }

        this.setRestricted(codeForm.getTime() > 0 || codeForm.getViews() > 0);
    }
}
