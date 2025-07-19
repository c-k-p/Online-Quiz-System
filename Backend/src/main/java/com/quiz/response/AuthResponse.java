package com.quiz.response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse 
{
    private String jwt;
    private String message;
    private boolean status;
    private Long id;          // User ID
    private String email;     // User email
    private String fullName;  // User full name
    private String role;      // User role
}