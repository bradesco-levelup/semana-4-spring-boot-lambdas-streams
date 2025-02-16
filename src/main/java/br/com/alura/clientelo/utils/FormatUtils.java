package br.com.alura.clientelo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {

    public static String formataParaReal(BigDecimal valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                .format(valor.setScale(2, RoundingMode.HALF_EVEN));
    }
}
