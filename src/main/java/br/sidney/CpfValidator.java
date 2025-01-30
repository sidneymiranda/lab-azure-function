package br.sidney;

public class CpfValidator {
    public static boolean isValidCPF(String cpf) {
        cpf = cpf != null ? cpf.replaceAll("[^0-9]", "") : cpf;

        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += (cpf.charAt(i) - '0') * (10 - i);
            }
            int firstCheck = 11 - (sum % 11);
            if (firstCheck >= 10) firstCheck = 0;
            if (firstCheck != (cpf.charAt(9) - '0')) {
                return false;
            }

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += (cpf.charAt(i) - '0') * (11 - i);
            }
            int secondCheck = 11 - (sum % 11);
            if (secondCheck >= 10) secondCheck = 0;

            return secondCheck == (cpf.charAt(10) - '0');
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
