package platform.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.requestBody.CodeForm;
import platform.responseBody.CodeID;
import platform.service.CodeService;

@RestController
@RequestMapping("/api/code")
public class APICodeController {
    @Autowired
    private CodeService service;

    @GetMapping("/{uuid}")
    public ResponseEntity<?> returnCode(@PathVariable String uuid) {
        return new ResponseEntity<>(service.getCodeObject(uuid), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<CodeID> updateCode(@RequestBody CodeForm codeForm) {
        return new ResponseEntity<>(service.createNewFragment(codeForm), HttpStatus.OK);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> returnLatestCode() {
        return new ResponseEntity<>(service.getLatestCodeList(), HttpStatus.OK);
    }
}
