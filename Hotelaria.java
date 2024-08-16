import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Classe Hospede para armazenas os detalhes de cada hóspede
class Hospede {
    String nome;
    String cpf;
    String telefone;
    String email;

    Hospede(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }
}

// Classe Quarto que representa os quartos do hotel
class Quarto {
    int numero;
    boolean ocupado;

    Quarto(int numero) {
        this.numero = numero;
        this.ocupado = false;
    }
}

// Classe Reserva para armazenar os detalhes de cada reserva
class Reserva {
    Hospede hospede;
    Quarto quarto;
    int dias;

    Reserva(Hospede hospede, Quarto quarto, int dias) {
        this.hospede = hospede;
        this.quarto = quarto;
        this.dias = dias;
    }
}

// Classe principal para gerenciar o hotel e a interface gráfica
public class Hotelaria extends JFrame {
    private JButton btnRealizarReserva;
    private JButton btnListarReservas;
    private JButton btnListarHospedesEQuartos;
    ArrayList<Hospede> hospedes = new ArrayList<Hospede>();
    ArrayList<Quarto> quartos = new ArrayList<Quarto>();
    ArrayList<Reserva> reservas = new ArrayList<Reserva>();
    final int LIMITE_QUARTOS = 200;

    Hotelaria() {
        setTitle("Gerenciamento de Hotel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        btnRealizarReserva = new JButton("Realizar Reserva");
        btnListarReservas = new JButton("Listar Reservas");
        btnListarHospedesEQuartos = new JButton("Listar Hóspedes e Quartos");

        add(btnRealizarReserva);
        add(btnListarReservas);
        add(btnListarHospedesEQuartos);

        btnRealizarReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarReserva();
            }
        });

        btnListarReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarReservas();
            }
        });

        btnListarHospedesEQuartos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarHospedesEQuartos();
            }
        });
    }
    private void realizarReserva() {
        // Variáveis para armazenar os dados preenchidos
        String nome = "";
        String cpf = "";
        String telefone = "";
        String email = "";
        String numeroQuartoStr = "";
        String diasStr = "";

        while (true) {
            // Definindo painel de formulário e layout
            JPanel formPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;

            // Campos de entrada de dados
            JTextField nomeField = new JTextField(15);
            JTextField cpfField = new JTextField(15);
            JTextField telefoneField = new JTextField(15);
            JTextField emailField = new JTextField(15);
            JTextField numeroQuartoField = new JTextField(5);
            JTextField diasField = new JTextField(5);

            // Preencher campos com valores salvos
            nomeField.setText(nome);
            cpfField.setText(cpf);
            telefoneField.setText(telefone);
            emailField.setText(email);
            numeroQuartoField.setText(numeroQuartoStr);
            diasField.setText(diasStr);

            // Adicionando campos ao painel de formulário
            gbc.gridx = 0;
            gbc.gridy = 0;
            formPanel.add(new JLabel("Nome do Hóspede:"), gbc);
            gbc.gridx = 1;
            formPanel.add(nomeField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            formPanel.add(new JLabel("CPF:"), gbc);
            gbc.gridx = 1;
            formPanel.add(cpfField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            formPanel.add(new JLabel("Telefone:"), gbc);
            gbc.gridx = 1;
            formPanel.add(telefoneField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            formPanel.add(new JLabel("Email:"), gbc);
            gbc.gridx = 1;
            formPanel.add(emailField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            formPanel.add(new JLabel("Número do Quarto:"), gbc);
            gbc.gridx = 1;
            formPanel.add(numeroQuartoField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            formPanel.add(new JLabel("Dias de Reserva:"), gbc);
            gbc.gridx = 1;
            formPanel.add(diasField, gbc);

            // Exibindo o formulário em um JOptionPane
            int result = JOptionPane.showConfirmDialog(null, formPanel, "Realizar Reserva", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                nome = nomeField.getText().trim();
                cpf = cpfField.getText().trim();
                telefone = telefoneField.getText().trim();
                email = emailField.getText().trim();
                numeroQuartoStr = numeroQuartoField.getText().trim();
                diasStr = diasField.getText().trim();

                // Verificação para garantir que nenhum campo esteja vazio
                if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || email.isEmpty() || numeroQuartoStr.isEmpty() || diasStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
                    continue; // Reabre o formulário para que o usuário corrija os dados
                }

                int numeroQuarto;
                int dias;

                try {
                    // Obtendo número do quarto e dias de reserva
                    numeroQuarto = Integer.parseInt(numeroQuartoStr);
                    dias = Integer.parseInt(diasStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Número de quarto ou dias inválido(s).");
                    continue; // Reabre o formulário para que o usuário corrija os dados
                }

                Hospede hospede = null;
                for (Hospede h : hospedes) {
                    if (h.cpf.equals(cpf)) {
                        hospede = h;
                        break;
                    }
                }

                if (hospede == null) {
                    hospede = new Hospede(nome, cpf, telefone, email);
                    hospedes.add(hospede);
                }

                // Estrutura de decisão para reservar o quarto
                String resultado = reservarQuarto(hospede, numeroQuarto, dias);
                if (resultado.equals("Quarto Ocupado")) {
                    JOptionPane.showMessageDialog(this, "Quarto Ocupado. Por favor, insira outro número de quarto.");
                    continue; // Reabre o formulário para que o usuário forneça um novo número de quarto
                } else {
                    JOptionPane.showMessageDialog(this, resultado);
                    break; // Sai do loop principal quando a reserva é realizada com sucesso
                }
            } else {
                break; // Sai do loop se o usuário cancelar a operação
            }
        }
    }

    private String reservarQuarto(Hospede hospede, int numeroQuarto, int dias) {
        // Verificado se o quarto já existe
        for (Quarto quarto : quartos) {
            if (quarto.numero == numeroQuarto) {
                if (!quarto.ocupado) {
                    quarto.ocupado = true;
                    Reserva reserva = new Reserva(hospede, quarto, dias);
                    reservas.add(reserva);
                    return "Reserva realizada com sucesso!";
                } else {
                    return "Quarto Ocupado";
                }
            }
        }

        // Criando novo quarto se não existir e ainda houver espaço no array
        if (quartos.size() < LIMITE_QUARTOS) {
            Quarto novoQuarto = new Quarto(numeroQuarto);
            novoQuarto.ocupado = true;
            quartos.add(novoQuarto);
            Reserva reserva = new Reserva(hospede, novoQuarto, dias);
            reservas.add(reserva);
            return "Reserva realizada com sucesso!";
        } else {
            return "Limite de Quartos Atingido";
        }
    }

    private void listarReservas() {
        StringBuilder sb = new StringBuilder("Reservas:\n");
        for (Reserva reserva : reservas) {
            sb.append("Quarto: ").append(reserva.quarto.numero).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void listarHospedesEQuartos() {
        StringBuilder sb = new StringBuilder("Hóspedes e Quartos:\n");
        for (Reserva reserva : reservas) {
            sb.append("Hóspede: ").append(reserva.hospede.nome)
                    .append(", Quarto: ").append(reserva.quarto.numero)
                    .append(", Dias: ").append(reserva.dias).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Hotelaria().setVisible(true);
            }
        });
    }
}
