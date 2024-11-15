package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.saidoff.crmecosystem.payload.QuestionDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.valid.CheckPermission;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class QuestionController {

    @CheckPermission("CREATE_QUESTION")
    @PostMapping("/create_question")
    public ResponseData<?> createQuestion(@RequestBody QuestionDto questionDto) {

        ResponseData<?> responseData = new ResponseData<>();

        return null;
    }
}
