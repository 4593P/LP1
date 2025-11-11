import java.util.*;

public class TinyPassOneAssembler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of lines in program:");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        String[] program = new String[n];
        System.out.println("Enter program lines:");
        for (int i = 0; i < n; i++) {
            program[i] = sc.nextLine();
        }

        // Simple opcode table
        Map<String, String> MOT = new HashMap<>();
        MOT.put("START", "AD");
        MOT.put("END", "AD");
        MOT.put("MOVER", "IS");
        MOT.put("ADD", "IS");
        MOT.put("MOVEM", "IS");
        MOT.put("DC", "DL");
        MOT.put("DS", "DL");

        int LC = 0; // Location Counter
        Map<String, Integer> symbolTable = new LinkedHashMap<>();

        System.out.println("\n----- INTERMEDIATE CODE -----");

        for (String line : program) {
            String[] parts = line.split("[ ,]+");

            String label = null;
            String opcode;
            String operand1 = null;
            String operand2 = null;

            // Check if first part is a label
            if (MOT.containsKey(parts[0])) {
                opcode = parts[0];
                if (parts.length > 1) operand1 = parts[1];
                if (parts.length > 2) operand2 = parts[2];
            } else {
                // First part is a label
                label = parts[0];
                opcode = parts[1];
                if (parts.length > 2) operand1 = parts[2];
                if (parts.length > 3) operand2 = parts[3];

                // Add label to symbol table for DC/DS
                if (opcode.equals("DC") || opcode.equals("DS")) {
                    symbolTable.put(label, LC);
                }
            }

            // START directive
            if (opcode.equals("START")) {
                LC = Integer.parseInt(operand1);
                System.out.println("(AD,01)\t(C," + LC + ")");
            }
            // END directive
            else if (opcode.equals("END")) {
                System.out.println("(AD,02)");
                break;
            }
            // DC directive
            else if (opcode.equals("DC")) {
                System.out.println("(DL,01)\t(C," + operand1 + ")");
                LC++;
            }
            // DS directive
            else if (opcode.equals("DS")) {
                System.out.println("(DL,02)\t(C," + operand1 + ")");
                LC += Integer.parseInt(operand1);
            }
            // Imperative statements
            else if (MOT.containsKey(opcode)) {
                System.out.println("(" + MOT.get(opcode) + ")\t" + operand1 + ", " + operand2);
                LC++;
            }
        }

        // Print symbol table
        System.out.println("\n----- SYMBOL TABLE -----");
        System.out.println("Symbol\tAddress");
        for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }
}

// input code
// START 200
// LOOP MOVER AREG, X
// ADD BREG, Y
// SUB CREG, Z
// X DC 10
// Y DC 20
// Z DC 30
// TEMP DS 2
// END

// output

// ----- INTERMEDIATE CODE -----
// (AD,01) (C,200)
// (IS)    AREG, X
// (IS)    BREG, Y
// (DL,01) (C,10)
// (DL,01) (C,20)
// (DL,01) (C,30)
// (DL,02) (C,2)
// (AD,02)

// ----- SYMBOL TABLE -----
// Symbol  Address
// X       202
// Y       203
// Z       204
// TEMP    205
