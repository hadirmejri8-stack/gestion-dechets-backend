package org.municipalite.gestiondechets.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "zones")
public class Zone {
    @Id
    private String _id;
    private String nom;
    private String code;
    private String description;
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }
}