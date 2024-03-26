package ru.netology.diploma.client;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClientFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String fileName;

    @Lob
    private byte[] fileContent;

    @ManyToOne
    private Client client;
}
