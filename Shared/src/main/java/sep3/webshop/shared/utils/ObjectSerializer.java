package sep3.webshop.shared.utils;

import java.io.*;
import java.util.List;

public class ObjectSerializer {
    public static <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return baos.toByteArray();
    }
    public static <T extends Serializable> T deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (T) ois.readObject();
    }
    public static <T> byte[] serialize(List<T> list) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(list);
        oos.close();
        return baos.toByteArray();
    }

}
