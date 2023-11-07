package Huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class huffmanTest {
    
    public static void main(String[] args) {
        String input = "Le cours d'algorithme et structure de données";
        Map<Character, String> huffmanCodes = buildHuffmanTree(input);
        String encodedString = encodeString(input, huffmanCodes);
        int asciiBits = input.length() * 8;
        int huffmanBits = encodedString.length();
        int gain = asciiBits - huffmanBits;

        System.out.println("Chaîne encodée en binaire : " + encodedString);
        System.out.println("Gain par rapport au codage ASCII : " + gain + " bits");
    }

    public static Map<Character, String> buildHuffmanTree(String input) {
        // Compter les fréquences d'apparition des caractères
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        // Créer une file de priorité pour les nœuds
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Construire l'arbre de Huffman
        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode parent = new HuffmanNode('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            priorityQueue.offer(parent);
        }

        // Générer les codes binaires de Huffman
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateHuffmanCodes(priorityQueue.peek(), "", huffmanCodes);
        return huffmanCodes;
    }

    public static void generateHuffmanCodes(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node != null) {
            if (node.character != '\0') {
                huffmanCodes.put(node.character, code);
            }
            generateHuffmanCodes(node.left, code + "0", huffmanCodes);
            generateHuffmanCodes(node.right, code + "1", huffmanCodes);
        }
    }

    public static String encodeString(String input, Map<Character, String> huffmanCodes) {
        StringBuilder encodedString = new StringBuilder();
        for (char c : input.toCharArray()) {
            encodedString.append(huffmanCodes.get(c));
        }
        return encodedString.toString();
    }

}
