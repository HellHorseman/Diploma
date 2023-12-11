package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class ExtendedAdDTO {
    private Integer id;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Integer price;
    private String title;
}
