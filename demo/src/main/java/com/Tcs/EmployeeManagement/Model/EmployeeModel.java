package com.Tcs.EmployeeManagement.Model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
@Builder
@Entity
@Table(name="employee_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeModel implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="jobtitle")
    private String jobtitle;

    @Column(name="department")
    private String department;

    @Column(name="phoneno")
    private String phoneno;


}
