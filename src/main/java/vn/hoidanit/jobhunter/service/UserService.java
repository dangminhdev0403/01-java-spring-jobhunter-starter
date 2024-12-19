package vn.hoidanit.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    public User saveUser(User user) {
      return  userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User handleUpdateUser(User user) {
        User userInDb = this.getUserById(user.getId());
        if (userInDb == null) {
            return null;
        }
        userInDb.setName(user.getName());
        userInDb.setEmail(user.getEmail());
        userInDb.setPassword(user.getPassword());

        return userRepository.save(user);
    }
}
