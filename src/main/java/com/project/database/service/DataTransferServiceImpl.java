package com.project.database.service;

import com.project.database.Entities.Contact;
import com.project.database.Entities.Name;
import com.project.database.Entities.User;
import com.project.database.repo.ContactRepository;
import com.project.database.repo.NameRepository;
import com.project.database.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataTransferServiceImpl implements DataTransferService {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final NameRepository nameRepository;

    public DataTransferServiceImpl(UserRepository userRepository,
                                   ContactRepository contactRepository,
                                   NameRepository nameRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.nameRepository = nameRepository;
    }

    @Override
    @Transactional
    public void transferUserData() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            // Create and save Contact entity
            Contact contact = new Contact();
            contact.setPhoneNumber(user.getPhoneNumber());
            contact.setUser(user);
            contactRepository.save(contact);

            // Create and save Name entity
            Name name = new Name();
            name.setName(user.getFirstName() + " " + user.getLastName());
            name.setUser(user);
            nameRepository.save(name);
        }
    }
}
