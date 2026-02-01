package com.bluecollar.management.service;

import org.springframework.stereotype.Service;

import com.bluecollar.management.dto.CustomerProfileRequestDTO;
import com.bluecollar.management.dto.CustomerProfileResponseDTO;
import com.bluecollar.management.entity.Customer;
import com.bluecollar.management.entity.User;
import com.bluecollar.management.entity.enums.Role;
import com.bluecollar.management.repository.CustomerRepository;
import com.bluecollar.management.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerProfileService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public CustomerProfileService(UserRepository userRepository,
                                  CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    public CustomerProfileResponseDTO getProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("User is not a CUSTOMER");
        }

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Customer profile not found"));

        return mapToResponse(user, customer);
    }

    public CustomerProfileResponseDTO createProfile(
            Long userId,
            CustomerProfileRequestDTO request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only CUSTOMER can create profile");
        }

        if (customerRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("Customer profile already exists");
        }

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setPhone(request.getPhone());
        customer.setAddressLine1(request.getAddressLine1());
        customer.setAddressLine2(request.getAddressLine2());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setPincode(request.getPincode());

        customerRepository.save(customer);

        return mapToResponse(user, customer);
    }

    public CustomerProfileResponseDTO updateProfile(
            Long userId,
            CustomerProfileRequestDTO request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only CUSTOMER can update profile");
        }

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Customer profile not found"));

        customer.setPhone(request.getPhone());
        customer.setAddressLine1(request.getAddressLine1());
        customer.setAddressLine2(request.getAddressLine2());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setPincode(request.getPincode());

        customerRepository.save(customer);

        return mapToResponse(user, customer);
    }

    public void deleteProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Customer profile not found"));

        customerRepository.delete(customer);
    }

    private CustomerProfileResponseDTO mapToResponse(User user, Customer customer) {

        CustomerProfileResponseDTO dto = new CustomerProfileResponseDTO();
        dto.setUserId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddressLine1(customer.getAddressLine1());
        dto.setAddressLine2(customer.getAddressLine2());
        dto.setCity(customer.getCity());
        dto.setState(customer.getState());
        dto.setPincode(customer.getPincode());

        return dto;
    }
}
