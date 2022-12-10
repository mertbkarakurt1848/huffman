import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// Huffman kodlamas�n� a�a� kullanmadan diziye kaydetmeyi sa�layan s�n�f
public class HuffmanArrayEncoding {
  // Verileri huffman kodlar�na g�re diziye kaydetmeyi sa�layan metod
  public static String[] encode(String data) {
    // Karakterleri ve bu karakterlerin s�kl�klar�n� tutan bir HashMap olu�turun
    Map<Character, Integer> charFrequencies = new HashMap<>();
    for (char c : data.toCharArray()) {
      charFrequencies.put(c, charFrequencies.getOrDefault(c, 0) + 1);
    }

    // S�kl�klar� k���kten b�y��e do�ru s�ralayan bir �ncelik kuyru�u olu�turun
    PriorityQueue<Node> queue = new PriorityQueue<>(
      (lhs, rhs) -> lhs.frequency - rhs.frequency
    );
    for (Map.Entry<Character, Integer> entry : charFrequencies.entrySet()) {
      queue.add(new Node(entry.getKey(), entry.getValue()));
    }

    // Huffman a�ac�n� olu�turun
    while (queue.size() > 1) {
      Node left = queue.poll();
      Node right = queue.poll();
      Node parent = new Node('\0', left.frequency + right.frequency, left, right);
      queue.add(parent);
    }

    // Huffman kodlar�n� diziye kaydetmeyi sa�layan bir dizi olu�turun
    String[] codes = new String[charFrequencies.size()];
    generateCodes(queue.poll(), "", codes);

    // Huffman kodlar�n� diziye kaydederek geri d�nd�r�n
    return codes;
  }

  // Huffman kodlar�n� diziye kaydetmeyi sa�layan metod
  private static void generateCodes(Node node, String code, String[] codes) {
    if (node.isLeaf()) {
      codes[node.data] = code;
      return;
    }

    generateCodes(node.left, code + "0", codes);
    generateCodes(node.right, code + "1", codes);
  }

  // Huffman a�ac�n�n d���mlerini temsil eden s�n�f
  private static class Node {
    char data;
    int frequency;
    Node left, right;

    Node(char data, int frequency) {
      this.data =