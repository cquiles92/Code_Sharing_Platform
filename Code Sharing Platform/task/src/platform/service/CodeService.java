package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import platform.dto.CodeObjectDto;
import platform.repository.CodeRepository;
import platform.requestBody.CodeForm;
import platform.model.CodeFragment;
import platform.responseBody.CodeID;
import platform.exception.notfound.CodeIDNotFoundException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;


@Service
public class CodeService {
    @Autowired
    private CodeRepository repository;

    public CodeID createNewFragment(CodeForm codeForm) {
        CodeFragment updatedCode = new CodeFragment(codeForm);
        repository.save(updatedCode);
        return new CodeID(updatedCode.getUuid());
    }

    public CodeObjectDto getCodeObject(String uuid) {
        CodeFragment codeFragment = repository.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new CodeIDNotFoundException(uuid));
        return getCodeObjectDto(codeFragment);
    }

    public void getSingleCodePage(ModelAndView modelAndView, String uuid) {
        CodeObjectDto codeFragment = getCodeObject(uuid);

        modelAndView.addObject("title", "Code");
        modelAndView.addObject("codeobjects", List.of(codeFragment));
    }

    public List<CodeObjectDto> getLatestCodeList() {
        return repository.findFirst10ByIsRestrictedFalseOrderByDateCreatedDesc().stream().map(CodeObjectDto::new).toList();
    }

    public void getLatestCodePage(ModelAndView modelAndView) {
        List<CodeObjectDto> codeFragments = getLatestCodeList();
        modelAndView.addObject("title", "Latest");
        modelAndView.addObject("codeobjects", codeFragments);
    }

    private CodeObjectDto getCodeObjectDto(CodeFragment codeFragment) {
        LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        boolean viewLimit = codeFragment.getViewLimit() > 0;
        boolean timeLimit = codeFragment.getExpirationDate() != null;

        ifCodeIsRestricted(codeFragment, currentTime, viewLimit, timeLimit);

        return new CodeObjectDto(codeFragment, timeLimit, viewLimit, currentTime);
    }

    private void ifCodeIsRestricted(CodeFragment codeFragment, LocalDateTime currentTime, boolean viewLimit, boolean timeLimit) {
        if (codeFragment.isRestricted()) {
            ifTimeRestricted(codeFragment, currentTime, timeLimit);
            ifViewRestricted(codeFragment, viewLimit);
        }
    }

    private void ifTimeRestricted(CodeFragment codeFragment, LocalDateTime currentTime, boolean timeLimit) {
        if (timeLimit) {
            if (currentTime.isAfter(codeFragment.getExpirationDate())) {
                repository.delete(codeFragment);
                throw new CodeIDNotFoundException(codeFragment.getUuid().toString());
            }
        }
    }

    private void ifViewRestricted(CodeFragment codeFragment, boolean viewLimit) {
        if (viewLimit) {
            codeFragment.setViewLimit(codeFragment.getViewLimit() - 1);
            if (codeFragment.getViewLimit() == 0) {
                repository.delete(codeFragment);
            } else {
                repository.save(codeFragment);
            }
        }
    }
}
