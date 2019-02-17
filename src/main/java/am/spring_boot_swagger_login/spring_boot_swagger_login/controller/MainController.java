package am.spring_boot_swagger_login.spring_boot_swagger_login.controller;

import am.spring_boot_swagger_login.spring_boot_swagger_login.dtoMapper.UserDTOUtil;
import am.spring_boot_swagger_login.spring_boot_swagger_login.model.entity.User;
import am.spring_boot_swagger_login.spring_boot_swagger_login.model.dto.UserCreatingDTO;
import am.spring_boot_swagger_login.spring_boot_swagger_login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rest/user")
public class MainController {

    private final UserRepository userRepository;

    @Autowired
    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody UserCreatingDTO userCreatingDTO) {
        User user = userRepository.save(UserDTOUtil.convertToEntity(userCreatingDTO));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity goUserListPage() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(userRepository::delete);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserCreatingDTO userCreatingDTO, @RequestParam Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userCreatingDTO.getFirstName());
            user.setLastName(userCreatingDTO.getLastName());
            user.setBirthDate(userCreatingDTO.getBirthDate());
            user.setPhoneNumber(userCreatingDTO.getPhoneNumber());
            user.setEmail(user.getEmail());
            user.setUserPassword(user.getUserPassword());

            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get")
    public ResponseEntity getUserById(@RequestParam Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
