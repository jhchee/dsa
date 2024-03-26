package consistenthashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing {
    private final TreeMap<Long, String> ring = new TreeMap<>();
    private final MessageDigest md = MessageDigest.getInstance("MD5");
    private final int replicas;

    public ConsistentHashing(int replicas) throws NoSuchAlgorithmException {
        this.replicas = replicas;
    }

    // Hash function with minimal collision.
    private long generateHash(String key) {
        md.reset();
        md.update(key.getBytes());
        byte[] digest = md.digest();
        return ((long) (digest[3] & 0xFF) << 24) |
               ((long) (digest[2] & 0xFF) << 16) |
               ((long) (digest[1] & 0xFF) << 8) |
               ((long) (digest[0] & 0xFF));
    }

    public void addServer(String server) {
        for (int i=0; i<replicas; i++) {
            long hash = generateHash(server + "_" + i);
            ring.put(hash, server); // Each server is annotated by hash.
        }
    }

    public void removeServer(String server) {
        for (int i=0; i<replicas; i++) {
            long hash = generateHash(server + "_" + i);
            ring.remove(hash);
        }
    }

    public String getServer(String key) {
        if (ring.isEmpty()) {
            return null;
        }
        long hash = generateHash(key);

        // Find the next closest server.
        if (!ring.containsKey(hash)) {
            // It can head or tail biased, in this case, we are going for tail biased.
            SortedMap<Long, String> tailMap = ring.tailMap(hash);
            hash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        }
        return ring.get(hash);
    }

    public Collection<String> listServers() {
        // Remove virtual nodes
        return new HashSet<>(ring.values());
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        ConsistentHashing ch = new ConsistentHashing(3);
        ch.addServer("Server1");
        ch.addServer("Server2");
        ch.addServer("Server3");

        System.out.println("key1: is present on server: " + ch.getServer("key1"));
        System.out.println("key67890: is present on server: " + ch.getServer("key67890"));
        System.out.println("All servers: " + ch.listServers());

        ch.removeServer("Server1");
        System.out.println("key1: is present on server: " + ch.getServer("key1"));
        System.out.println("key67890: is present on server: " + ch.getServer("key67890"));
        System.out.println("All servers: " + ch.listServers());
    }
}
