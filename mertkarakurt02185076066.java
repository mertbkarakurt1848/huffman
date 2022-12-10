import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// Huffman kodlamasýný aðaç kullanmadan diziye kaydetmeyi saðlayan sýnýf
public class HuffmanArrayEncoding {
  // Verileri huffman kodlarýna göre diziye kaydetmeyi saðlayan metod
  public static String[] encode(String data) {
    // Karakterleri ve bu karakterlerin sýklýklarýný tutan bir HashMap oluþturun
    Map<Character, Integer> charFrequencies = new HashMap<>();
    for (char c : data.toCharArray()) {
      charFrequencies.put(c, charFrequencies.getOrDefault(c, 0) + 1);
    }

    // Sýklýklarý küçükten büyüðe doðru sýralayan bir öncelik kuyruðu oluþturun
    PriorityQueue<Node> queue = new PriorityQueue<>(
      (lhs, rhs) -> lhs.frequency - rhs.frequency
    );
    for (Map.Entry<Character, Integer> entry : charFrequencies.entrySet()) {
      queue.add(new Node(entry.getKey(), entry.getValue()));
    }

    // Huffman aðacýný oluþturun
    while (queue.size() > 1) {
      Node left = queue.poll();
      Node right = queue.poll();
      Node parent = new Node('\0', left.frequency + right.frequency, left, right);
      queue.add(parent);
    }

    // Huffman kodlarýný diziye kaydetmeyi saðlayan bir dizi oluþturun
    String[] codes = new String[charFrequencies.size()];
    generateCodes(queue.poll(), "", codes);

    // Huffman kodlarýný diziye kaydederek geri döndürün
    return codes;
  }

  // Huffman kodlarýný diziye kaydetmeyi saðlayan metod
  private static void generateCodes(Node node, String code, String[] codes) {
    if (node.isLeaf()) {
      codes[node.data] = code;
      return;
    }

    generateCodes(node.left, code + "0", codes);
    generateCodes(node.right, code + "1", codes);
  }

  // Huffman aðacýnýn düðümlerini temsil eden sýnýf
  private static class Node {
    char data;
    int frequency;
    Node left, right;

    Node(char data, int frequency) {
      this.data =