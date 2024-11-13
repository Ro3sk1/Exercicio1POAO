import java.util.Arrays;

/**
 * @author gui
 */

public class Exercicio1 {
    public static final String RESET = "\u001B[0m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";

    public static float[][] calcula_salario(String[] especialidades, String[] medicos) {
        float[][] ordenado = new float[especialidades.length][medicos.length+1];
        // Estrutura da tabela ordenado:[[Total Especialidade 1, Salário Médico 1 (se pertencer à especialidade 1, senão 0), Salário Médico 2 (se pertencer à especialidade 1, senão 0), ...],
        //                               [Total Especialidade 2, Salário Médico 1 (se pertencer à especialidade 2, senão 0), Salário Médico 2 (se pertencer à especialidade 2, senão 0), ...],
        //                               [...]]

        // Inicializa os valores da tabela
        for(int i = 0; i < especialidades.length; i++) {
            for(int j = 0; j < medicos.length + 1; j++) {
                ordenado[i][j] = 0;
            }
        }

        // Calcula os salários
        for (int i = 0; i < medicos.length; i++) {
            String[] dados_medicos = medicos[i].split("/");

            for (int x = 0; x < especialidades.length; x++) {
                // ordenado[x][i+1] = 0;
                String[] dados_especialidades = especialidades[x].split("/");

                if (dados_medicos[1].equals(dados_especialidades[0].toLowerCase())) {
                    float salario_base = Float.parseFloat(dados_especialidades[1]);
                    int anos_servico = Integer.parseInt(dados_medicos[2]);
                    int horas_extra = Integer.parseInt(dados_medicos[3]);
                    float custo_hora_extra = Float.parseFloat(dados_especialidades[2]);
                    float pagamento_horas_extra = horas_extra * custo_hora_extra;

                    salario_base = Math.round(salario_base * (float)Math.pow(1.04f, (int)(anos_servico / 5)) * 10) / 10.0f;

                    ordenado[x][i+1] = salario_base + pagamento_horas_extra;
                    ordenado[x][0] += ordenado[x][i+1];
                }
            }
        }
        return ordenado;
    }

    public static void main(String[] args) {
        String[] especialidades = {
                // nome/salário base/custo da hora extra
                "Radiologia/2030/50",
                "Oftalmologia/2500/70",
                "Pediatria/2700/75"
        };
        String[] medicos = {
                // nome/especialidade/anos de serviço/horas extra
                "Vasco Santana/radiologia/15/10",
                "Laura Alves/oftalmologia/5/7",
                "António Silva/oftalmologia/12/5",
        };

        float[][] salarios = calcula_salario(especialidades, medicos);

        System.out.println("Salários " + VERDE + "individuais" + RESET + ":");
        for (int x = 0; x < especialidades.length; x++) {
            for (int i = 1; i < medicos.length + 1; i++) {
                if (salarios[x][i] != 0) {
                    System.out.println("| " + medicos[i - 1].split("/")[0] + ": " + VERMELHO + salarios[x][i] + "€" + RESET);
                }
            }
        }

        System.out.println("\nTotal por " + VERDE + "especialidade" + RESET + ":");
        for (int i = 0; i < especialidades.length; i++) {
            if(salarios[i][0] != 0) {
                System.out.println("| " + especialidades[i].split("/")[0] + ": " + VERMELHO + salarios[i][0] + "€" + RESET);
            }
        }

    }
}