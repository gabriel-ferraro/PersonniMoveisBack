package com.br.personniMoveis.utils;

import java.util.List;

/**
 * Classe para métodos de validação.
 */
public class ValidationUtils {

    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

}
