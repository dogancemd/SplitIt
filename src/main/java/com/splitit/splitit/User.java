package com.splitit.splitit;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "user")
public class User {
    @Id
    public ObjectId userId;
    public List<ObjectId> groups;
    private HashMap<ObjectId,Long> to_give;
    private HashMap<ObjectId,Long> to_take;

    public void give_money(ObjectId taker,Long money){

    }

}
