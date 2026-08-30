package br.com.barros.Cinema.controller;

import br.com.barros.Cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/promote")
    @ResponseStatus(HttpStatus.OK)
    public void adminPromote(@PathVariable Long id){
        userService.adminPromote(id);
    }
}
