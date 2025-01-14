package com.mundiapolis.digital_banking.web;

import com.mundiapolis.digital_banking.dtos.AgentDTO;
import com.mundiapolis.digital_banking.dtos.AppUserDto;
import com.mundiapolis.digital_banking.dtos.CustomerDTO;

import com.mundiapolis.digital_banking.exeptions.CustomerNotFoundException;
import com.mundiapolis.digital_banking.security.entities.AppUser;
import com.mundiapolis.digital_banking.security.repo.AppUserRepository;
import com.mundiapolis.digital_banking.services.BankAccountService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private final BankAccountService bankAccountService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerRestController(BankAccountService bankAccountService, 
                                AppUserRepository appUserRepository,
                                PasswordEncoder passwordEncoder) {
        this.bankAccountService = bankAccountService;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {

        return bankAccountService.saveCustomer(customerDTO);
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public AppUserDto saveUser(@RequestBody AppUserDto appUserDto) {
        AppUser appUser = new AppUser();
        appUser.setUserId(java.util.UUID.randomUUID().toString());
        appUser.setPassword(passwordEncoder.encode(appUserDto.password));
        appUser.setRoles(appUserDto.roleNames);
        appUser.setUsername(appUserDto.username);
        appUser.setEmail(appUserDto.email);
        
        AppUser savedUser = appUserRepository.save(appUser);
        
        AppUserDto savedUserDto = new AppUserDto();
        savedUserDto.username = savedUser.getUsername();
        savedUserDto.email = savedUser.getEmail();
        savedUserDto.password = savedUser.getPassword();
        savedUserDto.roleNames = savedUser.getRoles();
        savedUserDto.userId = savedUser.getUserId();
        
        return savedUserDto;
    }
    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<CustomerDTO> customers() {

        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return bankAccountService.searchCustomers("%" + keyword + "%");
    }



    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

//    @PostMapping("/customers")
//    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
//    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
//        return bankAccountService.saveCustomer(customerDTO);
//    }

    @PutMapping("/customer/{customerId}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        System.out.println("HERE"+customerDTO);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }



}
