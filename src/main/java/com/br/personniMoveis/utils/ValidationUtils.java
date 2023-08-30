package com.br.personniMoveis.utils;

import java.util.List;

/**
 * Classe para métodos de validação.
 */
public class ValidationUtils {

    /**
     * Checa se a lista é vazia ou nula.
     *
     * @param list lista recebida.
     * @return true para lista vazia.
     */
    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Checa se a string é vazia ou composta só de espaços.
     *
     * @param value String recebida.
     * @return true para nula, vazia ou só de espaços.
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
