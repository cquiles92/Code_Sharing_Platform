package platform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import platform.model.CodeFragment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeObjectDto {
    @JsonIgnore
    private UUID uuid;

    private String code;

    private String date;

    private int time;

    private int views;

    //default false
    @JsonIgnore
    private boolean isTimeLimited = false;
    @JsonIgnore
    private boolean isViewLimited = false;

    public CodeObjectDto(CodeFragment codeFragment) {
        this.uuid = codeFragment.getUuid();
        this.code = codeFragment.getCode();
        this.date = codeFragment.getDateCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public CodeObjectDto(CodeFragment codeFragment, boolean isTimeLimited, boolean isViewLimited, LocalDateTime currentTime) {
        this(codeFragment);
        this.isTimeLimited = isTimeLimited;
        this.isViewLimited = isViewLimited;
        if (isTimeLimited) {
            this.time = (int) Duration.between(currentTime, codeFragment.getExpirationDate()).toSeconds();
        }
        if (isViewLimited) {
            this.views = codeFragment.getViewLimit();
        }
    }
}
