package com.example.sessionid;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/session")
public class SessionIdController {
    private final SessionIdService sessionIdService;

    public SessionIdController(SessionIdService sessionIdService) {
        this.sessionIdService = sessionIdService;
    }

    @CrossOrigin
    @PostMapping("/create/{uid}")  //problem oluşturabilir ingresse tanıtılmayacak. çünkü sadece içerden istek alabilir. Daha sonra düşün.
    public SessionIdDTO createSessionId(@PathVariable Long uid){
        return sessionIdService.createSessionId(uid);
    }

    @CrossOrigin
    @PostMapping("/validate")
    public Integer sessionIdValidityCheck(@RequestBody SessionIdDTO sessionId){
        return sessionIdService.sessionIdValidityCheck(sessionId);
    }

    @CrossOrigin
    @PostMapping("delete")
    public Integer deleteSession(@RequestBody SessionIdDTO sessionId){
        return sessionIdService.deleteSession(sessionId);
    }
}
