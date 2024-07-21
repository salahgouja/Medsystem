package com.salah.reception;

import com.salah.user.UserDTO;

public record ReceptionDTO(
        UserDTO user,
         Float salary
) {}
