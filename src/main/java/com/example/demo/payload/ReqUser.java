
package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqUser {
    private Integer id;

    private String username;
    private String password;

    public ReqUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
