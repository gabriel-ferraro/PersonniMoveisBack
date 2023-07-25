package com.br.personniMoveis.constant;

public enum UserEntityRoleType {
    ADMIN,
    COLLABORATOR,
    USER;
    
    //Apagar código abaixo após conirmar viabilidade de usar anotação @Enumerated(EnumType.ORDINAL)
    //    ADMIN(0),
    //    COLLABORATOR(1),
    //    USER(2);
    //
    //    private int code;
    //
    //    private UserEntityRoleType(int code) {
    //        this.code = code;
    //    }
    //
    //    public int getCode() {
    //        return code;
    //    }
    //
    //    /**
    //     * Recebe um inteiro como índice do "papel" do usuário e retorna o valor equivalente:
    //     * 0 - ADMIN
    //     * 1 - COLLABORATOR
    //     * 2 - USER
    //     * Valor diferente - Exceção.
    //     * @param code Código para valor do papel do usuário
    //     * @return O valor do papel do usuário, se code não existe, joga exceção.
    //     */
    //    public static UserEntityRoleType valueOf(int code) {
    //        return Arrays.stream(UserEntityRoleType.values())
    //                .filter(value -> value.getCode() == code)
    //                .findFirst()
    //                .orElseThrow(() -> new IllegalArgumentException("Invalid integer passed for enum UserEntityRoleType"));
    //    }
}
